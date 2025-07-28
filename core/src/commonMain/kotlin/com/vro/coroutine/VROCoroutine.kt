package com.vro.coroutine

import com.vro.usecase.MainUseCaseResult
import kotlinx.coroutines.CoroutineScope

fun <T> executeCoroutine(
    fullException: Boolean = false,
    action: suspend CoroutineScope.() -> T,
): MainUseCaseResult<T> {
    return MainUseCaseResult(VROConcurrencyManager(), fullException, action)
}