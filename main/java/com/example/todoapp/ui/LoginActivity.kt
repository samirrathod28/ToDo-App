package com.example.todoapp.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.todoapp.data.ApiConfig
import com.example.todoapp.data.ApiServices
import com.example.todoapp.databinding.ActivityLoginBinding
import com.example.todoapp.model.LoginBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var api:ApiServices
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedPreferences=getSharedPreferences("user_session", Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean("user_session", false) && sharedPreferences.getString("email", "")!!
                .isNotEmpty())
        {
            val i = Intent(this, ToDoActivity::class.java)
            startActivity(i)
            finish()
        }
        binding.btnLogin.setOnClickListener{

            val email = binding.edtEmail.text.toString()
            val pass = binding.edtPass.text.toString()
            val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

            if (TextUtils.isEmpty(email)) {
                binding.layoutEmail.error = "Enter Email ID"
                binding.layoutPass.isErrorEnabled = false
            } else if (!email.matches(Regex(emailRegex))) {
                binding.layoutEmail.error = "Invalid Email ID"
                binding.layoutPass.isErrorEnabled = false
            } else if (TextUtils.isEmpty(pass)) {
                binding.layoutPass.error = "Enter Password"
                binding.layoutEmail.isErrorEnabled = false
            } else if (email.isNotEmpty() && pass.isNotEmpty()) {
                api = ApiConfig().getApiConfig().create(ApiServices::class.java)
                val call: Call<LoginBody?>? = api.insertData(email, pass)
                call!!.enqueue(object : Callback<LoginBody?> {
                    override fun onResponse(
                        call: Call<LoginBody?>,
                        response: Response<LoginBody?>
                    ) {
                        Toast.makeText(applicationContext, "Login Success", Toast.LENGTH_LONG).show()
                        val editor:SharedPreferences.Editor=sharedPreferences.edit()
                        editor.putString("email",email)
                        sharedPreferences.edit().putBoolean("user_session",true).apply()
                        editor.apply()
                        editor.apply()
                        startActivity(Intent(applicationContext,ToDoActivity::class.java))
                    }
                    override fun onFailure(call: Call<LoginBody?>, t: Throwable) {
                        Toast.makeText(applicationContext, "Error $t", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}