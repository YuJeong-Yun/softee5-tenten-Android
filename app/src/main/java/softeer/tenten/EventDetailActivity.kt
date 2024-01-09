package softeer.tenten

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import softeer.tenten.dialog.OnDialogResultListener
import softeer.tenten.fragments.moreInfo.EventDetailBottomSheetFragment
import softeer.tenten.network.api.EventApiService
import softeer.tenten.network.response.BaseResponse
import softeer.tenten.network.response.EventDetailResponse
import softeer.tenten.network.retrofit.RetrofitApi
import softeer.tenten.util.App

class EventDetailActivity : AppCompatActivity(), OnDialogResultListener {
    private lateinit var participateBtn: AppCompatButton
    private lateinit var completeParticipateBtn: AppCompatButton

    private lateinit var name: TextView
    private lateinit var title: TextView
    private lateinit var content: TextView

    private lateinit var image: ImageView

    private lateinit var bottomSheet: EventDetailBottomSheetFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        val popUpId = intent.getLongExtra("popUpId", 0)
        val eventId = intent.getLongExtra("eventId", 0)

        bottomSheet = EventDetailBottomSheetFragment(popUpId, eventId)

        participateBtn = findViewById(R.id.participateBtn) // 참여 인증 버튼
        completeParticipateBtn = findViewById(R.id.completeParticipateBtn) // 참여 완료 버튼
        name = findViewById(R.id.eventDetailName)
        image = findViewById(R.id.eventDetailImg)
        title = findViewById(R.id.eventDetailTitleContent)
        content = findViewById(R.id.eventDetailContent)

        participateBtn.setOnClickListener {
            bottomSheet.show(supportFragmentManager, "open bottom sheet")
        }

        getEventDetail(popUpId, eventId)
    }

    private fun getEventDetail(popUpId: Long, eventId: Long){
        val retrofit = RetrofitApi.getInstance().create(EventApiService::class.java)
        val userId = App.prefs.getString("id", "")

        retrofit.getEventDetail(popUpId, eventId, userId).enqueue(object: Callback<BaseResponse<EventDetailResponse>>{
            override fun onResponse(
                call: Call<BaseResponse<EventDetailResponse>>,
                response: Response<BaseResponse<EventDetailResponse>>
            ) {
                if(response.isSuccessful){
                    val data = response.body()!!.data

                    name.text = data.name
                    title.text = data.title
                    content.text = data.content

                    Glide.with(this@EventDetailActivity)
                        .load(data.image)
                        .override(328, 194)
                        .into(image)

                    if(data.status == 1){
                        setButtonDisable()
                    }
                } else{

                }

            }

            override fun onFailure(call: Call<BaseResponse<EventDetailResponse>>, t: Throwable) {

            }

        })
    }

    private fun setButtonDisable(){
        participateBtn.isEnabled = false
        participateBtn.text = "참여 완료"
        participateBtn.setTextColor(Color.BLACK)
    }

    override fun onEventParticipateDialogResult(result: Boolean?) {
        if(result!!){
            setButtonDisable()
        }
    }
}