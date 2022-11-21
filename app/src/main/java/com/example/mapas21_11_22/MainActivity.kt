package com.example.mapas21_11_22

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var maps: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        crearFragment()
    }

    private fun crearFragment() {
        var mapFragment = supportFragmentManager.findFragmentById(R.id.fmaps) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        maps = p0
        createMarker()
    }

    private fun createMarker() {
        val coordenadas = LatLng(36.850525016931655, -2.465031842530974)
        val mIsnti = MarkerOptions().position(coordenadas).title("IES Al-Ándalus")
        maps.addMarker(mIsnti)
        //Añadimos una pequeña animacion
        maps.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordenadas, 10f), 4500, null
        )
    }
}