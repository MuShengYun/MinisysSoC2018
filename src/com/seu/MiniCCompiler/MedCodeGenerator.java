package com.seu.MiniCCompiler;

import org.omg.PortableServer.THREAD_POLICY_ID;

import java.util.Vector;

/**
 * 中间代码生成器类
 */
public class MedCodeGenerator {

    Vector<ThreeAddrCode> medCode = new Vector<>();

    /**
     * 三地址代码类
     * 用四元式表示
     */
    class ThreeAddrCode {
        Integer op;
        Integer arg1;
        Integer arg2;
        Integer result;

        ThreeAddrCode(Integer op_, Integer arg1_, Integer arg2_, Integer result_) {
            op = op_;
            arg1 = arg1_;
            arg2 = arg2_;
            result = result_;
        }
    }


    public void gen(int... array) {

        //result = arg1 op arg2
        if ((array.length == 5) && (array[1] == '=')) {
            medCode.add(new ThreeAddrCode(array[3],array[2],array[4],array[0]));
        }
    }
}
