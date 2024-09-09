package com.example.cube.cube

import androidx.compose.ui.graphics.Color

data class Point3D(val x: Float, val y: Float, val z: Float)

data class Face(val points: List<Point3D>, val color: Color)

fun createCube(size: Float): List<Face> {
    val s = size / 2

    val vertices = listOf(
        Point3D(-s, s, -s),  // 0: top-left-front
        Point3D(s, s, -s),   // 1: top-right-front
        Point3D(s, -s, -s),  // 2: bottom-right-front
        Point3D(-s, -s, -s), // 3: bottom-left-front
        Point3D(-s, s, s),   // 4: top-left-back
        Point3D(s, s, s),    // 5: top-right-back
        Point3D(s, -s, s),   // 6: bottom-right-back
        Point3D(-s, -s, s)   // 7: bottom-left-back
    )

    return listOf(
        Face(listOf(vertices[0], vertices[1], vertices[2], vertices[3]), Color.Red),     // Front face
        Face(listOf(vertices[4], vertices[5], vertices[6], vertices[7]), Color.Blue),    // Back face
        Face(listOf(vertices[0], vertices[1], vertices[5], vertices[4]), Color.Green),   // Top face
        Face(listOf(vertices[3], vertices[2], vertices[6], vertices[7]), Color.Yellow),  // Bottom face
        Face(listOf(vertices[0], vertices[3], vertices[7], vertices[4]), Color.Cyan),    // Left face
        Face(listOf(vertices[1], vertices[2], vertices[6], vertices[5]), Color.Magenta)  // Right face
    )
}