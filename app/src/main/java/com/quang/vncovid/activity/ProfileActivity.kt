package com.quang.vncovid.activity

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.quang.vncovid.data.model.ProfileModel
import com.quang.vncovid.data.model.UserModel
import com.quang.vncovid.databinding.ActivityProfileBinding
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private var isSent = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val etName = binding.etName
        val radioButton1 = binding.radio1
        val radioButton2 = binding.radio2
        val etBirthday = binding.etBirthday
        val etAddress = binding.etAddress
        val loading = binding.componentLoading.loading

        val phoneNumber = FirebaseAuth.getInstance().currentUser?.phoneNumber ?: "0989767127"
        val firestore = FirebaseFirestore.getInstance()

        val docRef = firestore.collection("account").document(phoneNumber)
        docRef.get()
            .addOnSuccessListener { document ->
                val userModel = document.toObject(UserModel::class.java)
                if (userModel != null) {
                    val profile = userModel.profile
                    etName.setText(profile.name)
                    if (profile.isMale) {
                        radioButton1.isChecked = true
                    } else {
                        radioButton2.isChecked = false
                    }
                    etBirthday.setText(profile.birthday)
                    etAddress.setText(profile.address)
                } else {
                    val now = LocalDateTime.now()
                    etBirthday.setText(now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                }
                loading.visibility = View.GONE
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Có lỗi xảy ra.", Toast.LENGTH_SHORT).show()
                loading.visibility = View.GONE
            }

        etBirthday.setOnClickListener {
            val dateString = etBirthday.text.toString()
            val date =  LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            DatePickerDialog(
                this,
                { _, year, month, day ->
                    val selectedDateString = "${if (day < 10) "0$day" else day}/${if (month < 10) "0$month" else month}/$year"
                    etBirthday.setText(selectedDateString)
                },
                date.year,
                date.monthValue - 1,
                date.dayOfMonth
            ).show()

        }

        binding.button.setOnClickListener {
            if (isSent) {
                loading.visibility = View.VISIBLE
                isSent = false
                val name = etName.text.toString()
                val birthday = etBirthday.text.toString()
                val address = etAddress.text.toString()

                if (name.isNotEmpty() && address.isNotEmpty()) {
                    val userModel = UserModel(
                        profile = ProfileModel(
                            name = name,
                            isMale = radioButton1.isChecked,
                            birthday = birthday,
                            address = address,
                        )
                    )

                    firestore.collection("account").document(phoneNumber)
                        .set(userModel)
                        .addOnSuccessListener {
                            loading.visibility = View.GONE
                            startActivity(Intent(this, ReportActivity::class.java))
                            finish()
                        }
                        .addOnFailureListener { e ->
                            loading.visibility = View.GONE
                            Toast.makeText(
                                this,
                                "Có lỗi xảy ra",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                } else {
                    isSent = true
                    Toast.makeText(this, "Điền đầy đủ các mục", Toast.LENGTH_LONG).show()
                    loading.visibility = View.GONE
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