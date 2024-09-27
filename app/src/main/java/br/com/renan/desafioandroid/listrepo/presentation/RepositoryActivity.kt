package br.com.renan.desafioandroid.listrepo.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.renan.desafioandroid.model.data.Repository
import br.com.renan.desafioandroid.core.helper.PaginationScroll
import org.koin.android.ext.android.inject
import br.com.renan.desafioandroid.*
import br.com.renan.desafioandroid.core.state.ResultState
import br.com.renan.desafioandroid.databinding.ActivityRepositoryBinding
import br.com.renan.desafioandroid.listrepo.viewmodel.RepositoryViewModel

class RepositoryActivity : AppCompatActivity() {

    private lateinit var repositoryAdapter: RepositoryAdapter
    private var releasedLoad: Boolean = true
    private var page = 1
    private lateinit var toolbar: Toolbar

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var binding: ActivityRepositoryBinding

    private val repositoryViewModel: RepositoryViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRepositoryBinding.inflate(layoutInflater)

        setContentView(binding.root)

        toolbar = findViewById(R.id.include_toolbar)

        setupToolbar()
        fetchData(page)
        initListeners()
        initObservers()
    }

    private fun fetchData(page: Int) {
        repositoryViewModel.getRepository(page)
    }

    private fun initObservers() {
            repositoryViewModel.uiState.observe(this) {
                when (it) {
                    ResultState.Loading -> showRepositoryLoading()
                    is ResultState.Error -> showRepositoryError()
                    is ResultState.Success<*> -> {
                        (it.data as? List<Repository>)?.let { it1 -> successRepository(it1) }
                        showRepositorySuccess()
                    }
                    ResultState.Empty -> {
                        // do nothing
                    }
                }
            }
    }

    private fun successRepository(repositoryItemsList: List<Repository>) {

        repositoryAdapter = RepositoryAdapter(repositoryItemsList)

        linearLayoutManager = LinearLayoutManager(this)
        with(binding.repositoryRecycler) {
            itemAnimator = DefaultItemAnimator()
            layoutManager = linearLayoutManager
            adapter = repositoryAdapter

            addOnScrollListener(object : PaginationScroll(linearLayoutManager) {
                override fun loadMoreItems() {
                    releasedLoad = false
                    binding.pbBottom.visibility = View.VISIBLE
                    page++
                    fetchData(page)
                }

                override fun hideMoreItems() {
                    binding.pbRepository.visibility = View.GONE
                }

                override fun isLoading(): Boolean {
                    return releasedLoad
                }
            })
        }

        releasedLoad = true
    }

    private fun setupToolbar() {
        binding.includeToolbar.toolbarTitle.text = getString(R.string.title_toolbar_repository)
    }

    private fun initListeners() {
        binding.includeErrorRepository.ivError.setOnClickListener {
            binding.pbRepository.visibility = View.VISIBLE
            binding.includeErrorRepository.root.visibility = View.GONE
            fetchData(page)
        }
    }

    private fun showRepositoryLoading() {
        binding.pbRepository.visibility = View.VISIBLE
        binding.includeErrorRepository.root.visibility = View.GONE
    }

    private fun showRepositoryError() {
        binding.includeErrorRepository.root.visibility = View.VISIBLE
        binding.pbRepository.visibility = View.GONE
        binding.repositoryRecycler.visibility = View.GONE
        binding.pbBottom.visibility = View.GONE
    }

    private fun showRepositorySuccess() {
        binding.includeErrorRepository.root.visibility = View.GONE
        binding.pbRepository.visibility = View.GONE
        binding.repositoryRecycler.visibility = View.VISIBLE
        binding.pbBottom.visibility = View.GONE
    }
}
