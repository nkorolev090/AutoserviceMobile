package com.project.autoservicemobile.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.autoservicemobile.MainActivity
import com.project.autoservicemobile.R
import com.project.autoservicemobile.common.BaseFragment
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.databinding.FragmentHomeBinding
import com.project.autoservicemobile.ui.customViews.ErrorPage
import com.project.autoservicemobile.ui.home.models.NewsArticleUI
import com.project.common.data.RequestResult
import com.project.common.data.StatusCodeEnum
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val _viewModel: HomeViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setup()

        return root
    }

    override fun onResume() {
        super.onResume()

        loadData()

        _viewModel.newsPage = 2
    }

    override fun onStart() {
        super.onStart()

        (requireActivity() as MainActivity).showNavBar()
        (requireActivity() as MainActivity).changeSystemNavBarColor(com.google.android.material.R.attr.colorSecondary)
    }

    private fun loadData() {
        _viewModel.updateArticles(coroutinesErrorHandler)

        _viewModel.isAuthenticated(coroutinesErrorHandler)

        binding.swipeRefreshLayout.isRefreshing = false
    }

    override fun onPause() {
        super.onPause()
        _viewModel.isAuth.postValue(RequestResult.Loading())
    }


    private fun setup() {
        binding.appBar.setRightOnClickListener{
            (requireActivity() as MainActivity).navController.navigate(R.id.action_navigation_home_to_mapFragment)
        }
        with(binding) {
            textTitle.text = _viewModel.titleText
            regsTitle.text = _viewModel.regsTitle
            regsDescription.text = _viewModel.regsDescription
            registrationsContainer.setOnClickListener {
                navigateToRegistrations()
            }

            newsRecycler.layoutManager = LinearLayoutManager(context)
            newsRecycler.adapter = NewsRecyclerAdapter { item ->
                openNews(item)
            }

            newsRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if ((recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition() == _viewModel.articles.value?.data?.size?.minus(
                            1
                        )
                    ) {
                        _viewModel.loadMoreArticles()
                    }
                }
            })

            swipeRefreshLayout.setOnRefreshListener {
                loadData()
            }

            _viewModel.articles.observe(viewLifecycleOwner) {
                when (it) {
                    is RequestResult.Error -> {
                        //trobber.visibility = View.GONE
                        shimmerContainer.visibility = View.GONE
                        newsRecycler.visibility = View.GONE

                        showErrorPage(it.code as? StatusCodeEnum, binding.contentContainer)
                    }

                    is RequestResult.Loading -> {
                        removeErrorPage(binding.contentContainer)

                        //trobber.visibility = View.VISIBLE
                        shimmerContainer.visibility = View.VISIBLE
                        newsRecycler.visibility = View.GONE
                    }

                    is RequestResult.Success -> {
                        removeErrorPage(binding.contentContainer)

                        //trobber.visibility = View.GONE
                        newsRecycler.visibility = View.VISIBLE
                        shimmerContainer.visibility = View.GONE

//                        if (newsRecycler.childCount == 0) {
//                            startAnims()
//                        }

                        (newsRecycler.adapter as NewsRecyclerAdapter).items = it.data
                        newsRecycler.adapter?.notifyDataSetChanged()
                    }
                }
            }

            _viewModel.isAuth.observe(viewLifecycleOwner) {
                when (it) {
                    is RequestResult.Error -> {
                        registrationsContainer.visibility = View.GONE
                        registrationShimmer.visibility = View.GONE
                    }

                    is RequestResult.Loading -> {
                        registrationShimmer.visibility = View.VISIBLE
                        registrationsContainer.visibility = View.INVISIBLE
                    }

                    is RequestResult.Success -> {
//                        val anim = AnimationUtils.loadAnimation(
//                            requireContext(),
//                            R.anim.top_to_bottom_appearance
//                        )

                        registrationsContainer.visibility = View.VISIBLE
                        registrationShimmer.visibility = View.GONE
                        //registrationsContainer.startAnimation(anim)
                    }
                }
            }
        }
    }

//    private fun startAnims() {
//        val anim =
//            AnimationUtils.loadAnimation(requireContext(), R.anim.top_to_bottom_appearance)
//        with(binding) {
//            newsRecycler.startAnimation(anim)
//            textTitle.startAnimation(anim)
//        }
//    }

    private fun openNews(newsArticle: NewsArticleUI) {
        var url = newsArticle.url
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://$url"
        }

        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun navigateToRegistrations() {
        (requireActivity() as MainActivity).navController.navigate(R.id.action_navigation_home_to_registrationsFragment)
    }
}