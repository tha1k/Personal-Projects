import minipython.analysis.DepthFirstAdapter;
import minipython.node.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class functionDeclarationVisitor extends DepthFirstAdapter {
    //function with the same Identifier cannot have the same required args or the same total args
    public Boolean TestsFailed = false;
    // List to store all declared functions
    private List<FunctionDeclaration> functionDeclarations = new ArrayList<>();
    @Override
    public void inAFuncCommand(AFuncCommand node) {
        AFunction func = (AFunction) node.getFunction();
        LinkedList<PArgument> funcArgs = func.getArgument();
        if(funcArgs.size() == 0){
            FunctionDeclaration newDeclaration = new FunctionDeclaration(func.getId().toString());
            //check if function declaration conflicts and add to list function declarations
            for(FunctionDeclaration existingDeclaration : functionDeclarations){
                if(newDeclaration.conflictsWith(existingDeclaration)){
                    System.out.println("Error: Function '" + newDeclaration.getName() +
                            "' conflicts with an existing function '" + existingDeclaration.getName() + "'.");
                    System.out.println("Existing function: RequiredArgs = " + existingDeclaration.getRequiredArguments() +
                            ", TotalArgs = " + existingDeclaration.getTotalArguments());
                    System.out.println("New function: RequiredArgs = " + newDeclaration.getRequiredArguments() +
                            ", TotalArgs = " + newDeclaration.getTotalArguments());
                    this.TestsFailed = true;
                    return;
                }
            }
            functionDeclarations.add(newDeclaration);
        }
        int requiredArguments = 0;
        int totalArguments = 0;
        for(PArgument arg : funcArgs){
            if(arg instanceof AArgument){
                AArgument aArg = (AArgument) arg;
                // Process id_assign_value
                LinkedList<PIdAssignValue> idAssignValues = aArg.getIdAssignValue();
                for (PIdAssignValue idAssign : idAssignValues) {
                    AIdAssignValue assignValue = (AIdAssignValue) idAssign;
                    if(assignValue.getAssignValue().size()==0) {
                        requiredArguments++;
                    }
                    totalArguments++;
                }
                LinkedList<PCommaIdAssignValue> commaIdAssignValues = aArg.getCommaIdAssignValue();

                for (PCommaIdAssignValue commaIdAssign : commaIdAssignValues) {
                    ACommaIdAssignValue assignValue = (ACommaIdAssignValue) commaIdAssign;
                    AIdAssignValue argument = (AIdAssignValue) assignValue.getIdAssignValue().get(0);
                    if (argument.getAssignValue().size() == 0)
                        requiredArguments++;
                    totalArguments++;
                }
            }
        }
        FunctionDeclaration newDeclaration = new FunctionDeclaration(func.getId().toString(),requiredArguments, totalArguments);

        for(FunctionDeclaration existingDeclaration : functionDeclarations){
            if(newDeclaration.conflictsWith(existingDeclaration)){
                System.out.println("Error: Function '" + newDeclaration.getName() +
                        "' conflicts with an existing function '" + existingDeclaration.getName() + "'.");
                System.out.println("Existing function: RequiredArgs = " + existingDeclaration.getRequiredArguments() +
                        ", TotalArgs = " + existingDeclaration.getTotalArguments());
                System.out.println("New function: RequiredArgs = " + newDeclaration.getRequiredArguments() +
                        ", TotalArgs = " + newDeclaration.getTotalArguments());
                this.TestsFailed = true;
                return;
            }
        }
        functionDeclarations.add(newDeclaration);

    }

}
