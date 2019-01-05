package test.seu.Minisys32Assembler.io;

import com.seu.Minisys32Assembler.io.AsmFile;
import org.junit.Ignore;
import org.junit.Test;

public class AsmFileTest {

    @Test
    @Ignore
    public void readCode() throws Exception {
        AsmFile asmFile = new AsmFile("input/bios.asm");
        //asmFile.instructionBytes.forEach(System.out::println);
        //asmFile.directorReader.dataBytes.forEach(System.out::println);

        for (byte b : asmFile.insReader.dataBytes) {
            System.out.print(Integer.toHexString(b & 0xff | 0xffffff00).substring(6) + "\t");
        }

        System.out.println();

        for (byte b : asmFile.directorReader.dataBytes) {
            System.out.print(Integer.toHexString(b & 0xff | 0xffffff00).substring(6) + "\t");
        }
    }

}