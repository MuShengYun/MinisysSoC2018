package com.seu.MiniCCompiler.sem;

import com.seu.MiniCCompiler.Tag;

public class Symbol {
    public String name;
    public int type;

    Symbol(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public boolean checkType(Symbol that) {
        return this.type == that.type;
    }

    public String getTypeSpec() {
        if (type == Tag.TYPE_INT)
            return "int";
        if (type == Tag.TYPE_BOOL)
            return "bool";
        return "";
    }


    @Override
    public String toString() {
        return name;
    }
}
