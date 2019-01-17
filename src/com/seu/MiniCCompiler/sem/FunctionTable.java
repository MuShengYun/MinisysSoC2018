package com.seu.MiniCCompiler.sem;

import java.util.ArrayList;

public class FunctionTable {
    ArrayList<Function> functions = new ArrayList<>();

    public boolean containsDef(Function function) {
        for (Function func : functions) {
            if (func.isSameDefined(function))
                return true;
        }
        return false;
    }

    public boolean containsDecl(Function function) {
        for (Function func : functions) {
            if (func.isSameDefined(function) && func.code != null)
                return true;
        }
        return false;
    }

    public Function get(Function function) {
        for (Function func : functions) {
            if (func.isSameDefined(function) && func.code != null)
                return func;
        }
        return null;
    }

    public Function get(String s) {
        for (Function func : functions) {
            if (func.name.equals(s) && func.code != null)
                return func;
        }
        return null;
    }

    public ArrayList<Function> getFunctions() {
        return functions;
    }

    public void put(Function function) {
        if (containsDecl(function)) return;
        functions.add(function);
    }
}
