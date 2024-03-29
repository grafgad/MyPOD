package com.example.mypod.recycler

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mypod.R
import kotlinx.android.synthetic.main.activity_recycler.*
import kotlinx.android.synthetic.main.activity_recycler_item_earth.view.*
import kotlinx.android.synthetic.main.activity_recycler_item_mars.view.*

class RecyclerActivity : AppCompatActivity() {

    private var isNewList = false
    private lateinit var itemTouchHelper: ItemTouchHelper
    private lateinit var adapter: RecyclerActivityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        val data = arrayListOf(
            Pair(Data(1,"Mars", ""), false)
        )
        data.add(0,Pair(Data(0,"Header"), false))

        adapter = RecyclerActivityAdapter(
            object : RecyclerActivityAdapter.OnListItemClickListener {
                override fun onItemClick(data: Data) {
                    Toast.makeText(this@RecyclerActivity, data.someText, Toast.LENGTH_SHORT).show()
                }
            },
            data,
            object : RecyclerActivityAdapter.OnStartDragListener{
                override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                    itemTouchHelper.startDrag(viewHolder)
                }
            }
        )

        recyclerView.adapter = adapter
        recyclerActivityFAB.setOnClickListener { adapter.appendItem() }
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)
        recyclerActivityFAB.setOnClickListener { changeAdapterData() }
        changeAdapterData()
    }

    private fun changeAdapterData() {
        adapter.setItems(createItemList(isNewList).map { it })
        isNewList = !isNewList
    }

    private fun createItemList(instanceNumber: Boolean): List<Pair<Data, Boolean>> {
        return when (instanceNumber) {
            false -> listOf(
                Pair(Data(0, "Header"), false),
                Pair(Data(1, "Mars", ""), false),
                Pair(Data(2, "Mars", ""), false),
                Pair(Data(3, "Mars", ""), false),
                Pair(Data(4, "Mars", ""), false),
                Pair(Data(5, "Mars", ""), false),
                Pair(Data(6, "Mars", ""), false)
            )
            true -> listOf(
                Pair(Data(0, "Header"), false),
                Pair(Data(1, "Mars", ""), false),
                Pair(Data(2, "Jupiter", ""), false),
                Pair(Data(3, "Mars", ""), false),
                Pair(Data(4, "Neptune", ""), false),
                Pair(Data(5, "Saturn", ""), false),
                Pair(Data(6, "Mars", ""), false)
            )
        }
    }

    class RecyclerActivityAdapter(
        private var onListItemClickListener: OnListItemClickListener,
        private var data: MutableList<Pair<Data, Boolean>>,
        private val dragListener: OnStartDragListener
    ) :
        RecyclerView.Adapter<BaseViewHolder>(), ItemTouchHelperAdapter {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): BaseViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return when (viewType) {
                TYPE_EARTH -> EarthViewHolder(
                    inflater.inflate(R.layout.activity_recycler_item_earth, parent, false) as View
                )

                TYPE_MARS -> MarsViewHolder(
                    inflater.inflate(R.layout.activity_recycler_item_mars, parent, false) as View
                )
                else -> HeaderViewHolder(
                    inflater.inflate(R.layout.activity_recycler_item_header, parent, false) as View
                )
            }
        }

