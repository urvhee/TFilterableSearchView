package testapp.kotlin.urv.tfilterablesearchview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private val disposable = CompositeDisposable()

    private var adapter: SimpleRecycleAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]

        val recyclerView : RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SimpleRecycleAdapter(this, viewModel.oldFilteredPosts)

        val searchInput : EditText = findViewById(R.id.ed_search)
        searchInput
            .textChanges()
            .debounce(200, TimeUnit.MILLISECONDS)
            .subscribe {
                viewModel
                    .search(it.toString())
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        val diffResult = DiffUtil.calculateDiff(PostDiffUtilCallback(viewModel.oldFilteredPosts, viewModel.filteredPosts))
                        viewModel.oldFilteredPosts.clear()
                        viewModel.oldFilteredPosts.addAll(viewModel.filteredPosts)
                        diffResult.dispatchUpdatesTo(recyclerView.adapter!!)
                        adapter?.notifyDataSetChanged()
                    }.addTo(disposable)
            }.addTo(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}
