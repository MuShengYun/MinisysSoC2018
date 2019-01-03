package com.seu.Minisys32Assembler;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

/**
 * 读取ASM文件类
 *
 * @author XU CHENGZHUO
 */
public class ASMFile {

    private BufferedReader reader;

    /*Data*/
    public AddressDistributor dataAddrDistributor;
    public HashMap<String, Address> memonis; //助记符
    public Vector<Pair<String, Integer>> datas;

    /*Code*/
    public AddressDistributor codeAddrDistributor;
    public Vector<String> instructionCodes = new Vector<>();

    /*Symbol Table*/
    private HashMap<String, Address> codeLabels = new HashMap<>();
    static private HashSet<String> reserveWords = new HashSet<>();

    static {
        reserveWords.addAll(Instruction.registers.keySet());
        reserveWords.addAll(Instruction.ins_type.keySet());
    }

    /**
     * ASM文件构造函数
     * 进行两次扫描并编译生成机器码
     *
     * @param filePath asm文件路径
     * @throws Exception 编译错误
     */
    public ASMFile(String filePath) throws Exception {
        reader = new BufferedReader(new FileReader(filePath));

        //第一次扫描
        firstScan();

        reader.reset();
        readData();
        readCode();
    }

    /**
     * 第一遍扫描
     * 检查语法错误，确定符号名字；
     * 建立使用的全部符号名字表；每一符号名字后跟一对应值（地址或数）。
     *
     * @throws Exception 检测出语法错误
     */
    private void firstScan() throws Exception {
        String line;

        try {
            do {
                line = reader.readLine().trim();
            } while ((!line.startsWith(".data") && !line.startsWith(".DATA")));
        } catch (IOException e) {
            throw new Exception("Program format error - Data segment unfounded");
        }

        String address = line.substring(5).trim().split("[ \t]")[0];
        if (address.isEmpty())
            dataAddrDistributor = new AddressDistributor(0);
        else {
            try {
                dataAddrDistributor = new AddressDistributor(Integer.parseInt(address));
            } catch (NumberFormatException e) {
                throw new Exception("Number format error - Unidentified number");
            }
        }

        try {
            line = reader.readLine().trim().toLowerCase();
            while ((!line.startsWith(".text"))) {
                String dataDef = line.split("#", 2)[0].toLowerCase().trim();
                //判断本行是否为空或只有注释
                if (dataDef.isEmpty() || dataDef.startsWith("#")) continue;
                if (dataDef.contains(":")) {
                    //以冒号分隔助记符定义
                    String[] splits = dataDef.split(":", 2);
                    String memoni = splits[0].trim();
                    dataDef = splits[1].trim();
                    //byte[] data = FakeIns.transDataDefine(dataDef).getBytes();
                    if (memonis.containsKey(memoni))
                        throw new Exception("Syntax format error - Redefine of memoni" + memoni);
                    checkNaming(memoni);
                    //memonis.put(memoni, dataAddrDistributor.distributeAddress(data.length));
                }

            }
        } catch (IOException e) {
            throw new Exception("Program format error - Code segment unfounded");
        }

    }

    private void readData() throws Exception {
        String line;
        while ((line = reader.readLine()) != null && !line.startsWith(".TEXT")) {

        }
        //TODO:
    }

    /**
     * 读取代码段
     *
     * @throws Exception 代码段语法错误
     */
    private void readCode() throws Exception {
        String line;
        while ((line = reader.readLine()) != null) {
            String ins = line.split("#", 2)[0].toLowerCase().trim();
            //判断本行是否为空或只有注释
            if (ins.isEmpty() || ins.startsWith("#")) continue;
            if (codeLabels.isEmpty() && !ins.contains(":"))
                throw new Exception("Syntax format error - Need a label for the first instruction");
            if (ins.contains(":")) {
                String[] splits = ins.split(":", 2);
                String label = splits[0].trim();
                ins = splits[1].trim();
                if (codeLabels.containsKey(label))
                    throw new Exception("Syntax format error - Redefine of label" + label);
                checkNaming(label);
                //codeLabels.put(label, new Address());
            }
            instructionCodes.add(Instruction.transform(ins));
        }

    }

    private void checkNaming(String symbol) throws Exception {
        if (!symbol.matches("[a-zA-Z][a-zA-Z0-9$_.]{0,7}"))
            throw new Exception("Lex format error - Nonstandard naming of " + symbol);
        if (reserveWords.contains(symbol))
            throw new Exception("Syntax format error - Reserved word used as a symbol" + symbol);
    }


}
