package com.vro.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class VROConcurrencyManager : VROBaseConcurrencyManager {

    private val jobList: MutableList<Job> = mutableListOf()

    override fun launch(dispatcher: CoroutineDispatcher, fullException: Boolean, block: suspend CoroutineScope.() -> Unit): Job {
        val job = if (fullException) Job() else SupervisorJob()
        val scope = CoroutineScope(dispatcher + job)
        synchronized(jobList) {
            jobList.add(job)
            job.invokeOnCompletion {
                synchronized(jobList) {
                    jobList.remove(job)
                }
            }
        }
        scope.launch { block.invoke(this) }
        return job
    }

    override fun cancelPendingTasks() {
        synchronized(jobList) {
            val jobPending = mutableListOf<Job>()
            jobPending.addAll(jobList)
            jobPending.forEach { if (it.isActive) it.cancel() }
            jobList.clear()
        }
    }

    override fun cancelTask(job: Job) {
        synchronized(jobList) {
            if (jobList.remove(job)) {
                job.cancel()
            }
        }
    }
}