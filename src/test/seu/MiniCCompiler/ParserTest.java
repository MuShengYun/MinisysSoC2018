package test.seu.MiniCCompiler;

import com.seu.MiniCCompiler.Lexer;
import com.seu.MiniCCompiler.Parser;
import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.util.Vector;

import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void syntaxTree() {
        try {
            int ID;
            Lexer lexer = new Lexer("resource/compilerTest.cpp");
            Parser parser = new Parser(lexer);
            parser.yyparse();
            System.out.println(parser.syntaxTree);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void generate() {
        try {
            int ID;
            Lexer lexer = new Lexer("resource/compilerTest.cpp");
            Parser parser = new Parser(lexer);
            parser.yyparse();
            System.out.println(parser.generate());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}