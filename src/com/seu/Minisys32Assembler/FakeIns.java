package com.seu.Minisys32Assembler;

import javax.xml.ws.WebServiceException;
import java.util.Vector;

public class FakeIns {

    private static Integer nextSize;

    /**
     * 伪指令处理
     *
     * @param dataDef 伪指令,必须全部是小写且首尾没有空格
     * @return 以数组的形式返回字节流
     * @throws Exception 伪指令格式错误
     */
    public static Vector<Byte> transDataDefine(String dataDef) throws Exception {
        if (dataDef.startsWith(".space")) {
            try {
                return space(Integer.parseInt(dataDef.substring(".space".length())));
            } catch (NumberFormatException e) {
                throw new Exception("Data define error - Space must be positive number");
            }
        }

        if (dataDef.startsWith(".align")) {
            try {
                align(Integer.parseInt(dataDef.substring(".align".length())));
            } catch (NumberFormatException e) {
                throw new Exception("Data define error - Bits must be greater than one");
            }
            return new Vector<>();
        }

        Vector<Byte> bytes = defines(dataDef);
        while (bytes.size() < nextSize)
            bytes.add((byte) 0);
        nextSize = null;
        return bytes;

    }

    /**
     * 将数据定义伪指令翻译成数据字节
     * 小端存储
     *
     * @param dataDef 数据定义伪指令,必须全部是小写且首尾没有空格
     * @return 以数组的形式返回字节流
     * @throws Exception 伪指令格式错误
     */
    private static Vector<Byte> defines(String dataDef) throws Exception {

        Vector<Byte> bytes = new Vector<>();

        //分隔助记符类型
        String[] splits = dataDef.split("[ \t]", 2);
        String[] dataSet = splits[1].split(",");
        int sizeOfEach;
        switch (splits[0]) {
            case ".byte":
                sizeOfEach = 1;
                break;
            case ".half":
                sizeOfEach = 2;
                break;
            case ".word":
            case ".float":
                sizeOfEach = 4;
                break;
            case ".double":
                sizeOfEach = 8;
                break;
            case ".ascii":
            case ".asciiz":
                for (String data : dataSet) {
                    Vector<Byte> byteValue = new Vector<>();
                    data = data.trim();
                    int repeats = 1;
                    if (data.matches(".+:[0-9]+")) {
                        String[] twoSplits = data.split(":");
                        repeats = Integer.parseInt(twoSplits[1].trim());
                        if (repeats <= 0)
                            throw new Exception("Data define error - Repeats must be positive");
                        data = twoSplits[0].trim();
                    }
                    if (!data.matches("\"(\\\\.|[^\\\\\"])*\""))
                        throw new Exception("Data define error - String expected");
                    for (int i = 1; i < data.length() - 1; i++) {
                        char c = data.charAt(i);
                        if (c != '\\')
                            byteValue.add(String.valueOf(c).getBytes()[0]);
                        else {
                            c = data.charAt(++i);
                            if (c == 'r') byteValue.add("\r".getBytes()[0]);
                            if (c == 't') byteValue.add("\t".getBytes()[0]);
                            if (c == 'n') byteValue.add("\n".getBytes()[0]);
                            if (c == 'b') byteValue.add("\b".getBytes()[0]);
                            if (c == 'f') byteValue.add("\f".getBytes()[0]);
                            else byteValue.add(String.valueOf(c).getBytes()[0]);
                        }

                    }
                    if (splits[0].equals(".asciiz")) byteValue.add("\0".getBytes()[0]);
                    for (int i = 0; i < repeats; i++) {
                        bytes.addAll(byteValue);
                    }
                }
                return bytes;
            default:
                throw new Exception("Data define error - Unknown data type");
        }

        for (String data : dataSet) {
            data = data.trim();
            Integer value;
            Vector<Byte> byteValue = new Vector<>();
            int repeats = 1;
            if (data.contains(":")) {
                String[] twoSplits = data.split(":");
                repeats = Integer.parseInt(twoSplits[1].trim());
                if (repeats <= 0)
                    throw new Exception("Data define error - Repeats must be positive");
                data = twoSplits[0].trim();
            }
            try {
                if (data.startsWith("0x"))
                    value = Integer.parseInt(data.substring(2), 16);
                else
                    value = Integer.parseInt(data);
            } catch (NumberFormatException e) {
                throw new Exception("Data define error - Unidentified number");
            }
            for (int i = 0; i < sizeOfEach; i++) {
                byteValue.add(value.byteValue());
                value = value >> 8;
            }

            for (int i = 0; i < repeats; i++) {
                bytes.addAll(byteValue);
            }
        }

        return bytes;
    }

    private static Vector<Byte> space(int size) throws Exception {
        if (size <= 0) throw new Exception();
        Vector<Byte> bytes = new Vector<>();
        for (int i = 0; i < size; i++) {
            bytes.add((byte) 0);
        }
        return bytes;
    }

    private static void align(int numOfBits) throws Exception {
        if (numOfBits <= 1) throw new Exception();
        nextSize = 1 << numOfBits;
    }
}
