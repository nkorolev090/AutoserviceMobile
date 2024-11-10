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
import com.project.autoservicemobile.common.BaseFragment
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.databinding.FragmentServicesBinding
import com.project.autoservicemobile.ui.login.SignInOrUpBottomSheetDialog
import com.project.autoservicemobile.ui.services.models.ServiceUI
import com.project.autoservicemobile.ui.services.slots.SlotsBottomSheetDialog
import com.project.common.data.RequestResult
import com.project.common.data.StatusCodeEnum
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServicesFragment : BaseFragment() {

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

        val query = binding.searchInput.text.toString()
        _viewModel.getServices(query, coroutinesErrorHandler)
    }

    private fun setup() {
        with(binding){
            _viewModel.titleText.observe(viewLifecycleOwner) {
                textTitle.text = it
            }

            searchLayout.hint = _viewModel.searchInputHint
            searchInput.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                    (requireActivity() as MainActivity).hideKeyboard()

                    val query = searchInput.text.toString()
                    _viewModel.getServices(query, coroutinesErrorHandler)
                }
                false
            })

            servicesRecycler.layoutManager = LinearLayoutManager(context)
            servicesRecycler.adapter = ServicesRecyclerAdapter({ item ->
                if (item.inCart) { // remove from cart
                    Log.d(TAG, "${item?.title}", )
                    (servicesRecycler.adapter as ServicesRecyclerAdapter).onAddOrRemoveToCart(item)
                } else { //open dialog to set slot
                    openSlotsBottomSheetDialog(item)
                }
            }, { item ->
                _viewModel.onFavoritesBtnClick(item)
            })

            _viewModel.services.observe(viewLifecycleOwner) {
                when (it) {
                    is RequestResult.Error -> {
                        showErrorPage(it.code as? StatusCodeEnum, binding.contentContainer)
                        contentContainer.visibility = View.VISIBLE
                        shimmerContainer.visibility = View.GONE
                    }
                    is RequestResult.Loading -> {
                        removeErrorPage(binding.contentContainer)

                        shimmerContainer.visibility = View.VISIBLE
                        contentContainer.visibility = View.GONE
                    }
                    is RequestResult.Success -> {
                        removeErrorPage(binding.contentContainer)

                        contentContainer.visibility = View.VISIBLE
                        shimmerContainer.visibility = View.GONE

                        (servicesRecycler.adapter as ServicesRecyclerAdapter).items = it.data
                        servicesRecycler.adapter?.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    private fun openSlotsBottomSheetDialog(service: ServiceUI) {
        val modalBottomSheet = SlotsBottomSheetDialog(
            service,
            (binding.servicesRecycler.adapter as ServicesRecyclerAdapter)
        )
        modalBottomSheet.show(
            requireActivity().supportFragmentManager,
            SlotsBottomSheetDialog.TAG
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "ServicesFragment"
    }
}