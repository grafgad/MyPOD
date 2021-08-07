package com.example.mypod.recycler.myrecycler

import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mypod.R
import kotlinx.android.synthetic.main.activity_recycler_item_notice.view.*

class NoticeRecyclerActivityAdapter(
    private var onListItemClickListener: OnListItemClickListener,
    private var data: MutableList<Pair<NoticeRecyclerActivity.Data, Boolean>>,
    private val dragListener: OnStartDragListener
) :
    RecyclerView.Adapter<NoticeBaseViewHolder>(), NoticeRecyclerActivity.ItemTouchHelperAdapter {

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

    override fun onBindViewHolder(
        holder: NoticeBaseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty())
            super.onBindViewHolder(holder, position, payloads)
        else {
            val combinedChange =
                createCombinedPayloads(payloads as List<NoticeChange<Pair<NoticeRecyclerActivity.Data, Boolean>>>)
            val oldData = combinedChange.oldData
            val newData = combinedChange.newData

            if (newData.first.someTitle != oldData.first.someTitle) {
                holder.itemView.noticeTitle.text = newData.first.someTitle
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_HEADER
            else -> TYPE_NOTICE
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        data.removeAt(fromPosition).apply {
            data.add(if (toPosition > fromPosition) toPosition - 1 else toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    fun setItems(newItems: List<Pair<NoticeRecyclerActivity.Data, Boolean>>) {
        val result = DiffUtil.calculateDiff(DiffUtilCallback(data, newItems))
        result.dispatchUpdatesTo(this)
        data.clear()
        data.addAll(newItems)
    }

    fun appendItem() {
        data.add(generateItem())
        notifyDataSetChanged()
    }

    private fun generateItem(): Pair<NoticeRecyclerActivity.Data, Boolean> = Pair(
        NoticeRecyclerActivity.Data(1, "New notice", ""), false
    )

    inner class DiffUtilCallback(
        private var oldItems: List<Pair<NoticeRecyclerActivity.Data, Boolean>>,
        private var newItems: List<Pair<NoticeRecyclerActivity.Data, Boolean>>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldItems.size
        }

        override fun getNewListSize(): Int {
            return newItems.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition].first.id == newItems[newItemPosition].first.id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition].first.someTitle == newItems[newItemPosition].first.someTitle
        }

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]

            return NoticeChange(
                oldItem, newItem
            )
        }
    }

    inner class HeaderViewHolder(view: View) : NoticeBaseViewHolder(view) {
        override fun bind(dataItem: Pair<NoticeRecyclerActivity.Data, Boolean>) {
            itemView.setOnClickListener {
                onListItemClickListener.onItemClick(dataItem.first)
//                data[1] = Pair(RecyclerActivity.Data(1, "Jupiter", ""), false)
//                notifyItemChanged(1, Pair(RecyclerActivity.Data(1, "", ""), false))
            }
        }
    }

    inner class NoticeViewHolder(view: View) : NoticeBaseViewHolder(view),
        NoticeRecyclerActivity.ItemTouchHelperViewHolder {

        override fun bind(data: Pair<NoticeRecyclerActivity.Data, Boolean>) {
            itemView.noticeImage.setOnClickListener { onListItemClickListener.onItemClick(data.first) }
            itemView.addItemImageView.setOnClickListener { addItem() }
            itemView.removeItemImageView.setOnClickListener { removeItem() }
            itemView.moveItemDown.setOnClickListener { moveDown() }
            itemView.moveItemUp.setOnClickListener { moveUp() }
            itemView.noticeDescription.visibility =
                if (data.second) View.VISIBLE else View.GONE
            itemView.noticeTitle.setOnClickListener { toggleText() }
//            itemView.dragHandleImageView.setOnTouchListener { _, event ->
//                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
//                    dragListener.onStartDrag(this)
//                }
//                false
//            }
        }

        private fun addItem() {
            data.add(layoutPosition, generateItem())
            notifyItemInserted(layoutPosition)
        }

        private fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        private fun moveUp() {
            layoutPosition.takeIf { it > 1 }?.also { currentPosition ->
                data.removeAt(currentPosition).apply {
                    data.add(currentPosition - 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition - 1)
            }
        }

        private fun moveDown() {
            layoutPosition.takeIf { it < data.size - 1 }?.also { currentPosition ->
                data.removeAt(currentPosition).apply {
                    data.add(currentPosition + 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition + 1)
            }
        }

        private fun toggleText() {
            data[layoutPosition] = data[layoutPosition].let {
                it.first to !it.second
            }
            notifyItemChanged(layoutPosition)
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(Color.WHITE)
        }
    }

    interface OnListItemClickListener {
        fun onItemClick(data: NoticeRecyclerActivity.Data)
    }

    interface OnStartDragListener {
        fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
    }

    companion object {
        private const val TYPE_NOTICE = 0
        private const val TYPE_HEADER = 1
    }
}