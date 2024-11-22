package com.project.autoservicemobile.ui.services

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.FOCUSABLE
import android.view.ViewGroup
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.autoservicemobile.MainActivity
import com.project.autoservicemobile.R
import com.project.autoservicemobile.common.BaseFragment
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.databinding.FragmentServicesBinding
import com.project.autoservicemobile.ui.login.SignInOrUpBottomSheetDialog
import com.project.autoservicemobile.ui.services.models.SearchItemEnum
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
        _viewModel.getProducts(query, coroutinesErrorHandler)
    }

    private fun setup() {
        with(binding){
            _viewModel.titleText.observe(viewLifecycleOwner) {
                textTitle.text = it
            }

            searchLayout.hint = _viewModel.searchInputHint
            searchInput.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                    (requireActivity() as MainActivity).hideKeyboard()

                    val query = searchInput.text.toString()
                    if(searchSlider.mutableSelected.value == SearchItemEnum.SERVICES){
                        _viewModel.getServices(query, coroutinesErrorHandler)
                    }
                    else{
                        _viewModel.getProducts(query, coroutinesErrorHandler)
                    }
                }
                true
            })

            searchSlider.mutableSearchList.value = listOf(
                SearchItemEnum.SERVICES,
                SearchItemEnum.PRODUCTS
            )

            searchSlider.mutableSelected.observe(viewLifecycleOwner){
                when(it){
                    SearchItemEnum.SERVICES -> {
                        val query = binding.searchInput.text.toString()
                        _viewModel.getServices(query, coroutinesErrorHandler)

                        productsContainer?.visibility = View.GONE
                        servicesContainer?.visibility = View.VISIBLE
                    }
                    SearchItemEnum.PRODUCTS -> {
                        val query = binding.searchInput.text.toString()
                        _viewModel.getProducts(query, coroutinesErrorHandler)

                        servicesContainer?.visibility = View.GONE
                        productsContainer?.visibility = View.VISIBLE
                    }
                }
            }

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
//                        if (servicesContainer != null && servicesContainer.visibility != View.GONE) {
//                            showErrorPage(it.code as? StatusCodeEnum, servicesContainer)
//                        }
                        servicesRecycler.visibility = View.GONE
                        shimmerServicesContainer?.visibility = View.GONE
                    }

                    is RequestResult.Loading -> {
//                        if (servicesContainer != null) {
//                            removeErrorPage(servicesContainer)
//                        }

                        shimmerServicesContainer?.visibility = View.VISIBLE
                        servicesRecycler.visibility = View.GONE
                    }

                    is RequestResult.Success -> {
//                        if (servicesContainer != null) {
//                            removeErrorPage(servicesContainer)
//                        }

                        servicesRecycler.visibility = View.VISIBLE
                        shimmerServicesContainer?.visibility = View.GONE

                        (servicesRecycler.adapter as ServicesRecyclerAdapter).items = it.data
                        servicesRecycler.adapter?.notifyDataSetChanged()
                    }
                }
            }

            _viewModel.products.observe(viewLifecycleOwner){
                when(it){
                    is RequestResult.Error -> {
//                        if (productsContainer != null) {
//                            showErrorPage(it.code as? StatusCodeEnum, productsContainer)
//                        }
                        shimmerProductsContainer?.visibility = View.GONE
                        productsRecycler?.visibility = View.GONE
                    }

                    is RequestResult.Loading -> {
//                        if (productsContainer != null) {
//                            removeErrorPage(productsContainer)
//                        }

                        shimmerProductsContainer?.visibility = View.VISIBLE
                        productsRecycler?.visibility = View.GONE
                    }

                    is RequestResult.Success -> {
//                        if (productsContainer != null && productsContainer.visibility != View.GONE) {
//                            removeErrorPage(productsContainer)
//                        }

                        productsRecycler?.visibility = View.VISIBLE
                        shimmerProductsContainer?.visibility = View.GONE

                        productsRecycler?.productsList?.value = it.data
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