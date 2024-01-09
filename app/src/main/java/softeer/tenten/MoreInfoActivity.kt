package softeer.tenten

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import softeer.tenten.databinding.ActivityMoreInfoBinding
import softeer.tenten.fragments.moreInfo.EventFragment
import softeer.tenten.fragments.moreInfo.MoreInfoFragment
import softeer.tenten.fragments.moreInfo.ReviewFragment


class MoreInfoActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMoreInfoBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_info)

        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_more_info)

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
        val eventFragment: Fragment = EventFragment()
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
            standInLine()

            // 5초 뒤에 다이얼로그 띄우기
            Handler().postDelayed({
                // 입장 알림 다이어로그 (다이어로그 열릴 때 setView에 들어가는 뷰 새로 만들어줘야함. 이 부분 밖에 빼면 오류발생)
                val mDialogView =
                    LayoutInflater.from(this).inflate(R.layout.waiting_alert_dlg, null)
                val mBuilder = AlertDialog.Builder(this)
                    .setView(mDialogView)
                    .setTitle("입장 알림")

                // 다이어로그 닫기
                val mAlertDialog = mBuilder.show()
                mAlertDialog.findViewById<AppCompatButton>(R.id.okBtn)?.setOnClickListener {
                    mAlertDialog.dismiss()
                    showStandInLineBtn()
                }
            }, 2000)
        }

        if (chInLine) { // 이미 대기하고 있는 사람
            showWaitingStatusBtn()
        } else { // 대기 가능한 사람
            showStandInLineBtn()
        }

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

    fun showWaitingStatusBtn() {
        binding.standInLineContainer.visibility = View.GONE
        binding.waitingContainer.visibility = View.VISIBLE

        // 현재 웨이팅 팀 수 표시
        // 내 대기번호 표시
    }

    // 대기하고 있는 팝업 스토어인지 확인
    fun validateInLine(): Boolean {
        return false
    }

    // 대기 취소
    fun cancleWaiting() {
        showStandInLineBtn()
    }

    // 대기 줄서기
    fun standInLine() {
        showWaitingStatusBtn()
    }

}