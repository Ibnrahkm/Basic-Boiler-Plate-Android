package com.ibt.ava.util

import com.ibt.ava.service.model.ErrorModel

/**
 * Global Response class
 */
class Resource constructor(val status: Resource.Status, val data: Any?, val error: ErrorModel?) {

    enum class Status {
        LOADING, SUCCESS, ERROR
    }

    companion object {
        /**
         * success response
         */
        fun <T> success(data: T?): Resource {
            return Resource(Status.SUCCESS, data, null)
        }
        /**
         * data loading
         */
        fun <T> loading(data: T?): Resource {
            return Resource(Status.LOADING, data, null)
        }

        /**
         * error response
         */
        fun <T> error(error: ErrorModel): Resource {
            return Resource(Status.ERROR, null, error)
        }
    }

}