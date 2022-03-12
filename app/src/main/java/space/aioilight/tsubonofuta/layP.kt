package space.aioilight.tsubonofuta

import android.app.AndroidAppHelper
import android.content.Context
import android.content.SharedPreferences
import com.crossbowffs.remotepreferences.RemotePreferences
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XSharedPreferences
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XC_LoadPackage
import java.lang.Exception

class layP : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam?) {
        if (lpparam != null) {
            if (lpparam.packageName == "jp.co.airfront.android.a2chMate") {

                val pref = XSharedPreferences(BuildConfig.APPLICATION_ID, "tsuboprefs")

                if (pref.getBoolean("inline", true)) {
                    InlineAdRemover().removeInlineAd(lpparam)
                }

                if (pref.getBoolean("thread", true)) {
                    ThreadAdRemover().removeThreadAd(lpparam)
                }
            }
        }
    }
}