package com.seu.MiniCCompiler.sem;

import com.seu.MiniCCompiler.Tag;

import java.util.Vector;

public class Function {
    public String name;
    public int retType;
    public Vector<Symbol> params;
    public SymbolTable symbolTable;
    public Vector<ThreeAddrCode> code;

    public Function(String name, int retType, Vector<Symbol> params) {
        this.name = name;
        this.retType = retType;
        this.params = params;
    }

    public String getRetTypeSpec() {
        if (retType == Tag.TYPE_INT)
            return "int";
        if (retType == Tag.TYPE_VOID)
            return "void";
        return "";
    }

    /**
     * 获取局部变量
     *
     * @return 局部变量的向量
     */
    public Vector<Symbol> getLocals() {
        Vector<Symbol> locals = new Vector<>();
        locals.addAll(symbolTable.symbolList);
        if (symbolTable.tempList.size() > 10)
            locals.addAll(symbolTable.tempList.subList(10, symbolTable.tempList.size()));
        return locals;
    }

    /**
     * 获取局部临时变量，至多为十个
     *
     * @return 局部临时变量的向量
     */
    public Vector<Symbol> getTemps() {
        if (symbolTable.tempList.size() > 10)
            return (Vector<Symbol>) symbolTable.symbolList.subList(0, 10);
        else return symbolTable.tempList;
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
