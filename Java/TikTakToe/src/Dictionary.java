/**
 * 
 * @author Jin
 * Student number: 250964902
 * 
 * This class creates a dictionary of configurations and their scores in order for the computer to know which position is best for its marker. The array dictionary is filled with nodes that are of element Record class
 * 
 *
 */

public class Dictionary implements DictionaryADT{
	
	//static RecordNodes<Record>[] recordNode;
	//RecordNodes<Record>[] dict;
	@SuppressWarnings("unchecked")
	private RecordNodes<Record>[] dict = new RecordNodes[7151];
	private int numOfRecords = 0;
	private int collision = 0;
	
	
	public int insert(Record pair) throws DictionaryException{
		int indexValue = hashFunction(pair.getConfig());
		if (dict[indexValue] == null) {//No collision, the index space is NULL, therefore the new record pair is inserted and 0 is returned.
			dict[indexValue] = new RecordNodes<Record>(new Record(pair.getConfig(),(pair.getScore())));
			//RecordNodes head = dict[indexValue];
			numOfRecords += 1;
			return 0;
		}else if((dict[indexValue] != null) && ((dict[indexValue].getValue().getConfig()) != pair.getConfig())){ //Collision case
			RecordNodes<Record> temp = dict[indexValue];
			while (temp.getNext() != null) {
				temp=temp.getNext();	
				if (temp.getValue().getConfig().equals(pair.getConfig())) {
					//System.out.println(""+pair.getConfig());
					collision ++;
					throw new DictionaryException();
					//This pair is already in the hash table through separate chaining therefore was not added
				}
			}
			numOfRecords += 1;
			collision ++;
			temp.setNext(new RecordNodes<Record>(new Record(pair.getConfig(),(pair.getScore()))));
			return 1;
			//This record was inserted through separate chaining in the index
		}else if (dict[indexValue].getValue().getConfig().equals(pair.getConfig())){
			collision ++;
			throw new DictionaryException();
			//Same configuration 
		}
		return 1;
		//If the record pair was not inserted into the hash table (did not return 0), then there was a collision
		
		
	}
	
	/**
	 * removes the record with the configuration given in the parameter. If there is no record with that configuration, it throws a new DictionaryException
	 */
	public void remove(String config) throws DictionaryException{
		int indexValue = hashFunction(config);
		if (dict[indexValue] == null) {
			throw new DictionaryException();
		}else if (dict[indexValue].getValue().getConfig().equals(config)){ //The config matches the config of the first object in separate chaining(if there is one)
			if(dict[indexValue].getNext() != null) {
				dict[indexValue] = dict[indexValue].getNext();
			}else {
				dict[indexValue] = null;
			}
			numOfRecords -= 1;
		}else {
			RecordNodes<Record> temp = dict[indexValue];
			RecordNodes<Record> previous = dict[indexValue];
			while (temp.getNext() != null) {//Deletes the node with the respective config by iterating through the linkedlist
				temp = temp.getNext();
				if (temp.getValue().getConfig().equals(config)) {
					numOfRecords -= 1;
					previous.setNext(temp.getNext()); //The previous node skips the current node and points towards the next node, therefore deleting the node with the corresponding config 
					break;
				}
				previous = previous.getNext();
			}	
		}	
	}
	
	/**
	 * Gets the score of the configuration given in the parameter. Returns -1 if there are no records with the configuration given.
	 */
	public int get(String config) {
		int indexValue = hashFunction(config);
		if (dict[indexValue] == null) {
			return -1; //config not in the hash table
		}else if (dict[indexValue].getValue().getConfig().equals(config)){ //The config matches the config of the first object in separate chaining(if there is one)
			return (dict[indexValue].getValue().getScore());
		}else {
			RecordNodes<Record> temp = dict[indexValue];
			while (temp.getNext() != null) {//Deletes the node with the respective config by iterating through the linkedlist
				temp = temp.getNext();
				if (temp.getValue().getConfig().equals(config)) {
					return (temp.getValue().getScore());
				}
				
				
			}
			return -1; //Config not in the hash table
			
		}
	}
	
	/**
	 * returns number of records in the dictionary
	 */
	public int numElements() {
		return numOfRecords;
	}
	
	/**
	 * Creates a new dictionary of size given
	 * @param size
	 */
	@SuppressWarnings("unchecked")
	public Dictionary(int size) {
		dict = new RecordNodes[size];
	}
/**
	public static void main(String[] args) {
		System.out.println (hashFunction("OXXXOXOOX"));
	}
	**/

	/**Calculates an index number for the array dictionary using the configuration given.
	 * 
	 * @param config
	 * @return an index number in the dictionary array that the record should be stored in
	 */
	private static int hashFunction(String config) {
		int lengthOfWord = config.length();
		int valueX = 33;
		int indexValue = (int)(config.charAt(lengthOfWord-1));
		for (int i = lengthOfWord-2; i >= 0; i--) {
			indexValue = (indexValue*valueX + (int)config.charAt(i))%7151;
		}
		return indexValue;
	}
	
	

	
	
}
