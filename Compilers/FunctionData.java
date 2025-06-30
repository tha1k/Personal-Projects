import java.util.Hashtable;

public class FunctionData {
    public Hashtable<String, String> args = new Hashtable<>(); // Argument name to type mapping
    public int requiredArgs = 0; // Number of required arguments
    public int totalArgs = 0;
    public String returnType;


    @Override
    public String toString() {
        return "Args: " + args + ", Required: " + requiredArgs + ", Total: " + totalArgs + ", Return Type: " + returnType;
    }

}

