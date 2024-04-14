package kr.co.lion.finalproject_shoppingmallservice_team1.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.finalproject_shoppingmallservice_team1.R

class PilatesSelectFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CommonAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_swimming_select, container, false)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.swimming_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Setup adapter (assuming you have a SwimmingAdapter class)
        adapter = CommonAdapter()
        recyclerView.adapter = adapter

        // Load data into the adapter
        loadData()

        return view
    }

    private fun loadData() {
        // This is just a placeholder function to simulate loading data
        val data = listOf("Swimming Lesson 1", "Swimming Lesson 2", "Swimming Training")
        adapter.submitList(data) // Ensure your adapter has a function to accept data
    }
}


