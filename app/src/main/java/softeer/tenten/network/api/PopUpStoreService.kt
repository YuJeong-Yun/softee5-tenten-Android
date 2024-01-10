package softeer.tenten.network.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import softeer.tenten.network.request.LoginRequest
import softeer.tenten.network.response.BaseResponse
import softeer.tenten.network.response.LoginResponse
import softeer.tenten.network.response.PopUpDetail
import softeer.tenten.network.response.PopUpList

interface PopUpStoreService {

    // 팝업 스토어 리스트 조회
    @GET("/api/pop-up")
    fun getPopUpList(): Call<BaseResponse<List<PopUpList>>>

    // 팝업 스토어 상세 조회
    @GET("/api/pop-up/{id}")
    fun getPopUpDetail(
        @Path(value = "id") id: Long,
    ): Call<BaseResponse<PopUpDetail>>


}

