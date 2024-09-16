package com.project.autoservicemobile.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.autoservicedata.common.RequestResult
import com.project.autoservicemobile.R
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

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

        startAnims()

        _viewModel.isAuthenticated(object : CoroutinesErrorHandler {
            override fun onError(message: String) {
                Log.d("SignInBottomSheetDialog", "Error! $message")
            }
        })
    }

    override fun onPause() {
        super.onPause()
        _viewModel.isAuth.postValue(RequestResult.Loading())
    }

    private fun setup() {
        with(binding) {
            textTitle.text = _viewModel.titleText
            regsTitle.text = _viewModel.regsTitle
            regsDescription.text = _viewModel.regsDescription
            registrationsContainer.setOnClickListener(View.OnClickListener {
                _viewModel.onGoToRegistrationsClick()
            })

            newsRecycler.layoutManager = LinearLayoutManager(context)
            _viewModel.articles.observe(viewLifecycleOwner) {
                newsRecycler.adapter = NewsRecyclerAdapter(it) { item ->
                    {}
                }
            }

            _viewModel.isAuth.observe(viewLifecycleOwner) {
                if (it is RequestResult.Error) {
                    registrationsContainer.visibility = View.GONE
                }
                if (it is RequestResult.Success) {
                    val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.top_to_bottom_appearance)

                    registrationsContainer.visibility = View.VISIBLE
                    registrationsContainer.startAnimation(anim)
                }
            }
        }
    }

    private fun startAnims(){
        val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.top_to_bottom_appearance)
        with(binding){
            newsRecycler.startAnimation(anim)
            textTitle.startAnimation(anim)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}