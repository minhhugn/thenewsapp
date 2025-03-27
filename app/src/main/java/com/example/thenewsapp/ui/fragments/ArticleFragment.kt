package com.example.thenewsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.thenewsapp.R
import com.example.thenewsapp.databinding.FragmentArticleBinding
import com.example.thenewsapp.ui.NewsActivity
import com.example.thenewsapp.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.Observer

class ArticleFragment : Fragment(R.layout.fragment_article) {

    lateinit var newsViewModel: NewsViewModel
    val args: ArticleFragmentArgs by navArgs()
    lateinit var binding: FragmentArticleBinding
    private var summaryObserver: Observer<String>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticleBinding.bind(view)

        newsViewModel = (activity as NewsActivity).newsViewModel
        val article = args.article

        binding.webView.apply {
            webViewClient = WebViewClient()
            article.url?.let {
                loadUrl(it)
            }
        }

        val navController = findNavController()

        binding.buttonBack.setOnClickListener {
            Log.d("ArticleFragment", "buttonBack clicked, navigating back")
            navController.popBackStack()
        }

        binding.buttonHideSummary.setOnClickListener {
            binding.summaryTextView.visibility = View.GONE
        }

        binding.fab.setOnClickListener {
            newsViewModel.addToFavourites(article)
            Snackbar.make(view, "Added to favourites", Snackbar.LENGTH_SHORT).show()
        }

        binding.sum.setOnClickListener {
            article.content?.let { content ->
                Log.d("ArticleFragment", "Summarize button clicked, content: $content")
                binding.progressBar.visibility = View.VISIBLE
                newsViewModel.summarizeArticle(content)
            }
        }

        summaryObserver = Observer { summaryText ->
            Log.d("ArticleFragment", "Summary text received: $summaryText")
            Log.d("ArticleFragment", "SummaryTextView visibility before update: ${binding.summaryTextView.visibility}")
            binding.progressBar.visibility = View.GONE
            binding.summaryTextView.text = summaryText
            binding.summaryTextView.visibility = View.VISIBLE
            binding.buttonHideSummary.visibility = View.VISIBLE // Hiển thị nút ẩn tóm tắt
            Log.d("ArticleFragment", "SummaryTextView visibility after update: ${binding.summaryTextView.visibility}")
        }

        newsViewModel.summary.observe(viewLifecycleOwner, summaryObserver!!)

        newsViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Log.e("ArticleFragment", "Error message: $errorMessage")
            binding.progressBar.visibility = View.GONE
            if (errorMessage != null) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }

        newsViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            Log.d("ArticleFragment", "Is loading: $isLoading")
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }
}