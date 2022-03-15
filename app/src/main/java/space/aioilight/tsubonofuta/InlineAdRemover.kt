package space.aioilight.tsubonofuta

import android.content.Context
import android.view.View
import android.view.ViewGroup
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import java.lang.Exception

class InlineAdRemover {
    fun removeInlineAd(lpparam: XC_LoadPackage.LoadPackageParam)
    {
        try {
            XposedHelpers.findAndHookMethod(
                XposedHelpers.findClass("o.fireExposureChange", lpparam.classLoader),
                "RemoteActionCompatParcelizer",
                "android.view.View",
                "android.view.ViewGroup",
                object : XC_MethodHook() {
                    override fun beforeHookedMethod(param: MethodHookParam?) {
                        if (param != null) {
                            param.result = View((param.args[1] as ViewGroup).context)
                        }
                    }
                }
            )
//            XposedBridge.hookAllMethods(
//                XposedHelpers.findClass(
//                    "jp.syoboi.a2chMate.view.ad.InlineAdContainer", lpparam.classLoader),
//                "c",
//                object : XC_MethodHook() {
//                    public override fun beforeHookedMethod(param: MethodHookParam?) {
//                        if (param != null) {
//                            param.result = null
//                        }
//                    }
//                })
        }
        catch (e: Exception) {
            XposedBridge.log("Cannot hook method of inline ads.")
        }
    }
}