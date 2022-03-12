package space.aioilight.tsubonofuta

import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class ThreadAdRemover {

    private val view: String = "jp.syoboi.a2chMate.view.MyAdView"

    private fun isHome(p: XC_MethodHook.MethodHookParam?): Boolean {
        if (p != null) {
            val str = XposedHelpers.getObjectField(p.thisObject, "valueOf").toString()
            return str.isEmpty()
        }
        return false
    }

    fun removeThreadAd(lpparam: XC_LoadPackage.LoadPackageParam) {
        try {
            XposedBridge.hookAllMethods(
                XposedHelpers.findClass(
                    view,
                    lpparam.classLoader),
                "reload",
                object: XC_MethodHook () {
                    override fun beforeHookedMethod(param: MethodHookParam?) {
                        if (!isHome(param)) {
                            param?.result = null
                        }
                    }
                })

            XposedBridge.hookAllMethods(
                XposedHelpers.findClass(
                    view,
                    lpparam.classLoader),
                "onDestroy",
                object: XC_MethodHook () {
                    override fun beforeHookedMethod(param: MethodHookParam?) {
                       if (!isHome(param)) {
                            param?.result = null
                        }
                    }
                })

            XposedBridge.hookAllMethods(
                XposedHelpers.findClass(
                    view,
                    lpparam.classLoader),
                "onPause",
                object: XC_MethodHook () {
                    override fun beforeHookedMethod(param: MethodHookParam?) {
                        if (!isHome(param)) {
                            param?.result = null
                        }
                    }
                })

            XposedHelpers.findAndHookMethod(
                XposedHelpers.findClass(
                    view,
                    lpparam.classLoader),
                "onResume",
                object: XC_MethodHook () {
                    override fun beforeHookedMethod(param: MethodHookParam?) {
                        if (!isHome(param)) {
                            param?.result = null
                        }
                    }
                })
        }
        catch (e: Exception) {
            XposedBridge.log("Cannot hook method of thread ad.")
            XposedBridge.log(e.toString())
        }
    }
}