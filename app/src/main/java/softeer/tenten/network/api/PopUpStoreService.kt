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

    //    // 팝업 스토어 상세 조회
//    @GET("/api/pop-up/{id}")
//    fun getPopUpInfo(@Path(value = "id") id: String): Call<BaseResponse<LoginResponse>>
//
//    // 팝업 스토어 리뷰 조회
//    @GET("/api/pop-up/{id}/reviews")
//    fun getPopUpReview(@Path(value = "id") id: String): Call<BaseResponse<LoginResponse>>
//
    // 팝업 스토어 이벤트 조회
    @GET("/api/pop-up/{id}/events")
    fun getPopUpEvent(@Path(value = "id") id: String): Call<BaseResponse<LoginResponse>>

    // 팝업 스토어 이벤트 상세 조회
    @GET("/api/pop-up/{id}/events/{eventId}")
    fun getPopUpEventInfo(
        @Path(value = "id") id: String,
        @Path(value = "eventId") eventId: String,
    ): Call<BaseResponse<LoginResponse>>

    //
//    // 팝업 스토어 이벤트 참여(코드 입력)
//    @POST("/api/pop-up/{id}/events/{eventId}")
//    fun participatePopUpEvent(
//        @Path(value = "id") id: String,
//        @Path(value = "eventId") eventId: String,
//    ): Call<BaseResponse<LoginRequest>>
//
//    // 팝업 스토어 투표 내역 조회
//    @GET("/api/pop-up/{id}/vote")
//    fun getPopUpVoteRecord(
//        @Path(value = "id") id: String,
//    ): Call<BaseResponse<LoginRequest>>
//
//    // 팝업 스토어 지역 투표
//    @POST("/api/pop-up/{id}/vote")
//    fun votePopUp(
//        @Path(value = "id") id: String,
//    ): Call<BaseResponse<LoginRequest>>
//
    // 대기 명단 등록
    @POST("/api/pop-up/{id}/waiting")
    fun registerWaitingList(
        @Path(value = "id") id: String,
    ): Call<BaseResponse<LoginRequest>>
//
//    // 대기 인원수 조회(대기번호 포함)
//    @GET("/api/pop-up{id}/waiting")
//    fun getWaitingCnt(
//        @Path(value = "id") id: String,
//    ): Call<BaseResponse<LoginResponse>>

}

