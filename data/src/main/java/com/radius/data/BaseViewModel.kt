package com.radius.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.radius.domain.util.BaseError
import com.radius.domain.util.BaseProgress

open class BaseViewModel: ViewModel() {
    protected val _showError = SingleLiveEvent<BaseError>()
    val showError: LiveData<BaseError> = _showError

    protected val _showActivityProgress = SingleLiveEvent<BaseProgress>()
    val showProgress: LiveData<BaseProgress> = _showActivityProgress

}