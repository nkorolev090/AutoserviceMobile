package com.project.autoservicemobile.ui.services

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.autoservicemobile.databinding.FragmentServicesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServicesFragment : Fragment() {

    private var _binding: FragmentServicesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val _viewModel: ServicesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentServicesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setup()

        return root
    }
private fun setup(){
    binding.textTitle.text = _viewModel.titleText

    binding.servicesRecycler.layoutManager = LinearLayoutManager(context)
    _viewModel.services.observe(viewLifecycleOwner){
        binding.servicesRecycler.adapter = ServicesRecyclerAdapter(it,
            { item ->
                _viewModel.onCartBtnClick(item)
            },
            { item ->
                _viewModel.onFavoritesBtnClick(item)
            })
    }
}
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}