//        override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
//            when (getItemViewType(position)) {
//                TYPE_EARTH -> {
//                holder as EarthViewHolder
//                holder.bind(data[position])
//            }
//                TYPE_MARS -> {
//                holder as MarsViewHolder
//                holder.bind(data[position])
//            }
//                else -> {
//                    holder as HeaderViewHolder
//                    holder.bind(data[position])
//                }
//            }
//        }


        override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
            holder.bind(data[position])
        }

        override fun onBindViewHolder(
            holder: BaseViewHolder,
            position: Int,
            payloads: MutableList<Any>
        ) {
            if (payloads.isEmpty())
                super.onBindViewHolder(holder, position, payloads)
            else {
                val combinedChange =
                    createCombinedPayload(payloads as List<Change<Pair<Data, Boolean>>>)
                val oldData = combinedChange.oldData
                val newData = combinedChange.newData

                if (newData.first.someText != oldData.first.someText) {
                    holder.itemView.marsTextView.text = newData.first.someText
                }
            }
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun getItemViewType(position: Int): Int {
            return when {
                position == 0 -> TYPE_HEADER
                data[position].first.someDescription.isNullOrBlank() -> TYPE_MARS
                else -> TYPE_EARTH
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

        fun setItems(newItems: List<Pair<Data, Boolean>>) {
            val result = DiffUtil.calculateDiff(DiffUtilCallback(data, newItems))
            result.dispatchUpdatesTo(this)
            data.clear()
            data.addAll(newItems)
        }

        fun appendItem() {
            data.add(generateItem())
            notifyDataSetChanged()
        }

        private fun generateItem(): Pair<Data, Boolean> = Pair(Data(1,"My new notice", ""),false)

        inner class DiffUtilCallback(
            private var oldItems: List<Pair<Data, Boolean>>,
            private var newItems: List<Pair<Data, Boolean>>
        ) : DiffUtil.Callback() {

            override fun getOldListSize(): Int = oldItems.size

            override fun getNewListSize(): Int = newItems.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldItems[oldItemPosition].first.id == newItems[newItemPosition].first.id

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldItems[oldItemPosition].first.someText == newItems[newItemPosition].first.someText

            override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
                val oldItem = oldItems[oldItemPosition]
                val newItem = newItems[newItemPosition]

                return Change(
                    oldItem,
                    newItem
                )
            }
        }

        inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {
            override fun bind(dataItem: Pair<Data, Boolean>) {
                itemView.setOnClickListener {
//                    onListItemClickListener.onItemClick(data.first)
                    data[1] = Pair(Data(1,"Jupiter", ""), false)
                    notifyItemChanged(1, Pair(Data(1,"", ""), false))
                }
            }
        }

        inner class EarthViewHolder(view: View) : BaseViewHolder(view) {

            override fun bind(data: Pair<Data, Boolean>) {
                if (layoutPosition != RecyclerView.NO_POSITION) {
                    itemView.descriptionTextView.text = data.first.someDescription
                    itemView.wikiImageView.setOnClickListener {
                        onListItemClickListener.onItemClick(
                            data.first
                        )
                    }
                }
            }
        }


        inner class MarsViewHolder(view: View) : BaseViewHolder(view), ItemTouchHelperViewHolder {

            override fun bind(data: Pair<Data, Boolean>) {
                itemView.marsImageView.setOnClickListener { onListItemClickListener.onItemClick(data.first) }
                itemView.addItemImageView.setOnClickListener { addItem() }
                itemView.removeItemImageView.setOnClickListener { removeItem() }
                itemView.moveItemDown.setOnClickListener { moveDown() }
                itemView.moveItemUp.setOnClickListener { moveUp() }
                itemView.marsDescriptionTextView.visibility =
                    if (data.second) View.VISIBLE else View.GONE
                itemView.marsTextView.setOnClickListener { toggleText() }
                itemView.dragHandleImageView.setOnTouchListener { _, event ->
                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                        dragListener.onStartDrag(this)
                    }
                    false
                }

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

            fun appendItem() {
                data.add(generateItem())
                notifyItemInserted(itemCount - 1)
            }


        }


        interface OnListItemClickListener {
            fun onItemClick(data: Data)
        }

        interface OnStartDragListener {
            fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
        }

        companion object {
            private const val TYPE_EARTH = 0
            private const val TYPE_MARS = 1
            private const val TYPE_HEADER = 2
        }




    }

    data class Data(
        val id: Int = 0,
        val someText: String = "Text",
        val someDescription: String? = "Description"
    )



    interface ItemTouchHelperAdapter {
        fun onItemMove(fromPosition: Int, toPosition: Int)

        fun onItemDismiss(position: Int)
    }

    interface ItemTouchHelperViewHolder {

        fun onItemSelected()

        fun onItemClear()
    }

    class ItemTouchHelperCallback(private val adapter: RecyclerActivityAdapter) :
        ItemTouchHelper.Callback() {

        override fun isLongPressDragEnabled(): Boolean {
            return true
        }

        override fun isItemViewSwipeEnabled(): Boolean {
            return true
        }

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
            return makeMovementFlags(
                dragFlags,
                swipeFlags
            )
        }

        override fun onMove(
            recyclerView: RecyclerView,
            source: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            adapter.onItemMove(source.adapterPosition, target.adapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
            adapter.onItemDismiss(viewHolder.adapterPosition)
        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                val itemViewHolder = viewHolder as ItemTouchHelperViewHolder
                itemViewHolder.onItemSelected()
            }
            super.onSelectedChanged(viewHolder, actionState)
        }

        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            super.clearView(recyclerView, viewHolder)
            val itemViewHolder = viewHolder as ItemTouchHelperViewHolder
            itemViewHolder.onItemClear()
        }
    }

}