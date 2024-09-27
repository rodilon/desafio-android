package br.com.renan.desafioandroid.pullrequest.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.renan.desafioandroid.R
import br.com.renan.desafioandroid.core.state.ResultState
import br.com.renan.desafioandroid.databinding.ActivityPullRequestBinding
import br.com.renan.desafioandroid.model.data.Content
import br.com.renan.desafioandroid.model.data.PullRequest
import br.com.renan.desafioandroid.pullrequest.viewmodel.PullRequestViewModel
import org.koin.android.ext.android.inject

class PullRequestActivity : AppCompatActivity() {

    private lateinit var repoName: String
    private lateinit var login: String

    private lateinit var binding: ActivityPullRequestBinding
    private val viewModel: PullRequestViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityPullRequestBinding = DataBindingUtil.setContentView(
                this, R.layout.activity_pull_request)

        binding.apply {
            lifecycleOwner = this@PullRequestActivity
            viewModel = this@PullRequestActivity.viewModel
        }

        this.binding = binding

        val (login, repoName) = getExtras()

        init(login, repoName)

        setupToolbar()

        initListeners(login, repoName)

        changeUiState()

    }

    private fun changeUiState() {
        viewModel.uiState.observe(this) {
            when (it) {
//                ResultState.Empty -> {
//                    showPullRequestEmpty()
//                }
//
//                is ResultState.Error -> {
//                    showPullRequestError()
//                }
//
//                ResultState.Loading -> {
//                    showPullRequestLoading()
//                }

                is ResultState.Success<*> -> {
//                    showPullRequestSuccess()
                    (it.data as Content).pullRequestList?.let { requestList ->
                        pullRequestSuccess(
                            requestList
                        )
                    }
                    showTotalPulls(it.data.pullRequestCalculated)
                }
            }
        }
    }

    private fun setupToolbar() {
        binding.includeToolbar.toolbar.setNavigationIcon(R.drawable.ic_arrow_left)
        binding.includeToolbar.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.includeToolbar.toolbarTitle.text = repoName
    }

    private fun initListeners(login: String, creator: String) {
        binding.includeErrorPullRequest.ivError.setOnClickListener {
            init(login, creator)
        }
    }

    private fun init(login: String, repoName: String) {
        viewModel.getPullRequests(login, repoName)
    }

    private fun getExtras(): Pair<String, String> {
        login = intent.getStringExtra("creator") as String
        repoName = intent.getStringExtra("repository") as String
        return Pair(login, repoName)
    }


    private fun pullRequestSuccess(pullRequestList: List<PullRequest>) {
        val pullRequestAdapter = PullRequestAdapter(pullRequestList)
        with(binding.pullRequestRecycler) {
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(this@PullRequestActivity)
            adapter = pullRequestAdapter
        }
    }

    private fun showTotalPulls(calculatedPulls: Pair<Int, Int>) {
        binding.openClosedPulls.text =
            getString(R.string.open_close_pulls, calculatedPulls.first, calculatedPulls.second)
    }

    private fun showPullRequestLoading() {
        binding.pbPullRequest.visibility = View.VISIBLE
        binding.includeErrorPullRequest.root.visibility = View.GONE
    }

    private fun showPullRequestError() {
        binding.includeErrorPullRequest.root.visibility = View.VISIBLE
        binding.pbPullRequest.visibility = View.GONE
        binding.openClosedPulls.visibility = View.GONE
    }

    private fun showPullRequestSuccess() {
        binding.openClosedPulls.visibility = View.VISIBLE
        binding.pullRequestRecycler.visibility = View.VISIBLE
        binding.includeEmptyLayout.root.visibility = View.GONE
        binding.pbPullRequest.visibility = View.GONE
        binding.includeErrorPullRequest.root.visibility = View.GONE
    }

    private fun showPullRequestEmpty() {
        binding.includeEmptyLayout.root.visibility = View.VISIBLE
        binding.pbPullRequest.visibility = View.GONE
        binding.openClosedPulls.visibility = View.GONE
        binding.includeErrorPullRequest.root.visibility = View.GONE
    }
}
