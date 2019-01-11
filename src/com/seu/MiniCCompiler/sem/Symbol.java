package com.seu.MiniCCompiler.sem;

import com.seu.MiniCCompiler.Tag;

public class Symbol {
    public String name;
    public int type;

    Symbol(String name, int type) {
        this.name = name;
        this.type = type;
        Boolean b;
    }

    /**
     * 两者类型相同，或者后者可以转化为前者
     */
    public boolean matchType(Symbol that) {
        return this.type == that.type;
    }

    /**
     * 判断一个二元表达式 this op that
     * 是否符合类型规则
     *
     * @param that arg2
     * @param op   算符
     * @return -2：变量类型与算符不符合；-1：that与this不符合类型转化；>=0:符合,返回类型
     */
    public int matchType(Symbol that, int op) {
        if (!matchOp(op)) return 2;
        if (!matchType(that)) return 1;
        return type;
    }

    public Boolean matchOp(int op) {
        if (type == Tag.TYPE_INT)
            return isArithmetic(op);
        if (type == Tag.TYPE_BOOL)
            return isLogical(op);
        return false;
    }

    Boolean isArithmetic(int op) {
        switch (op) {
            case '+':
            case '-':
            case '*':
            case '/':
            case '%':
            case '$':
            case '&':
            case '^':
            case '~':
            case Tag.LSHIFT:
            case Tag.RSHIFT:
            case '|':
                return true;
            default:
                return false;
        }
    }

    Boolean isLogical(int op) {
        switch (op) {
            case Tag.OR:
            case Tag.EQ:
            case Tag.NE:
            case Tag.LE:
            case '<':
            case Tag.GE:
            case '>':
            case Tag.AND:
            case '!':
                return true;
            default:
                return false;
        }
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
