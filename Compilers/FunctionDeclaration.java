public class FunctionDeclaration {
    String name;
    int requiredArgs;
    int totalArgs;

    public String getName() {
        return name;
    }

    public int getRequiredArguments() {
        return requiredArgs;
    }

    public int getTotalArguments() {
        return totalArgs;
    }

    public FunctionDeclaration(String name){
        this.name = name;
        this.requiredArgs = 0;
        this.totalArgs = 0;
    }

    public FunctionDeclaration(String name, int requiredArgs, int totalArgs) {
        this.name = name;
        this.requiredArgs = requiredArgs;
        this.totalArgs = totalArgs;
    }

    // Check if this declaration conflicts with another
    public boolean conflictsWith(FunctionDeclaration other) {
        return this.name.equals(other.name) &&
                (this.requiredArgs == other.requiredArgs || this.totalArgs == other.totalArgs);
    }
}
