package com.radius.radius.util

abstract class BaseError(
    open val throwable: Throwable? = null,
    override val message: String? = null,
): Throwable(message = message, cause = throwable)

data class NetworkConnectionError(
    override val throwable: Throwable? = null,
    override val message: String? = throwable?.message,
    val isRetryEnabled: Boolean = true
): BaseError(message = message, throwable = throwable)

data class NetworkResponseError(
    override val throwable: Throwable? = null,
    override val message: String? = throwable?.message,
    val httpStatusCode: Int = -1,
    val remoteStatusCode: Int = -1,
    val remoteMessage: String = ""
): BaseError(message = message, throwable = throwable)


data class ExecutionError(
    override val throwable: Throwable? = null,
    override val message: String? = throwable?.message,
): BaseError(message = message, throwable = throwable)

