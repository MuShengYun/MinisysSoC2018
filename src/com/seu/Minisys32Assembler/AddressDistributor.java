package com.seu.Minisys32Assembler;

public class AddressDistributor {

    int baseAddress = 0;
    int offsetAddress = 0;
    int maxSize = 2^16; //64KB

    public AddressDistributor(int base) {
        baseAddress = base;
    }

    public Address distributeAddress(int size) {
        Address address = new Address(baseAddress, offsetAddress, size);
        offsetAddress += size;
        return address;
    }

}
