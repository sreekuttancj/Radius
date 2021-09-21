package com.radius.domain.util

sealed class ExecutionResult<T>(private val data: T? = null,
                                private val error: BaseError? = null,
                                private val progress: Boolean? = null
) {
    private data class Success<T>(val data: T): ExecutionResult<T>(data = data)
    private data class Error<T>(val error: BaseError, val oldData: T? = null): ExecutionResult<T>(data = oldData, error = error)
    private data class Progress<T>(val progress: Boolean, val oldData: T? = null): ExecutionResult<T>(data = oldData, progress = progress)

    companion object{
        fun <T> success(data: T): ExecutionResult<T> = Success(data = data)
        fun <T> error(error: BaseError, oldData: T? = null): ExecutionResult<T> = Error(oldData = oldData, error = error)
        fun <T> progress(progress: Boolean, oldData: T? = null): ExecutionResult<T> = Progress(progress = progress, oldData = oldData)

        fun <T> dispatchProgress (progress: Boolean,
                                  data: T? = null,
                                  onSuccess: (data: T)  -> Unit,
                                  onError: (error: BaseError, oldData: T?) -> Unit,
                                  onProgress: (progress: Boolean, oldData: T?) -> Unit){
            progress(progress, data)
                .result(onError, onProgress, onSuccess)
        }

        fun <T> dispatchError (error: BaseError,
                               data: T? = null,
                               onSuccess: (data: T)  -> Unit,
                               onError: (error: BaseError, oldData: T?) -> Unit,
                               onProgress: (progress: Boolean, oldData: T?) -> Unit){
            error(error, data)
                .result(onError, onProgress, onSuccess)
        }
    }

    fun result(onError: (error: BaseError, oldData: T?) -> Unit,
               onProgress: (progress: Boolean, oldData: T?) -> Unit,
               onSuccess: (data: T)  -> Unit){
        when(this){
            is Success -> onSuccess(data)
            is Error -> onError(error, oldData)
            is Progress -> onProgress(progress, oldData)
        }
    }



}