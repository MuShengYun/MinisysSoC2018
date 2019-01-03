package test.seu.Minisys32Assembler;

import com.seu.Minisys32Assembler.FakeIns;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.Vector;

public class FakeInsTest {

    @Test
    public void transDataDefine() {
        try {
            StringBuilder fact = new StringBuilder();
            StringBuilder expect = new StringBuilder();
            Vector<String> dataDefs = new Vector<>();
            dataDefs.add(".word  0x000000ff, 0x55005500");
            dataDefs.add(".byte 7,15,-3,0");
            dataDefs.add(".half 20,70,-15,67");
            dataDefs.add(".word 100,-50");
            dataDefs.add(".word 0x10002000");
            dataDefs.add(".half 0x77c,0");
            dataDefs.add(".word 0xd");
            dataDefs.add(".ascii \"hello\0\",\"a\"");
            dataDefs.add(".asciiz \"hello\"");
            dataDefs.add(".byte 6:3,4:2,6");
            dataDefs.add(".byte 9999");
            dataDefs.add(".ascii \"abc\":3");
            dataDefs.add(".asciiz \"a:c\"");
            dataDefs.add(".space 4");
            {
                //dataDefs.add(".align 2");
                //dataDefs.add(".byte 3,2,1");
            }

            expect.append("ff00000000550055");
            expect.append("070ffd00");
            expect.append("14004600f1ff4300");
            expect.append("64000000ceffffff");
            expect.append("00200010");
            expect.append("7c070000");
            expect.append("0d000000");
            expect.append("68656c6c6f0061");
            expect.append("68656c6c6f00");
            expect.append("060606040406");
            expect.append("0f");
            expect.append("616263616263616263");
            expect.append("613a6300");
            expect.append("00000000");

            for (String dataDef : dataDefs) {
                Vector<Byte> bytes = FakeIns.transDataDefine(dataDef);

                for (byte b : bytes) {
                    fact.append(Integer.toHexString(b & 0xff | 0xffffff00).substring(6));
                    //System.out.print(Integer.toHexString(b & 0xff | 0xffffff00).substring(6) + "\t");
                }
            }
            assertEquals(expect.toString(), fact.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}