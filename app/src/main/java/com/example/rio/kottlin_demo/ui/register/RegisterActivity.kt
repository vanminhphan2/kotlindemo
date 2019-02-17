package com.example.rio.kottlin_demo.ui.register

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.example.rio.kottlin_demo.R
import com.example.rio.kottlin_demo.databinding.ActivityRegisterBinding
import com.example.rio.kottlin_demo.ui.base.BaseActivity
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.*
import android.app.Activity
import android.nfc.NfcAdapter.EXTRA_DATA
import android.content.Intent






class RegisterActivity : BaseActivity<RegisterViewModel>() {

    private lateinit var activityRegisterBinding: ActivityRegisterBinding

    val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewReferences()
        registerEvents()
    }

    override fun getViewReferences() {
        activityRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RegisterViewModel::class.java)
        activityRegisterBinding.viewModel = viewModel
    }

    override fun initializeViews() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun registerEvents() {

        viewModel.getViewData().observe(this, Observer {
            activityRegisterBinding.viewModel = viewModel
        })

        viewModel.onRegisterSuccessEvent().observe(this, Observer {
            hideLoading()
            Log.e("Rio", "onRegisterSuccessEvent: okokokokook")
            var data = Intent()
            data.putExtra("INFO_USER", viewModel.userInfo)
            setResult(Activity.RESULT_OK, data)
            finish()
        })

        viewModel.onLoadingEvent().observe(this, Observer {t->
            if (t!!)
                showLoading()
            else hideLoading()
        })

        viewModel.onShowMessageEvent().observe(this, Observer {
            Toast.makeText(this, viewModel.registerViewData.message, Toast.LENGTH_SHORT).show()
        })

        viewModel.onVerifyCodeEvent().observe(this, Observer {

            verifyVerificationCode()
        })

        viewModel.onReceiveCodeEvent().observe(this, Observer {

            activityRegisterBinding.etCode.setText(viewModel.registerViewData.code)
        })
    }

    override fun observeDataChange() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun verifyVerificationCode() {
        Log.e(
            "Rio",
            "verifyVerificationCode  :" + viewModel.registerViewData.verificationId + "---code: " + viewModel.registerViewData.code
        )
        val credential =
            PhoneAuthProvider.getCredential(viewModel.registerViewData.verificationId, viewModel.registerViewData.code)
        //signing the user
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this@RegisterActivity,
                OnCompleteListener<AuthResult> { task ->
                    if (task.isSuccessful) {

                        Log.e("Rio", "task.isSuccessful  id: "+ firebaseAuth.getCurrentUser()!!.uid)
                        viewModel.registerViewData.idUser = firebaseAuth.getCurrentUser()!!.uid
                        viewModel.verifySuccess()
                    } else {
                        Log.e("Rio", "task.exception  :" + task.exception)
                        var message = "Somthing is wrong, we will fix it soon..."
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            message = "Invalid code entered..."
                        }
                    }
                })
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_CANCELED)
        super.onBackPressed()
    }
}
