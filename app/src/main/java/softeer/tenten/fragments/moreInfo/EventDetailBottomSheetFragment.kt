package softeer.tenten.fragments.moreInfo

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import softeer.tenten.R
import softeer.tenten.dialog.OnDialogResultListener
import softeer.tenten.network.api.EventApiService
import softeer.tenten.network.request.EventParticipationRequest
import softeer.tenten.network.response.BaseResponse
import softeer.tenten.network.retrofit.RetrofitApi
import softeer.tenten.util.App

class EventDetailBottomSheetFragment(val popUpId: Long, val eventId: Long) : BottomSheetDialogFragment() {
    private var mListener: OnDialogResultListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_detail_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val clearButton = view.findViewById<ImageButton>(R.id.eventBottomSheetXButton)
        val participateBtn = view.findViewById<AppCompatButton>(R.id.participateBtn)

        // 다이어로그 닫기
        clearButton.setOnClickListener {
            dismiss()
        }

        // 참여 인증 버튼 클릭
        participateBtn.setOnClickListener {
            val inputVal = view.findViewById<EditText>(R.id.eventBottomSheetCode).text.toString()

            participateEvent(inputVal)
        }
    }

    fun participateEvent(code: String){
        val retrofit = RetrofitApi.getInstance().create(EventApiService::class.java)
        val userId = App.prefs.getString("id", "")

        retrofit.participateEvent(popUpId, eventId, EventParticipationRequest(userId, code)).enqueue(object: Callback<BaseResponse<String>>{
            override fun onResponse(
                call: Call<BaseResponse<String>>,
                response: Response<BaseResponse<String>>
            ) {
                val alertDialogBuilder = AlertDialog.Builder(context) // 인증 코드 유효성 검사 결과 알림 다이어로그

                if(response.isSuccessful){
                    alertDialogBuilder.setMessage("참여 완료되었습니다.")

                    alertDialogBuilder.setPositiveButton("확인") { dialog, which ->
                    }

                    // 확인 버튼 클릭시 이벤트 상세 페이지로 다시 이동
                    sendResultToActivity(true)
//                // 취소 버튼 설정 및 클릭 이벤트 처리
//                alertDialogBuilder.setNegativeButton("취소") { dialog, which ->
//                }

                    // 다이얼로그 출력
                    val alertDialog: AlertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                    dismiss()
                } else{
                    alertDialogBuilder.setMessage("유효하지 않은 코드 입니다.")

                    alertDialogBuilder.setPositiveButton("확인") { dialog, which ->
                    }

                    sendResultToActivity(false)

                    val alertDialog: AlertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                    dismiss()
                }
            }

            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {

            }

        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mListener = context as OnDialogResultListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement OnDialogResultListener")
        }
    }

    private fun sendResultToActivity(result: Boolean) {
        mListener?.onEventParticipateDialogResult(result)
    }
}