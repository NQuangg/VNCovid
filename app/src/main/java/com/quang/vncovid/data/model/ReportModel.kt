package com.quang.vncovid.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp
import com.google.type.Date

data class ReportModel(
    var id: String = "",
    @ServerTimestamp
    val date: Timestamp = Timestamp.now(),
    val foreignerHadCovid: Boolean = false,
    val haveSymptom: Boolean = false,
    val patientHadCovid: Boolean = false,
    val patientHadSymptom: Boolean = false,
    val throughPlace: Boolean = false,
    val place: String = "",
    val symptom: String = "",
)
