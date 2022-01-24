/**
 * 
 * @author Jin
 * Student Number: 250964902
 * This class is to save a record of the configuration of the board and the respective score
 */
public class Record {
	
	private String config;
	private int score;
	//Map<String, Integer> dictionary = new HashMap();
	//String[] records = new String 	
	public Record (String config, int score) {
		
		this.config = config;
		this.score = score;
		
	}
	
	public String getConfig() {
		return config;
		
	}
	
	public int getScore() {
		return (score);
	}
	
	
}
