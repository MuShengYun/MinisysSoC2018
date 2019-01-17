package com.seu.MiniCCompiler.sem;

/**
 * 地址分配器类
 */
public class AddrDistributor {

    int addr = 0;

    public AddrDistributor(){}
    public AddrDistributor(int base) {
        addr = base;
    }

    public int getNext() {
        return addr++;
    }

}
