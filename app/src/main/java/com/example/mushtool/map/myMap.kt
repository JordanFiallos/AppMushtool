package com.example.mushtool.map

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.mushtool.MyMushroom
import com.example.mushtool.R
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.MinimapOverlay
import org.osmdroid.views.overlay.ScaleBarOverlay
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


class myMap : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Crea un MapView
        val mapView = MapView(this)

        // Obtiene los DisplayMetrics
        val displayMetrics = resources.displayMetrics

        // Recupera las fotos con información de ubicación desde Firestore
        val db = Firebase.firestore
        db.collection("profiles")
            .whereNotEqualTo("mLatitude", null)
            .whereNotEqualTo("mLongitude", null)
            .get()
            .addOnSuccessListener { documents ->
                val photos = mutableListOf<MyMushroom>()
                for (document in documents) {
                    val photo = document.toObject(MyMushroom::class.java)
                    photos.add(photo)
                }
                setContent {
                    MapsMush(photos, mapView, displayMetrics)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("MyMap", "Error getting photos with location info", exception)
            }
    }
}

@Composable
fun MapsMush(photos: List<MyMushroom>,  mapView: MapView, dm: DisplayMetrics) {

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(
            factory = { context ->
                mapView.apply {
                    // Calcular los límites de la región que abarca todas las ubicaciones
                    var minLat = Double.MAX_VALUE
                    var maxLat = Double.MIN_VALUE
                    var minLon = Double.MAX_VALUE
                    var maxLon = Double.MIN_VALUE

                    for (photo in photos) {
                        val latitude = photo.mLatitude ?: 0.0
                        val longitude = photo.mLongitude ?: 0.0

                        minLat = minOf(minLat, latitude)
                        maxLat = maxOf(maxLat, latitude)
                        minLon = minOf(minLon, longitude)
                        maxLon = maxOf(maxLon, longitude)
                    }

                    val centerLat = (minLat + maxLat) / 2
                    val centerLon = (minLon + maxLon) / 2
                    val center = GeoPoint(centerLat, centerLon)

                    controller.setCenter(center)
                    controller.setZoom(10.0)

                    // Agrega marcadores para cada ubicación de foto
                    photos.forEach { photo ->
                        val photoLocation = GeoPoint(photo.mLatitude ?: 0.0, photo.mLongitude ?: 0.0)
                        val marker = Marker(this)
                        marker.position = photoLocation
                        marker.title = photo.mComment

                        // Personaliza el marcador con la imagen de la seta
                        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.buscarseta)
                        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false)
                        val image = BitmapDrawable(resources, scaledBitmap)
                        marker.icon = image

                        overlayManager.add(marker)
                    }

                    // Agrega la ubicación actual
                    val locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), this)
                    locationOverlay.enableMyLocation()
                    overlays.add(locationOverlay)

                    // Agrega la brújula
                    val compassOverlay = CompassOverlay(context, InternalCompassOrientationProvider(context), this)
                    compassOverlay.enableCompass()
                    overlays.add(compassOverlay)

                    // Agrega la superposición de gestos de rotación
                    val rotationGestureOverlay = RotationGestureOverlay(context, this)
                    rotationGestureOverlay.isEnabled = true
                    this.setMultiTouchControls(true)
                    overlays.add(rotationGestureOverlay)

                    //Agrega un miniMapa en el Mapa
                    val MinimapOverlay = MinimapOverlay(context,mapView.handler)
                    MinimapOverlay.width
                    MinimapOverlay.height
                    overlays.add(MinimapOverlay)

                    /*// Agrega la superposición de líneas de cuadrícula de latitud/longitud
                    val gridlineOverlay = LatLonGridlineOverlay2()
                    overlays.add(gridlineOverlay)*/

                    // Agrega la barra de escala del mapa
                    val scaleBarOverlay = ScaleBarOverlay(mapView)
                    scaleBarOverlay.setCentred(true)

                    // Ubicación de la escala del mapa
                    scaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10)
                    overlays.add(scaleBarOverlay)
                }
            }
        )
    }
}