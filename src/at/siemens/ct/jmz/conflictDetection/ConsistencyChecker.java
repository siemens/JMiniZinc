package at.siemens.ct.jmz.conflictDetection;

import java.io.File;
import java.util.List;

import at.siemens.ct.jmz.IModelBuilder;
import at.siemens.ct.jmz.ModelBuilder;
import at.siemens.ct.jmz.elements.constraints.Constraint;
import at.siemens.ct.jmz.elements.include.IncludeItem;
import at.siemens.ct.jmz.elements.solving.SolvingStrategy;
import at.siemens.ct.jmz.executor.IExecutor;
import at.siemens.ct.jmz.executor.PipedMiniZincExecutor;
import at.siemens.ct.jmz.writer.IModelWriter;
import at.siemens.ct.jmz.writer.ModelWriter;

public class ConsistencyChecker {
	
	private static final String Unsatisfiable = "=====UNSATISFIABLE=====";
	
	IModelBuilder modelBuilder;
	IModelWriter modelWriter;
	IExecutor executor;
	
	public ConsistencyChecker(){
		modelBuilder = new ModelBuilder();
		
		modelWriter = new ModelWriter(modelBuilder);
		modelWriter.setSolvingStrategy(SolvingStrategy.SOLVE_SATISFY);
		
		executor = new PipedMiniZincExecutor("consistencyChecker", modelWriter);
	}
	
	public boolean isConsistent(File mznFile) throws Exception{		
		modelBuilder.reset();		
		
		IncludeItem includeItem = new IncludeItem(mznFile);
		modelWriter.addIncludeItem(includeItem);
		
		String res = callExecutor();
		
	    return (isSolverResultConsistent(res));		
	}
	
	public boolean isConsistent(List<Constraint> constraintsSet, File mznFile) throws Exception{
		modelBuilder.reset();		
		
		IncludeItem includeItem = new IncludeItem(mznFile);
		modelWriter.addIncludeItem(includeItem);		
		
		modelBuilder.add(constraintsSet);
		
		String res = callExecutor();		
	    return (isSolverResultConsistent(res));
	}
	
	public boolean isConsistent(List<Constraint> constraintsSet) throws Exception{		
		modelBuilder.reset();
		modelBuilder.add(constraintsSet);		
		
		String res = callExecutor();
	    return (isSolverResultConsistent(res));
	}
	
	private boolean isSolverResultConsistent(String result){
		boolean res = !result.contains(Unsatisfiable);
		//System.out.println("isSolverResultConsistent = " + res);
		return res;
	}
	
	private String callExecutor() throws Exception{
		executor.startProcess();
		executor.waitForSolution();
		
		if (executor.getLastExitCode() != IExecutor.EXIT_CODE_SUCCESS){
			throw new Exception("An error occured while running the solver. \n" + executor.getLastSolverErrors());
		}
		
	    String lastSolverOutput = executor.getLastSolverOutput();
	    
	    //System.out.println("Solver output = " + lastSolverOutput);
	    return lastSolverOutput;
	}
	
	/*private void createMznFileForConstraints(List<Constraint> constraints) throws IOException{
		IModelBuilder modelBuilder = new ModelBuilder();
		IModelWriter modelWriter = new ModelWriter(modelBuilder);
		modelBuilder.reset();
		modelWriter.addIncludeItem(includeItem);
		
		for(Constraint c : constraints){
			modelBuilder.add(c);
		}
				
		File file = new File(generatedFileName);		
		modelWriter.toFile(file);		
	}*/
}
