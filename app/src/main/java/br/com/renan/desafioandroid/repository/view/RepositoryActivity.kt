package br.com.renan.desafioandroid.repository.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import br.com.renan.desafioandroid.RepositoryViewModel
import br.com.renan.desafioandroid.model.data.Repository
import br.com.renan.desafioandroid.util.PaginationScroll
import kotlinx.android.synthetic.main.activity_repository.*
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.toolbar.*
import org.koin.android.ext.android.inject
import br.com.renan.desafioandroid.*


private const val KEY_RECYCLER_STATE = "recycler_state"


class RepositoryActivity : AppCompatActivity() {

    private lateinit var repositoryAdapter: RepositoryAdapter
    private val listRepository = ArrayList<Repository>()
    private var releasedLoad: Boolean = true
    private var page = 1
    private lateinit var toolbar: Toolbar

    private lateinit var linearLayoutManager: LinearLayoutManager

    private val repositoryViewModel: RepositoryViewModel by inject()

    private var bundleRecyclerViewState: Bundle? = null

    var index = -1
    var top = -1
    var layoutManager: LinearLayoutManager = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository)

        toolbar = findViewById(R.id.include_toolbar)

        setupToolbar()
        fetchData(page)
        setupLayout()
        initListeners()
        initObservers()
    }

    override fun onPause() {
        super.onPause()
        index = layoutManager.findFirstVisibleItemPosition()
        val v = repositoryRecycler.getChildAt(0)
        top = if (v == null) 0 else v.top - repositoryRecycler.paddingTop
    }

    override fun onResume() {
        super.onResume()
        if (index != -1) {
            layoutManager.scrollToPositionWithOffset(index, top)
        }
    }

//    override fun onSaveInstanceState(outState: Bundle?) {
//        super.onSaveInstanceState(outState)
//        if (outState != null) {
//            val scrollPosition = (repositoryRecycler.layoutManager as LinearLayoutManager)
//                .findFirstCompletelyVisibleItemPosition()
//            mRecipeListParcelable = linearLayoutManager.onSaveInstanceState()
//            outState.putParcelable("KEY_LAYOUT", mRecipeListParcelable)
//            outState.putInt("POSITION", scrollPosition)
//        }
//
//    }
//
//    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
//        super.onRestoreInstanceState(savedInstanceState)
//        if(savedInstanceState != null) {
//            mRecipeListParcelable = savedInstanceState.getParcelable("KEY_LAYOUT")
//            mScrollPosition = savedInstanceState.getInt("POSITION")
//        }
//    }

//    override fun onPause() {
//        super.onPause()
//        bundleRecyclerViewState = Bundle()
//        val listState: Parcelable? = repositoryRecycler.layoutManager?.onSaveInstanceState()
//        bundleRecyclerViewState?.putParcelable(KEY_RECYCLER_STATE, listState)
//    }
//
//    override fun onResume() {
//        super.onResume()
//        if (bundleRecyclerViewState != null) {
//            val listState: Parcelable? = bundleRecyclerViewState!!.getParcelable(KEY_RECYCLER_STATE)
//            repositoryRecycler.layoutManager?.onRestoreInstanceState(listState)
//        }
//    }

    private fun fetchData(page: Int) {
        repositoryViewModel.getRepositoryData(page)
    }

    private fun initObservers() {
        showRepositoryLoading()
        repositoryViewModel.repositoryResult.observeResource(this, onSuccess = {
            showRepositorySucess()
            successRepository(it.repositoryItemsList)
        }, onError = {
            showRepositoryError()
        })
    }

    private fun successRepository(repositoryItemsList: List<Repository>) {
        listRepository.addAll(repositoryItemsList)
        releasedLoad = true
        repositoryAdapter.notifyDataSetChanged()
    }

    private fun setupToolbar() {
        toolbar_title.text = getString(br.com.renan.desafioandroid.R.string.title_toolbar_repository)
    }

    private fun setupLayout() {
        repositoryAdapter = RepositoryAdapter(listRepository as List<Repository>)
        repositoryRecycler.itemAnimator = DefaultItemAnimator()
        linearLayoutManager = LinearLayoutManager(this)
        repositoryRecycler.layoutManager = linearLayoutManager
        repositoryRecycler.adapter = repositoryAdapter
    }

    private fun initListeners() {
        ivError.setOnClickListener {
            fetchData(page)
        }

        repositoryRecycler.addOnScrollListener(object : PaginationScroll(linearLayoutManager) {
            override fun loadMoreItems() {
                releasedLoad = false
                pbBottom.visibility = View.VISIBLE
                page++
                fetchData(page)
            }

            override fun hideMoreItems() {
                pbRepository.visibility = View.GONE
            }

            override fun isLoading(): Boolean {
                return releasedLoad
            }
        })
    }

//    private fun init(page: Int) {
//        repositoryPresenter.requestRepositoryData(page)
//    }

    private fun showRepositoryLoading() {
        pbRepository.visibility = View.VISIBLE
        include_error_repository.visibility = View.GONE
    }

    private fun showRepositoryError() {
        include_error_repository.visibility = View.VISIBLE
        pbRepository.visibility = View.GONE
        repositoryRecycler.visibility = View.GONE
        pbBottom.visibility = View.GONE
    }

    private fun showRepositorySucess() {
        pbRepository.visibility = View.GONE
        repositoryRecycler.visibility = View.VISIBLE
        pbBottom.visibility = View.GONE
    }
}
