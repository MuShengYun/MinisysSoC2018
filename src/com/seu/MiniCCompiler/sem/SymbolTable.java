package com.seu.MiniCCompiler.sem;

import java.util.HashMap;

/**
 * 符号表类
 */
public class SymbolTable {
    HashMap<String, Integer> symbolTable = new HashMap<>();
    int tempCount = 0;

    public String newTemp(int type_spec) {
        String name = "t" + tempCount++;
        symbolTable.put(name, type_spec);
        return name;
    }
}
