package test.seu.Minisys32Assembler;

import com.seu.Minisys32Assembler.Instruction;
import org.junit.Ignore;
import org.junit.Test;

public class InstructionTest {

    @Test
    @Ignore
    public void transform() {
        String ins0 = "j 000010000100001000011";
        String ins1 = "add\t$a3,$a1,$a2";
        try {
            System.out.println(Instruction.transform(ins1,true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}