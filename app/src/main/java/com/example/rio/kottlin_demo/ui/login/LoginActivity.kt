package com.example.rio.kottlin_demo.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import com.example.rio.kottlin_demo.R
import com.example.rio.kottlin_demo.databinding.ActivityLoginBinding
import com.example.rio.kottlin_demo.ui.base.BaseActivity
import com.example.rio.kottlin_demo.ui.register.RegisterActivity
import com.example.rio.kottlin_demo.utils.AppConstants
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import android.nfc.NfcAdapter.EXTRA_DATA
import android.app.Activity
import com.example.rio.kottlin_demo.ui.main.MainActivity


class LoginActivity : BaseActivity<LoginViewModel>() {

    private lateinit var activityLoginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getViewReferences()
        registerEvents()
    }

    override fun getViewReferences() {
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
        activityLoginBinding.viewModel = viewModel
    }

    override fun initializeViews() {

    }

    override fun registerEvents() {
        viewModel.getToRegisterEvent().observe(this, Observer {

            val starter = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivityForResult(starter,AppConstants.REQUEST_CODE_TO_REGISTER_ACTIVITY)
        })
    }

    override fun observeDataChange() {

    }

    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser != null) {

//            FirebaseAuth.getInstance().signOut()
            Log.e("Rio", "currentUser da login  :")
        } else
            Log.e("Rio", "currentUser chuaaa login  :")
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Kiểm tra requestCode có trùng với REQUEST_CODE vừa dùng
        if (requestCode == AppConstants.REQUEST_CODE_TO_REGISTER_ACTIVITY) {

            // resultCode được set bởi DetailActivity
            // RESULT_OK chỉ ra rằng kết quả này đã thành công
            if (resultCode == Activity.RESULT_OK) {
                // Nhận dữ liệu từ Intent trả về
                val result = data!!.getStringExtra("INFO_USER")

                var intent= Intent(this@LoginActivity, MainActivity::class.java)
                intent.putExtra("INFO_USER",result)
                startActivity(intent)
            } else {
                // DetailActivity không thành công, không có data trả về.
            }
        }
    }
}
