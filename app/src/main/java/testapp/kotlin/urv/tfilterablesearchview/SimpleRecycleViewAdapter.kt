package testapp.kotlin.urv.tfilterablesearchview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_layout.view.*

class SimpleRecycleViewAdapter(private val context : Context,
                               private val posts : List<Post>) :
    RecyclerView.Adapter<SimpleRecycleViewAdapter.PostHolder>(){

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : PostHolder {

        return PostHolder(LayoutInflater.from(context)
            .inflate(R.layout.item_layout, parent, false))
    }

    override fun onBindViewHolder(holder : PostHolder, position : Int) {

        holder.bind(posts[position])
    }

    override fun getItemCount() = posts.size

    class PostHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bind(post: Post) {
            itemView.tv_title.text = post.title
            itemView.tv_content.text = post.content
        }
    }
}