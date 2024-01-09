package softeer.tenten.network.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import softeer.tenten.network.request.WaitingRequest
import softeer.tenten.network.response.BaseResponse
import softeer.tenten.network.response.WaitingResponse

interface WaitingApiService {
    @POST("/api/pop-up/{id}/waiting")
    fun registerWaiting(@Path(value = "id") popUpId: Long, @Body waitingRequest: WaitingRequest): Call<BaseResponse<WaitingResponse>>

    @GET("/api/pop-up/{id}/waiting")
    fun getWaitingInformation(@Path(value = "id") popUpId: Long, @Query(value = "userId") userId: String) : Call<BaseResponse<WaitingResponse>>
}