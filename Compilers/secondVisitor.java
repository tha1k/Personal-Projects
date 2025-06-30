import minipython.analysis.DepthFirstAdapter;
import minipython.node.*;

import java.awt.*;
import java.util.*;
import java.util.List;

public class secondVisitor extends DepthFirstAdapter {
    private HashSet<String> declaredFunctions;
    private HashMap<String, FunctionData> functions;
    private HashMap<String, String> declaredVariables;

    secondVisitor(HashSet<String> declaredFuncs, HashMap<String, FunctionData> funcs, HashMap<String, String> DeclaredVariables) {
        declaredFunctions = declaredFuncs;
        functions = funcs;
        declaredVariables = DeclaredVariables;
    }

    public void inAFuncCallStatement(AFuncCallStatement node) {
        AFunctionCall funcCall= (AFunctionCall) node.getFunctionCall();
        String funcCallName = funcCall.getId().toString();
        LinkedList arglist =  funcCall.getArglist();
        int usedArgs = 0;
        //no args
        if(functions.containsKey(funcCallName)) {
            if (arglist.size() == 0) {
                //check if func has 0 required args

                if (functions.get(funcCallName).requiredArgs > usedArgs) {
                    System.out.println("Function Called " + funcCallName + " at Line " + funcCall.getId().getLine()
                            + " Does not have the required number of arguments"
                    );

                }
            } else {
                usedArgs++;
                AArglist arglist1 = (AArglist) arglist.getFirst();

                usedArgs += arglist1.getCExpression().size();

                //1) check if func called with at least the required number of args
                if (functions.get(funcCallName).requiredArgs > usedArgs) {
                    System.out.println("Function Called " + funcCallName + " at Line " + funcCall.getId().getLine()
                            + " Does not have the required number of arguments"
                    );

                }
                //2) check if function is called with more than the total number of args
                if (functions.get(funcCallName).totalArgs < usedArgs) {
                    System.out.println("Function Called " + funcCallName + " at Line " + funcCall.getId().getLine()
                            + " Uses too many arguments"
                    );

                }

            }
        }
        String functionCallName = funcCall.getId().toString();

        if(!declaredFunctions.contains(functionCallName)){
            System.out.println("on Line: "+ funcCall.getId().getPos() +
                    " Function " + functionCallName + " Is never declared " );
        }

        AFunctionCall funcCall2 = (AFunctionCall) node.getFunctionCall();
        String name = funcCall.getId().toString();
        LinkedList args = funcCall.getArglist();
        AArglist arglist2 = new AArglist();
        List<String> ArgTypes = new ArrayList<>();
        if(args.size() > 0){
            arglist2 = (AArglist) args.getFirst();
            String arg1Type = inferExpressionType(arglist2.getExpression());

            ArgTypes.add(arg1Type);
        }
        for(Object arg : arglist2.getCExpression()){
            ACExpression actualArg = (ACExpression) arg;
            ArgTypes.add(inferExpressionType(actualArg.getExpression()));
        }
        if(!functions.containsKey(name)){
            //System.out.println("AAAAAAAA");
            return;
        }
        Hashtable<String, String> typedVars = functions.get(name).args;
        Iterator<String> argTypeIterator = ArgTypes.iterator();

// Iterate over the keys of typedVars and update the values with ArgTypes
        for (String var : typedVars.keySet()) {
            if (argTypeIterator.hasNext()) {
                String inferredType = argTypeIterator.next();
                typedVars.put(var, inferredType); // Update the type of the variable
                //System.out.println("Updated var " + var + " with type " + inferredType);
            }
        }

    }

