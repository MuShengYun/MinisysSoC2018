package com.seu.Minisys32Assembler;

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
    public AddrDistributor dataAddrDistributor;
    public HashMap<String, Address> memonis; //助记符
    public Vector<Byte> dataBytes;

    /*Code*/
    public AddrDistributor codeAddrDistributor;
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

        //第二次扫描
        reader.reset();
        secondScan();
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

        String dataBaseAddr = line.substring(5).trim().split("[ \t]")[0];
        try {
            dataAddrDistributor = new AddrDistributor(dataBaseAddr.isEmpty() ? 0 : Integer.parseInt(dataBaseAddr));
        } catch (NumberFormatException e) {
            throw new Exception("Number format error - Unidentified address");
        }

        try {
            line = reader.readLine().trim();
            while (!line.startsWith(".text") && !line.startsWith(".TEXT")) {
                String dataDef = line.split("#", 2)[0].toLowerCase().trim();
                //判断本行是否为空或只有注释
                if (dataDef.isEmpty() || dataDef.startsWith("#")) continue;
                if (dataDef.contains(":")) {
                    //以冒号分隔助记符定义
                    String[] splits = dataDef.split(":", 2);
                    String memoni = splits[0].trim();
                    dataDef = splits[1].trim();
                    Vector<Byte> data = FakeIns.transDataDefine(dataDef);
                    dataBytes.addAll(data);
                    if (memonis.containsKey(memoni))
                        throw new Exception("Syntax format error - Redefine of memoni" + memoni);
                    checkNaming(memoni);
                    memonis.put(memoni, dataAddrDistributor.distributeAddress(data.size()));
                } else
                    dataBytes.addAll(FakeIns.transDataDefine(dataDef));

            }
        } catch (IOException e) {
            throw new Exception("Program format error - Code segment unfounded");
        }

        String codeBaseAddr = line.substring(5).trim().split("[ \t]")[0];
        try {
            codeAddrDistributor = new AddrDistributor(codeBaseAddr.isEmpty() ? 0 : Integer.parseInt(codeBaseAddr));
        } catch (NumberFormatException e) {
            throw new Exception("Number format error - Unidentified address");
        }

        while ((line = reader.readLine()) != null) {
            String ins = line.split("#", 2)[0].toLowerCase().trim();
            //判断本行是否为空或只有注释
            if (ins.isEmpty() || ins.startsWith("#")) continue;
            if (codeLabels.isEmpty() && !ins.contains(":"))
                throw new Exception("Syntax format error - Need a label for the first instruction");
            Address currentAddr = codeAddrDistributor.distributeAddress(4);
            if (ins.contains(":")) {
                String[] splits = ins.split(":", 2);
                String label = splits[0].trim();
                ins = splits[1].trim();
                if (!ins.matches("[a-zA-Z].*[ \t]+[a-zA-Z].*(,[ \t]*[a-zA-Z].*)*"))
                    throw new Exception("Instruction format error - Not match \"op s1,s2,...\"");
                if (codeLabels.containsKey(label))
                    throw new Exception("Syntax format error - Redefine of label" + label);
                checkNaming(label);
                codeLabels.put(label, currentAddr);
            }
        }

    }

    /**
     * 第二次扫描
     *
     * @throws Exception 代码段语法错误
     */
    private void secondScan() throws Exception {
        String line;
        while ((line = reader.readLine()) != null) {
            String ins = line.split("#", 2)[0].toLowerCase().trim();
            //判断本行是否为空或只有注释
            if (ins.isEmpty() || ins.startsWith("#")) continue;
            if (ins.contains(":")) {
                String[] splits = ins.split(":", 2);
                String label = splits[0].trim();
                ins = splits[1].trim();
            }
            for (String memoni : memonis.keySet()) {
                if (ins.contains(memoni))
                    ins = ins.replace(memoni, memonis.get(memoni).toString());
            }

            for (String label : codeLabels.keySet()) {
                if (ins.contains(label))
                    ins = ins.replace(label, codeLabels.get(label).toString());
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
