package softeer.tenten.util

import android.content.Context
import android.content.SharedPreferences

class LoginSharedPreferences(context: Context) {
    private val PREFS_FILENAME = "login"
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String): String{
        return prefs.getString(key,defValue).toString()
    }

    fun setString(key: String, value: String){
        prefs.edit().putString(key, value).apply()
    }
}