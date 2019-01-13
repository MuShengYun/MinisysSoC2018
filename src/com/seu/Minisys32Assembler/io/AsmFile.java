package com.seu.Minisys32Assembler.io;

import com.seu.Minisys32Assembler.addr.Address;
import com.seu.Minisys32Assembler.ins.DirectorReader;
import com.seu.Minisys32Assembler.ins.InsReader;

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
public class AsmFile {

    private BufferedReader reader;
    private int lineCount = 0;
    public Vector<String> errors = new Vector<>();

    /*Data*/
    public DirectorReader directorReader = new DirectorReader();

    /*Code*/
    public InsReader insReader = new InsReader();
    private Vector<String> instructions = new Vector<>();

    /*Symbol Table*/
    private HashMap<String, Address> memonis = new HashMap<>(); //助记符
    private HashMap<String, Address> codeLabels = new HashMap<>();
    static private HashSet<String> reserveWords = new HashSet<>();

    static {
        reserveWords.addAll(InsReader.registers.keySet());
        reserveWords.addAll(InsReader.ins_type.keySet());
    }

    /**
     * ASM文件构造函数
     * 进行两次扫描并编译生成机器码
     *
     * @param filePath asm文件路径
     * @throws Exception 编译异常
     */
    public AsmFile(String filePath) throws Exception {
        reader = new BufferedReader(new FileReader(filePath));
        firstScan();
        StringBuilder builder = new StringBuilder();
        for (String error : errors) {
            builder.append(error).append("\n");
        }
        if (!errors.isEmpty())
            throw new Exception(builder.toString());
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
                line = readLine().trim();
            } while ((!line.startsWith(".data") && !line.startsWith(".DATA")));
        } catch (IOException e) {
            throw new Exception("Program format error - Data segment unfounded");
        }

        String dataBaseAddr = line.substring(5).trim().split("[ \t]")[0];
        if (!dataBaseAddr.isEmpty() && !dataBaseAddr.startsWith("#"))
            try {
                directorReader.initAddr(parseInt(dataBaseAddr));
            } catch (NumberFormatException e) {
                asmError("Number format error - Unidentified address");
            }

        try {
            line = readLine().trim();
            while (!line.startsWith(".text") && !line.startsWith(".TEXT")) {
                String dataDef = line.split("#", 2)[0].toLowerCase().trim();
                //判断本行是否为空或只有注释
                if (dataDef.isEmpty() || dataDef.startsWith("#")) {
                    line = reader.readLine().trim();
                    continue;
                }
                if (dataDef.contains(":")) {
                    //以冒号分隔助记符定义
                    String[] splits = dataDef.split(":", 2);
                    String memoni = splits[0].trim();
                    dataDef = splits[1].trim();
                    if (memonis.containsKey(memoni))
                        asmError("Syntax format error - Redefine of memoni" + memoni);
                    checkNaming(memoni);
                    memonis.put(memoni, directorReader.readDataDefine(dataDef));
                } else
                    directorReader.readDataDefine(dataDef);
                line = readLine().trim();
            }
        } catch (IOException e) {
            asmError("Program format error - Code segment unfounded");
        }

        String codeBaseAddr = line.substring(5).trim().split("[ \t]")[0];
        if (!codeBaseAddr.isEmpty() && !codeBaseAddr.startsWith("#"))
            try {
                insReader.initAddr(parseInt(codeBaseAddr));
            } catch (NumberFormatException e) {
                asmError("Number format error - Unidentified address");
            }

        while ((line = readLine()) != null) {
            String ins = line.split("#", 2)[0].toLowerCase().trim();
            //判断本行是否为空或只有注释
            if (ins.isEmpty() || ins.startsWith("#")) continue;
            if (codeLabels.isEmpty() && !ins.contains(":"))
                asmError("Syntax format error - Need a label for the first instruction");
            if (ins.contains(":")) {
                String[] splits = ins.split(":", 2);
                String label = splits[0].trim();
                ins = splits[1].trim();
                if (!ins.matches("[a-zA-Z].*[ \t]+[a-zA-Z0-9_$].*(,[ \t]*[a-zA-Z0-9_$].*)*"))
                    asmError("InsReader format error - Not match \"op s1,s2,...\"");
                if (codeLabels.containsKey(label))
                    asmError("Syntax format error - Redefine of label" + label);
                checkNaming(label);
                Address currentAddr = insReader.codeAddrDistributor.distributeAddress(4);
                codeLabels.put(label, currentAddr);
            }
            instructions.add(ins);
        }

    }

    /**
     * 第二次扫描
     * 替换标号和助记符
     *
     * @throws Exception
     */
    private void secondScan() throws Exception {

        insReader.codeAddrDistributor.resetOffset();
        for (String ins : instructions) {
            for (String memoni : memonis.keySet()) {
                if (ins.contains(memoni))
                    ins = ins.replaceAll("(?<!\\w)" + memoni + "(?!\\w)", memonis.get(memoni).toString());
            }
            for (String label : codeLabels.keySet()) {
                if (ins.contains(label))
                    ins = ins.replaceAll("(?<!\\w)" + label + "(?!\\w)", codeLabels.get(label).toString());
            }

            Address currentAddress = insReader.codeAddrDistributor.distributeAddress(4);
            try {
                insReader.transform(ins);
            } catch (Exception e) {
                throw new Exception("Inner error of instruction:" + e.getMessage());
            }

        }

    }

    private String readLine() throws IOException {
        lineCount++;
        return reader.readLine();
    }

    private void asmError(String s) {
        errors.add("Error at line" + lineCount + ":" + s);
    }

    private void checkNaming(String symbol) throws Exception {
        if (!symbol.matches("[a-zA-Z][a-zA-Z0-9$_.]{0,7}"))
            asmError("Lex format error - Nonstandard naming of " + symbol);
        if (reserveWords.contains(symbol))
            asmError("Syntax format error - Reserved word used as a symbol" + symbol);
    }

    private int parseInt(String s) {
        if (s.startsWith("0x"))
            return Integer.parseInt(s.substring(2), 16);
        else
            return Integer.parseInt(s);
    }


}
