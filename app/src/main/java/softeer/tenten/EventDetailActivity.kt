package softeer.tenten

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import softeer.tenten.dialog.OnDialogResultListener
import softeer.tenten.fragments.moreInfo.EventDetailBottomSheetFragment

class EventDetailActivity : AppCompatActivity(), OnDialogResultListener {
    private lateinit var participateBtn: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        val bottomSheet = EventDetailBottomSheetFragment()
        participateBtn = findViewById(R.id.participateBtn) // 참여 인증 버튼
        val completeParticipateBtn =
            findViewById<AppCompatButton>(R.id.completeParticipateBtn) // 참여 완료 버튼

        var participate = checkParticipate() // 참여 여부 확인

        if (participate) { // 이미 참여 완료한 사람
            participateBtn.visibility = View.GONE
            completeParticipateBtn.visibility = View.VISIBLE
        } else { // 참여 가능한 사람
            participateBtn.setOnClickListener {
                participateBtn.visibility = View.VISIBLE
                completeParticipateBtn.visibility = View.GONE
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

    override fun onEventParticipateDialogResult(result: Boolean?) {
        if(result!!){
            participateBtn.setTextColor(Color.parseColor("#000000"))
            participateBtn.isEnabled = false
            participateBtn.text = "참여 완료"
        }
    }
}