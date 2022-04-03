package com.quang.vncovid.data.model

import com.google.android.gms.maps.model.LatLng

data class MarkerModel(val title: String, val position: LatLng)

val listMarkerModel = listOf(
    MarkerModel("Bệnh viện Đa khoa Hà Nội", LatLng(21.019604535152595, 105.85601274622631)),
    MarkerModel("Bệnh viện Đa khoa XANH PÔN", LatLng(21.03258627086195, 105.83644310321347)),
    MarkerModel("Bệnh viện Nhi Trung Ương", LatLng(21.027377446760514, 105.80940376241061)),
    MarkerModel("Phòng khám Đa Khoa Quốc Tế Hà Nội", LatLng(21.011442320111843, 105.83895451875252)),
    MarkerModel("Bệnh viện Thanh Nhàn", LatLng(21.005786521803433, 105.85993254553861)),
    MarkerModel("Bệnh viện Quân Y 354", LatLng(21.041978337495735, 105.81275293750899)),
    MarkerModel("Bệnh viện Bưu Điện", LatLng(20.99237953236961, 105.83291477565506)),
    MarkerModel("Bệnh viện Xây Dựng", LatLng(20.995558481963013, 105.79667717075237)),
    MarkerModel("Bệnh viện Bạch Mai", LatLng(21.00047362631172, 105.8410990294814)),
)
