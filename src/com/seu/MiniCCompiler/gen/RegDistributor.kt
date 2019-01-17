package com.seu.MiniCCompiler.gen

import com.seu.MiniCCompiler.sem.Symbol
import com.seu.MiniCCompiler.sem.ThreeAddrCode
import java.util.*
import kotlin.collections.HashMap

class RegDistributor {
    var tRegs: Vector<Symbol?> = Vector(10)
    var addrRecord: HashMap<Symbol, String> = HashMap()

    init {
        reset(HashMap())
    }

    fun getReg(code: ThreeAddrCode): String {
        val builder = StringBuilder()
        val arg1 = code.arg1
        val arg2 = code.arg2
        val result = code.result
        if (arg1 is Symbol)
            when {
                tRegs.contains(arg1) -> {
                }
                tRegs.contains(null) -> {
                    val i = tRegs.indexOf(null)
                    tRegs[i] = arg1
                    builder.append("lw ${'$'}t$i,${addrRecord[arg1] ?: throw Exception()}")
                }
                else -> throw Exception("寄存器分配问题，寄存器数量不足")

            }
        if (arg2 is Symbol)
            when {
                tRegs.contains(arg2) -> {
                }
                tRegs.contains(null) -> {
                    val i = tRegs.indexOf(null)
                    tRegs[i] = arg2
                    builder.append("lw ${'$'}t$i,${addrRecord[arg2] ?: throw Exception()}")
                }
                else -> throw Exception("寄存器分配问题，寄存器数量不足")
            }
        when {
            tRegs.contains(result) -> {
            }
            tRegs.contains(null) -> {
                val i = tRegs.indexOf(null)
                tRegs[i] = result
            }
            else -> throw Exception("寄存器分配问题，寄存器数量不足")
        }

        return builder.toString()
    }

    fun getReg(symbol: Symbol): String {
        if (!tRegs.contains(symbol))
            throw Exception()
        return "${'$'}t" + tRegs.indexOf(symbol)
    }

    fun reset(record: HashMap<Symbol, String>) {
        tRegs = Vector(10)
        tRegs.clear()
        for (i in 0 until 10)
            tRegs.addElement(null)
        addrRecord.clear()
        this.addrRecord = record

    }

    fun clear(): String {
        val builder = StringBuilder()
        for ((i, symbol) in tRegs.withIndex()) {
            if (addrRecord[symbol] != null)
                builder.append("sw ${'$'}t$i,${addrRecord[symbol]}\n\t\t")
        }
        return builder.toString()
    }

}