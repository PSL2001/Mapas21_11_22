package com.example.mapas21_11_22

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
        maps.mapType = GoogleMap.MAP_TYPE_SATELLITE
        val settings = maps.uiSettings
        settings.isZoomControlsEnabled = true
        createMarker()
        crearAnimacion()
        activarLocalizacion()
    }

    //---------------------------------------------------------------------------------------------
    private fun activarLocalizacion() {
        //Recomendado por google, para comprobar que el mapa esta listo o no
        if (!::maps.isInitialized) return
        //Comprobamos que tengo los permisos
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            //Tenemos los permisos, activo la localizacion
            maps.isMyLocationEnabled = true
        } else {
            //No tengo los permisos, tengo que pedirlos
            pedirPermisosLocalizacion()

        }
    }

    private fun pedirPermisosLocalizacion() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            && ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            //No es la primera vez
            Toast.makeText(
                this,
                "Para poder utilizar la localizacion, vete a ajustes y a los permisos de la app",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            //Es la primera vez
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                100
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                activarLocalizacion()
            }
        }
    }

    //---------------------------------------------------------------------------------------------
    private fun crearAnimacion() {
        //Añadimos una pequeña animacion
        val coordenadas = LatLng(36.850525016931655, -2.465031842530974)
        maps.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordenadas, 18f), 4500, null
        )
    }

    private fun createMarker() {
        val coordenadas = LatLng(36.850525016931655, -2.465031842530974)
        val mIsnti = MarkerOptions().position(coordenadas).title("IES Al-Ándalus")
        maps.addMarker(mIsnti)

    }
}