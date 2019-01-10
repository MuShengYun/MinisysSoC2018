package com.seu.MiniCCompiler;

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
     * i = j [Integer op = '=', String/Integer arg1 = j, arg2 = null, String result = i ]
     * result = arg1 op arg2
     */
    class ThreeAddrCode {
        Integer op;
        Object arg1;
        Object arg2;
        String result;

        ThreeAddrCode(Integer op_, Object arg1_, Object arg2_, String result_) {
            op = op_;
            arg1 = arg1_;
            arg2 = arg2_;
            result = result_;
        }

        @Override
        public String toString() {
            return "ThreeAddrCode{" +
                    "op=" + op +
                    ", arg1=" + arg1 +
                    ", arg2=" + arg2 +
                    ", result=" + result +
                    '}';
        }
    }


    public void gen(Object... array) {

        //i = j [Integer op = '=', String/Integer arg1 = j, arg2 = null, String result = i ]
        if (array.length == 3 && (char) array[1] == '=') {
            medCode.add(new ThreeAddrCode((int) '=', array[2], null, (String) array[0]));
        }

        //result = arg1 op arg2
        /*if ((array.length == 5) && (array[1] == '=')) {
            medCode.add(new ThreeAddrCode(array[3],array[2],array[4],array[0]));
        }*/
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Generated intermediate code as follow:\n");
        for(ThreeAddrCode code : medCode) {
            builder.append(code);
            builder.append("\n");
        }
        return builder.toString();
    }

}
