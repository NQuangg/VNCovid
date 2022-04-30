package com.quang.vncovid.activity

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.quang.vncovid.databinding.ActivityLoginBinding
import java.lang.Exception
import java.util.concurrent.TimeUnit


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var isSentOtp = true
    private var isLogined = true

    private var storedVerificationId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                isSentOtp = true
            }

            override fun onVerificationFailed(e: FirebaseException) {
                if (e is FirebaseAuthInvalidCredentialsException) {
                } else if (e is FirebaseTooManyRequestsException) {
                }
                isSentOtp = true
                Toast.makeText(baseContext, "Có lỗi xảy ra, vui lòng thử lại sau", Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                storedVerificationId = verificationId
                isSentOtp = true
            }
        }


        binding.btnOtp.setOnClickListener {
            if (isSentOtp) {
                isSentOtp = false

                val phoneNumber = binding.etPhoneNumber.text.toString()
                val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                    .setPhoneNumber("+84${phoneNumber}")
                    .setTimeout(60L, TimeUnit.SECONDS)
                    .setActivity(this)
                    .setCallbacks(callbacks)
                    .build()
                PhoneAuthProvider.verifyPhoneNumber(options)
            }
        }

        binding.btnLogin.setOnClickListener {
            if (isLogined) {
                isLogined = false

                val otp = binding.etOtp.text.toString()
                try {
                    val credential = PhoneAuthProvider.getCredential(storedVerificationId, otp)
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                val user = task.result?.user
                                val phoneNumber = user?.phoneNumber!!

                                val intent = Intent(this, ProfileActivity::class.java)
                                intent.putExtra("phone_number", phoneNumber)
                                startActivity(intent)
                                finish()

                            } else {
                                if (task.exception is FirebaseAuthInvalidCredentialsException) {

                                }
                                Toast.makeText(this, "Có lỗi xảy ra, vui lòng thử lại sau", Toast.LENGTH_SHORT).show()
                            }
                            isLogined = true
                        }
                } catch (e: Exception) {
                    isLogined = true
                    Toast.makeText(this, "Có lỗi xảy ra, vui lòng thử lại sau", Toast.LENGTH_SHORT).show()
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