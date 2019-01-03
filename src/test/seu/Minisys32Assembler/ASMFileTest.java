package test.seu.Minisys32Assembler;

import com.seu.Minisys32Assembler.ASMFile;
import com.seu.Minisys32Assembler.Instruction;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class ASMFileTest {

    @Test
    @Ignore
    public void readCode() throws Exception {
        ASMFile asmFile = new ASMFile("input/assemblerTest.asm");
        asmFile.instructionCodes.forEach(System.out::println);
    }

}