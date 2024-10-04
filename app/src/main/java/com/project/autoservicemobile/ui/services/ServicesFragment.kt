package com.project.autoservicemobile.ui.services

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.autoservicemobile.MainActivity
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.databinding.FragmentServicesBinding
import com.project.autoservicemobile.ui.login.SignInOrUpBottomSheetDialog
import com.project.autoservicemobile.ui.services.slots.SlotsBottomSheetDialog
import com.project.common.data.RequestResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServicesFragment : Fragment() {

    private var _binding: FragmentServicesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val _viewModel: ServicesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentServicesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setup()

        return root
    }

    override fun onResume() {
        super.onResume()

        _viewModel.updateServices(object : CoroutinesErrorHandler {
            override fun onError(message: String) {
                Log.d("HomeFragment", "Error! $message")
            }
        })
    }

    private fun setup() {
        _viewModel.titleText.observe(viewLifecycleOwner) {
            binding.textTitle.text = it
        }

        binding.searchLayout.hint = _viewModel.searchInputHint
        binding.searchInput.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                (requireActivity() as MainActivity).hideKeyboard()

                val query = binding.searchInput.text.toString()

                if (query == "") {
                    _viewModel.updateServices(object : CoroutinesErrorHandler {
                        override fun onError(message: String) {
                            Log.d("HomeFragment", "Error! $message")
                        }
                    })
                } else {
                    _viewModel.getServicesByQuery(query, object : CoroutinesErrorHandler {
                        override fun onError(message: String) {
                            Log.d("HomeFragment", "Error! $message")
                        }
                    })
                }

            }
            false
        })

        binding.servicesRecycler.layoutManager = LinearLayoutManager(context)
        binding.servicesRecycler.adapter = ServicesRecyclerAdapter({ item ->
            openSlotsBottomSheetDialog()
        }, { item ->
            _viewModel.onFavoritesBtnClick(item)
        })
        _viewModel.services.observe(viewLifecycleOwner) {
            when (it) {
                is RequestResult.Error -> {}
                is RequestResult.Loading -> {}
                is RequestResult.Success -> {
                    (binding.servicesRecycler.adapter as ServicesRecyclerAdapter).items = it.data
                    binding.servicesRecycler.adapter?.notifyDataSetChanged()
                }
            }
        }

    }

    private fun openSlotsBottomSheetDialog() {
        val modalBottomSheet = SlotsBottomSheetDialog()
        modalBottomSheet.show(
            requireActivity().supportFragmentManager,
            SlotsBottomSheetDialog.TAG
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}