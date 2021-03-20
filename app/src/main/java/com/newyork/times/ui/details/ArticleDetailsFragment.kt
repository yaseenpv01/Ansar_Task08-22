
package com.newyork.times.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.RoundedCornersTransformation
import com.newyork.times.databinding.FragmentArticleDetailsBinding
import com.newyork.times.utils.ArticleViewModel
import dagger.hilt.android.AndroidEntryPoint
import www.thecodemonks.techbytes.ui.base.BaseFragment


@AndroidEntryPoint
class ArticleDetailsFragment : BaseFragment<FragmentArticleDetailsBinding, ArticleViewModel>() {


    override val viewModel: ArticleViewModel by activityViewModels()

    private val args: ArticleDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)


        args.let {

            binding.textviewNewsdetailsTime.text  = it.article.published_date
            binding.textviewNewsdetailsTitle.text = it.article.title
            binding.textviewNewsdetailsAbstract.text = it.article.abstractt

            try {
                var tempUrl = it.article!!.media?.get(0)!!.media_metadata?.get(1)!!.url!!
                binding.imageviewNewsdetails.load(tempUrl) {
                    crossfade(true)
                    crossfade(50)
                    transformations(
                            RoundedCornersTransformation(
                                    6f,
                                    6f,
                                    6f,
                                    6f
                            )
                    )
                }
            } catch (e: Exception) {
            }
        }

       // binding.textviewNewsdetailsTime.text="test test one two three"

    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentArticleDetailsBinding.inflate(inflater, container, false)
}
