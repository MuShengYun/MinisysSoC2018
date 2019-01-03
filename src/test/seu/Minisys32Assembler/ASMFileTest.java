package test.seu.Minisys32Assembler;

import com.seu.Minisys32Assembler.ASMFile;
import org.junit.Ignore;
import org.junit.Test;

public class ASMFileTest {

    @Test
    @Ignore
    public void readCode() throws Exception {
        ASMFile asmFile = new ASMFile("input/assemblerTest.asm");
        //asmFile.instructionBytes.forEach(System.out::println);
        //asmFile.fakeInsReader.dataBytes.forEach(System.out::println);

        for (byte b : asmFile.instructionBytes) {
            System.out.print(Integer.toHexString(b & 0xff | 0xffffff00).substring(6) + "\t");
        }

        System.out.println();

        for (byte b : asmFile.fakeInsReader.dataBytes) {
            System.out.print(Integer.toHexString(b & 0xff | 0xffffff00).substring(6) + "\t");
        }
    }

}