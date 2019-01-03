package test.seu.Minisys32Assembler;

import com.seu.Minisys32Assembler.FakeIns;
import org.junit.Test;

import java.util.Vector;

public class FakeInsTest {

    @Test
    public void transDataDefine() {
        try {

            String dataDef = ".WORD  0X000000FF, 0X55005500".toLowerCase();
            Vector<Byte> bytes = FakeIns.transDataDefine(dataDef);
            for (byte b : bytes) {
                System.out.print(Integer.toHexString(b & 0xff | 0xffffff00).substring(6) + "\t");
            }
            System.out.print("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}