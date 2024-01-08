package softeer.tenten

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import softeer.tenten.databinding.ActivityMoreInformationBinding

class MoreInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_information)

        val binding: ActivityMoreInformationBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_more_information)

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