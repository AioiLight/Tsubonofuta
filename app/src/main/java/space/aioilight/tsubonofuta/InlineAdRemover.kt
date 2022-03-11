package space.aioilight.tsubonofuta

import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import java.lang.Exception

class InlineAdRemover {
    fun removeInlineAd(lpparam: XC_LoadPackage.LoadPackageParam)
    {
        try {
            XposedBridge.hookAllMethods(
                XposedHelpers.findClass(
                    "jp.syoboi.a2chMate.view.ad.InlineAdContainer", lpparam.classLoader),
                "isAvailableInlineAd",
                object : XC_MethodHook() {
                    public override fun afterHookedMethod(param: MethodHookParam?) {
                        super.afterHookedMethod(param)
                        if (param != null) {
                            param.result = false
                        }
                    }
                })
        }
        catch (e: Exception) {
            XposedBridge.log("Cannot hook method of inline ads.")
        }
    }
}