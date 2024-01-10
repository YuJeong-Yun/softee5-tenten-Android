package softeer.tenten

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Response
import softeer.tenten.databinding.ActivityLoginBinding
import softeer.tenten.network.api.LoginApiService
import softeer.tenten.network.request.LoginRequest
import softeer.tenten.network.response.BaseResponse
import softeer.tenten.network.response.LoginResponse
import softeer.tenten.network.retrofit.RetrofitApi
import softeer.tenten.util.App

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.apply {
            loginLoginButton.setOnClickListener {
                Log.d("login", "login button click")

                val id = loginIdEditText.text.toString()
                val password = loginPasswordEditText.text.toString()

                val loginRequest = LoginRequest(id, password)

                login(loginRequest)
            }
        }
    }

    fun login(loginRequest: LoginRequest) {
        Log.d("login", "in login method")

        val retrofit = RetrofitApi.getInstance().create(LoginApiService::class.java)

        retrofit.login(loginRequest)
            .enqueue(object : retrofit2.Callback<BaseResponse<LoginResponse>> {
                override fun onResponse(
                    call: Call<BaseResponse<LoginResponse>>,
                    response: Response<BaseResponse<LoginResponse>>
                ) {
                    Log.d("login", "in login communication")

                    if (response.isSuccessful) {
                        val body = response.body()!!.data
                        val id = body.userId
                        val nickname = ""

                        try {
                            App.prefs!!.setString("id", id)
                            App.prefs!!.setString("nickname", nickname)

                            val intent = Intent(applicationContext, HomeActivity::class.java)
                            startActivity(intent)

                            finish()
                        } catch (e: Exception) {
                            Log.d("login", e.message!!)
                        }
                    }
                }

                override fun onFailure(call: Call<BaseResponse<LoginResponse>>, t: Throwable) {
                    Log.d("login", "failed login communication")
                    Log.d("login", t.toString())
                }
            })
    }
}