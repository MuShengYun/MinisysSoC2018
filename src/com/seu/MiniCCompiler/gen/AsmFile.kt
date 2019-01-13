package com.seu.MiniCCompiler.gen

import com.seu.MiniCCompiler.Tag
import com.seu.MiniCCompiler.sem.Function
import com.seu.MiniCCompiler.sem.Symbol
import com.seu.MiniCCompiler.sem.ThreeAddrCode
import java.io.BufferedWriter
import java.io.FileWriter

class AsmFile(private var generator: CodeGenerator) {
    private lateinit var writer: BufferedWriter
    private val regDistributor: RegDistributor = RegDistributor()

    fun writeFile(filePath: String) {
        writer = BufferedWriter(FileWriter(filePath))
        writer.write(data())
        writer.write(code())
        writer.close()
    }

    private fun data(): String {
        val builder = StringBuilder()
        builder.append("""
            .DATA   1024
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

    /**
     * 生成汇编代码
     */
    private fun code(): String {
        val builder = StringBuilder()
        builder.append("""
            .TEXT   1024
            start:  push ${'$'}gp
                    addi ${'$'}gp, ${'$'}zero, 1024

        """.trimIndent())
        val main = generator.funcs.get("main") ?: throw Exception("编译错误 - 未找到main函数声明或函数未定义")
        builder.append(code(main))
        generator.funcs.functions.filter { it.name != "main" }.forEach { builder.append(code(it)) }
        builder.append("""
            pop ${'$'}gp
        """.trimIndent())
        return builder.toString()
    }

    /**
     * 为一个程序段生成汇编代码
     */
    private fun code(func: Function): String {
        val builder = StringBuilder(func.name + ":\t")

        //记录全局变量
        val addrRecord: HashMap<Symbol, String> = HashMap()
        generator.global.symbolList.forEach { addrRecord[it] = it.name + "(${'$'}gp)" }

        //将局部变量压栈
        var sp = 0
        val addr = HashMap<Symbol, Int>()
        for (symbol in func.params) {
            for (i in 0 until symbol.size() / 4) {
                builder.append("push ${'$'}zero")
                builder.append("\n\t\t")
            }
            addr[symbol] = sp
            sp += symbol.size()
        }
        for (symbol in func.symbolTable.symbolList) {
            for (i in 0 until symbol.size() / 4) {
                builder.append("push ${'$'}zero")
                builder.append("\n\t\t")
            }
            addr[symbol] = sp
            sp += symbol.size()
        }
        for (symbol in func.symbolTable.tempList) {
            for (i in 0 until symbol.size() / 4) {
                builder.append("push ${'$'}zero")
                builder.append("\n\t\t")
            }
            addr[symbol] = sp
            sp += symbol.size()
        }
        func.params.forEach { addrRecord[it] = "${sp - addr[it]!!}(${'$'}sp)" }
        func.symbolTable.symbolList.forEach { addrRecord[it] = "${sp - addr[it]!!}(${'$'}sp)" }
        func.symbolTable.tempList.forEach { addrRecord[it] = "${sp - addr[it]!!}(${'$'}sp)" }

        //重置寄存器分配器
        regDistributor.reset(addrRecord)

        //生成代码
        for (code_item in func.code) {
            builder.append(code(code_item, func))
            builder.append("\n\t\t")
        }

        //将活动变量存回
        builder.append(regDistributor.clear())

        //将局部变量弹出栈
        for (symbol in func.symbolTable.tempList.reversed()) {
            for (i in 0 until symbol.size() / 4) {
                builder.append("pop ${'$'}t0")
                builder.append("\n\t\t")
            }
        }

        for (symbol in func.symbolTable.symbolList.reversed()) {
            for (i in 0 until symbol.size() / 4) {
                builder.append("pop ${'$'}t0")
                builder.append("\n\t\t")
            }
        }
        for (symbol in func.params.reversed()) {
            for (i in 0 until symbol.size() / 4) {
                builder.append("pop ${'$'}t0")
                builder.append("\n\t\t")
            }
        }
        return builder.toString()
    }

    /**
     * 为一个三地址代码生成汇编代码
     */
    private fun code(code_item: ThreeAddrCode, func: Function): String {
        val builder = StringBuilder()

        val op = code_item.op
        val arg1 = code_item.arg1
        val arg2 = code_item.arg2
        val result = code_item.result
        builder.append(regDistributor.getReg(code_item))

        builder.append(when {
        //[+,arg1,arg2,result]
            op == '+'.toInt()
                    && arg1 is Symbol
                    && arg2 is Symbol ->
                "add ${regDistributor.getReg(result)},${regDistributor.getReg(arg1)},${regDistributor.getReg(arg2)}"

        //[+,arg1,arg2(int),result]
            op == '+'.toInt()
                    && arg1 is Symbol
                    && arg2 is Int ->
                "addi ${regDistributor.getReg(result)},${regDistributor.getReg(arg1)},$arg2"
        //[*,arg1,arg2,result]
            op == '*'.toInt()
                    && arg1 is Symbol
                    && arg2 is Symbol ->
                """
                    mult ${regDistributor.getReg(arg1)},${regDistributor.getReg(arg2)}
                            mflo ${regDistributor.getReg(result)}
                """.trimIndent()
        //[=,arg1,null,result]
            op == '='.toInt()
                    && arg1 is Symbol
                    && arg2 == null ->
                "xor ${regDistributor.getReg(result)},${'$'}zero,${regDistributor.getReg(arg1)}"
        //[=,arg1(int),null,result]
            op == '='.toInt()
                    && arg1 is Int
                    && arg2 == null ->
                "xori ${regDistributor.getReg(result)},${'$'}zero,$arg1"

            else -> {
                ""
            }
        })
        return builder.toString()
    }

}