    public void inAFuncExpression(AFuncExpression node){
        AFunctionCall funcCall= (AFunctionCall) node.getFunctionCall();
        String funcCallName = funcCall.getId().toString();
        LinkedList arglist =  funcCall.getArglist();
        int usedArgs = 0;


        //no args
        if(functions.containsKey(funcCallName)) {
            if (arglist.size() == 0) {
                //check if func has 0 required args

                if (functions.get(funcCallName).requiredArgs > usedArgs) {
                    System.out.println("Function Called " + funcCallName + " at Line " + funcCall.getId().getLine()
                            + " Does not have the required number of arguments"
                    );

                }
            } else {
                usedArgs++;
                AArglist arglist1 = (AArglist) arglist.getFirst();

                usedArgs += arglist1.getCExpression().size();

                //1) check if func called with at least the required number of args
                if (functions.get(funcCallName).requiredArgs > usedArgs) {
                    System.out.println("Function Called " + funcCallName + " at Line " + funcCall.getId().getLine()
                            + " Does not have the required number of arguments"
                    );

                }
                //2) check if function is called with more than the total number of args
                if (functions.get(funcCallName).totalArgs < usedArgs) {
                    System.out.println("Function Called " + funcCallName + " at Line " + funcCall.getId().getLine()
                            + " Uses too many arguments"
                    );

                }

            }
        }
        String functionCallName = funcCall.getId().toString();

        if(!declaredFunctions.contains(functionCallName)){
            System.out.println("on Line: "+ funcCall.getId().getLine() +
                    " Function " + functionCallName + " Is never declared " );
        }

        String name = funcCall.getId().toString();
        LinkedList args = funcCall.getArglist();
        AArglist arglist2 = new AArglist();
        List<String> ArgTypes = new ArrayList<>();
        if(args.size() > 0){
            arglist2 = (AArglist) args.getFirst();
            String arg1Type = inferExpressionType(arglist2.getExpression());

            ArgTypes.add(arg1Type);
        }
        for(Object arg : arglist2.getCExpression()){
            ACExpression actualArg = (ACExpression) arg;
            ArgTypes.add(inferExpressionType(actualArg.getExpression()));
        }
        if(!functions.containsKey(name)){
            //System.out.println("AAAAAAAA");
            return;
        }
        Hashtable<String, String> typedVars = functions.get(name).args;
        Iterator<String> argTypeIterator = ArgTypes.iterator();

// Iterate over the keys of typedVars and update the values with ArgTypes
        for (String var : typedVars.keySet()) {
            if (argTypeIterator.hasNext()) {
                String inferredType = argTypeIterator.next();
                typedVars.put(var, inferredType); // Update the type of the variable
                //System.out.println("Updated var " + var + " with type " + inferredType);
            }
        }
    }



    private String inferExpressionType(PExpression expression) {
        if (expression instanceof AValueExpression) {
            // Base case: Directly infer the type of the value
            return inferType(((AValueExpression) expression).getValue());
        } else if (expression instanceof AIdExpression) {
            // For identifier expressions, look up the type in declared variables
            String varName = ((AIdExpression) expression).getId().toString().trim();
            return declaredVariables.getOrDefault(varName, "unknown");
        } else if (expression instanceof AIdArrayExpression) {
            // For array access expressions, infer the type of the array and return the element type
            String arrayName = ((AIdArrayExpression) expression).getId().toString().trim();
            return inferArrayElementType(arrayName);
        } else if (expression instanceof AFuncExpression) {
            // For function calls, infer the return type from the declared function
            AFunctionCall func = (AFunctionCall) ((AFuncExpression) expression).getFunctionCall();
            String funcName = func.getId().toString();
            if (functions.containsKey(funcName)) {

                return functions.get(funcName).returnType;
            } else {

                return "unknown";
            }
        } else if (expression instanceof ALenExpression) {
            // The `len` function always returns an integer
            return "int";
        } else if (expression instanceof AAsciiExpression) {
            // The `ascii` function returns an integer
            return "int";
        } else if (expression instanceof AMaxExpression ) {
            // For `max` or `min`, infer the type of the first value (all values should be of the same type)
            AMaxExpression maxExp = (AMaxExpression) expression;
            return inferType(maxExp.getValue1());
        } else if(expression instanceof AMinExpression){
            AMinExpression minExp = (AMinExpression) expression;
            return inferType(minExp.getValue1());
        }else if (expression instanceof AParExpExpression) {
            // For parenthesized expressions, infer the type of the inner expression
            return inferExpressionType(((AParExpExpression) expression).getExpression());
        } else if (expression instanceof AArrayExpression) {
            // For array creation, infer the type of the first value (assuming homogeneity)
            AArrayExpression arrayExp = (AArrayExpression) expression;
            return inferType(arrayExp.getValue1()) + "[]";
        } else if (expression instanceof AAdditionExpression) {
            return inferExpressionType(((AAdditionExpression) expression).getEx1());
        }
        //in other cases (Numeric expression its always a number
        return "int";
    }

