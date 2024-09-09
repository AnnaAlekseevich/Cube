package com.example.cube.cube

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Cube3D(cube: List<Face>, modifier: Modifier = Modifier) {
    var rotationX by remember { mutableFloatStateOf(0f) }
    var rotationY by remember { mutableFloatStateOf(0f) }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, _, _ ->
                    rotationX -= pan.y / 5
                    rotationY += pan.x / 5
                }
            }
    ) {
        // Calculate center
        val centerX = size.width / 2
        val centerY = size.height / 2

        // Transform and sort faces
        val transformedFaces = cube.map { face ->
            val transformedPoints = face.points.map { point ->
                val rotatedX = point.x * cos(Math.toRadians(rotationY.toDouble())) - point.z * sin(
                    Math.toRadians(rotationY.toDouble())
                )
                val rotatedZAfterY = point.x * sin(Math.toRadians(rotationY.toDouble())) + point.z * cos(
                    Math.toRadians(rotationY.toDouble())
                )

                val rotatedY = point.y * cos(Math.toRadians(rotationX.toDouble())) - rotatedZAfterY * sin(
                    Math.toRadians(rotationX.toDouble())
                )
                val rotatedZ = point.y * sin(Math.toRadians(rotationX.toDouble())) + rotatedZAfterY * cos(
                    Math.toRadians(rotationX.toDouble())
                )

                Point3D(rotatedX.toFloat(), rotatedY.toFloat(), rotatedZ.toFloat())
            }
            Face(transformedPoints, face.color)
        }

        // Sort faces by depth
        val sortedFaces = transformedFaces.sortedBy { face ->
            face.points.map { it.z }.average()
        }.reversed()

        // Draw all the faces
        sortedFaces.forEach { face ->
            drawFace(face, centerX, centerY)
        }
    }
}

    private fun DrawScope.drawFace(face: Face, centerX: Float, centerY: Float) {
    val path = Path()

    face.points.map { point ->
        // Projection onto 2D plane
        val scale = 500 / (500 + point.z) // Adjusted value for a more dynamic projection
        val projectedX = centerX + point.x * scale
        val projectedY = centerY - point.y * scale
        Offset(projectedX, projectedY)
    }.forEachIndexed { index, offset ->
        if (index == 0) {
            path.moveTo(offset.x, offset.y)
        } else {
            path.lineTo(offset.x, offset.y)
        }
    }

    path.close()

    drawPath(path, color = face.color)
}