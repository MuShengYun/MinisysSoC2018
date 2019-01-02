package com.seu.Minisys32Assembler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

/**
 * 汇编指令类
 * <p>
 * 包含一个静态方法transform()
 * 应用汇编语言规则，将指令转换为机器码
 *
 * @author XU CHENGZHUO
 * @author ZHANG BINGXI
 */
public class Instruction {

    public static HashMap<String, Character> ins_type = new HashMap<>();
    private static HashMap<String, Vector<String>> ins_rule = new HashMap<>();
    public static HashMap<String, Integer> registers = new HashMap<>();

    /*
     * 初始化块
     * 读取指令集规则并保存在ins_type和ins_rule中,初始化寄存器组的名称
     * @param ins_type 保存了每个指令的类型
     * @param ins_rule 保存了每个指令的规则
     */
    static {

        try {
            BufferedReader reader;
            reader = new BufferedReader(new FileReader("input/Minisys-1A 57条指令集与机器码对照表.txt"));
            reader.readLine();

            String line;
            Character thisType = 'R';
            Character nextType = 'I';
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(nextType.toString())) {
                    thisType = nextType;
                    nextType = 'J';
                    continue;
                }
                String operator = line.split("\t")[0];
                Vector<String> operands = new Vector<>();
                String[] splits = line.replaceFirst(operator, "").split("\t");
                for (String split : splits) {
                    if (!split.trim().isEmpty())
                        operands.add(split.trim());
                }
                ins_type.put(operator, thisType);
                ins_rule.put(operator, operands);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        int registerCount = 0;
        registers.put("$zero", registerCount++);
        registers.put("$at", registerCount++);
        registers.put("$v0", registerCount++);
        registers.put("$v1", registerCount++);
        for (int i = 0; i < 4; i++) {
            registers.put("$a" + i, registerCount++);
        }
        for (int i = 0; i < 8; i++) {
            registers.put("$t" + i, registerCount++);
        }
        for (int i = 0; i < 8; i++) {
            registers.put("$s" + i, registerCount++);
        }
        registers.put("$t8", registerCount++);
        registers.put("$t9", registerCount++);
        registers.put("$k1", registerCount++);
        registers.put("$k2", registerCount++);
        registers.put("$gp", registerCount++);
        registers.put("$sp", registerCount++);
        registers.put("$fp", registerCount);
        registers.put("$s8", registerCount++);
        registers.put("$ra", registerCount);

    }

    /**
     * 默认采用非debug模式进行指令转换
     *
     * @param ins 输入的指令
     * @return 输出的32位机器码 用String类型表示
     * @throws Exception 指令转换存在异常
     */
    public static String transform(String ins) throws Exception {
        return transform(ins, false);
    }

    /**
     * @param ins         输入的指令
     * @param isDebugMode 若为true，则输出便于调试的机器码，否则直接输出
     * @return 输出的32位机器码 用String类型表示
     * @throws Exception 指令转换存在异常
     */
    public static String transform(String ins, boolean isDebugMode) throws Exception {

        //分割操作符与操作数
        String operator = ins.split("[ \t]")[0];
        ArrayList<String> operands = new ArrayList<>();
        String[] splits = ins.replaceFirst(operator, "").split(",");    //按照逗号分隔
        for (String split : splits) {
            split = split.replaceAll("[ \t]","");
            if (split.matches(".+\\(.+\\)")) {   //如果有寄存器间接寻址，进行分离
                String[] innerSplits = split.split("[( )]");
                operands.add(innerSplits[1]);     //如20($t0), 分隔为$t0和20两个操作数
                operands.add(innerSplits[0]);
            }
            else
                operands.add(split);
        }

        //如果代码规则部分是二进制码，直接添加至字符串尾部；否则寻找对应的操作数，并转换
        StringBuilder code = new StringBuilder();
        int operandCount = 0;
        Vector<String> code_parts = ins_rule.get(operator);
        if (null == code_parts)
            throw new Exception("Instruction format error - Operator expect");
        if (isDebugMode)
            switch (ins_type.get(operator)) {
                case 'R':
                    code.append("op\t\trs\t\trt\t\trd\t\tshamt\tfunct\n");
                    break;
                case 'I':
                    code.append("op\trs\t\trt\t\timmediate\\offset\n");
                    break;
                case 'J':
                    code.append("op\taddress\n");
                    break;
            }
        for (String code_part : code_parts) {
            if (code_part.matches("[0-1]*")) {
                code.append(code_part);
            } else {
                if (operands.size() <= operandCount)
                    throw new Exception("Instruction format error - Not enough operands");
                code.append(operandStandardize(operator, code_part, operands.get(operandCount++)));
            }
            if (isDebugMode)
                code.append("\t");
        }
        if (isDebugMode)
            code.append("\b");

        if (operands.size() < operandCount)
            throw new Exception("Syntax format error - Extra characters on line");
        return code.toString();

    }


