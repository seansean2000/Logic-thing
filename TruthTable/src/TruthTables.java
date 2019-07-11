import java.util.ArrayList;

public class TruthTables {
	private boolean[][] defaultTable=new boolean[2][4];//boolean[column][row] order
	private boolean[][] mainDefaultTable;
	private boolean[][] mainTable;
	private int numOfStatements;
	private ArrayList<String> names;
	
	public TruthTables(int num, ArrayList<String> names)
	{
		numOfStatements=num;
		this.names=names;
		//create default truth table for 2 statements
		if(num>=2)
		{
			for(int i=0; i<4; i++)
			{
				if(i<2)
					defaultTable[0][i]=true;
				else
					defaultTable[0][i]=false;
			}
			boolean val=true;
			for(int i=0; i<4; i++)
			{
				defaultTable[1][i]=val;
				val=!val;
			}
			
			//mainDefaultTable=new boolean[numOfStatements][(int)Math.pow(2, numOfStatements)];
			mainDefaultTable=fillMainDefault(num-1);
		}
		
		
		
		
	}
	
	//cur is how many times repeated,rep is how many times to repeat
	public boolean[][] fillMainDefault(int rep)
	{
		boolean[][] def=null;
		if(rep==1)
			def=defaultTable;
		
		else if(rep>1)
		{
			def=new boolean[fillMainDefault(rep-1).length+1][fillMainDefault(rep-1)[0].length*2];
			
			//fill the most left column with half T and half F
			for(int r=0; r<def[0].length/2; r++)
			{
				def[0][r]=true;
			}
			for(int r=def[0].length/2; r<def[0].length; r++)
			{
				def[0][r]=false;
			}
			
			//fill the rest of the table repeating the returned table twice
			for(int c=1; c<def.length; c++)
			{
				for(int r=0; r<def[c].length/2; r++)
				{
					def[c][r]=fillMainDefault(rep-1)[c-1][r];
				}
				for(int r=def[c].length/2; r<def[c].length; r++)
				{
					def[c][r]=fillMainDefault(rep-1)[c-1][r-def[c].length/2];
				}
			}
		}
		
		
		return def;
		
	}
	
	
	
	
	public boolean calculateTruth(int statement1, int statement2, int col, char operator)
	{
		if(operator=='.')
		{
			return mainDefaultTable[col][statement1] && mainDefaultTable[col][statement2];
		}
		
		if(operator=='V' || operator=='v')
		{
			return mainDefaultTable[col][statement1] || mainDefaultTable[col][statement2];
		}
		
		if(operator=='>')
		{
			if(mainDefaultTable[col][statement1] && !mainDefaultTable[col][statement2])
				return false;
			else
				return true;
		}
		
		if(operator=='=')
		{
			return mainDefaultTable[col][statement1]==mainDefaultTable[col][statement2];
		}
		
		else
			return;
	}
	
	public boolean negation(int statement, int col)
	{
		return !mainDefaultTable[col][statement];
	}
	
	
	
	public void print()
	{
		
		for(int r=0; r<mainDefaultTable[0].length; r++)
		{
			System.out.print(r+1+".\t ");
			for(int c=0; c<mainDefaultTable.length; c++)
			{
				if(mainDefaultTable[c][r])
					System.out.print("T ");
				
				else
					System.out.print("F ");
			}
			
			System.out.println();
		}
	}
	
	
	
}
