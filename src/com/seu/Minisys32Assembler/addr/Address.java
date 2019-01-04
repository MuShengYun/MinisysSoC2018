package com.seu.Minisys32Assembler.addr;

/**
 * 地址类
 * 对应一个数据的所在基址，偏移地址以及所占空间字节数
 */
public class Address {

    int baseAddress;
    int offsetAddress;
    int byteCount;

    public Address(int base, int offset, int size) {
        baseAddress = base;
        offsetAddress = offset;
        byteCount = size;
    }

    public int getAddress() {
        return baseAddress + offsetAddress;
    }

    @Override
    public String toString() {
        return "0x" + Integer.toHexString(baseAddress + offsetAddress);
    }
}
