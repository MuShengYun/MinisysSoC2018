package com.seu.MiniCCompiler.gen

import java.io.BufferedWriter
import java.io.FileWriter

class AsmFile(private var generator: CodeGenerator) {
    private lateinit var writer: BufferedWriter

    fun writeFile(filePath: String) {
        writer = BufferedWriter(FileWriter(filePath))
        writeData()
        writeIns()
        writer.close()
    }

    private fun writeData() {
        val builder = StringBuilder()
        builder.append("""
            .DATA

        """.trimIndent())

    }

    private fun writeIns() {}

}
