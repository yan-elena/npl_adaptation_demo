import adaptation.NPLAInterpreter;
import jason.asSyntax.ASSyntax;
import jason.asSyntax.Literal;
import npl.NormativeProgram;
import npl.parser.nplp;
import util.NPLMonitor;

import java.io.FileInputStream;

/**
 * An example to illustrate the addition of a norm to the NPLA interpreter at runtime.
 */
public class ExampleAdaptNorm {
    NormativeProgram  np          = new NormativeProgram(); // where the parser will place the result of parsing (norms, rules, ....)
    NPLAInterpreter interpreter = new NPLAInterpreter(); // the NPL interpreter

    public static void main(String[] args) throws Exception {
        new ExampleAdaptNorm().run();
    }

    public void run() throws Exception {
        // parsing
        nplp parser = new nplp(new FileInputStream("src/npl/ex1.npl"));
        parser.program(np, null);

        // loads the program into the interpreter
        interpreter.loadNP(np.getRoot());

        // listen events from NPInterpreter
        interpreter.addListener(new MyListener());

        // starts GUI
        NPLMonitor m = new NPLMonitor();
        m.add("example adapt norm", interpreter);

        System.out.println("** Initial set of norms **");
        System.out.println(interpreter.getNormsString());

        // add a fact vl-- obligation start to be created
        interpreter.addFact(ASSyntax.parseLiteral("vl(20)"));

        // unfulfillment of the obligation and creation of the sanction
        for (int i=0; i<5; i++) {
            Thread.sleep(2000);
            interpreter.verifyNorms();
        }

        // add a new norm
        interpreter.addNorm("n2", Literal.parseLiteral("obligation(bob,true,apply_fine(A,X*C),false)"), Literal.parseLiteral("sanction(A,fine(X)) & extra(C)"));
        System.out.println();
        System.out.println("** New set of norms **");
        System.out.println(interpreter.getNormsString());

        for (int i=0; i<5; i++) {
            Thread.sleep(2000);
            interpreter.verifyNorms();
        }


        // add a fact extra -- obligation for bob start to be created
        interpreter.addFact(ASSyntax.parseLiteral("extra(10)"));

        for (int i=0; i<5; i++) {
            Thread.sleep(2000);
            interpreter.verifyNorms();
        }

        System.out.println("**INTERPRETER FACTS:**\\ " + interpreter.getFacts());

    }
}
