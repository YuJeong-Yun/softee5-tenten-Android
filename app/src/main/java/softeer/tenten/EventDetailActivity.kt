package softeer.tenten

import android.app.DatePickerDialog
import android.app.ProgressDialog.show
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import softeer.tenten.databinding.ActivityMoreInformationBinding
import softeer.tenten.fragments.moreInfo.EventDetailBottomSheetFragment
import java.util.Calendar
import java.util.GregorianCalendar

class EventDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        val bottomSheet = EventDetailBottomSheetFragment()
        val participateBtn = findViewById<AppCompatButton>(R.id.participateBtn) // 참여 인증 버튼
        val completeParticipateBtn =
            findViewById<AppCompatButton>(R.id.completeParticipateBtn) // 참여 완료 버튼


        var participate = checkParticipate()

        if (participate) { // 참여 완료한 사람
            participateBtn.visibility = View.GONE
            completeParticipateBtn.visibility = View.VISIBLE
        } else { // 참여 가능한 사람
            participateBtn.setOnClickListener {
                bottomSheet.show(supportFragmentManager, "open bottom sheet")
            }
        }


    }

    // db에서 참여 인증 여부 확인하기 위한 변수
    fun checkParticipate(): Boolean {
        // db에서 확인하는 코드~~~~~~~~~~~~~~~~~~~
        // ...
        return false;
    }
}