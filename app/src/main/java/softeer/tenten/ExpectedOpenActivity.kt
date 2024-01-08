package softeer.tenten

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import softeer.tenten.databinding.ActivityExpectedOpenBinding
import softeer.tenten.databinding.ActivityMoreInformationBinding
import softeer.tenten.fragments.VoteDialogFragment
import softeer.tenten.vote.VoteItemModel
import softeer.tenten.vote.VoteRVAdapter

class ExpectedOpenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExpectedOpenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpectedOpenBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.apply {
            expectedOpenVote.setOnClickListener {
                VoteDialogFragment().show(supportFragmentManager, "open vote dialog")
            }
        }
    }
}