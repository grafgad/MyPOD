package com.example.mypod.recycler.myrecycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class NoticeBaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(data: RecyclerNoticeActivity.Data)
}