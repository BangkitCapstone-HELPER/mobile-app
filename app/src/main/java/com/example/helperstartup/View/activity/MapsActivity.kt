package com.example.helperstartup.View.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.helperstartup.R
import com.example.helperstartup.View.catering.keranjang.OrderConfirmationActivity
import com.example.helperstartup.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private var cameraPosition: CameraPosition? = null

    // default location
    private val defaultLocation = LatLng(-6.8957643, 107.6338462)

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private var selectedLocation: LatLng? = null
    private var selectedAddressLocation: String? = null
    private var selectedMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            selectedLocation = savedInstanceState.getParcelable(KEY_LOCATION)
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION)
            selectedAddressLocation = savedInstanceState.getString(KEY_ADDRESS_STRING)
        }

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Saves the state of the map when the activity is paused.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        mMap.let { map ->
            outState.putParcelable(KEY_CAMERA_POSITION, map.cameraPosition)
            outState.putParcelable(KEY_LOCATION, selectedLocation)
            outState.putString(KEY_ADDRESS_STRING, selectedAddressLocation)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        setupControls(mMap)

        getMyLocation()

        selectedLocation = defaultLocation
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(selectedLocation!!, DEFAULT_ZOOM))
//        selectedMarker = mMap.addMarker(
//            MarkerOptions()
//                .position(lastKnownLocation!!)
//                .title("Dicoding Space")
//                .snippet("Batik Kumeli No.50")
//        )


        mMap.setOnMapClickListener { latLng ->
            selectedMarker?.remove()
            selectedAddressLocation = getCompleteAddressString(latLng.latitude, latLng.longitude)

            if (selectedAddressLocation == null || selectedAddressLocation!!.isEmpty()) {
                selectedAddressLocation = "Lat: ${latLng.latitude} Long: ${latLng.longitude}"
            }
            selectedMarker = mMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title("Titik Antar")
                    .snippet("${selectedAddressLocation}")
            )

            selectedLocation = latLng
        }

    }

    private fun setupControls(mMap: GoogleMap) {
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isScrollGesturesEnabled = true
        mMap.uiSettings.isZoomGesturesEnabled = true

        binding.btnSendLocation.setOnClickListener {
            val intent = Intent(this@MapsActivity, OrderConfirmationActivity::class.java)
            intent.putExtra(KEY_LOCATION, selectedLocation)
            intent.putExtra(KEY_ADDRESS_STRING, selectedAddressLocation)
            startActivity(intent)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun getCompleteAddressString(LATITUDE: Double, LONGITUDE: Double): String {
        var strAdd = ""
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses: List<Address>? = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1)
            if (addresses != null) {
                val returnedAddress: Address = addresses[0]
                val strReturnedAddress = StringBuilder("")
                for (i in 0..returnedAddress.maxAddressLineIndex) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n")
                }
                strAdd = strReturnedAddress.toString()
                Log.w("My Current location address", strReturnedAddress.toString())
            } else {
                Log.w("My Current location address", "No Address returned!")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.w("My Current location address", "Cannot get Address!")
        }
        return strAdd
    }

    companion object {
        private const val DEFAULT_ZOOM = 18f

        // Keys for storing activity state.
        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"
        private const val KEY_ADDRESS_STRING = "address"

    }
}