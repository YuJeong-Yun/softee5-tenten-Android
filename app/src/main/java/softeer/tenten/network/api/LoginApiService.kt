package softeer.tenten.network.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import softeer.tenten.network.request.LoginRequest
import softeer.tenten.network.response.BaseResponse
import softeer.tenten.network.response.LoginResponse

interface LoginApiService {
    @POST("/api/login")
    fun login(@Body loginRequest: LoginRequest): Call<BaseResponse<LoginResponse>>
}