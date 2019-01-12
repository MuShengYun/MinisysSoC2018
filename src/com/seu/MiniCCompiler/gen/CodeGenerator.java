package com.seu.MiniCCompiler.gen;

import com.seu.MiniCCompiler.sem.FunctionTable;
import com.seu.MiniCCompiler.sem.Symbol;
import com.seu.MiniCCompiler.sem.SymbolTable;
import com.seu.MiniCCompiler.sem.ThreeAddrCode;

import java.util.Vector;

/**
 * 中间代码生成器类
 */
public class CodeGenerator {

    Vector<ThreeAddrCode> medCode = new Vector<>();
    public SymbolTable global;
    public FunctionTable funcs;


    public Vector<ThreeAddrCode> reduce() {
        Vector<ThreeAddrCode> code = medCode;
        medCode = new Vector<>();
        return code;
    }

    public void gen(Object... array) {

        //i = j [Integer op = '=', Symbol/Integer arg1 = j, arg2 = null, Symbol result = i ]
        if (array.length == 3 && (char) array[1] == '=') {
            medCode.add(new ThreeAddrCode((int) '=', array[2], null, (Symbol) array[0]));
        }

        //result = arg1 op arg2
        if ((array.length == 5) && (char) array[1] == '=') {
            medCode.add(new ThreeAddrCode((int) array[3], array[2], array[4], (Symbol) array[0]));
        }

        //result = op arg1
        if ((array.length == 4) && (char) array[1] == '=') {
            medCode.add(new ThreeAddrCode((int) array[2], array[3], null, (Symbol) array[0]));
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Generated intermediate code as follow:\n");
        for (ThreeAddrCode code : medCode) {
            builder.append(code);
            builder.append("\n");
        }
        return builder.toString();
    }

}
