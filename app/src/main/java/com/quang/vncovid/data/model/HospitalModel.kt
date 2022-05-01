package com.quang.vncovid.data.model

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint
import com.google.maps.android.clustering.ClusterItem

data class HospitalModel(val name: String = "", val position: GeoPoint = GeoPoint(0.0, 0.0))
