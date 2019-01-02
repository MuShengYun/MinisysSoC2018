package com.seu.Minisys32Assembler;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;
import java.util.Vector;

/**
 * 读取ASM文件类
 *
 * @author XU CHENGZHUO
 */
public class ASMFile {

    private BufferedReader reader;

    /*Data*/
    public Vector<Pair<String, Integer>> datas;
    public Stack<Pair<String, Integer>> stack;

    /*Code*/
    public Vector<String> instructionCodes = new Vector<>();

    /*Symbol Table*/
    private HashMap<String, Address> codeLabels = new HashMap<>();
    static private HashSet<String> reserveWords = new HashSet<>();

    static {
        reserveWords.addAll(Instruction.registers.keySet());
        reserveWords.addAll(Instruction.ins_type.keySet());
    }

    /**
     *ASM文件构造函数
     *进行两次扫描并编译生成机器码
     *
     * @param filePath asm文件路径
     * @throws Exception 编译错误
     */
    public ASMFile(String filePath) throws Exception {
        reader = new BufferedReader(new FileReader(filePath));

        //第一次扫描

        reader.reset();
        readData();
        readCode();
    }

    private void firstScan() throws Exception {

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
            if (ins.isEmpty() || ins.startsWith(";")) continue;
            if (codeLabels.isEmpty() && !ins.contains(":"))
                throw new Exception("Syntax format error - Need a label for the first instruction");
            if (ins.contains(":")) {
                String[] splits = ins.split(":", 2);
                String label = splits[0].trim();
                ins = splits[1].trim();
                if (!label.matches("[a-zA-Z][a-zA-Z0-9$_.]{0,7}"))
                    throw new Exception("Lex format error - Nonstandard naming of " + label);
                if (codeLabels.containsKey(label))
                    throw new Exception("Syntax format error - Redefine of label" + label);
                if (reserveWords.contains(label))
                    throw new Exception("Syntax format error - Reserved word used as a symbol" + label);
                //codeLabels.put(label, new Address());
            }
            instructionCodes.add(Instruction.transform(ins));
        }

    }


}
