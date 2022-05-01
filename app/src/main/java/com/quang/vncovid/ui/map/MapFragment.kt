package com.quang.vncovid.ui.map

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.quang.vncovid.BuildConfig
import com.quang.vncovid.R
import com.quang.vncovid.data.model.HospitalModel
import com.quang.vncovid.data.model.MarkerModel
import com.quang.vncovid.databinding.FragmentMapBinding


class MapFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var googleMap: GoogleMap
    private val firestore = FirebaseFirestore.getInstance()
    private var isFirstRequest = true
    private var onMapReady: Boolean = false
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
                requestLocation()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
                requestLocation()
            }
            else -> {
                // No location access granted.
                AlertDialog.Builder(requireContext())
                    .setTitle("Chức năng cần sử dụng vị trí hiện tại của bạn")
                    .setNegativeButton(
                        "Hủy"
                    ) { _, _ -> }
                    .setPositiveButton(
                        "Đồng ý"
                    ) { _, _ ->
                        val uri: Uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri)
                        startActivity(intent)
                    }
                    .create()
                    .show()
            }
        }
        isFirstRequest = false
    }

    override fun onResume() {
        super.onResume()
        if (isFirstRequest && onMapReady) {
            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                )
            )
        }
    }

    override fun onPause() {
        super.onPause()
        isFirstRequest = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        onMapReady = true
        firestore.collection("hospital").get()
            .addOnSuccessListener { result ->
                val listMarker = mutableListOf<MarkerModel>()
                for (document in result) {
                    val hospitalModel = document.toObject(HospitalModel::class.java)
                    listMarker.add(
                        MarkerModel(
                            hospitalModel.name,
                            LatLng(
                                hospitalModel.position.latitude,
                                hospitalModel.position.longitude
                            )
                        )
                    )
                }

                setUpClusterer(listMarker)
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Có lỗi xảy ra.", Toast.LENGTH_SHORT).show()
            }

        // NEU
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(21.000752966009475, 105.84240880656974), 13.5f), object: GoogleMap.CancelableCallback {
            override fun onFinish() {
                locationPermissionRequest.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                    )
                )
            }

            override fun onCancel() {
            }
        })
    }

    private fun setUpClusterer(listMarker: MutableList<MarkerModel>) {
        val markerIcon = bitmapDescriptorFromVector(requireContext(), R.drawable.ic_marker)

        val clusterManager = ClusterManager<MarkerModel>(context, googleMap)
        clusterManager.renderer = object : DefaultClusterRenderer<MarkerModel>(
            context, googleMap,
            clusterManager
        ) {
            override fun onBeforeClusterItemRendered(
                item: MarkerModel,
                markerOptions: MarkerOptions
            ) {
                markerOptions.icon(markerIcon)
                super.onBeforeClusterItemRendered(item, markerOptions)
            }

            override fun getColor(clusterSize: Int): Int {
                return ContextCompat.getColor(requireContext(), R.color.marker_color)
            }
        }

        googleMap.setOnCameraIdleListener(clusterManager)

        clusterManager.addItems(listMarker)
    }

    private fun requestLocation() {
        val client = LocationServices.getFusedLocationProviderClient(requireActivity())

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            client.lastLocation.addOnCompleteListener {
                val location = it.result
                if (location != null) {
                    googleMap.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                location.latitude,
                                location.longitude
                            ), 13.5f
                        )
                    )
                    googleMap.isMyLocationEnabled = true
                    googleMap.uiSettings.isMyLocationButtonEnabled = true
                    googleMap.setMapStyle(MapStyleOptions(getMapStyle()))
                }
            }
        }
    }

    private fun getMapStyle(): String {
        return "[\n" +
                "  {\n" +
                "    \"featureType\": \"administrative\",\n" +
                "    \"elementType\": \"geometry\",\n" +
                "    \"stylers\": [\n" +
                "      {\n" +
                "        \"visibility\": \"off\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"featureType\": \"poi\",\n" +
                "    \"stylers\": [\n" +
                "      {\n" +
                "        \"visibility\": \"off\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"featureType\": \"road\",\n" +
                "    \"elementType\": \"labels.icon\",\n" +
                "    \"stylers\": [\n" +
                "      {\n" +
                "        \"visibility\": \"off\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"featureType\": \"transit\",\n" +
                "    \"stylers\": [\n" +
                "      {\n" +
                "        \"visibility\": \"off\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "]";
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }


}