package space.aioilight.tsubonofuta

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage

class layP : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam?) {
        if (lpparam != null) {
            if (lpparam.packageName == "jp.co.airfront.android.a2chMate")
            {
                InlineAdRemover().removeInlineAd(lpparam)
            }
        }
    }
}