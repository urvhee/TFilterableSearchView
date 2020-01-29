package testapp.kotlin.urv.tfilterablesearchview

import androidx.recyclerview.widget.DiffUtil

class PostDiffUtilCallback(private val oldList : List<Post>,
                           private val newList : List<Post>) :
        DiffUtil.Callback() {

    override fun getOldListSize() : Int = oldList.size

    override fun getNewListSize() : Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) : Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) : Boolean = true
}