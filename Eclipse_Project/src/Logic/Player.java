package Logic;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Player {

	private int id;
	@SuppressWarnings("rawtypes")
	private static ArrayList idList = new ArrayList<int[]>();
	private String name;
	
	public Player(){
		this.name = "Ahmet";
		id = generateID();
	}
	
	public void setName(){
		this.name = "Ahmet";
	}
	
	public String getName(){ return name;}
	
	public int getID(){return id;}
	
	
	
	@Override
	public String toString() {
		return "Player [Name= " + getName() + ", ID=" + getID() + "]";
	}

	@SuppressWarnings("unchecked")
	private static int generateID(){
		
		
		Random rand = new Random();
		int tobeID = rand.nextInt(1000);
		
		while(idList.contains(tobeID)){
			tobeID = rand.nextInt(1000);
		}
		idList.add(tobeID);
		Collections.sort(idList);
		
		return tobeID;
	}
	
	public void setID(int id){
		this.id = id;
	}
	
	public static void printIdList(){
		for(int i=0; i<idList.size(); i++){
			System.out.println(idList.get(i));
		}
	}
}
