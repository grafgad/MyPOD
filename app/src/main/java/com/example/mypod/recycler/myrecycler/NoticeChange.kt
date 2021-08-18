package com.example.mypod.recycler.myrecycler

import com.example.mypod.recycler.Change

data class NoticeChange <out T> (
    val oldData: T,
    val newData: T
)

fun <T> createCombinedPayloads(payloads: List<NoticeChange<T>>): NoticeChange<T> {
    assert(payloads.isNotEmpty())
    val firstChange = payloads.first()
    val lastChange = payloads.last()

    return NoticeChange(firstChange.oldData, lastChange.newData)
}