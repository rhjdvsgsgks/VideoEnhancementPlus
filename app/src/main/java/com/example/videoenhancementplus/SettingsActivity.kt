@file:Suppress("DEPRECATION")

package com.example.videoenhancementplus

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.preference.PreferenceFragment
import java.io.File

class SettingsActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.settings, SettingsFragment())
                    .commit()
        }
    }

    class SettingsFragment : PreferenceFragment() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.root_preferences)
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




