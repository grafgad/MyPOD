package com.example.mypod.recycler.myrecycler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.mypod.R
import kotlinx.android.synthetic.main.activity_recycler.*

class RecyclerNoticeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        val data = arrayListOf(
            Data("Earth"),
            Data("Earth"),
            Data("Mars", ""),
            Data("Earth"),
            Data("Earth"),
            Data("Earth"),
            Data("Mars", null)
        )
        data.add(0, Data("Header"))

        recyclerView.adapter = RecyclerNoticeActivityAdapter(
            object : RecyclerNoticeActivityAdapter.OnListItemClickListener {
                override fun onItemClick(data: Data) {
                    Toast.makeText(this@RecyclerNoticeActivity, data.someTitle, Toast.LENGTH_SHORT)
                        .show()
                }
            }, data
        )
    }


    class RecyclerNoticeActivityAdapter(
        private var onListItemClickListener: OnListItemClickListener,
        private var data: List<Data>
    ) :
        RecyclerView.Adapter<NoticeBaseViewHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): NoticeBaseViewHolder {
            val inflater = LayoutInflater.from(parent.context)
                    return when (viewType) {
                        TYPE_NOTICE -> NoticeViewHolder(
                            inflater.inflate(R.layout.activity_recycler_item_notice, parent, false)
                        )
                        else -> HeaderViewHolder(
                            inflater.inflate(R.layout.activity_recycler_item_header, parent, false)
                        )
                    }
        }

        override fun onBindViewHolder(holder: NoticeBaseViewHolder, position: Int) {
            holder.bind(data[position])
        }

        inner class NoticeViewHolder(view: View) : NoticeBaseViewHolder(view) {
            override fun bind(data: Data) {
                itemView.setOnClickListener {
                    onListItemClickListener.onItemClick(data)
//                data[1] = Pair(RecyclerActivity.Data(1, "Jupiter", ""), false)
//                notifyItemChanged(1, Pair(RecyclerActivity.Data(1, "", ""), false))
                }
            }


        }

        inner class HeaderViewHolder(view: View) : NoticeBaseViewHolder(view) {
            override fun bind(data: Data) {
                itemView.setOnClickListener {
                    onListItemClickListener.onItemClick(data)
//                data[1] = Pair(RecyclerActivity.Data(1, "Jupiter", ""), false)
//                notifyItemChanged(1, Pair(RecyclerActivity.Data(1, "", ""), false))
                }
            }
        }

        interface OnListItemClickListener {
            fun onItemClick(data: Data)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun getItemViewType(position: Int): Int {
            return when {
                position == 0 -> TYPE_HEADER
                else -> TYPE_NOTICE
            }
        }

        companion object {
            private const val TYPE_NOTICE = 0
            private const val TYPE_HEADER = 1
        }


    }

    data class Data(val someTitle: String = "Title", val someDescription: String? = "Description")


}