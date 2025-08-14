package com.vro.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class VROConcurrencyManager : VROBaseConcurrencyManager {
    private val mutex = Mutex()
    private val jobList = mutableListOf<Job>()

    override fun launch(dispatcher: CoroutineDispatcher, fullException: Boolean, block: suspend CoroutineScope.() -> Unit): Job {
        val job = if (fullException) Job() else SupervisorJob()
        val scope = CoroutineScope(dispatcher + job)

        scope.launch {
            mutex.withLock {
                jobList.add(job)
            }

            try {
                block.invoke(this)
            } finally {
                mutex.withLock {
                    jobList.remove(job)
                }
            }
        }

        return job
    }

    override suspend fun cancelPendingTasks() {
        mutex.withLock {
            jobList.toList().forEach { it.cancel() }
            jobList.clear()
        }
    }

    override suspend fun cancelTask(job: Job) {
        mutex.withLock {
            if (jobList.remove(job)) {
                job.cancel()
            }
        }
    }
}