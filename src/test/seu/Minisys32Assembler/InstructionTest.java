package test.seu.Minisys32Assembler;

import com.seu.Minisys32Assembler.Instruction;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Vector;

import static org.junit.Assert.*;

public class InstructionTest {

    @Test
    @Ignore
    public void transform() {
        Vector<String> expect = new Vector<>();
        Vector<String> instructions = new Vector<>();
        //instructions.add("j 1024");//todo:j型指令操作数address没有除以4
        instructions.add("add\t$a3,$a1,$a2");
        instructions.add("addu\t$a3,$a1,$a2");
        instructions.add("sub\t$a2,$a1,$a0");
        instructions.add("subu\t$a2,$a1,$a0");
        instructions.add("and\t$a2,$a1,$a0");
        instructions.add("mult\t$a2,$a1");//todo:"Operator expected"少打了ed
        instructions.add("multu\t$a2,$a1");
        instructions.add("div\t$a2,$a1");
        instructions.add("divu\t$a2,$a1");
        instructions.add("mfhi\t$a1");
        instructions.add("mflo\t$a1");
        instructions.add("mthi\t$a1");
        instructions.add("mtlo\t$a1");
        instructions.add("mfc0\t$a2,$a1,0");
        instructions.add("mtc0\t$a2,$a1,0");
        instructions.add("or $a2,$a1,$a0");
        instructions.add("xor $a2,$a1,$a0");
        instructions.add("nor $a2,$a1,$a0");
        instructions.add("slt $a2,$a1,$a0");
        instructions.add("sltu $a2,$a1,$a0");
        instructions.add("sll $a2,$a1,10");
        //instructions.add("sll $a2,$a1,00010b");//todo:识别二进制数时后面应“-1”而不是“-2”
        instructions.add("srl $a2,$a1,10");
        instructions.add("sra $a2,$a1,10");
        instructions.add("sllv $a2,$a1,$a0");
        instructions.add("srlv $a2,$a1,$a0");
        instructions.add("srav $a2,$a1,$a0");
        instructions.add("jr $a1");
        instructions.add("jalr $a1,$a0");
        //instructions.add("break");
        //instructions.add("syscall");
        //instructions.add("eret");

        instructions.add("addi $a2,$a1,10");
        instructions.add("addi $a2,$a1,-10");
        instructions.add("addiu $a2,$a1,10");
        instructions.add("andi $a2,$a1,10");
        instructions.add("ori $a2,$a1,10");
        instructions.add("xori $a2,$a1,10");
        instructions.add("lui $a1,10");
        instructions.add("lb $a1,10($a0)");
        instructions.add("lbu $a1,10($a0)");
        instructions.add("lh $a1,10($a0)");
        instructions.add("lhu $a1,10($a0)");
        instructions.add("sb $a1,10($a0)");
        instructions.add("sh $a1,10($a0)");
        instructions.add("lw $a1,10($a0)");
        instructions.add("sw $a1,10($a0)");
        instructions.add("beq $a1,$a0,10");
        instructions.add("bne $a1,$a0,10");
        instructions.add("bgez $a1,10");
        instructions.add("bgtz $a1,10");
        instructions.add("blez $a1,10");
        instructions.add("bltz $a1,10");
        instructions.add("bgezal $a1,10");
        instructions.add("bltzal $a1,10");
        instructions.add("slti $a1,$a0,10");
        instructions.add("sltiu $a1,$a0,10");

        //instructions.add("jal 1024");

        Vector<String> fact = new Vector<>();


        //expect.add("00001000000000000000000100000000");//j 1024

        /**todo:rs,rt,td逻辑顺序错误
         * */
        expect.add("00000000101001100011100000100000");//add\t$a3,$a1,$a2
        expect.add("00000000101001100011100000100001");//addu\t$a3,$a1,$a2
        expect.add("00000000101001000011000000100010");//sub\t$a2,$a1,$a0
        expect.add("00000000101001000011000000100011");//subu\t$a2,$a1,$a0
        expect.add("00000000101001000011000000100100");//and\t$a2,$a1,$a0


        /**correct
         * */
        expect.add("00000000110001010000000000011000");//mult\t$a2,$a1
        expect.add("00000000110001010000000000011001");//multu\t$a2,$a1
        expect.add("00000000110001010000000000011010");//div\t$a2,$a1
        expect.add("00000000110001010000000000011011");//divu\t$a2,$a1
        expect.add("00000000000000000010100000010000");//mfhi\t$a1
        expect.add("00000000000000000010100000010010");//mflo\t$a1
        expect.add("00000000101000000000000000010001");//mthi\t$a1
        expect.add("00000000101000000000000000010011");//mtlo\t$a1
        expect.add("01000000000001100010100000000000");//mfc0\t$a2,$a1,0
        expect.add("01000000100001100010100000000000");//mtc0\t$a2,$a1,0


        /**todo:rs,rt,td逻辑顺序错误
         * */
        expect.add("00000000101001000011000000100101");//or $a2,$a1,$a0
        expect.add("00000000101001000011000000100110");//xor $a2,$a1,$a0
        expect.add("00000000101001000011000000100111");//nor $a2,$a1,$a0
        expect.add("00000000101001000011000000101010");//slt $a2,$a1,$a0
        expect.add("00000000101001000011000000101011");//sltu $a2,$a1,$a0

        expect.add("00000000000001010011001010000000");//sll $a2,$a1,10
        //expect.add("00000000000001010011000010000000");//todo:sll $a2,$a1,00010b
        expect.add("00000000000001010011001010000010");//srl $a2,$a1,10
        expect.add("00000000000001010011001010000011");//sra $a2,$a1,10

        expect.add("00000000100001010011000000000100");//sllv $a2,$a1,$a0
        expect.add("00000000100001010011000000000110");//srlv $a2,$a1,$a0
        expect.add("00000000100001010011000000000111");//srav $a2,$a1,$a0


        /**correct
         * */
        expect.add("00000000101000000000000000001000");//jr $a1


        /**todo:rs,rt,td逻辑顺序错误
         * */
        expect.add("00000000100000000010100000001001");//jalr $a1,$a0


        /**todo:code字段内容
         * */
        //expect.add("");//todo:break
        //expect.add("");//todo:syscall
        //expect.add("00000010000000000000000000011000");//todo:eret读定义文件时，判断空格应改为[ \t]


        /**todo:rs,rt,td逻辑顺序错误
         * */
        expect.add("00100000101001100000000000001010");//addi $a2,$a1,10
        expect.add("00100000101001100000000000010110");//addi $a2,$a1,-10
        expect.add("00100100101001100000000000001010");//addiu $a2,$a1,10
        expect.add("00110000101001100000000000001010");//andi $a2,$a1,10
        expect.add("00110100101001100000000000001010");//ori $a2,$a1,10
        expect.add("00111000101001100000000000001010");//xori $a2,$a1,10


        /**correct
         * */
        expect.add("00111100000001010000000000001010");//lui $a1,10


        /**todo:rs,rt,td逻辑顺序错误
         * */
        expect.add("10000000100001010000000000001010");//lb $a1,10($a0)
        expect.add("10010000100001010000000000001010");//lbu $a1,10($a0)
        expect.add("10000100100001010000000000001010");//lh $a1,10($a0)
        expect.add("10010100100001010000000000001010");//lhu $a1,10($a0)
        expect.add("10100000100001010000000000001010");//sb $a1,10($a0)
        expect.add("10100100100001010000000000001010");//sh $a1,10($a0)
        expect.add("10001100100001010000000000001010");//lw $a1,10($a0)
        expect.add("10101100100001010000000000001010");//sw $a1,10($a0)


        /**
         * beq,bne的顺序是正序的，注意不要改反
         * */
        expect.add("00010000101001000000000000001010");//beq $a1,$a0,10
        expect.add("00010100101001000000000000001010");//bne $a1,$a0,10


        /**correct
         * */
        expect.add("00000100101000010000000000001010");//bgez $a1,10
        expect.add("00011100101000000000000000001010");//bgtz $a1,10
        expect.add("00011000101000000000000000001010");//blez $a1,10
        expect.add("00000100101000000000000000001010");//bltz $a1,10
        expect.add("00000100101100010000000000001010");//bgezal $a1,10
        expect.add("00000100101100000000000000001010");//bltzal $a1,10


        /**todo:rs,rt,td逻辑顺序错误
         * */
        expect.add("00101000100001010000000000001010");//slti $a1,$a0,10
        expect.add("00101100100001010000000000001010");//sltiu $a1,$a0,10

        //expect.add("00001100000000000000000100000000");//jal 1024



        try {
            for (String instruction : instructions) {
                fact.add(Instruction.transform(instruction));
            }
                //System.out.println(Instruction.transform(instructions.get(i), false));
                assertEquals(expect,fact);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}