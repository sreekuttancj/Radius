package com.radius.domain.util

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver


abstract class InteractorService<PARAM, DATA>(open val executionThread: ExecutionThread) {

    open fun getSubscriberThread() = executionThread.io
    open fun getObserverThread() = executionThread.main

    private val compositeDisposable = CompositeDisposable()


    fun execute(param: PARAM,
                onError: (error: BaseError, oldData: DATA?) -> Unit = { _, _ -> },
                onProgress: (progress: Boolean, oldData: DATA?, ) -> Unit = {_, _ -> },
                onComplete: () -> Unit  = {},
                onSuccess: (data: DATA)  -> Unit
    ){

        val observer = object : DisposableObserver<ExecutionResult<DATA>>(){
            override fun onNext(data: ExecutionResult<DATA>) {
                data.result(onError, onProgress, onSuccess)
            }

            override fun onError(e: Throwable) {
                ExecutionResult.dispatchError(error = NetworkResponseError(throwable = e), onError = onError, onProgress = onProgress, onSuccess = onSuccess)
            }

            override fun onComplete() {
               onComplete()
            }

        }

        getObservable(param)
            .subscribeOn(getSubscriberThread())
            .observeOn(getObserverThread())
            .doOnComplete {
                compositeDisposable.remove(observer)
            }
            .subscribe(observer)
        if(!compositeDisposable.isDisposed){
            compositeDisposable.add(observer)
        }
    }


    fun getObserver(param: PARAM): Observable<ExecutionResult<DATA>> {
        return getObservable(param)
            .subscribeOn(getSubscriberThread())
            .observeOn(getObserverThread())
    }


    fun dispose(){
        if(!compositeDisposable.isDisposed && compositeDisposable.size() > 0){
            compositeDisposable.dispose()
        }
    }

    abstract fun getObservable(param: PARAM): Observable<ExecutionResult<DATA>>

}