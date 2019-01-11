package com.seu.MiniCCompiler;

import com.seu.MiniCCompiler.sem.Symbol;

import java.util.Vector;

/**
 * 中间代码生成器类
 */
public class MedCodeGenerator {

    Vector<ThreeAddrCode> medCode = new Vector<>();

    /**
     * 三地址代码类
     * 用四元式表示
     * 包含以下几种种类
     * i = j [Integer op = '=', Symbol/Integer arg1 = j, arg2 = null, Symbol result = i ]
     * result = arg1 op arg2
     */
    class ThreeAddrCode {
        Integer op;
        Object arg1;
        Object arg2;
        Symbol result;

        ThreeAddrCode(Integer op_, Object arg1_, Object arg2_, Symbol result_) {
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


    public void gen(Object... array) {

        //i = j [Integer op = '=', Symbol/Integer arg1 = j, arg2 = null, Symbol result = i ]
        if (array.length == 3 && (char) array[1] == '=') {
            medCode.add(new ThreeAddrCode((int) '=', array[2], null, (Symbol) array[0]));
        }

        //result = arg1 op arg2
        if ((array.length == 5) && (char) array[1] == '=') {
            medCode.add(new ThreeAddrCode((int) (char) array[3], array[2], array[4], (Symbol) array[0]));
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
