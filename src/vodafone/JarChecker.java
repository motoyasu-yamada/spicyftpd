package vodafone;

import java.io.*;

public class JarChecker {
    public void run() {
        String[] argv = new String[] {
        "-jadfile", "jadFile",
        "-sigfile", "signatureFile",
        "-comfile", "combinationFile",
        "-libfile", "stubClassLibFile",
        };

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int ret = jmain.JarChecker.start(argv,output);
        System.out.println("Return:" + ret);
        System.out.println(new String(output.toByteArray()));
    }
}
