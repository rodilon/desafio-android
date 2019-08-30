package br.com.renan.desafioandroid.repository

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.renan.desafioandroid.model.data.Repository
import br.com.renan.desafioandroid.core.helper.PaginationScroll
import kotlinx.android.synthetic.main.activity_repository.*
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.toolbar.*
import org.koin.android.ext.android.inject
import br.com.renan.desafioandroid.*

class RepositoryActivity : AppCompatActivity() {

    private lateinit var repositoryAdapter: RepositoryAdapter
    private val listRepository = ArrayList<Repository>()
    private var releasedLoad: Boolean = true
    private var page = 1
    private lateinit var toolbar: Toolbar

    private lateinit var linearLayoutManager: LinearLayoutManager

    private val repositoryViewModel: RepositoryViewModel by inject()

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

    private fun fetchData(page: Int) {
        repositoryViewModel.getRepositoryData(page)
    }

    private fun initObservers() {
        showRepositoryLoading()
        repositoryViewModel.repositoryResult.observeResource(this, onSuccess = {
            showRepositorySuccess()
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
            pbRepository.visibility = View.VISIBLE
            include_error_repository.visibility = View.GONE
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

    private fun showRepositorySuccess() {
        include_error_repository.visibility = View.GONE
        pbRepository.visibility = View.GONE
        repositoryRecycler.visibility = View.VISIBLE
        pbBottom.visibility = View.GONE
    }
}
