import java.io.*;
import minipython.lexer.Lexer;
import minipython.parser.Parser;
import minipython.node.*;
import java.util.*;

public class ParserTest
{
  public static void main(String[] args)
  {
    try
    {
      Parser parser =
        new Parser(
        new Lexer(
        new PushbackReader(
        new FileReader(args[0].toString()), 1024)));

     Hashtable symtable =  new Hashtable();
     Start ast = parser.parse();
     //only checks 7)
     functionDeclarationVisitor v0 = new functionDeclarationVisitor();
     ast.apply(v0);
     if(!v0.TestsFailed){//check 1-3 and helps with next checks of v2

            myvisitor v1 = new myvisitor(symtable);
            ast.apply(v1);
            //check 4, 5, 6
            secondVisitor v2 = new secondVisitor(v1.getDeclaredFunctions(), v1.getFunctions(), v1.getDeclaredVariables());
            ast.apply(v2);
     }
    }
    catch (Exception e)
    {
      System.err.println(e);
        System.err.println("Exception occurred: " + e.getMessage());
        for (StackTraceElement element : e.getStackTrace()) {
            System.err.println("at " + element);
        }
    }
  }
}

