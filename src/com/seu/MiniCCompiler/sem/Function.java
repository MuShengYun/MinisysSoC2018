package com.seu.MiniCCompiler.sem;

import com.seu.MiniCCompiler.Tag;

import java.util.ArrayList;
import java.util.Vector;

public class Function {
    public String name;
    public int retType;
    public ArrayList<Symbol> params;
    public SymbolTable symbolTable;
    public Vector<ThreeAddrCode> code;

    Function(String name, int retType, ArrayList<Symbol> params) {
        this.name = name;
        this.retType = retType;
        this.params = params;
    }

    public String getRetTypeSpec() {
        if (retType == Tag.TYPE_INT)
            return "int";
        if (retType == Tag.TYPE_BOOL)
            return "bool";
        return "";
    }

    /**
     * 判断函数是否定义相同
     *
     * @param func 被判断的函数
     * @return 当函数名和参数类型都一致时，返回真；否则返回假
     */
    boolean isSameDefined(Function func) {
        if (!name.equals(func.name)) return false;
        if (params.size() != func.params.size()) return false;
        for (int i = 0; i < params.size(); i++) {
            if (!params.get(i).matchType(func.params.get(i)))
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name + "(" + params.toString().replaceAll("[\\[\\]]", "") + ")";
    }
}
