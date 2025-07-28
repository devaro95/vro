package com.vro.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

abstract class VROUseCase<I, O> {
    suspend fun execute(input: I): O = withContext(dispatcher) { useCaseFunction(input) }

    protected abstract suspend fun useCaseFunction(input: I): O

    protected open val dispatcher: CoroutineDispatcher = Dispatchers.IO
}