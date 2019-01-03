package com.seu.Minisys32Assembler;

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

}
