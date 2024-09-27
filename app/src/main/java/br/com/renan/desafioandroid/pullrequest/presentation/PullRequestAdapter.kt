package br.com.renan.desafioandroid.pullrequest.presentation

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.renan.desafioandroid.R
import br.com.renan.desafioandroid.databinding.ItemPullRequestBinding
import br.com.renan.desafioandroid.model.data.PullRequest
import com.squareup.picasso.Picasso

class PullRequestAdapter(private val recyclerList: List<PullRequest>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var binding: ItemPullRequestBinding
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        binding = ItemPullRequestBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return recyclerList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(
            recyclerList[position],
            binding
        )
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(pullRequest: PullRequest, binding: ItemPullRequestBinding) = with(itemView) {

            Picasso.get()
                .load(pullRequest.user.avatarUrl)
                .into(binding.ivPullRequestAvatar)

            binding.tvPullRequestName.text = pullRequest.title
            binding.tvPullRequestDescription.text =
                if (pullRequest.body.isNullOrEmpty()) context.getString(R.string.description_not_found) else pullRequest.body
            binding.tvPullRequestUserName.text = pullRequest.user.login
            binding.tvPullRequestFullName.text = pullRequest.user.login

            this.setOnClickListener {
                val openBrowser = Intent(Intent.ACTION_VIEW)
                openBrowser.data = Uri.parse(pullRequest.html_url)
                context.startActivity(openBrowser)
            }
        }
    }
}