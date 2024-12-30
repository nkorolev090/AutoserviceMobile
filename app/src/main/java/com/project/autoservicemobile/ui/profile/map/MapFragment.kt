package com.project.autoservicemobile.ui.profile.map

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.MutableLiveData
import com.project.autoservicemobile.MainActivity
import com.project.autoservicemobile.R
import com.project.autoservicemobile.common.BaseFragment
import com.project.autoservicemobile.databinding.FragmentMapBinding
import com.project.autoservicemobile.ui.profile.map.models.StationUI
import com.project.common.data.RequestResult
import com.project.common.data.StatusCodeEnum
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.runtime.ui_view.ViewProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : BaseFragment() {
    private var _binding: FragmentMapBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: MapViewModel by viewModels()

    private val placeMarkObjects: HashMap<MapObject, StationUI> = hashMapOf()

    private var selectedStation: StationUI? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        MapKitFactory.initialize(requireContext())

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setup()

        return root;
    }

    override fun onStart() {
        super.onStart()

        (requireActivity() as MainActivity).hideNavBar()
        (requireActivity() as MainActivity).changeSystemNavBarColor(com.google.android.material.R.attr.colorOnPrimary)


        MapKitFactory.getInstance().onStart()
        binding.mapview.onStart()
    }

    override fun onStop() {
        binding.mapview.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onResume() {
        super.onResume()

        viewModel.loadStations(coroutinesErrorHandler)
        viewModel.loadUserData(coroutinesErrorHandler)
    }

    override fun onPause() {
        super.onPause()
    }

    private fun setup() {
        with(binding) {
            appBar.setLeftOnClickListener {
                (requireActivity() as MainActivity).goPreviousFragment()
            }

            bottomSheetContainer.bringToFront()

            mapview.map.move(
                CameraPosition(
                    Point(56.9972, 40.9714),
                    12.5f,
                    /* azimuth = */ 150.0f,
                    /* tilt = */ 30.0f
                )
            )

            binding.selectStationButton.setOnClickListener{
                if(selectedStation != null){
                    viewModel.updateDefaultStation(selectedStation!!.id, coroutinesErrorHandler)
                }
            }
        }

        with(viewModel) {
            stations.observe(viewLifecycleOwner) {
                when (it) {
                    is RequestResult.Success -> {
                        binding.errorContainer.visibility = View.GONE
                        binding.bottomShimmerContainer.visibility = View.GONE
                        binding.coordinatesContainer.visibility = View.VISIBLE

                        val view = View(requireContext()).apply {
                            background =
                                AppCompatResources.getDrawable(requireContext(), R.drawable.ic_pin)
                        }

                        for (station in it.data) {
                            val placeMark = binding.mapview.map.mapObjects.addPlacemark(
                                Point(station.latitude, station.longitude),
                                ViewProvider(view)
                            )

                            placeMark.addTapListener(placeMarkTapListener)

                            placeMarkObjects[placeMark] = station
                            if(it.data.indexOf(station) == 0){
                                selectedStation = station
                                setupBottomContainer(station)

                                val stationId = if(user.value is RequestResult.Success){
                                    (user.value as RequestResult.Success).data.client?.defaultStationId
                                }else{
                                    null
                                }
                                setupSelectStationButton(stationId)
                            }
                        }
                    }

                    is RequestResult.Loading -> {
                        setupSelectStationButton(null)
                        binding.errorContainer.visibility = View.GONE
                        binding.coordinatesContainer.visibility = View.INVISIBLE
                        binding.bottomShimmerContainer.visibility = View.VISIBLE
                    }

                    is RequestResult.Error -> {
                        showErrorPage(it.code as? StatusCodeEnum, binding.errorContainer)
                        binding.errorContainer.visibility = View.VISIBLE
                        binding.bottomSheetContainer.hide()
                        binding.bottomShimmerContainer.visibility = View.GONE

                    }
                }
            }

            user.observe(viewLifecycleOwner){
                when (it) {
                    is RequestResult.Error -> {
                        binding.selectStationButton.visibility = View.GONE
                        setupSelectStationButton(null)
                    }

                    is RequestResult.Success -> {
                        setupSelectStationButton(it.data.client?.defaultStationId)
                    }

                    is RequestResult.Loading -> {
                        binding.selectStationButton.visibility = View.GONE
                        setupSelectStationButton(null)
                    }
                }
            }

            stationUpdated.observe(viewLifecycleOwner){
                when{
                    it is RequestResult.Success -> {
                        loadUserData(coroutinesErrorHandler)
                    }
                }
            }
        }
    }

    private fun setupSelectStationButton(defaultStationId: Int?){
        if(selectedStation?.id == defaultStationId){
            binding.selectStationButton.isEnabled = false
            binding.selectStationButton.setText(R.string.station_is_selected)
        }else{
            binding.selectStationButton.isEnabled = true
            binding.selectStationButton.setText(R.string.select_station)
        }

        if(defaultStationId != null && selectedStation != null){
            binding.selectStationButton.visibility = View.VISIBLE
        }else{
            binding.selectStationButton.visibility = View.INVISIBLE
        }
    }

    private fun setupBottomContainer(station: StationUI){
        with(binding) {
            stationTitle.text = station.titleText
            stationAdress.text = station.adressText
            stationCoordinates.text = station.coordinatesText
            stationSchedule.text = station.scheduleText
        }
    }

    private val placeMarkTapListener = MapObjectTapListener { mapObject, point ->
        val station = placeMarkObjects[mapObject]
        if (station != null) {
            selectedStation = station
            setupBottomContainer(station)

            val stationId = if(viewModel.user.value is RequestResult.Success){
                (viewModel.user.value as RequestResult.Success).data.client?.defaultStationId
            }else{
                null
            }
            setupSelectStationButton(stationId)

            binding.bottomSheetContainer.show()
        }
        true
    }
}