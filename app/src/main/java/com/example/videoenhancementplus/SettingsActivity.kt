package com.example.videoenhancementplus

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import java.io.File

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.settings, SettingsFragment())
                    .commit()

        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat()/*,SharedPreferences.OnSharedPreferenceChangeListener*/ {


        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            fixpermission()
        }

        /*private val handler by lazy { Handler(Looper.getMainLooper()) }
        private val toast by lazy { Toast.makeText(this.context , "", Toast.LENGTH_SHORT) }

        private fun Log(msg: String) {
            handler.post {
                toast.setText("11111111:\n$msg")
                toast.duration = Toast.LENGTH_SHORT
                toast.show()
            }
            ALog.i("11111111",msg)
        }*/

        /*override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
            //fixpermission()
        }

        override fun onResume() {
            super.onResume()
            //preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        }*/

        override fun onPause() {
            super.onPause()
            //preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
            fixpermission()
        }

        @SuppressLint("SetWorldReadable")
        fun fixpermission() {
            File(context?.dataDir.toString()).setExecutable(true,false)
            File(context?.dataDir.toString()+"/shared_prefs/"+activity?.packageName.toString()+"_preferences.xml").setReadable(true,false)
            Log.i("permissions has been fixed "+context?.dataDir.toString()+"/shared_prefs/"+activity?.packageName.toString()+"_preferences.xml")
            if (preferenceManager.sharedPreferences.getBoolean("showtoast",false)) {
                Toast.makeText(activity, "VideoEnhancementPlus:\npermissions has been fixed\n"+context?.dataDir.toString()+"/shared_prefs/"+activity?.packageName.toString()+"_preferences.xml", Toast.LENGTH_LONG).show()
            }

        }

    }

}




