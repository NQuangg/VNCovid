package com.quang.vncovid.activity

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.quang.vncovid.R
import com.quang.vncovid.data.model.ReportModel
import com.quang.vncovid.databinding.ActivityDetailReportBinding
import com.quang.vncovid.databinding.ActivityReportBinding

class DetailReportActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailReportBinding
    private var isConfirm = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailReportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val rgThroughPlace = binding.rgThroughPlace
        val radioThroughPlaceNo = binding.radioThroughPlaceNo
        val radioThroughPlaceYes = binding.radioThroughPlaceYes
        val tilThroughPlace = binding.tilThroughPlace
        val etThroughPlace = binding.etThroughPlace

        val rgHaveSympton = binding.rgHaveSympton
        val radioHaveSymptonNo = binding.radioHaveSymptonNo
        val radioHaveSymptonYes = binding.radioHaveSymptonYes
        val tilHaveSympton = binding.tilHaveSympton
        val etHaveSympton = binding.etHaveSympton

        val radioPatienHadCovidNo = binding.radioPatienHadCovidNo
        val radioPatienHadCovidYes = binding.radioPatienHadCovidYes
        val radioForeignerHadCovidNo = binding.radioForeignerHadCovidNo
        val radioForeignerHadCovidYes = binding.radioForeignerHadCovidYes
        val radioPatienHadSymptomNo = binding.radioPatienHadSymptomNo
        val radioPatienHadSymptomYes = binding.radioPatienHadSymptomYes

        if (intent?.getStringExtra("report") != null) {
            val report = Gson().fromJson(intent.getStringExtra("report"), ReportModel::class.java)

            if (report.throughPlace) {
                radioThroughPlaceYes.isChecked = true
                tilThroughPlace.visibility = View.VISIBLE
                etThroughPlace.setText(report.place)
            } else {
                radioThroughPlaceNo.isChecked = true
            }

            if (report.haveSymptom) {
                radioHaveSymptonYes.isChecked = true
                tilHaveSympton.visibility = View.VISIBLE
                etHaveSympton.setText(report.symptom)
            } else {
                radioHaveSymptonNo.isChecked = true
            }

            if (report.patientHadCovid) {
                radioPatienHadCovidYes.isChecked = true
            } else {
                radioPatienHadCovidNo.isChecked = true
            }

            if (report.foreignerHadCovid) {
                radioForeignerHadCovidYes.isChecked = true
            } else {
                radioForeignerHadCovidNo.isChecked = true
            }

            if (report.patientHadSymptom) {
                radioPatienHadSymptomYes.isChecked = true
            } else {
                radioPatienHadSymptomNo.isChecked = true
            }
        }

        rgThroughPlace.setOnCheckedChangeListener { _, id ->
            if (id == R.id.radio_through_place_yes) {
                tilThroughPlace.visibility = View.VISIBLE
            } else {
                tilThroughPlace.visibility = View.GONE
            }
        }

        rgHaveSympton.setOnCheckedChangeListener { _, id ->
            if (id == R.id.radio_have_sympton_yes) {
                tilHaveSympton.visibility = View.VISIBLE
            } else {
                tilHaveSympton.visibility = View.GONE
            }
        }

        binding.btnConfirm.setOnClickListener {
            if (isConfirm) {
                isConfirm = false

                val throughPlace = radioThroughPlaceYes.isChecked
                val haveSymptom = radioHaveSymptonYes.isChecked
                val patientHadCovid = radioPatienHadCovidYes.isChecked
                val foreignerHadCovid = radioForeignerHadCovidYes.isChecked
                val patientHadSymptom = radioPatienHadSymptomYes.isChecked


                val phoneNumber =
                    FirebaseAuth.getInstance().currentUser?.phoneNumber ?: "+84989767127"
                val firestore = FirebaseFirestore.getInstance()
                if (intent?.getStringExtra("report") != null) {
                    val report = Gson().fromJson(intent.getStringExtra("report"), ReportModel::class.java)

                    val docRef =
                        firestore.collection("account").document(phoneNumber).collection("report")
                    val updateReport = ReportModel(
                        id = report.id,
                        date = report.date,
                        foreignerHadCovid = foreignerHadCovid,
                        haveSymptom = haveSymptom,
                        patientHadCovid = patientHadCovid,
                        patientHadSymptom = patientHadSymptom,
                        place = if (throughPlace) etThroughPlace.text.toString() else "",
                        symptom = if (haveSymptom) etThroughPlace.text.toString() else "",
                        throughPlace = throughPlace,
                    )

                    docRef.document(report.id).set(updateReport)
                        .addOnSuccessListener { result ->
                            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                            setResult(RESULT_OK, Intent().apply {
                                putExtra("create_or_update", true)
                            })
                            finish()
                        }
                        .addOnFailureListener { exception ->
                            isConfirm = true
                            Toast.makeText(this, "Có lỗi xảy ra.", Toast.LENGTH_SHORT).show()
                        }

                } else {

                    val docRef =
                        firestore.collection("account").document(phoneNumber).collection("report")
                    val documentId = docRef.document().id
                    val newReport = ReportModel(
                        id = documentId,
                        date = Timestamp.now(),
                        foreignerHadCovid = foreignerHadCovid,
                        haveSymptom = haveSymptom,
                        patientHadCovid = patientHadCovid,
                        patientHadSymptom = patientHadSymptom,
                        place = if (throughPlace) etThroughPlace.text.toString() else "",
                        symptom = if (haveSymptom) etThroughPlace.text.toString() else "",
                        throughPlace = throughPlace,
                    )
                    docRef.document().set(newReport)
                        .addOnSuccessListener { result ->
                            Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show()
                            setResult(RESULT_OK, Intent().apply {
                                putExtra("create_or_update", true)
                            })
                            finish()
                        }
                        .addOnFailureListener { exception ->
                            isConfirm = true
                            Toast.makeText(this, "Có lỗi xảy ra.", Toast.LENGTH_SHORT).show()
                        }
                }

            }
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v: View? = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm: InputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish();
        return true;
    }
}