/**
 * Creates a node using an element of the Record class
 * These nodes are used for separate chaining of the hash table
 * 
 * @author Jin
 * Student number: 250964902
 * @param <Record>
 */
@SuppressWarnings("hiding")
public class RecordNodes<Record> {
	
	    private RecordNodes<Record> next;
	    private Record pair;
	    
	    /**
	     * Creates a node storing the specified element.
	     *
	     * @param elem  the element to be stored within the new node
	     */
	    
	   
	    public RecordNodes () {
	    	next = null;
	    	pair = null;
	    }
	    
	    public RecordNodes (Record enterRecord)
	    {
	        next = null;
	        pair = enterRecord;
	    }
	    
	    /**
	     * Returns the node that follows this one.
	     *
	     * @return  the node that follows the current one
	     */
	    public RecordNodes<Record> getNext()
	    {
	        return next;
	    }
	    
	    /**
	     * Sets the node that follows this one.
	     *
	     * @param node  the node to be set to follow the current one
	     */
	    public void setNext (RecordNodes<Record> node)
	    {
	        next = node;
	    }
	    
	    
	    
	    /**
	     * Returns the element stored in this node.
	     *
	     * @return  the element stored in this node
	     */
	    public Record getValue()
	    {
	        return pair;
	    }
	    
	    /**
	     * Sets the element stored in this node.
	     *
	     * @param elem  the element to be stored in this node
	     */
	   
	    
	    /**
	     * public void setValue (String score)
	     
	    {
	        value = score;
	    }
	

	    public String getIndex () {
	    	return index;
	    }
	    
	    public void setIndex (String config) {
	    	index = config;
	    }
	
	
		*/
	
	
	
	
}