    /**
     * 识别操作数，将并依据规则操作数变为标准的二进制代码
     * 对于rs,rt,rd等字段,将操作数作为寄存器名查询，并返回寄存器号
     * 对于其他字段，读取数据（包括二进制，十进制，十六进制,字符等）
     * 并扩展位数且转换为补码
     * 最后根据是否为address进行移位操作
     *
     * @param operandType 操作数类型，如rs，rt，address等
     * @param operand     操作数的值
     * @return 标准二进制代码
     */
    private static String operandStandardize(String operator, String operandType, String operand) throws Exception {

        int num;
        int length = operand.length();
        //寄存器号
        if (operandType.equals("rs") || operandType.equals("rt") || operandType.equals("rd")) {
            if (null == registers.get(operand))
                throw new Exception("Instruction format error - Operand must be register");
            num = (registers.get(operand));
            String code = "00000" + Integer.toBinaryString(num);
            return code.substring(code.length() - 5, code.length());
        }
        //单引号字符
        if (operand.startsWith("'") && operand.endsWith("'")) {
            if (operand.length() == 3)
                num = operand.charAt(1);
            else if (operand.length() > 3 && operand.charAt(1) == '\\') {
                String es = operand.substring(2, length - 2);
                if (es.equals("r")) num = '\r';
                if (es.equals("t")) num = '\t';
                if (es.equals("n")) num = '\n';
                if (es.equals("r")) num = '\b';
                if (es.equals("r")) num = '\f';
                else if (es.length() == 1)
                    num = es.charAt(0);
                else num = Integer.parseInt(es, 8);
            } else
                throw new Exception("Lex format error");
        }
        //不同进制的数字
        else
            try {

                switch (operand.charAt(length - 1)) {
                    case 'b':
                        num = Integer.parseInt(operand.substring(0, length - 2), 2);
                        break;
                    case 'h':
                        num = Integer.parseInt(operand.substring(0, length - 2), 16);
                        break;
                    case 'o':
                        num = Integer.parseInt(operand.substring(0, length - 2), 8);
                        break;
                    case 'd':
                        num = Integer.parseInt(operand.substring(0, length - 2));
                        break;
                    default:
                        if (operand.startsWith("0x"))
                            num = Integer.parseInt(operand.substring(2, length - 1), 16);
                        else num = Integer.parseInt(operand);
                }
            } catch (NumberFormatException e) {
            if(operand.matches("[a-zA-Z]"))
                throw new Exception("Syntax format error - Symbol not defined");
            else
                throw new Exception("Lex format error - Unidentified number");

            }

        //跳转指令的偏移或地址除以4
        if (operator.startsWith("b") && operand.equals("offset") ||
                operand.startsWith("j") && operand.equals("address"))
            num /= 4;

        //转化为补码,扩展至相应位数
        String code = Integer.toBinaryString(num);
        Character signDigit = '0';
        if (operandType.equals("offset") || operandType.equals("immediate"))
            if (!operator.endsWith("u"))
                signDigit = code.charAt(0);
        int numOfBits = 0;
        switch (operandType) {
            case "sel":
                numOfBits = 3;
                break;
            case "shamt":
                numOfBits = 5;
                break;
            case "immediate":
            case "offset":
                numOfBits = 6;
                break;
            case "address":
                numOfBits = 26;
                break;
        }
        String codeWithFilledBits = String.join("", Collections.nCopies(32, signDigit.toString())).concat(code);
        return codeWithFilledBits.substring(codeWithFilledBits.length() - numOfBits, codeWithFilledBits.length());
    }

}
