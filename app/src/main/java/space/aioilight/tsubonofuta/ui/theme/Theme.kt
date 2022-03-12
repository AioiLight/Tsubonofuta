package space.aioilight.tsubonofuta.ui.theme

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

val PrimaryColor = Color(0xff3ddc84)
val DarkColor = Color(0xff121212)
val LightColor = Color(0xffededed)

@SuppressLint("NewApi")
@Composable
fun DynamicColor(
    darkTheme: Boolean = isSystemInDarkTheme(),
    isEnableDynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    // ダイナミックカラー使う？
    val isUseDynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
            && isEnableDynamicColor

    // Android 12以降で
    val colorScheme = when {
        isUseDynamicColor && darkTheme -> dynamicDarkColorScheme(context)
        isUseDynamicColor && !darkTheme -> dynamicLightColorScheme(context)
        darkTheme -> darkColorScheme(
            primary = PrimaryColor,
            secondary = LightColor,
            tertiary = DarkColor,
        )
        else -> lightColorScheme(
            primary = PrimaryColor,
            secondary = LightColor,
            tertiary = DarkColor,
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}