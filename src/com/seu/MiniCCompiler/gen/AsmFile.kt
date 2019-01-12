package com.seu.MiniCCompiler.gen

import com.seu.MiniCCompiler.Tag
import java.io.BufferedWriter
import java.io.FileWriter

class AsmFile(private var generator: CodeGenerator) {
    private lateinit var writer: BufferedWriter

    fun writeFile(filePath: String) {
        writer = BufferedWriter(FileWriter(filePath))
        writer.write(data())
        writer.write(code())
        writer.close()
    }

    private fun data(): String {
        val builder = StringBuilder()
        builder.append("""
            .DATA
        """.trimIndent())
        val global = generator.global
        for (symbol in global.symbolList) {
            builder.append("\n\t")
            val type = when (symbol.type) {
                Tag.TYPE_INT -> "WORD"
                else -> throw Exception()
            }
            builder.append("${symbol.name}:.$type 0")
        }
        builder.append('\n')
        return builder.toString()
    }

    private fun code(): String {
        val builder = StringBuilder()
        builder.append("""
            .TEXT
        """.trimIndent())
        return builder.toString()
    }

}
