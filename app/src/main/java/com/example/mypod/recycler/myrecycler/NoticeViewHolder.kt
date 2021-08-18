package com.example.mypod.recycler
//
//import android.graphics.Color
//import android.view.MotionEvent
//import android.view.View
//import androidx.core.view.MotionEventCompat
//import com.example.mypod.recycler.myrecycler.NoticeBaseViewHolder
//import com.example.mypod.recycler.myrecycler.NoticeRecyclerActivity
//import com.example.mypod.recycler.myrecycler.NoticeRecyclerActivityAdapter
//import kotlinx.android.synthetic.main.activity_recycler_item_notice.view.*
//
//class NoticeViewHolder(view: View) : NoticeBaseViewHolder(view),
//    NoticeRecyclerActivity.ItemTouchHelperViewHolder {
//
//    lateinit var data: MutableList<Pair<NoticeRecyclerActivity.Data, Boolean>>
//
//    override fun bind(data: Pair<NoticeRecyclerActivity.Data, Boolean>) {
//        var onListItemClickListener: NoticeRecyclerActivityAdapter.OnListItemClickListener
//
//        val dragListener: NoticeRecyclerActivityAdapter.OnStartDragListener
//
//        itemView.noticeImage.setOnClickListener { onListItemClickListener.onItemClick(data.first) }
//        itemView.addItemImageView.setOnClickListener { addItem() }
//        itemView.removeItemImageView.setOnClickListener { removeItem() }
//        itemView.moveItemDown.setOnClickListener { moveDown() }
//        itemView.moveItemUp.setOnClickListener { moveUp() }
//        itemView.noticeDescription.visibility =
//            if (data.second) View.VISIBLE else View.GONE
//        itemView.noticeTitle.setOnClickListener { toggleText() }
//        itemView.dragHandleImageView.setOnTouchListener { _, event ->
//            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
//                dragListener.onStartDrag(this)
//            }
//            false
//        }
////                data[1] = Pair(RecyclerActivity.Data(1, "Jupiter", ""), false)
////                notifyItemChanged(1, Pair(RecyclerActivity.Data(1, "", ""), false))
////            }
////            if (layoutPosition != RecyclerView.NO_POSITION)
////            {
////                itemView.noticeDescription.text = data.someDescription
////                itemView.wikiImageView.setOnClickListener {
////                    onListItemClickListener.onItemClick(
////                        data
////                    )
////                }
////            }
//
//    }
//}
//
//
//
//
//
//
