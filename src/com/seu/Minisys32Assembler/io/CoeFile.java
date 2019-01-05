package com.seu.Minisys32Assembler.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class CoeFile {

    private BufferedWriter programWriter;
    private BufferedWriter[] dataWriters = new BufferedWriter[4];

    private Vector<Byte> insBytes = new Vector<>();
    private Vector<Byte> dataBytes = new Vector<>();

    private int prgmip32_size = 1 << 16;//64KB
    private int ram_size = 1 << 14;//16KB

    public CoeFile(AsmFile asmFile) {
        insBytes = asmFile.insReader.insBytes;
        dataBytes = asmFile.directorReader.dataBytes;
    }

    public CoeFile(Vector<AsmFile> asmFiles) {

    }

    public void writeFiles(String directoryPath) throws IOException {
        writeProgram(directoryPath + "prgmip32.coe");
        writeDatas(directoryPath);

    }

    public void writeProgram(String filePath) throws IOException {
        programWriter = new BufferedWriter(new FileWriter(filePath));
        programWriter.write(head());
        //输出字节
        for (int i = 0; i < prgmip32_size - 4 && i < insBytes.size(); i++) {
            programWriter.write(byt(insBytes.get(i)));
            if (i % 4 == 3) {
                programWriter.write(",");
                programWriter.newLine();
            }
        }
        //如果文件长度不足，补0
        for (int i = insBytes.size(); i < prgmip32_size - 4; i++) {
            programWriter.write(byt((byte) 0));
            if (i % 4 == 3) {
                programWriter.write(",");
                programWriter.newLine();
            }
        }
        //处理最后一个字后的分号问题
        for (int i = prgmip32_size - 4; i < prgmip32_size; i++) {
            if (i < insBytes.size()) programWriter.write(byt(insBytes.get(i)));
            else programWriter.write(byt((byte) 0));
            if (i % 4 == 3) {
                programWriter.write(";");
            }
        }
        programWriter.close();
    }

    private void writeDatas(String directoryPath) throws IOException {
        int numOfFiles = dataWriters.length;

        for (int i = 0; i < numOfFiles; i++) {
            dataWriters[i] = new BufferedWriter(new FileWriter(directoryPath + "ram" + i + ".coe"));
            dataWriters[i].write(head());
        }
        //轮流输出字节
        for (int i = 0; i < ram_size * numOfFiles - 4 && i < dataBytes.size(); i++) {
            BufferedWriter dataWriter = dataWriters[i % numOfFiles];
            dataWriter.write(byt(dataBytes.get(i)));
            dataWriter.write(",");
            dataWriter.newLine();
        }
        //如果文件长度不足，补0
        for (int i = dataBytes.size(); i < ram_size * numOfFiles - 4; i++) {
            BufferedWriter dataWriter = dataWriters[i % numOfFiles];
            dataWriter.write(byt((byte) 0));
            dataWriter.write(",");
            dataWriter.newLine();
        }
        //处理最后一个字后的分号问题
        for (int i = ram_size * numOfFiles - 4; i < ram_size * numOfFiles; i++) {
            BufferedWriter dataWriter = dataWriters[i % numOfFiles];
            if (i < dataBytes.size()) dataWriter.write(byt(dataBytes.get(i)));
            else dataWriter.write(byt((byte) 0));
            dataWriter.write(";");
            dataWriter.close();
        }
    }

    private String head() {
        return "memory_initialization_radix = 16;\r\n"
                + "memory_initialization_vector = \r\n";
    }

    private String byt(Byte b) {
        return Integer.toHexString(b & 0xff | 0xffffff00).substring(6);
    }

}
