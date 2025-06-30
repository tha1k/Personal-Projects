import minipython.analysis.*;
import minipython.node.*;

import javax.management.ValueExp;
import java.util.*;

public class myvisitor extends DepthFirstAdapter {
	private Hashtable symtable;
	private HashMap<String, String> declaredVariables = new HashMap<>();
	private HashSet<String> functionVariables = new HashSet<>();
	private HashMap<String , FunctionData> functions = new HashMap<>();
	private HashSet<String> declaredFunctions = new HashSet<>();

	myvisitor(Hashtable symtable) {
		this.symtable = symtable;
	}
	public HashMap<String, String> getDeclaredVariables(){
		return declaredVariables;
	}
	public HashSet<String> getDeclaredFunctions(){
		return declaredFunctions;
	}
	public HashMap<String,FunctionData> getFunctions(){
		return functions;
	}

	// Get variables and their types from assignments
	public void inAAssignStatement(AAssignStatement node) {
		String varName = node.getId().toString().trim(); // Trim variable name
		String type = "unknown";
		if(node.getExpression() instanceof ValueExp){
			type = inferType(((AValueExpression) node.getExpression()).getValue());
		}else{
			type = inferExpressionType(node.getExpression());
		}

		declaredVariables.put(varName, type);
		//System.out.println("Added to declaredVariables: " + varName + " with type " + varType);

	}

	public void inAFuncCommand(AFuncCommand node) {
		AFunction func = (AFunction) node.getFunction();
		FunctionData funcData = new FunctionData();

		declaredFunctions.add(func.getId().toString());
		LinkedList<PArgument> funcArgs = func.getArgument();
		if(funcArgs.size() == 0){
			functions.put(func.getId().toString() , new FunctionData());
		}

		for (PArgument arg : funcArgs) {
			if (arg instanceof AArgument) {
				AArgument aArg = (AArgument) arg;
				// Process id_assign_value
				LinkedList<PIdAssignValue> idAssignValues = aArg.getIdAssignValue();
				for (PIdAssignValue idAssign : idAssignValues) {
					AIdAssignValue assignValue = (AIdAssignValue) idAssign;
					String argName = assignValue.getId().toString().trim();
					functionVariables.add(argName);
					String type = "unknown";
					if(assignValue.getAssignValue().size()==0) {

						funcData.requiredArgs++;
					}else{

						AAssignValue value = (AAssignValue) assignValue.getAssignValue().getFirst();
						PValue val = value.getValue();
						type = inferType(val);
					}

					funcData.args.put(argName, type);
					funcData.totalArgs++;
					//System.out.println("Added to functionVariables: " + argName);
				}

				// Process comma_id_assign_value
				LinkedList<PCommaIdAssignValue> commaIdAssignValues = aArg.getCommaIdAssignValue();

				for (PCommaIdAssignValue commaIdAssign : commaIdAssignValues) {
					ACommaIdAssignValue assignValue = (ACommaIdAssignValue) commaIdAssign;
					AIdAssignValue argument = (AIdAssignValue) assignValue.getIdAssignValue().get(0);
					//argument without default value
					if (argument.getAssignValue().size() == 0){
						String argName = argument.getId().toString().trim();
						functionVariables.add(argName);
						funcData.requiredArgs++;
						funcData.args.put(argName, "unknown");
					}else{
						String argName = argument.getId().toString().trim();
						argName = argName.split(" ")[0];
						functionVariables.add(argName);
						AAssignValue argValue = (AAssignValue) argument.getAssignValue().get(0);
						String type = inferType(argValue.getValue());
						funcData.args.put(argName, type);

					}

					funcData.totalArgs++;
				}
			}

			//funcData.returnType = "string";
			functions.put(func.getId().toString() , funcData);
		}


	}

	@Override
	public void outAFuncCommand(AFuncCommand node) {
		//function args are declared only within the scope of the function

		functionVariables.clear();
	}

	public void inAIdExpression(AIdExpression node) {
		String varName = node.getId().toString().trim(); // Trim variable name
		//System.out.println("Checking variable: " + varName);

		if (!declaredVariables.containsKey(varName) && !functionVariables.contains(varName)) {
			System.out.println("Variable '" + varName + "' is used without being declared.");
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
				//System.out.println("Function '" + funcName + "' is called but not declared.");
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
		} else if (expression instanceof AParExpExpression) {
			// For parenthesized expressions, infer the type of the inner expression

			return inferExpressionType(((AParExpExpression) expression).getExpression());
		} else if (expression instanceof AArrayExpression) {
			// For array creation, infer the type of the first value (assuming homogeneity)
			AArrayExpression arrayExp = (AArrayExpression) expression;
			return inferType(arrayExp.getValue1()) + "[]";
		}else if (expression instanceof AAdditionExpression) {
			return inferExpressionType(((AAdditionExpression) expression).getEx1());
		}

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


	//When a function is called we should infer its args and in visitor 2 find any Type mismatch and the functions return type

	public void inAFuncCallStatement(AFuncCallStatement node){
		AFunctionCall funcCall = (AFunctionCall) node.getFunctionCall();
		String name = funcCall.getId().toString();

		if(functions.getOrDefault(name , null) != null){
		LinkedList args = funcCall.getArglist();
		AArglist arglist = (AArglist) args.getFirst();
		String arg1Type = inferExpressionType(arglist.getExpression());
		List<String> ArgTypes = new ArrayList<>();
		ArgTypes.add(arg1Type);
		for(Object arg : arglist.getCExpression()){
			ACExpression actualArg = (ACExpression) arg;
			ArgTypes.add(inferExpressionType(actualArg.getExpression()));
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
	}
	public void inAFuncExpression(AFuncExpression node){
		AFunctionCall funcCall = (AFunctionCall) node.getFunctionCall();
		String name = funcCall.getId().toString();
		if(functions.getOrDefault(name , null) != null) {
			LinkedList args = funcCall.getArglist();
			AArglist arglist = (AArglist) args.getFirst();
			String arg1Type = inferExpressionType(arglist.getExpression());
			List<String> ArgTypes = new ArrayList<>();
			ArgTypes.add(arg1Type);
			for (Object arg : arglist.getCExpression()) {
				ACExpression actualArg = (ACExpression) arg;
				ArgTypes.add(inferExpressionType(actualArg.getExpression()));
			}

			Hashtable<String, String> typedVars = functions.get(name).args;
			Iterator<String> argTypeIterator = ArgTypes.iterator();

// Iterate over the keys of typedVars and update the values with ArgTypes
			for (String var : typedVars.keySet()) {
				if (argTypeIterator.hasNext()) {
					String inferredType = argTypeIterator.next();
					typedVars.put(var, inferredType); // Update the type of the variable
					//System.out.println("Updated var " + var + " with type " + inferredType);
				} else {
					//System.out.println("Mismatch: More variables in typedVars than ArgTypes");
				}
			}
		}

	}

}