    // Helper method to infer the type of an array's elements
    private String inferArrayElementType(String arrayName) {
        if (declaredVariables.containsKey(arrayName)) {
            String type = declaredVariables.get(arrayName);
            if (type.endsWith("[]")) {
                return type.substring(0, type.length() - 2); // Remove `[]` to get the element type
            }
        }
        return "unknown";
    }
    private String inferType(PValue value) {
        if (value instanceof ANumValue) {
            return "int";
        } else if (value instanceof AStringValue) {
            return "string";
        } else if (value instanceof ANoneValue) {
            return "None";
        }

        return "unknown";
    }

    public void inAAdditionExpression(AAdditionExpression node){

        String t1 = inferExpressionType(node.getEx1());
        String t2 = inferExpressionType(node.getEx2());
        if(Objects.equals(t1, "None") || Objects.equals(t2, "None")){
            System.out.println("Numeric operations cannot have None type as an operand");
            return;
        }

        if(!Objects.equals(t1, t2)){
            System.out.println("Cannot add " + inferExpressionType(node.getEx1()) +" " +node.getEx1().toString()+ " with " +
                    inferExpressionType(node.getEx2()) + " " + node.getEx2().toString());
        }

    }

    public void inASubtractionExpression(ASubtractionExpression node){

        String t1 = inferExpressionType(node.getEx1());
        String t2 = inferExpressionType(node.getEx2());
        if(Objects.equals(t1, "None") || Objects.equals(t2, "None")){
            System.out.println("Numeric operations cannot have None type as an operand");
            return;
        }
        if(!Objects.equals(t1, t2)){
            System.out.println("Cannot subtract " + inferExpressionType(node.getEx1()) +" " +node.getEx1().toString() + " with " +
                    inferExpressionType(node.getEx2()) + " " + node.getEx2().toString());
        }
    }

    public void inAPowerExpression(APowerExpression node){

        String t1 = inferExpressionType(node.getEx1());
        String t2 = inferExpressionType(node.getEx2());
        if(Objects.equals(t1, "None") || Objects.equals(t2, "None")){
            System.out.println("Numeric operations cannot have None type as an operand");
            return;
        }
        if(!Objects.equals(t1, t2)){
            System.out.println("Cannot have power of " + inferExpressionType(node.getEx1())+ " " + node.getEx1().toString() + " with " +
                    inferExpressionType(node.getEx2()) + " " + node.getEx2().toString());
        }
    }

    public void inAMultiplicationExpression(AMultiplicationExpression node){

        String t1 = inferExpressionType(node.getEx1());
        String t2 = inferExpressionType(node.getEx2());
        if(Objects.equals(t1, "None") || Objects.equals(t2, "None")){
            System.out.println("Numeric operations cannot have None type as an operand");
            return;
        }
        if(!Objects.equals(t1, t2)){
            System.out.println("Cannot multiply " + inferExpressionType(node.getEx1())+ " " + node.getEx1().toString() + " with " +
                    inferExpressionType(node.getEx2()) + " " + node.getEx2().toString());
        }
    }

    public void inADivisionExpression(ADivisionExpression node){

        String t1 = inferExpressionType(node.getEx1());
        String t2 = inferExpressionType(node.getEx2());
        if(Objects.equals(t1, "None") || Objects.equals(t2, "None")){
            System.out.println("Numeric operations cannot have None type as an operand");
            return;
        }
        if(!Objects.equals(t1, t2)){
            System.out.println("Cannot divide " + inferExpressionType(node.getEx1()) +" " +node.getEx1().toString()+ " with " +
                    inferExpressionType(node.getEx2()) + " " + node.getEx2().toString());
        }
    }

    public void inAArrayExpression(AArrayExpression node){
        String arrayType = inferType(node.getValue1()); //check
        for(Object elem : node.getValue2()){
            String elemType = inferType((PValue) elem);
            if(!Objects.equals(arrayType,elemType)){
                System.out.println("Arrays cannot have elements of " + arrayType + " and " + elemType);
                break;
            }
        }
    }

