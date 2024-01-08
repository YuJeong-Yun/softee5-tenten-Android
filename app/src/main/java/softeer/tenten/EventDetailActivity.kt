package softeer.tenten

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import softeer.tenten.fragments.moreInfo.EventDetailBottomSheetFragment

class EventDetailActivity : AppCompatActivity() {
    private lateinit var button : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        val bottomSheet = EventDetailBottomSheetFragment()
        button = findViewById(R.id.eventDetailBtn)

        button.setOnClickListener {
            bottomSheet.show(supportFragmentManager, "open bottom sheet")
        }
    }
}