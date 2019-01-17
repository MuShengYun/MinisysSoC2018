package com.seu.MiniCCompiler;

import com.seu.MiniCCompiler.gen.AsmFile;

import java.io.File;

public class MCCApp {

    public static void main(String[] args) {

    }

    public static void run(File file) throws Exception {
        Parser parser = null;
        try {
            Lexer lexer = new Lexer(file.getPath());
            parser = new Parser(lexer);
            parser.yyparse();
        } catch (Exception e) {
            parser.error.append(e.getMessage()).append("\n");
        }
        if (!parser.error.toString().isEmpty())
            throw new Exception(parser.error.toString());
        AsmFile asmFile = new AsmFile(parser.generator);
        asmFile.writeFile(file.getParent() + "/main.asm");
    }

}
