package test.seu.MiniCCompiler;

import com.seu.MiniCCompiler.Lexer;
import org.junit.Test;

import java.io.IOException;

public class LexerTest {

    @Test
    public void yylex() {
        try {
            int ID;
            Lexer  lexer = new Lexer("resource/compilerTest.cpp");
            while ((ID =  lexer.yylex()) >= 0)
            {
                System.out.println(lexer.yytext.toString() + "\tID:" + ID +"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}