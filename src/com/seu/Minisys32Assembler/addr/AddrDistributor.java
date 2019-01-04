package com.seu.Minisys32Assembler.addr;

/**
 * 地址分配器类
 */
public class AddrDistributor {

    int baseAddress = 0;
    int offsetAddress = 0;
    int maxSize = 1 << 16; //64KB

    public AddrDistributor(int base) {
        baseAddress = base;
    }

    public Address distributeAddress(int numOfBytes) {
        if (numOfBytes <= 0) return null;
        Address address = new Address(baseAddress, offsetAddress, numOfBytes);
        offsetAddress += numOfBytes;
        return address;
    }

    public void resetOffset() {
        offsetAddress = 0;
    }

    public int getCurrentAddress() {
        return baseAddress + offsetAddress;
    }

}
