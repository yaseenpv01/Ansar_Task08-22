

package com.newyork.times.ui.articles

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.newyork.times.R
import com.newyork.times.databinding.FragmentArticlesBinding
import com.newyork.times.ui.adapter.NewsAdapter
import com.newyork.times.utils.*
import dagger.hilt.android.AndroidEntryPoint
import www.thecodemonks.techbytes.ui.base.BaseFragment

@AndroidEntryPoint
class ArticlesFragment : BaseFragment<FragmentArticlesBinding, ArticleViewModel>() {

    override val viewModel: ArticleViewModel by activityViewModels()

    private lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setup()
    }

    private fun setup() = with(binding) {
        initArticlesRv()
        observeNetworkConnectivity()
        observeArticles()
        articleItemOnClick()
        swipeToRefreshArticles()

        viewModel.getNewYorkNews(7)
    }




    private fun initArticlesRv() = with(binding) {
        // init article rv
        newsAdapter = NewsAdapter().also {
            articleRv.adapter = it
            articleRv.addItemDecoration(SpacesItemDecorator(16))
        }
    }



    private fun swipeToRefreshArticles() = with(binding) {
        refreshArticles.setOnRefreshListener {
            viewModel.getNewYorkNews(7)
            refreshArticles.isRefreshing = true
        }
    }



    private fun articleItemOnClick() {
        // pass bundle onclick
        newsAdapter.setOnItemClickListener { article ->
            val bundle = Bundle().apply {
                putSerializable("article", article)
            }
            findNavController().navigate(
                R.id.action_articlesFragment_to_articleDetailsFragment,
                bundle
            )
        }
    }



    private fun observeArticles() = with(binding) {
        // observe the articles

        viewModel.articles.observe(viewLifecycleOwner, Observer {

            when(it){

                is State.Success -> {
                    if (it.data!=null) {
                        refreshArticles.isRefreshing = false
                        newsAdapter.differ.submitList(it.data.getPopularArticles())
                        articleRv.animate().alpha(1f)
                        articleRv.itemAnimator = null
                    }
                }

            }

        })


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {


        if (null == activity) return

        val searchView: SearchView
        activity?.menuInflater?.inflate(R.menu.menu, menu)

        // Associate searchable configuration with the SearchView

        // Associate searchable configuration with the SearchView
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.action_search)
                .actionView as SearchView

        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(activity?.componentName))
        searchView.setMaxWidth(Int.MAX_VALUE)

        // listening to search query text change

        // listening to search query text change
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                newsAdapter.getFilter()?.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newsAdapter.getFilter()?.filter(newText)
                return false
            }

        })
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        return if (id == R.id.action_search) {
            true
        } else super.onOptionsItemSelected(item)

    }



    private fun observeNetworkConnectivity() {

       /* NetworkUtils.observeConnectivity(requireContext())
            .observe(viewLifecycleOwner, Observer {
                    isConnected ->
                if (isConnected) onConnectivityAvailable() else onConnectivityUnavailable()

            })*/

    }

    private fun onConnectivityAvailable() = with(binding) {
        textNetworkStatus.apply {
            text = getString(R.string.text_connectivity)
            setDrawableLeft(R.drawable.ic_internet_on)
        }
        containerNetworkStatus.apply {
            setBackgroundColor(
                context.getColorCompat(R.color.colorStatusConnected)
            )
            animate()
                .alpha(1f)
                .setStartDelay(ANIMATION_DURATION)
                .setDuration(ANIMATION_DURATION)
                .setListener(
                    object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            hide()
                        }
                    }
                )
                .start()
        }
    }

    private fun onConnectivityUnavailable() = with(binding) {
        textNetworkStatus.apply {
            text = getString(R.string.text_no_connectivity)
            setDrawableLeft(R.drawable.ic_internet_off)
        }
        containerNetworkStatus.apply {
            show()
            setBackgroundColor(
                context.getColorCompat(R.color.colorStatusNotConnected)
            )
        }
    }

    companion object {
        const val ANIMATION_DURATION = 3000L
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentArticlesBinding.inflate(inflater, container, false)
}
