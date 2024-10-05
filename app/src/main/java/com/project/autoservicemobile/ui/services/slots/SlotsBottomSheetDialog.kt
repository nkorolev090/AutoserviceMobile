package com.project.autoservicemobile.ui.services.slots

import android.app.DatePickerDialog
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.project.autoservicemobile.R
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.common.ToCartListener
import com.project.autoservicemobile.databinding.FragmentSlotsBinding
import com.project.autoservicemobile.ui.services.models.ServiceUI
import com.project.common.data.RequestResult
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class SlotsBottomSheetDialog(private val service: ServiceUI, private val toCartListener: ToCartListener) : BottomSheetDialogFragment() {

    private var _binding: FragmentSlotsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.

    private val _viewModel: SlotsViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSlotsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setup()

        return root;
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
        _viewModel.isAuthenticated(object : CoroutinesErrorHandler {
            override fun onError(message: String) {
                Log.d(TAG, "Error! $message")
            }
        })
    }

    private fun setup() {
        with(binding) {
            titleText.text = _viewModel.title

            dateInput.setOnClickListener {
                // on below line we are getting
                // the instance of our calendar.
                val c = Calendar.getInstance()

                // on below line we are getting
                // our day, month and year.
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)

                // on below line we are creating a
                // variable for date picker dialog.
                val datePickerDialog = DatePickerDialog(
                    // on below line we are passing context.
                    requireContext(),
                    { _, year, monthOfYear, dayOfMonth ->
                        // on below line we are setting
                        // date to our edit text.
                        val dat = (dayOfMonth.toString() + "." + (monthOfYear + 1) + "." + year)
                        dateInput.setText(dat)
                    },
                    // on below line we are passing year, month
                    // and day for the selected date in our date picker.
                    year,
                    month,
                    day
                )
                // at last we are calling show
                // to display our date picker dialog.
                datePickerDialog.show()
            }

            slotsRecycler.layoutManager = LinearLayoutManager(context)

            val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
            dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.recycler_divider))
            slotsRecycler.addItemDecoration(dividerItemDecoration)

            slotsRecycler.adapter = SlotsRecyclerAdapter{
                _viewModel.selectedSlot = it
                _viewModel.selectedSlot!!.service = service
            }

            toCartBtn.setText(R.string.to_cart)
            toCartBtn.setOnClickListener {
                if(_viewModel.selectedSlot != null)
                    toCartListener.onAddOrRemoveToCart(_viewModel.selectedSlot!!.service!!)
                dismiss()
            }

            _viewModel.slots.observe(viewLifecycleOwner) {
                when (it) {
                    is RequestResult.Error -> Log.d(TAG, it.message.toString())
                    is RequestResult.Loading -> Log.d(TAG, "Loading")
                    is RequestResult.Success -> {
                        (slotsRecycler.adapter as SlotsRecyclerAdapter).items = it.data
                    }
                }
            }

            _viewModel.isAuth.observe(viewLifecycleOwner) {
                when (it) {
                    is RequestResult.Error -> Log.d(TAG, it.message.toString())
                    is RequestResult.Loading -> Log.d(TAG, "Loading")
                    is RequestResult.Success -> {
                    }
                }
            }
        }
    }

    companion object {
        const val TAG = "SlotsBottomSheetDialog"
    }
}