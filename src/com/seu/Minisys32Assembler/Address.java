package com.seu.Minisys32Assembler;

public class Address {

    int baseAddress;
    int offsetAddress;
    int byteCount;

    public Address(int base, int offset, int size) {
        baseAddress = base;
        offsetAddress = offset;
        byteCount = size;
    }

    @Override
    public String toString() {
        return "0x" + Integer.toHexString(baseAddress + offsetAddress);
    }
}
