package test.seu.Minisys32Assembler.io;

import com.seu.Minisys32Assembler.io.AsmFile;
import com.seu.Minisys32Assembler.io.CoeFile;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class CoeFileTest {

    AsmFile asmFile;
    CoeFile coeFile;

    {
        try {
            asmFile = new AsmFile("resource/bios.asm");
            coeFile = new CoeFile(asmFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void writeFiles() {
        try {
            coeFile.writeFiles("target/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void writeProgram() {
    }
}