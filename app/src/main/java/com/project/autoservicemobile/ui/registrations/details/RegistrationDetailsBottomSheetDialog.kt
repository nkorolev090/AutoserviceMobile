package com.project.autoservicemobile.ui.registrations.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.project.autoservicedata.registration.models.RegStatusEnum
import com.project.autoservicemobile.R
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.common.DismissListener
import com.project.autoservicemobile.databinding.FragmentRegistrationDetailsBinding
import com.project.autoservicemobile.ui.registrations.models.RegistrationUI
import com.project.autoservicemobile.ui.registrations.models.toStatusColor
import com.project.common.data.RequestResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationDetailsBottomSheetDialog(
    private val registration: RegistrationUI,
    private val listener: DismissListener
) :
    BottomSheetDialogFragment() {

    private var _binding: FragmentRegistrationDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.

    private val _viewModel: RegistrationDetailsViewModel by viewModels()
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setup()

        return root;
    }

    private fun setup() {
        with(binding) {
            totalText.text = _viewModel.totalText
            totalValueText.text = registration.price
            textTitle.text = registration.registrationTitle
            numberText.text = registration.registrationNumber
            regStartOrFinishDate.text = registration.startOrFinishDate

            statusText.text = registration.statusTitle
            statusText.setTextColor(registration.status.toStatusColor(requireContext()))

            closeRegistrationBtn.setText(R.string.close_registration_text)
            closeRegistrationBtn.setOnClickListener {
                _viewModel.closeRegistration(registration.id, coroutinesErrorHandler)
            }

            closeBtnContainer.visibility =
                if (registration.status == RegStatusEnum.APPROVED || registration.status == RegStatusEnum.PROCESSING) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            regServicesRecycler.layoutManager = GridLayoutManager(context, 2)

            val dividerVerticalItemDecoration =
                DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
            dividerVerticalItemDecoration.setDrawable(resources.getDrawable(R.drawable.recycler_divider))
            val dividerHorizontalItemDecoration =
                DividerItemDecoration(requireContext(), RecyclerView.HORIZONTAL)
            dividerHorizontalItemDecoration.setDrawable(resources.getDrawable(R.drawable.recycler_divider))

            regServicesRecycler.addItemDecoration(dividerHorizontalItemDecoration)
            regServicesRecycler.addItemDecoration(dividerVerticalItemDecoration)

            regServicesRecycler.adapter =
                RegServicesRecyclerAdapter(registration.slots.orEmpty(), {}, {})

            _viewModel.closed.observe(viewLifecycleOwner) {
                when {
                    it is RequestResult.Success -> {
                        dismiss()
                        listener.onChildDismiss()
                    }
                }
            }
        }
    }

    companion object {
        val coroutinesErrorHandler = object : CoroutinesErrorHandler {
            override fun onError(message: String) {
                Log.d(TAG, "Error! $message")
            }
        }

        val TAG = this::class.simpleName ?: "ErrorClassName"
    }
}