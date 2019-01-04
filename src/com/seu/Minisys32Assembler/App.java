package com.seu.Minisys32Assembler;

import com.seu.Minisys32Assembler.io.AsmFile;
import com.seu.Minisys32Assembler.io.CoeFile;

public class App {

    public static void main(String[] args) {
        try {
            if (args.length <= 0) {
                System.out.println("Need at least one argument - InputFile");
                return;
            }
            AsmFile asmFile = new AsmFile(args[0]);

            CoeFile coeFile = new CoeFile(asmFile);
            //coeFile.writeFile(args[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
