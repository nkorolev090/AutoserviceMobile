package com.project.autoservicemobile.ui.loyalty

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.autoservicemobile.R
import com.project.autoservicemobile.databinding.FragmentCartBinding
import com.project.autoservicemobile.databinding.FragmentLoyaltyBinding
import com.project.autoservicemobile.databinding.FragmentServicesBinding
import com.project.autoservicemobile.ui.services.ServicesRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoyaltyFragment : Fragment() {

    private var _binding: FragmentLoyaltyBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val _viewModel: LoyaltyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoyaltyBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setup()

        return root
    }

    private fun setup(){
        binding.titleText.text = _viewModel.titleText
        binding.descriptionText.text = _viewModel.descriptionText
        binding.howItText.text = _viewModel.howItText
        binding.lpStartText.text = _viewModel.startText
        binding.lpMidText.text = _viewModel.midText
        binding.lpFinalText.text = _viewModel.finText
        binding.pointsTitleText.text = _viewModel.pointsTitleText
        binding.rewardsTitleText.text = _viewModel.rewardsTitleText
        binding.joinBtn.text = _viewModel.joinBtnText

        binding.pointsRecycler.layoutManager = LinearLayoutManager(context)
        _viewModel.points.observe(viewLifecycleOwner){
            binding.pointsRecycler.adapter = PointsRecyclerAdapter(it)}

        _viewModel.rewards.observe(viewLifecycleOwner){
            binding.rewardsRecycler.adapter = RewardsRecyclerAdapter(it)}
    }
}