package com.quang.vncovid.data.model

data class RecommendModel(val id: Int, val name: String, val imagePath: ArrayList<String>)

val listRecommendModel = listOf(
    RecommendModel(id = 1, name = "Thông điệp 5K", imagePath = arrayListOf("5K.jpg")),
    RecommendModel(id = 2, name = "Thông điệp 5T", imagePath = arrayListOf("5T.jpg")),
    RecommendModel(
        id = 3,
        name = "Khuyến cáo về vaccine Astrazeneca",
        imagePath = arrayListOf(
            "astra_1.jpg",
            "astra_2.jpg",
            "astra_3.jpg",
            "astra_4.jpg",
            "astra_5.jpg",
        )
    ),
    RecommendModel(
        id = 4,
        name = "Khuyến cáo về vaccine Moderna",
        imagePath = arrayListOf(
            "moderna_1.jpg",
            "moderna_2.jpg",
            "moderna_3.jpg",
            "moderna_4.jpg",
            "moderna_5.jpg",
            "moderna_6.jpg",
        )
    ),
    RecommendModel(
        id = 4,
        name = "Khuyến cáo về vaccine Pfizer",
        imagePath = arrayListOf(
            "pfizer_1.jpg",
            "pfizer_2.jpg",
            "pfizer_3.jpg",
            "pfizer_4.jpg",
            "pfizer_5.jpg",
        )
    ),
    RecommendModel(
        id = 5,
        name = "Khuyến cáo về vaccine Hayat",
        imagePath = arrayListOf(
            "hayat_1.jpg",
            "hayat_2.jpg",
            "hayat_3.jpg",
        )
    ),
    RecommendModel(
        id = 6,
        name = "Khuyến cáo về vaccine Vero Cell",
        imagePath = arrayListOf(
            "vero_1.jpg",
            "vero_2.jpg",
            "vero_3.jpg",
            "vero_4.jpg",
            "vero_5.jpg",
        )
    ),
    RecommendModel(
        id = 7,
        name = "Khuyến cáo về vaccine Abdala",
        imagePath = arrayListOf(
            "abdala_1.jpg",
            "abdala_2.jpg",
            "abdala_3.jpg",
        )
    ),
)