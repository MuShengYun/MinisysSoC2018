package com.seu.MiniCCompiler.gen

import com.seu.MiniCCompiler.Tag
import com.seu.MiniCCompiler.sem.Function
import com.seu.MiniCCompiler.sem.Symbol
import com.seu.MiniCCompiler.sem.ThreeAddrCode
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
        val main = generator.funcs.get("main") ?: throw Exception("编译错误 - 未找到main函数声明或函数未定义")
        builder.append(code(main))
        generator.funcs.functions.filter { it.name != "main" }.forEach { builder.append(code(it)) }
        return builder.toString()
    }

    private fun code(func: Function): String {
        val builder = StringBuilder(func.name + ":\t")
        for (code_item in func.code) {
            builder.append(code(code_item, func))
            builder.append("\n\t\t")
        }
        return builder.toString()
    }

    private fun code(code_item: ThreeAddrCode, func: Function): String {
        val locals = func.locals
        val temps = func.temps
        val op = code_item.op
        val arg1 = code_item.arg1
        val arg2 = code_item.arg2
        val result = when {
            temps.contains(code_item.result) -> "$" + code_item.result.name
            else -> code_item.result.name
        }
        val arg1_s = when {
            arg1 == null -> ""
            arg1 !is Symbol -> arg1.toString()
            temps.contains(arg1) -> "$" + arg1.name
            else -> arg1.name
        }
        val arg2_s = when {
            arg2 == null -> ""
            arg2 !is Symbol -> arg2.toString()
            temps.contains(arg2) -> "$" + arg2.name
            else -> arg2.name
        }

        return when {
        //[+,arg1,arg2,result]
            op == '+'.toInt()
                    && arg1 is Symbol
                    && arg2 is Symbol ->
                "add $result,$arg1_s,$arg2_s"

        //[+,arg1,arg2(int),result]
            op == '+'.toInt()
                    && arg1 is Symbol
                    && arg2 is Int ->
                "addi $result,$arg1_s,$arg2_s"
        //[*,arg1,arg2,result]
            op == '*'.toInt()
                    && arg1 is Symbol
                    && arg2 is Int ->
                """
                    mult $arg1_s,$arg2_s
                """.trimIndent()
        //[=,arg1(int),null,result]
            op == '='.toInt()
                    && arg1 is Int
                    && arg2 == null ->
                "xori $result,\$zero,$arg1_s"

            else -> {
                ""
            }
        }
    }

}
