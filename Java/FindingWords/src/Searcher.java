import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * searcher class
 * @variable private hashtable
 * @variable input file
 */
public class Searcher {
    private HashTable table;
    private String inputFile;

    /**
     * Constructor for the class
     * @param inputTable
     * @param inputString
     */
    public Searcher(HashTable inputTable, String inputString) {
        inputFile = inputString;
        table = inputTable;

    }


    public void findAllWords() {
        /**
         * goes through the file and compiles the output
         */
        try {
            BufferedReader in = new BufferedReader(new FileReader(inputFile));
            String line = in.readLine();
            while (line != null) {
                String[] wordList = line.split(" ");
                for (int i = 0; i < wordList.length; i++) {
                    findWord(wordList[i]);
                }
                line = in.readLine();
            }
        }
        catch(Exception e){

        }


    }

    /**
     * works with the binary tree to find the words
     * @param searchWord
     */
    public void findWord(String searchWord) {
        BinSearchTree[] workingtable = table.getTable();
        int index = table.computeIndex(searchWord);
        BinSearchTree wordTree = workingtable[index];
        FileNode currentNode = wordTree.getWord(searchWord).getFiles().getHead();
        if (wordTree.getWord(searchWord) == null){
            CustomPrinter.wordNotFound(searchWord,inputFile);
        }
        else{
            CustomPrinter.wordFound(searchWord,inputFile);
            while(currentNode != null) {
                ArrayList<Integer> lq = currentNode.getPositions();
                CustomPrinter.printPositionsPerFileFound(currentNode.getFilename(), lq, inputFile);
                currentNode = currentNode.getNext();

            }
        }


    }
}