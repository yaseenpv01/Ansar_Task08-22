

package com.newyork.times.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.newyork.times.app.model.Article
import com.newyork.times.data.remote.model.ArticleResponse
import com.newyork.times.databinding.ItemPostArticleBinding
import com.newyork.times.utils.State
import java.util.ArrayList


class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsVH>() {

    var articlesFiltered = mutableListOf<Article>()

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {

        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }


    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsVH {
        val binding =
            ItemPostArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsVH(binding)
    }


    override fun getItemCount(): Int {

        if(articlesFiltered.size==0){
            articlesFiltered = differ.currentList
        }
        return articlesFiltered.size
    }

    fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    articlesFiltered = differ.currentList
                } else {
                    val filteredList: MutableList<Article> = ArrayList<Article>()
                    for (row in articlesFiltered) {

                        // name match condition. this might differ depending on your requirement
                        if (row.title?.toLowerCase()?.contains(charString.toLowerCase())!!
                                || row.published_date?.contains(charString.toLowerCase())!!) {
                            filteredList.add(row)
                        }
                    }
                    articlesFiltered = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = articlesFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                articlesFiltered = filterResults.values as MutableList<Article>
                notifyDataSetChanged()
            }
        }
    }


    override fun onBindViewHolder(holder: NewsVH, position: Int) {

        if(articlesFiltered.size==0){
            articlesFiltered = differ.currentList
        }
        val item = articlesFiltered[position]
        holder.binding.apply {

            // TODO clean logic
            if (item.title?.isBlank()!! || item.title.isNullOrBlank() ||
                item.url.isNullOrBlank()
            ) {
                itemArticleTitle.visibility = View.GONE
                itemPostDescription.visibility = View.GONE
                itemPostAuthor.visibility = View.GONE
                itemArticleImage.visibility = View.GONE
            } else {
                itemArticleTitle.text = item.title
                itemPostDescription.text = item.byline
                itemPostAuthor.text = item.published_date



                try {
                    var tempUrl = item!!.media?.get(0)!!.media_metadata?.get(0)!!.url!!
                    itemArticleImage.load(tempUrl) {
                        crossfade(true)
                        crossfade(200)
                        transformations(
                            RoundedCornersTransformation(
                                12f,
                                12f,
                                12f,
                                12f
                            )
                        )
                    }
                } catch (e: Exception) {
                }
            }

            // on item click
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(item) }
            }
        }
    }

    inner class NewsVH(val binding: ItemPostArticleBinding) : RecyclerView.ViewHolder(binding.root)

    // on item click listener
    private var onItemClickListener: ((Article) -> Unit)? = null
    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}
