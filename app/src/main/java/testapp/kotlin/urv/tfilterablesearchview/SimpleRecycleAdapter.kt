package testapp.kotlin.urv.tfilterablesearchview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_layout.view.*
import kotlinx.android.synthetic.main.item_monitor_story.view.*

class SimpleRecycleAdapter(private val context : Context,
                           private val posts : List<Post>) :
    RecyclerView.Adapter<SimpleRecycleAdapter.PostHolder>(), Filterable{

    private var fullList: MutableList<Post>? = null

    init {
        fullList = ArrayList<Post>(posts)
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : PostHolder {

        return PostHolder(LayoutInflater.from(context)
            .inflate(R.layout.item_monitor_story, parent, false))
    }

    override fun onBindViewHolder(holder : PostHolder, position : Int) {

        holder.bind(posts[position])
    }

    override fun getItemCount() = posts.size

    class PostHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bind(post: Post) {
            itemView.title.text = post.title
            itemView.description.text = post.content
        }
    }

    override fun getFilter(): Filter {
        return exampleFilter
    }

    private val exampleFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {

            val filteredList: MutableList<Post> = mutableListOf()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(posts)
            } else {
                val filterPattern : String = constraint.toString().toLowerCase().trim()

                for (item: Post in posts ) {
                    if (item.title.toLowerCase().contains(filterPattern) ||
                        item.content.toLowerCase().contains(filterPattern))
                    filteredList.add(item)
                }
            }

            val results = FilterResults()
            results.values = filteredList

            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            fullList?.clear()
            fullList?.addAll(results?.values!! as Collection<Post>)
            notifyDataSetChanged()
        }

    }
}