package softeer.tenten.util

import android.app.Application

class App : Application() {
    companion object {
        lateinit var prefs: LoginSharedPreferences
    }

    override fun onCreate() {
        prefs = LoginSharedPreferences(this)
        super.onCreate()
    }
}