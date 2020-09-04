package com.example.videoenhancementplus

import android.app.AndroidAppHelper
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import de.robv.android.xposed.*
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam

class XposedInit : IXposedHookLoadPackage, IXposedHookZygoteInit {
    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam) {
        modulePath = startupParam.modulePath
    }
    companion object {
        private val currentContext by lazy { AndroidAppHelper.currentApplication() as Context }
        private val handler by lazy { Handler(Looper.getMainLooper()) }
        private val toast by lazy { Toast.makeText(currentContext, "", Toast.LENGTH_LONG) }
        lateinit var modulePath: String

        fun toastlog(msg: String, showtoast: Boolean) {
            if (showtoast) {
                handler.post {
                    toast.setText("VideoEnhancementPlus:\n$msg")
                    toast.show()
                }
            }
            Log.i(msg)
        }
    }

    //@Throws(Throwable::class)
    override fun handleLoadPackage(lpparam: LoadPackageParam) {
        if (lpparam.packageName != "com.sonymobile.swiqisystemservice") return

        val prefs = XSharedPreferences("com.example.videoenhancementplus")

        val instantmode = prefs.getBoolean("instantmode",true)
        val showtoast = prefs.getBoolean("showtoast",false)

        toastlog("we are in com.sonymobile.swiqisystemservice", showtoast)

        if (instantmode) {
            val observerHook: (XC_MethodHook.MethodHookParam) -> Any? = {
                prefs.reload()
                val setIsEffectWhiteList = prefs.getBoolean("setIsEffectWhiteList",true)
                val setIsDemoWhiteList = prefs.getBoolean("setIsDemoWhiteList",false)

                toastlog(prefs.all.toString(), showtoast)

                lpparam.classLoader.loadClass("com.sonymobile.swiqisystemservice.HidlSwiqiAccessor").callStaticMethod("setIsEffectWhiteList",setIsEffectWhiteList)
                lpparam.classLoader.loadClass("com.sonymobile.swiqisystemservice.HidlSwiqiAccessor").callStaticMethod("setIsDemoWhiteList",setIsDemoWhiteList)

                //toastlog("setIsEffectWhiteList $setIsEffectWhiteList\nsetIsDemoWhiteList $setIsDemoWhiteList", showtoast)

            }
            "com.sonymobile.swiqisystemservice.observer.ForegroundAppObserver".replaceMethod(lpparam.classLoader,"notifyForegroundActivityChanged",hooker = observerHook)
        } else {
            val setIsEffectWhiteList = prefs.getBoolean("setIsEffectWhiteList",true)
            val setIsDemoWhiteList = prefs.getBoolean("setIsDemoWhiteList",false)

            toastlog(prefs.all.toString(), showtoast)

            lpparam.classLoader.loadClass("com.sonymobile.swiqisystemservice.HidlSwiqiAccessor").callStaticMethod("setIsEffectWhiteList",setIsEffectWhiteList)
            lpparam.classLoader.loadClass("com.sonymobile.swiqisystemservice.HidlSwiqiAccessor").callStaticMethod("setIsDemoWhiteList",setIsDemoWhiteList)

            "com.sonymobile.swiqisystemservice.observer.ForegroundAppObserver".hookMethod(lpparam.classLoader,"notifyForegroundActivityChanged", XC_MethodReplacement.DO_NOTHING)

            //toastlog("setIsEffectWhiteList $setIsEffectWhiteList\nsetIsDemoWhiteList $setIsDemoWhiteList", showtoast)

        }
    }
}