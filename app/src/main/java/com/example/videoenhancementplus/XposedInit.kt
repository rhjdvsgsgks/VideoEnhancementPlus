package com.example.videoenhancementplus

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XSharedPreferences
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam

class XposedInit : IXposedHookLoadPackage {

	override fun handleLoadPackage(lpparam: LoadPackageParam) {
		when (lpparam.packageName) {
			"com.sonymobile.swiqisystemservice" -> {

				val prefs = XSharedPreferences("com.example.videoenhancementplus")

				val instantmode = prefs.getBoolean("instantmode", false)
				val hijacklog = prefs.getBoolean("hijacklog", false)

				if (hijacklog) {

					"com.sonymobile.swiqisystemservice.util.LogUtil".replaceMethod(lpparam.classLoader, "e", String::class.java, String::class.java) { param ->
						Log.e("swiqisystemservice " + param.args[0] + ": " + param.args[1])
					}

					"com.sonymobile.swiqisystemservice.util.LogUtil".replaceMethod(lpparam.classLoader, "w", String::class.java, String::class.java) { param ->
						Log.w("swiqisystemservice " + param.args[0] + ": " + param.args[1])
					}

					"com.sonymobile.swiqisystemservice.util.LogUtil".replaceMethod(lpparam.classLoader, "i", String::class.java, String::class.java) { param ->
						Log.i("swiqisystemservice " + param.args[0] + ": " + param.args[1])
					}

					"com.sonymobile.swiqisystemservice.util.LogUtil".replaceMethod(lpparam.classLoader, "d", String::class.java, String::class.java) { param ->
						Log.d("swiqisystemservice " + param.args[0] + ": " + param.args[1])
					}

					"com.sonymobile.swiqisystemservice.util.LogUtil".replaceMethod(lpparam.classLoader, "v", String::class.java, String::class.java) { param ->
						Log.v("swiqisystemservice " + param.args[0] + ": " + param.args[1])
					}

				}

				if (instantmode) {

					"com.sonymobile.swiqisystemservice.observer.ForegroundAppObserver".replaceMethod(lpparam.classLoader, "notifyForegroundActivityChanged") {

						prefs.reload()
						val setIsEffectWhiteList = prefs.getBoolean("setIsEffectWhiteList", true)
						val setIsDemoWhiteList = prefs.getBoolean("setIsDemoWhiteList", false)

						lpparam.classLoader.loadClass("com.sonymobile.swiqisystemservice.HidlSwiqiAccessor").callStaticMethod("setIsEffectWhiteList", setIsEffectWhiteList)
						lpparam.classLoader.loadClass("com.sonymobile.swiqisystemservice.HidlSwiqiAccessor").callStaticMethod("setIsDemoWhiteList", setIsDemoWhiteList)

					}

				} else {

					val setIsEffectWhiteList = prefs.getBoolean("setIsEffectWhiteList", true)
					val setIsDemoWhiteList = prefs.getBoolean("setIsDemoWhiteList", false)

					lpparam.classLoader.loadClass("com.sonymobile.swiqisystemservice.HidlSwiqiAccessor").callStaticMethod("setIsEffectWhiteList", setIsEffectWhiteList)
					lpparam.classLoader.loadClass("com.sonymobile.swiqisystemservice.HidlSwiqiAccessor").callStaticMethod("setIsDemoWhiteList", setIsDemoWhiteList)

					"com.sonymobile.swiqisystemservice.observer.ForegroundAppObserver".hookMethod(lpparam.classLoader, "start", XC_MethodReplacement.DO_NOTHING)

				}

			}
			"com.sonymobile.displaybooster" -> {

				val prefs = XSharedPreferences("com.example.videoenhancementplus")

				val instantmode = prefs.getBoolean("instantmode", false)
				val hijacklog = prefs.getBoolean("hijacklog", false)

				if (hijacklog) {

					"com.sonymobile.displaybooster.LogUtil".replaceMethod(lpparam.classLoader, "e", String::class.java, String::class.java) { param ->
						Log.e("DisplayBooster " + param.args[0] + ": " + param.args[1])
					}

					"com.sonymobile.displaybooster.LogUtil".replaceMethod(lpparam.classLoader, "w", String::class.java, String::class.java) { param ->
						Log.w("DisplayBooster " + param.args[0] + ": " + param.args[1])
					}

					"com.sonymobile.displaybooster.LogUtil".replaceMethod(lpparam.classLoader, "i", String::class.java, String::class.java) { param ->
						Log.i("DisplayBooster " + param.args[0] + ": " + param.args[1])
					}

					"com.sonymobile.displaybooster.LogUtil".replaceMethod(lpparam.classLoader, "d", String::class.java, String::class.java) { param ->
						Log.d("DisplayBooster " + param.args[0] + ": " + param.args[1])
					}

					"com.sonymobile.displaybooster.LogUtil".replaceMethod(lpparam.classLoader, "v", String::class.java, String::class.java) { param ->
						Log.v("DisplayBooster " + param.args[0] + ": " + param.args[1])
					}

				}

				if (instantmode) {

					"com.sonymobile.displaybooster.DisplayBoosterService".replaceMethod(lpparam.classLoader, "isCABCSuppported") {
						return@replaceMethod true
					}

					"com.sonymobile.displaybooster.DisplayBoosterService".replaceMethod(lpparam.classLoader, "onForegroundChanged", Int::class.java, Int::class.java, Boolean::class.java) { param ->

						prefs.reload()
						val boostDisplay = prefs.getBoolean("boostDisplay", true)
						val enableCABC = prefs.getBoolean("enableCABC", false)

						param.thisObject.callMethod("boostDisplay", boostDisplay)
						param.thisObject.callMethod("enableCABC", enableCABC)

					}

				} else {

					val boostDisplay = prefs.getBoolean("boostDisplay", true)
					val enableCABC = prefs.getBoolean("enableCABC", false)

					"com.sonymobile.displaybooster.DisplayBoosterService".findClass(lpparam.classLoader)?.hookAfterConstructor { param ->
						param.thisObject.setBooleanField("mIsCABC", true)
					}

					"com.sonymobile.displaybooster.DisplayBoosterService".hookBeforeMethod(lpparam.classLoader, "onCreate") { param ->
						param.thisObject.setObjectField("mLight", lpparam.classLoader.loadClass("vendor.semc.hardware.light.V1_0.IExtLight").callStaticMethod("getService"))
						param.thisObject.callMethod("boostDisplay", boostDisplay)
						param.thisObject.callMethod("enableCABC", enableCABC)
						param.result = null
					}

				}

			}
		}
	}
}