package com.radius.domain.util

interface BaseMapper<FROM, TO> {
    fun map(data: FROM?): TO?
}