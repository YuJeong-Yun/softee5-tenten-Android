package softeer.tenten

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import softeer.tenten.databinding.ActivityMoreInfoBinding
import softeer.tenten.fragments.moreInfo.EventFragment
import softeer.tenten.fragments.moreInfo.MoreInfoFragment
import softeer.tenten.fragments.moreInfo.ReviewFragment
import softeer.tenten.network.api.PopUpStoreService
import softeer.tenten.network.api.WaitingApiService
import softeer.tenten.network.request.WaitingRequest
import softeer.tenten.network.response.BaseResponse
import softeer.tenten.network.response.PopUpDetail
import softeer.tenten.network.response.WaitingResponse
import softeer.tenten.network.retrofit.RetrofitApi
import softeer.tenten.util.App


class MoreInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoreInfoBinding
    var waitingNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_info)

        val popUpId = intent.getLongExtra("id", 0)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_more_info)

        getWaitingInformation(popUpId)

        ////////// 이동일정 확인 버튼 토글
        val openScheduleBtn = binding.moveScheduleOpen
        val closeScheduleBtn = binding.moveScheduleClose
        val scheduleImg = binding.moveSchedule

        // 이미지 높이 계산
        val dp = 140
        val density = resources.displayMetrics.density
        val imgHeight = (dp * density).toInt()

        // 이동일정 클릭
        openScheduleBtn.setOnClickListener {
            openScheduleBtn.visibility = View.GONE
            closeScheduleBtn.visibility = View.VISIBLE
            scheduleImg.setHeight(imgHeight)
        }

        // 접기 클릭
        closeScheduleBtn.setOnClickListener {
            closeScheduleBtn.visibility = View.GONE
            openScheduleBtn.visibility = View.VISIBLE
            scheduleImg.setHeight(0)
        }


        ////////// 탭 동작
        val tabLayout: TabLayout = binding.tabLayout
        val moreInfoFragment: Fragment = MoreInfoFragment()
        val eventFragment: Fragment = EventFragment(popUpId)
        val reviewFragment: Fragment = ReviewFragment()

        supportFragmentManager.beginTransaction().replace(R.id.main_view, moreInfoFragment).commit()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            // 선택된 탭에 대한 처리
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_view, moreInfoFragment).commit()
                    }

                    1 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_view, eventFragment).commit()
                    }

                    2 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_view, reviewFragment).commit()
                    }
                }
            }

            // 선택이 해제된 탭에 대한 처리
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            // 이미 선택된 탭이 다시 선택된 경우 처리
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })


        ////////// 대기 버튼 동작
        // db 데이터 필요. 줄서 있는지 여부 확인~~~
        val chInLine: Boolean = validateInLine()


        // 대기 취소 버튼 클릭 시
        // db 데이터 필요 ~~~~
        binding.cancleWaitingBtn.setOnClickListener {
            cancleWaiting();
        }
        // 대기 줄서기 버튼 클릭 시
        // db 데이터 필요~~~
        // 실제로는 해당하는 유저에게 푸쉬 알림 => 우선 지금은 2초 뒤에 다이어로그 띄우도록 구현
        binding.standInLineBtn.setOnClickListener {
            standInLine(popUpId)

            // 2초 뒤에 다이얼로그 띄우기
            Handler().postDelayed({
                // 입장 알림 다이어로그 (다이어로그 열릴 때 setView에 들어가는 뷰 새로 만들어줘야함. 이 부분 밖에 빼면 오류발생)
                val mDialogView =
                    LayoutInflater.from(this).inflate(R.layout.waiting_alert_dlg, null)

                val waitingNumberTv =
                    mDialogView.findViewById<TextView>(R.id.waitingAlertWaitingNumber)
                waitingNumberTv.text = waitingNumber.toString()

                val mBuilder = AlertDialog.Builder(this).setView(mDialogView).setTitle("입장 알림")
                    .setCancelable(false)

                // 다이어로그 닫기
                val mAlertDialog = mBuilder.show()
                mAlertDialog.findViewById<AppCompatButton>(R.id.okBtn)?.setOnClickListener {
                    mAlertDialog.dismiss()
                    showStandInLineBtn()
                }
            }, 2000)
        }

        if (chInLine) { // 이미 대기하고 있는 사람
            // showWaitingStatusBtn()
        } else { // 대기 가능한 사람
            showStandInLineBtn()
        }

        getPopUpDetail(popUpId)
    }

    // 사이즈 조절 위한 확장 함수
    fun View.setHeight(value: Int) {
        val lp = layoutParams
        lp?.let {
            lp.height = value
            layoutParams = lp
        }
    }

    fun showStandInLineBtn() {
        binding.standInLineContainer.visibility = View.VISIBLE
        binding.waitingContainer.visibility = View.GONE
    }

    fun showWaitingStatusBtn(waitingNumber: Int, orderNumber: Int) {
        binding.standInLineContainer.visibility = View.GONE
        binding.waitingContainer.visibility = View.VISIBLE

        // 현재 웨이팅 팀 수 표시
        // 내 대기번호 표시
        binding.waitingNum.text = waitingNumber.toString()
        binding.waitingTeamCnt.text = orderNumber.toString()
    }

    // 대기하고 있는 팝업 스토어인지 확인
    fun validateInLine(): Boolean {
        return false
    }

    // 대기 취소
    fun cancleWaiting() {
        showStandInLineBtn()
    }

    fun getWaitingInformation(popUpId: Long) {
        val retrofit = RetrofitApi.getInstance().create(WaitingApiService::class.java)
        val userId = App.prefs.getString("id", "")

        retrofit.getWaitingInformation(popUpId, userId)
            .enqueue(object : Callback<BaseResponse<WaitingResponse>> {
                override fun onResponse(
                    call: Call<BaseResponse<WaitingResponse>>,
                    response: Response<BaseResponse<WaitingResponse>>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()!!.data

                        showWaitingStatusBtn(data.waitingNumber, data.orderNumber)
                        waitingNumber = data.waitingNumber
                    } else {
                        showStandInLineBtn()
                    }
                }

                override fun onFailure(call: Call<BaseResponse<WaitingResponse>>, t: Throwable) {

                }

            })
    }

    // 대기 줄서기
    fun standInLine(popUpId: Long) {
        val retrofit = RetrofitApi.getInstance().create(WaitingApiService::class.java)
        val userId = App.prefs.getString("id", "")

        retrofit.registerWaiting(popUpId, WaitingRequest(userId))
            .enqueue(object : Callback<BaseResponse<WaitingResponse>> {
                override fun onResponse(
                    call: Call<BaseResponse<WaitingResponse>>,
                    response: Response<BaseResponse<WaitingResponse>>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()!!.data

                        showWaitingStatusBtn(data.waitingNumber, data.orderNumber)
                        waitingNumber = data.waitingNumber
                    } else {

                    }
                }

                override fun onFailure(call: Call<BaseResponse<WaitingResponse>>, t: Throwable) {

                }

            })
    }

    private fun getPopUpDetail(popUpId: Long) {
        val retrofit = RetrofitApi.getInstance().create(PopUpStoreService::class.java)
        Log.e("test", "test")

        retrofit.getPopUpDetail(popUpId).enqueue(object : Callback<BaseResponse<PopUpDetail>> {
            override fun onResponse(
                call: Call<BaseResponse<PopUpDetail>>, response: Response<BaseResponse<PopUpDetail>>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data

                    binding.apply {
                        Glide.with(this@MoreInfoActivity).load(data.images[0]).into(binding.mainImg)
                        brand.text = data.brand
                        title.text = data.title
                        if (data.tags.size == 1) tag1.text = data.tags[0]
                        if (data.tags.size == 2) tag2.text = data.tags[1]
                        introduction.text = data.introduction
                        location.text = data.destination[0]
                        time.text = "10:00 - 17:00"
                        duration.text = data.duration
                        capacity.text = "수용인원 " + data.capacity.toString() + "명"

                    }

                } else {

                }
            }

            override fun onFailure(call: Call<BaseResponse<PopUpDetail>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}