    public void inAModuloExpression(AModuloExpression node){

        String t1 = inferExpressionType(node.getEx1());
        String t2 = inferExpressionType(node.getEx2());
        if(Objects.equals(t1, "None") || Objects.equals(t2, "None")){
            System.out.println("Numeric operations cannot have None type as an operand");
            return;
        }
        if(!Objects.equals(t1, t2)){
            System.out.println("Cannot use modulo operator between " + inferExpressionType(node.getEx1()) + " " + node.getEx1().toString() + " and " +
                    inferExpressionType(node.getEx2()) + " " + node.getEx2().toString());
        }
    }

    public void inAMaxExpression(AMaxExpression node) {
        validateArgs(node.getValue1(), node.getValue2(), "max");
    }

    public void inAMinExpression(AMinExpression node) {
        validateArgs(node.getValue1(), node.getValue2(), "min");
    }

    private void validateArgs(PValue firstValue, List<Object> additionalValues, String funcName) {
        String firstType = inferType(firstValue);
        if (!isNumericType(firstType)) {
            System.out.println(funcName + " function cannot have non-numeric argument: " + firstType + " " + firstValue.toString());
        }
        for (Object arg : additionalValues) {
            if (arg instanceof PValue) { // Check the object type before casting
                PValue value = (PValue) arg;
                String argType = inferType(value);
                if (!isNumericType(argType)) {
                    System.out.println(funcName + " function cannot have non-numeric argument: " + argType + " " + value.toString());
                    break;
                }
            } else {
                System.out.println(funcName + " function received unexpected argument type: " + arg.getClass().getName());
            }
        }
    }



    public void inALenExpression(ALenExpression node) {
        String argType = inferExpressionType(node.getExpression());
        if (!argType.endsWith("[]") && !argType.equals("string")) {
            System.out.println("len function cannot be applied to " + argType + " " + node.getExpression().toString());
        }
    }
    private boolean isNumericType(String type) {
        return type.equals("int") ;
    }



    //Todo: Validate that return statement's expression is infact valid


    @Override
    public void inAFuncCommand(AFuncCommand node) {
        AFunction func = (AFunction) node.getFunction();
        String funcName = func.getId().toString();
        AReturnStatement statement = (AReturnStatement) func.getStatement();
        PExpression expression = statement.getExpression();

        String returnType = ReturnType(expression, funcName);
        functions.get(funcName).returnType = returnType;
        CheckExpressionCorrectness(funcName , expression);

    }

    public String ReturnType(PExpression expression, String functionName){
        if (expression instanceof AValueExpression) {
            // Base case: Directly infer the type of the value
            //System.out.println(1);
            return inferType(((AValueExpression) expression).getValue());
        } else if (expression instanceof AIdExpression) {
            // For identifier expressions, look up the type in declared variables
            //System.out.println(2);
            String varName = ((AIdExpression) expression).getId().toString().trim();
            Hashtable<String ,String> variables = functions.get(functionName).args;
            //System.out.println(variables + "Var looked up " + varName + "with type " + variables.get(varName));
            return variables.get(varName);
        } else if (expression instanceof AIdArrayExpression) {
            // For array access expressions, infer the type of the array and return the element type
            //
            // System.out.println(3);
            String arrayName = ((AIdArrayExpression) expression).getId().toString().trim();
            return inferArrayElementType(arrayName);
        } else if (expression instanceof AFuncExpression) {
            // For function calls, infer the return type from the declared function
            //System.out.println(4);
            AFunctionCall func = (AFunctionCall) ((AFuncExpression) expression).getFunctionCall();
            String funcName = func.getId().toString();
            if (functions.containsKey("add")) {
               //
                // System.out.println("HELLO");
                return functions.get(funcName).returnType;
            } else {
                //System.out.println("Function '" + funcName + "' is called but not declared.");
                return "unknown";
            }
        } else if (expression instanceof ALenExpression) {
            //System.out.println(5);
            // The `len` function always returns an integer
            return "int";
        } else if (expression instanceof AAsciiExpression) {
            // The `ascii` function returns an integer
            //System.out.println(6);
            return "int";
        } else if (expression instanceof AMaxExpression ) {
            // For `max` or `min`, infer the type of the first value (all values should be of the same type)
            //System.out.println(7);
            AMaxExpression maxExp = (AMaxExpression) expression;
            return inferType(maxExp.getValue1());
        }else if(expression instanceof AMinExpression){
           // System.out.println(77);
            AMinExpression minExp = (AMinExpression) expression;
            return inferType(minExp.getValue1());
        }
        else if (expression instanceof AParExpExpression) {
            //System.out.println(8);
            // For parenthesized expressions, infer the type of the inner expression
            return inferExpressionType(((AParExpExpression) expression).getExpression());
        } else if (expression instanceof AArrayExpression) {
            // For array creation, infer the type of the first value (assuming homogeneity)
            //System.out.println(9);
            AArrayExpression arrayExp = (AArrayExpression) expression;
            return inferType(arrayExp.getValue1()) + "[]";
        } else if (expression instanceof AAdditionExpression) {
            //System.out.println(10);

            return ReturnType(((AAdditionExpression) expression).getEx2(), functionName);
        }
        //in other cases (Numeric expression its always a number
        //System.out.println(11);
        return "int";
    }


