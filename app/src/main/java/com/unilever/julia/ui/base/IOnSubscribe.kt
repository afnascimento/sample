package com.unilever.julia.ui.base

interface IOnSubscribe<T> {

    fun doOnSubscribe()

    fun onNext(value: T)

    fun onComplete()

    fun onError(e: Throwable)
}
