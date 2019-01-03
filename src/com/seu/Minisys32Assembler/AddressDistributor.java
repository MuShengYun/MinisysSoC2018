package com.seu.Minisys32Assembler;

public class AddressDistributor {

    int baseAddress = 0;
    int offsetAddress = 0;
    int maxSize = 1 << 16; //64KB

    public AddressDistributor(int base) {
        baseAddress = base;
    }

    public Address distributeAddress(int size) {
        if (size <= 0) return null;
        Address address = new Address(baseAddress, offsetAddress, size);
        offsetAddress += size;
        return address;
    }

}
