package com.project.autoservicemobile.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.autoservicemobile.databinding.FragmentCartBinding
import com.project.autoservicemobile.ui.services.ServicesRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val _viewModel: CartViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setup()
        return root
    }

    private fun setup(){
        //binding.subTotalText.text = _viewModel.titleText

        binding.cartRecycler.layoutManager = LinearLayoutManager(context)
        _viewModel.cartItems.observe(viewLifecycleOwner){
            binding.cartRecycler.adapter = CartRecyclerAdapter(it,
                {item -> {}}
            )
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}