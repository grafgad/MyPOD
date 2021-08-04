package com.example.mypod.recycler.myrecycler

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
import com.example.mypod.recycler.RecyclerActivity
import kotlinx.android.synthetic.main.activity_recycler.*
import kotlinx.android.synthetic.main.activity_recycler_item_notice.view.*

class NoticeRecyclerActivity : AppCompatActivity() {

    private var isNewList = false
    private lateinit var itemTouchHelper: ItemTouchHelper
    private lateinit var adapter: NoticeRecyclerActivityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        val data = arrayListOf(
            Pair(Data(1, "Title", ""), false)
        )
        data.add(0, Pair(Data(0, "Header", ""), false))

        adapter = NoticeRecyclerActivityAdapter(
            object : NoticeRecyclerActivityAdapter.OnListItemClickListener {
                override fun onItemClick(data: Data) {
                    Toast.makeText(this@NoticeRecyclerActivity, data.someTitle, Toast.LENGTH_SHORT).show()
                }
            },
            data,
            object : NoticeRecyclerActivityAdapter.OnStartDragListener {
                override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                    itemTouchHelper?.startDrag(viewHolder)
                }
            }
        )

        recyclerView.adapter = adapter
        recyclerActivityFAB.setOnClickListener { adapter.appendItem() }
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        recyclerActivityFAB.setOnClickListener { changeAdapterData() }
//        changeAdapterData()
    }

    private fun changeAdapterData() {
        adapter.setItems(createItemList(isNewList).map { it })
        isNewList = !isNewList
    }

    private fun createItemList(instanceNumber: Boolean): List<Pair<Data, Boolean>> {
        return when (instanceNumber) {
            false -> listOf(
                Pair(Data(0, "Header"), false),
                Pair(Data(1, "My Title1", ""), false),
                Pair(Data(2, "My Title2", ""), false)
            )
            true -> listOf(
                Pair(Data(0, "Header"), false),
                Pair(Data(1, "My Title1", ""), false),
                Pair(Data(2, "My Title2", ""), false),
                Pair(Data(3, "My Title3", ""), false)
            )
        }
    }


    data class Data(
        val id: Int = 0,
        val someTitle: String = "Title",
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



    class ItemTouchHelperCallback(private val adapter: NoticeRecyclerActivityAdapter) :
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
            val itemViewHolder = viewHolder as RecyclerActivity.ItemTouchHelperViewHolder
            itemViewHolder.onItemClear()
        }
    }

}