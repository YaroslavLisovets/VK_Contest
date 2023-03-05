package com.example.vk_contest

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vk_contest.ui.theme.Black
import com.example.vk_contest.ui.theme.VK_ContestTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VK_ContestTheme {
                App()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    App()
}


@Composable
fun App() {

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {}

    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(8.dp)
        ) {
            Button(
                onClick = {
                if( checkPermissions(context, launcher) ){
                    beginRecording()
                } }, modifier = Modifier.size(100.dp),
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Blue),
            ) {
                Icon(
                    painterResource(id = R.drawable.baseline_mic),
                    contentDescription = "Record Button",
                    Modifier.size(80.dp),
                    tint = Color.White
                )
            }

        }

    }
}

fun checkPermissions(context: Context, launcher: ManagedActivityResultLauncher<String, Boolean>): Boolean {
    val permissions = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.RECORD_AUDIO
        ) != PackageManager.PERMISSION_GRANTED ||
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED ||
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(context as Activity, permissions, 0)
        return false
    }
    return true
}


fun beginRecording() {




    val mediaRecorder = MediaRecorder()
    val output = Environment.getExternalStorageDirectory().absolutePath + "/temp.mp3"

    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
    mediaRecorder.setOutputFile(output)
    mediaRecorder.prepare()
    mediaRecorder.start()

    mediaRecorder.stop()


}


