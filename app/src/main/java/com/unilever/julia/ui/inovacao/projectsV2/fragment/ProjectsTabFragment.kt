package com.unilever.julia.ui.inovacao.projectsV2.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unilever.julia.R
import com.unilever.julia.data.model.InnovationProjectsModel
import com.unilever.julia.ui.adapter.ProjectsAdapter
import com.unilever.julia.components.LoadView
import com.unilever.julia.ui.inovacao.projectsV2.ProjectsActivity

class ProjectsTabFragment : Fragment() {

    private var mIndex : Int = 0

    companion object {

        private const val INDEX = "INDEX"

        fun newInstance(index: Int): ProjectsTabFragment {
            val fragment = ProjectsTabFragment()

            val bundleArgs = Bundle()
            bundleArgs.putInt(INDEX, index)
            fragment.arguments = bundleArgs

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null && arguments!!.containsKey(INDEX))
            mIndex = arguments!!.getInt(INDEX)
    }

    lateinit var mRecyclerView : RecyclerView

    lateinit var mLoadView : LoadView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.innovation_projects_fragment, container, false)
        mRecyclerView = view.findViewById(R.id.rcview)
        mLoadView = view.findViewById(R.id.loadView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val items : MutableList<InnovationProjectsModel.Item> = (activity as ProjectsActivity).mPresenter.getProducts(mIndex)

        if (items.isNullOrEmpty()) {
            mLoadView.visibility =View.VISIBLE
            mLoadView.showEmpty(
                getString(R.string.innovation_projects_empty_title),
                getString(R.string.innovation_projects_empty_description)
            )
            mRecyclerView.visibility = View.GONE
            return
        }

        val adapter = ProjectsAdapter(this, activity as ProjectsActivity)
        adapter.addItems(items)
        mRecyclerView.adapter = adapter
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.setItemViewCacheSize(20)
        mRecyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
    }
}
