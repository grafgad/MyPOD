package com.example.mypod.recycler.myrecycler
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.mypod.R
//import com.example.mypod.recycler.BaseViewHolder
//import com.example.mypod.recycler.RecyclerActivity
//import com.google.android.material.textfield.TextInputEditText
//
//class RecyclerNoticeActivityAdapter(
//    private var notices: List<String>
//) :
//    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var title: TextInputEditText? = null
//        var description: TextInputEditText? = null
//
//        init {
//            title = itemView.findViewById(R.id.noticeTitle)
//            description = itemView.findViewById(R.id.noticeDescription)
//        }
//
//    }
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): MyViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//            .inflate(R.layout.activity_recycler_item_notice, parent, false)
//        return MyViewHolder(inflater)
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//
//        when (getItemViewType(position)) {
//            TYPE_NOTICE -> {
//                holder as
//                holder.bind(data[position])
//            }
//
//            else -> {
//                holder as HeaderViewHolder
//                holder.bind(data[position])
//            }
//        }
//
//
//    }
//
//    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {
//        override fun bind(dataItem: Pair<RecyclerActivity.Data, Boolean>) {
//            itemView.setOnClickListener {
//                    onListItemClickListener.onItemClick(data.first)
////                data[1] = Pair(RecyclerActivity.Data(1, "Jupiter", ""), false)
////                notifyItemChanged(1, Pair(RecyclerActivity.Data(1, "", ""), false))
//            }
//        }
//    }
//
//    interface OnListItemClickListener {
//        fun onItemClick(data: RecyclerNoticeActivity.Data)
//    }
//    override fun getItemCount(): Int {
//        return notices.size
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return when {
//            position == 0 -> TYPE_HEADER
//            else -> TYPE_NOTICE
//        }
//    }
//
//    companion object {
//        private const val TYPE_NOTICE = 0
//        private const val TYPE_HEADER = 1
//    }
//}
//
//
