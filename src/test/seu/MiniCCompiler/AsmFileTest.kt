package test.seu.MiniCCompiler

import com.seu.MiniCCompiler.Lexer
import com.seu.MiniCCompiler.Parser
import com.seu.MiniCCompiler.gen.AsmFile
import com.seu.MiniCCompiler.gen.CodeGenerator
import org.junit.BeforeClass
import org.junit.Ignore
import org.junit.Test

class AsmFileTest {

    companion object {
        private lateinit var asmFile: AsmFile

        @BeforeClass
        @JvmStatic
        fun constructor() {
            val lexer = Lexer("resource/compilerTest.c")
            val parser = Parser(lexer)
            parser.yyparse()
            asmFile = AsmFile(parser.generator)
        }
    }

    @Ignore
    @Test
    fun writeFile() {
        asmFile.writeFile("target/main.asm")
    }
}