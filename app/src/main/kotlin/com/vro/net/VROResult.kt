package com.vro.net

sealed interface VROResult<T, E> {
    data class Success<T, E>(val data: T) : VROResult<T, E>
    data class Failure<T, E>(val failure: E) : VROResult<T, E>

    companion object {
        fun <T, E> failure(error: E): VROResult<T, E> = Failure(error)
        fun <T, E> success(data: T): VROResult<T, E> = Success(data)
    }

    fun getFailureOrNull(): E? {
        return if (this is Failure) {
            failure
        } else
            null
    }

    fun getOrNull(): T? {
        return if (this is Success) {
            data
        } else
            null
    }

    fun getOrThrow(): T {
        return when (this) {
            is Failure -> throw (failure as? Throwable) ?: IllegalArgumentException(failure.toString())
            is Success -> data
        }
    }

    fun getOrDefault(default: T): T {
        return if (this is Success) {
            data
        } else
            default
    }

    val isFailure: Boolean
        get() {
            return this is Failure<*, *>
        }

    val isSuccess: Boolean
        get() {
            return this is Success<*, *>
        }
}

inline fun <T, E, R> VROResult<T, E>.map(successMap: (T) -> R): VROResult<R, E> {
    return when (this) {
        is VROResult.Failure -> VROResult.failure(this.failure)
        is VROResult.Success -> VROResult.Success(successMap.invoke(data))
    }
}

inline fun <T, E, R> VROResult<T, E>.flatMap(successMap: (T) -> VROResult<R, E>): VROResult<R, E> {
    return when (this) {
        is VROResult.Failure -> {
            VROResult.failure(this.failure)
        }
        is VROResult.Success -> {
            successMap.invoke(this.data)
        }
    }
}

inline fun <T, E, R, ER> VROResult<T, E>.mapResult(resultMap: (VROResult<T, E>) -> VROResult<R, ER>): VROResult<R, ER> {
    return resultMap.invoke(this)
}

inline fun <T, E, ER> VROResult<T, E>.flatMapError(errorMap: (E) -> VROResult<T, ER>): VROResult<T, ER> {
    return when (this) {
        is VROResult.Failure -> {
            errorMap.invoke(this.failure)
        }
        is VROResult.Success -> {
            VROResult.success(this.data)
        }
    }
}


inline fun <T, E> VROResult<T, E>.onSuccess(success: (T) -> Unit): VROResult<T, E> {
    if (this is VROResult.Success) {
        success.invoke(data)
    }
    return this
}

inline fun <T, E> VROResult<T, E>.onFailure(error: (E) -> Unit): VROResult<T, E> {
    if (this is VROResult.Failure) {
        error.invoke(this.failure)
    }
    return this
}

inline fun <T, E, R> VROResult<T, E>.mapError(errorMap: (E) -> R): VROResult<T, R> {
    return when (this) {
        is VROResult.Failure -> VROResult.Failure(errorMap.invoke(failure))
        is VROResult.Success -> VROResult.Success(data)
    }
}
