@file:Suppress("DEPRECATION")

package com.example.videoenhancementplus

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.preference.PreferenceFragment

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

        @SuppressLint("WorldReadableFiles")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            preferenceManager.sharedPreferencesMode = Context.MODE_WORLD_READABLE
            addPreferencesFromResource(R.xml.root_preferences)
        }
    }
}




