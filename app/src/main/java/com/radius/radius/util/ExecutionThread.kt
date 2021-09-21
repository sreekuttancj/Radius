package com.radius.radius.util


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

interface ExecutionThread{
    val io: Scheduler
    val computation: Scheduler
    val newThread: Scheduler
    val single: Scheduler
    val main: Scheduler
}
class AppExecutionThread @Inject constructor(): ExecutionThread {
    override val io = Schedulers.io()
    override val computation = Schedulers.io()
    override val newThread = Schedulers.newThread()
    override val single = Schedulers.single()
    override val main = AndroidSchedulers.mainThread()
}