package com.example.tresstech.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.tresstech.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val apiKey = "AIzaSyDJpD0ciVjD_zh5PrNVscNXSj9QqQRbBmM"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val buttonBack = findViewById<Button>(R.id.btnBack)
        buttonBack.setOnClickListener {
            finish()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        getLocationAccess()
    }

    private fun getLocationAccess() {
        Log.e("tag","getLocationAkses berhasil bang")
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            map.isMyLocationEnabled = true
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val userLocation = LatLng(location.latitude, location.longitude)
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))
                    map.addMarker(MarkerOptions().position(userLocation).title("Your Location"))

                    // Cari lokasi "barber" di sekitar userLocation
                    findNearbyBarbers(userLocation)
                }
            }
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
    }

    private fun findNearbyBarbers(userLocation: LatLng) {
        Log.e("tag","findNearbybarbers berhasil bang")

        val location = "${userLocation.latitude},${userLocation.longitude}"
        val radius = 2000
        val keyword = "laundry"

        RetrofitClient.googlePlacesService.getNearbyPlaces(location, radius, keyword, apiKey)
            .enqueue(object : Callback<PlacesResponse> {
                override fun onResponse(call: Call<PlacesResponse>, response: Response<PlacesResponse>) {
                    if (response.isSuccessful) {
                        val places = response.body()?.results ?: emptyList()
                        Log.d("tag", "Places found: ${places.size}")
                        for (place in places) {
                            val placeLocation = LatLng(place.geometry.location.lat, place.geometry.location.lng)
                            map.addMarker(MarkerOptions()
                                .position(placeLocation)
                                .title(place.name)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)))
                            Log.d("tag", "Marker added for: ${place.name}") // Tambahkan log ini
                        }
                    }else{
                        Log.e("tag", "API Response failed: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<PlacesResponse>, t: Throwable) {
                    Log.e("tag", "API Call failed: ${t.message}")
                    // Handle failure
                }
            })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        Log.e("tag","getLocationAkses berhasil bang")

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLocationAccess()
        }else{
            Log.e("tag", "Location permission not granted")
        }
    }
}