package com.seu.MiniCCompiler.sem;

/**
 * 三地址代码类
 * 用四元式表示
 * 包含以下几种种类
 * i = j [Integer op = '=', Symbol/Integer arg1 = j, arg2 = null, Symbol result = i ]
 * a = b op c [op, arg1 = b, arg2 = c, result = a ]
 * ...
 */
public class ThreeAddrCode {
    public Integer op;
    public Object arg1;
    public Object arg2;
    public Symbol result;

    public ThreeAddrCode(Integer op_, Object arg1_, Object arg2_, Symbol result_) {
        op = op_;
        arg1 = arg1_;
        arg2 = arg2_;
        result = result_;
    }

    @Override
    public String toString() {
        String op_s = op.toString();
        if (op < 128 && op >= 0) {
            op_s += "(" + (char) ((int) op) + ")";
        }
        return "ThreeAddrCode{" +
                "op=" + op_s +
                ", arg1=" + arg1 +
                ", arg2=" + arg2 +
                ", result=" + result +
                '}';
    }
}
