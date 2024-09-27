package br.com.renan.desafioandroid.listrepo.presentation

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.renan.desafioandroid.databinding.ItemRepositoryBinding
import br.com.renan.desafioandroid.model.data.Repository
import br.com.renan.desafioandroid.pullrequest.presentation.PullRequestActivity
import com.squareup.picasso.Picasso

class RepositoryAdapter(private val recyclerList: List<Repository>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var binding: ItemRepositoryBinding

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        binding = ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return recyclerList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(recyclerList[position], binding)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(repository: Repository, binding: ItemRepositoryBinding) = with(itemView) {

            Picasso.get()
                .load(repository.owner.avatarUrl)
                .into(binding.ivRepositoryAvatar)

            binding.tvRepositoryName.text = repository.name
            binding.tvRepositoryDescription.text = repository.description
            binding.tvRepositoryForks.text = repository.forks.toString()
            binding.tvRepositoryStars.text = repository.starts.toString()
            binding.tvRepositoryUserName.text = repository.owner.login
            binding.tvRepositoryFullName.text = repository.fullName.replace("/", " ")

            this.setOnClickListener { v ->
                val intent = Intent(v.context, PullRequestActivity::class.java)
                intent.putExtra("creator", repository.owner.login)
                intent.putExtra("repository", repository.name)
                context.startActivity(intent)
            }
        }
    }
}