    public void CheckExpressionCorrectness(String funcName, PExpression expression) {
        // Get the type of the expression using the ReturnType method
        String t1, t2;

        if (expression instanceof AAdditionExpression) {
            AAdditionExpression node = (AAdditionExpression) expression;
            t1 = ReturnType(node.getEx1(), funcName);
            t2 = ReturnType(node.getEx2(), funcName);
            validateExpressionTypes(t1, t2, "add", node.getEx1().toString(), node.getEx2().toString());
        } else if (expression instanceof ASubtractionExpression) {
            ASubtractionExpression node = (ASubtractionExpression) expression;
            t1 = ReturnType(node.getEx1(), funcName);
            t2 = ReturnType(node.getEx2(), funcName);
            validateExpressionTypes(t1, t2, "subtract", node.getEx1().toString(), node.getEx2().toString());
        } else if (expression instanceof APowerExpression) {
            APowerExpression node = (APowerExpression) expression;
            t1 = ReturnType(node.getEx1(), funcName);
            t2 = ReturnType(node.getEx2(), funcName);
            validateExpressionTypes(t1, t2, "power", node.getEx1().toString(), node.getEx2().toString());
        } else if (expression instanceof AMultiplicationExpression) {
            AMultiplicationExpression node = (AMultiplicationExpression) expression;
            t1 = ReturnType(node.getEx1(), funcName);
            t2 = ReturnType(node.getEx2(), funcName);
            validateExpressionTypes(t1, t2, "multiply", node.getEx1().toString(), node.getEx2().toString());
        } else if (expression instanceof ADivisionExpression) {
            ADivisionExpression node = (ADivisionExpression) expression;
            t1 = ReturnType(node.getEx1(), funcName);
            t2 = ReturnType(node.getEx2(), funcName);
            validateExpressionTypes(t1, t2, "divide", node.getEx1().toString(), node.getEx2().toString());
        } else if (expression instanceof AModuloExpression) {
            AModuloExpression node = (AModuloExpression) expression;
            t1 = ReturnType(node.getEx1(), funcName);
            t2 = ReturnType(node.getEx2(), funcName);
            validateExpressionTypes(t1, t2, "modulo", node.getEx1().toString(), node.getEx2().toString());
        } else if (expression instanceof AArrayExpression) {
            AArrayExpression node = (AArrayExpression) expression;
            String arrayType = inferType(node.getValue1());
            for (Object elem : node.getValue2()) {
                String elemType = inferType((PValue) elem);
                if (!Objects.equals(arrayType, elemType)) {
                    System.out.println("Arrays cannot have elements of " + arrayType + " and " + elemType);
                    break;
                }
            }
        } else if (expression instanceof AMaxExpression) {
            AMaxExpression node = (AMaxExpression) expression;
            validateArgs(node.getValue1(), node.getValue2(), "max");
        } else if (expression instanceof AMinExpression) {
            AMinExpression node = (AMinExpression) expression;
            validateArgs(node.getValue1(), node.getValue2(), "min");
        } else {
           // System.out.println("Unknown expression type encountered in function: " + funcName);
        }
    }

    // Helper method to validate expression types
    private void validateExpressionTypes(String t1, String t2, String operation, String ex1Str, String ex2Str) {
        if (Objects.equals(t1, "None") || Objects.equals(t2, "None")) {
            System.out.println("Numeric operations cannot have None type as an operand");
            return;
        }
        if (!Objects.equals(t1, t2)) {
            System.out.println("Cannot " + operation + " " + t1 + " " + ex1Str + " with " + t2 + " " + ex2Str);
        }
    }





}
