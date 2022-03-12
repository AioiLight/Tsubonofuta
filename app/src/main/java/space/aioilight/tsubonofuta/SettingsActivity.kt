package space.aioilight.tsubonofuta

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import space.aioilight.tsubonofuta.ui.theme.DynamicColor
import space.aioilight.tsubonofuta.ui.theme.Typography

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SettingScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen() {
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = remember(decayAnimationSpec) {
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(decayAnimationSpec)
    }

    DynamicColor {
        // A surface container using the 'background' color from the theme
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            content = {
                SettingsContent()
            },
            topBar = {
                SettingsTopBar(scrollBehavior)
            }
        )
    }
}

@SuppressLint("WorldReadableFiles")
@Composable
fun SettingsContent() {
    val scrollState = rememberScrollState()
    val pref = LocalContext.current.getSharedPreferences("tsuboprefs", Context.MODE_WORLD_READABLE)
    Column(
        Modifier.verticalScroll(scrollState)
    ) {
        SettingsSwitch(
            title = stringResource(id = R.string.settings_thread_title),
            description = stringResource(id = R.string.settings_thread_desc),
            key = "thread",
            default = true,
            prefs = pref
        )
        SettingsSwitch(
            title = stringResource(id = R.string.settings_inline_title),
            description = stringResource(id = R.string.settings_inline_desc),
            key = "inline",
            default = true,
            prefs = pref
        )
        Spacer(modifier = Modifier.height(256.dp))
        GitHub()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTopBar(topAppBarScrollBehavior: TopAppBarScrollBehavior) {
    LargeTopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name))},
        scrollBehavior = topAppBarScrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CommitPrefEdits", "ApplySharedPref")
@Composable
fun SettingsSwitch(title: String,
                   description: String,
                   key:String,
                   default: Boolean,
                   prefs: SharedPreferences) {
    val def = prefs.getBoolean(key, default)
    val checkedState = remember { mutableStateOf(def)}
    Row(
        Modifier
            .clickable {
                checkedState.value = !checkedState.value
                val editor = prefs.edit()
                editor.putBoolean(key, checkedState.value)
                editor.commit()
            }
            .fillMaxWidth()
            .padding(24.dp),
        Arrangement.SpaceBetween,
    ) {
        Column (
            Modifier.weight(1f)
        ){
            Text(text = title,
                style = Typography.subtitle1)
            if (description.isNotEmpty()) {
                Text(text = description,
                    style = Typography.caption)
            }
        }
        Checkbox(checked = checkedState.value, onCheckedChange = null)
    }
}

@Composable
fun GitHub() {
    val uri = stringResource(id = R.string.repository_url)
    val context = LocalContext.current
    Row(
        Modifier
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                context.startActivity(intent)
            }
            .fillMaxWidth()
            .padding(24.dp),
        Arrangement.SpaceBetween,
    ) {
        Column(
            Modifier.weight(1f)
        ) {
            Text(
                text = stringResource(id = R.string.settings_github_title),
                style = Typography.subtitle1
            )
            Text(
                text = stringResource(id = R.string.settings_github_desc),
                style = Typography.caption
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SettingScreen()
}