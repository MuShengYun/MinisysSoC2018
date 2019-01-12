package com.seu.MiniCCompiler.sem;

import java.util.Stack;
import java.util.Vector;

/**
 * 符号表类
 */
public class SymbolTable {

    /*符号表数据 （符号名称，符号类型）*/
    public Vector<Symbol> symbolList = new Vector<>();
    public Vector<Symbol> tempList = new Vector<>();

    /*环境符号表，作用域包含当前语句的符号表栈*/
    public Stack<SymbolTable> env = new Stack<>();

    public SymbolTable(Stack<SymbolTable> env) {
        this.env.addAll(env);
    }

    public Symbol newTemp(int type_spec) {
        String name = "t" + tempList.size();
        Symbol symbol = new Symbol(name, type_spec);
        tempList.add(symbol);
        return symbol;
    }

    /**
     * 为符号表添加一个符号
     * 使用前需要判断是否重定义
     * @param name 符号名
     * @param type 符号类型
     */
    public void put(String name, int type) {
        if (containsBlock(name))
            return;
        symbolList.add(new Symbol(name, type));
    }

    public Boolean contains(String name) {
        if (containsBlock(name))
            return true;
        if (env.empty())
            return false;
        return env.peek().contains(name);
    }

    public Boolean containsBlock(String name) {
        for (Symbol symbol : symbolList) {
            if (symbol.name.equals(name))
                return true;
        }
        for (Symbol symbol : tempList) {
            if (symbol.name.equals(name))
                return true;
        }
        return false;
    }


    public Symbol get(String name) {
        for (Symbol symbol : symbolList) {
            if (symbol.name.equals(name))
                return symbol;
        }
        for (Symbol symbol : tempList) {
            if (symbol.name.equals(name))
                return symbol;
        }
        if (env.empty())
            return null;
        return env.peek().get(name);
    }
}
