package softeer.tenten

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import softeer.tenten.databinding.ActivityMoreInformationBinding
import softeer.tenten.fragments.home.HomeFragment
import softeer.tenten.fragments.moreInfo.EventFragment
import softeer.tenten.fragments.moreInfo.MoreInfoFragment
import softeer.tenten.fragments.moreInfo.ReviewFragment
import kotlin.math.log

class MoreInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_information)

        val binding: ActivityMoreInformationBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_more_information)

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
                Log.d("ff", tab.position.toString())
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
    }

    // 사이즈 조절 위한 확장 함수
    fun View.setHeight(value: Int) {
        val lp = layoutParams
        lp?.let {
            lp.height = value
            layoutParams = lp
        }
    }
}