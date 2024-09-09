package com.example.cube

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cube.cube.Cube3D
import com.example.cube.cube.createCube
import com.example.cube.ui.theme.CubeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CubeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Cube(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Cube(modifier: Modifier = Modifier) {
    val cube = createCube(300f)
    Surface(modifier = modifier) {
        Box(modifier = Modifier.fillMaxSize()) {
            Cube3D(cube, Modifier.fillMaxSize())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val cube = createCube(300f)
    CubeTheme {
        Cube3D(cube)
    }
}