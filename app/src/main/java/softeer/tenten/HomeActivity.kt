package softeer.tenten

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import softeer.tenten.fragments.home.BrowseFragment
import softeer.tenten.fragments.home.HomeFragment
import softeer.tenten.fragments.home.MypageFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var navi: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navi = findViewById(R.id.bottomNavigation)

        navi.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(HomeFragment())
                R.id.browse -> replaceFragment(BrowseFragment())
                R.id.myPage -> replaceFragment(MypageFragment())

                else -> {}
            }
            true
        }

        navi.selectedItemId = R.id.home
        navi.callOnClick()
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainFrameLayout,fragment)
        fragmentTransaction.commit()
    }
}