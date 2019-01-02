package com.seu.Minisys32Assembler;

public class Main {

    public static void main(String[] args) {
        try {
            if (args.length <= 0) {
                System.out.println("Need at least one argument - InputFile");
                return;
            }
            ASMFile asmFile = new ASMFile(args[0]);

            //COEFile coeFile = new COEFile(asmFile);
            //codeFile.writeFile(args[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
