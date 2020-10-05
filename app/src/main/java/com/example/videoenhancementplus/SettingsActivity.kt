package com.example.videoenhancementplus

import android.annotation.SuppressLint
import android.os.Bundle
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
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }

        override fun onPause() {
            super.onPause()
            fixpermission()
        }

        @SuppressLint("SetWorldReadable")
        fun fixpermission() {
            File(context?.dataDir.toString()).setExecutable(true,false)
            File(context?.dataDir.toString()+"/shared_prefs/"+activity?.packageName.toString()+"_preferences.xml").setReadable(true,false)
            Log.d("permissions has been fixed "+context?.dataDir.toString()+"/shared_prefs/"+activity?.packageName.toString()+"_preferences.xml")
        }
    }
}




