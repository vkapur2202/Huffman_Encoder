package com.jetbrains;
import java.io.*;
import java.util.*;

/**
 * @author Vivek Kapur
 */
public class HuffmanCompressor {
    private static HashMap<Character, Integer> hashMap = new HashMap<>();
    private static HashMap<Character, String> encodingMap = new HashMap<>();

    /**
     * Method that creates a 'compressed' output file based on the input file provided
     * with the name of the output file name provided
     * @param inputFileName name of the input file
     * @param outputFileName name of the output file
     * @return String that tells you the output of the attempt.
     */
    public static String huffmanCoder(String inputFileName, String outputFileName) {
        try{
            encode(createTree(scan(inputFileName)), ""); //encode a tree that is built by scanning the input file.
        }
        catch(IOException e){ //if there is an IOException signal a problem with the input file name.
            return ("Input file error");
        }
        try{
            BufferedWriter output = new BufferedWriter(new FileWriter(outputFileName)); //create a writer to output
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(inputFileName))); //create a reader to scan the input
            int tempNumber;
            String code;
            int characterCount = 0;
            int binaryCount = 0;
            char c;
            while((tempNumber = bufferedReader.read()) != -1) { //while there is more to scan
                c = (char)tempNumber;
                code = encodingMap.get(c); //get the encoded string associated with the character
                output.write(code); //output that encoded string to the output file
                characterCount++; //signal that there has been one more character processed
                binaryCount += hashMap.get(c); //signal the amount of bits outputted
            }
            output.close();
            System.out.println("Computed space savings: " + (characterCount*8 - binaryCount) + " bits"); //compute space savings
            return("OK"); //no errors, operation was successful
        }
        catch(IOException e){ //if there is an IOException signal a problem with the output file name
            return("Output file error");
        }
    }

    /**Method to output special characters to the System.
     *
     * @param c character to check
     * @return A string representation of the character, using \ if it is a special character
     */
    public static String escapeSpecialCharacter(Character c) {
       switch(c) { //if the character is a special character, output a specified string instead of the character
           case ' ': //return 'space' as a word instead of ' '
               return "Space";
           case '\n': // return \n instead of creating a new line
               return "\\n";
           case '\r': // return \r instead of a carriage return
               return "\\r";
           case'\t': // return \t instead of a tab
               return "\\t";
           default: // if none of these cases apply, just output the character itself
               return ("" + c);
       }
    }

    /**
     *
     * @param inputFileName name of the input file to be scanned
     * @return PriorityQueue of huffman nodes
     * @throws IOException throws IOexception if there is an error with the input file
     * Priority Queue was used to keep a sorted list of huffman nodes at all times from lowest to highest frequency
     */
    public static PriorityQueue<HuffmanNode> scan(String inputFileName) throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(inputFileName)));
        int tempNumber;
        Character c;
        while((tempNumber = bufferedReader.read()) != -1){ //runs through the input file
            c = (char)tempNumber;
            hashMap.put(c, hashMap.getOrDefault(c, 0) + 1); // puts characters into hash map and assigns frequency
        }
        Character[] keyArray = hashMap.keySet().toArray(new Character[0]); //creates a character array
        PriorityQueue<HuffmanNode> minHeap = new PriorityQueue<>(hashMap.size(), Comparator.comparing(HuffmanNode::getFrequency));
        for(int i = 0; i < hashMap.size(); i++) { //adds each character - frequency pair to the priority queue
            c = keyArray[i];
            minHeap.add(new HuffmanNode(c, hashMap.get(c), null, null));
        }
        return minHeap; // returns the priority queue
    }

    /**Merges two nodes
     *
     * @param firstNode first node to be merged
     * @param secondNode second node to be merged
     * @return the node resulting from the merge with the combined frequency
     */
    public static HuffmanNode merge(HuffmanNode firstNode, HuffmanNode secondNode){
        return new HuffmanNode(null, firstNode.getFrequency() + secondNode.getFrequency(), firstNode, secondNode);
    }

    /**Creates a Huffman tree out of the nodes in the queue
     *
     * @param minHeap input the priority queue of huffman nodes to create the tree
     * @return the remaining root node of the entire tree
     */
    public static HuffmanNode createTree(PriorityQueue<HuffmanNode> minHeap) {
        while(minHeap.size() > 1) { //while there is more than one node
            HuffmanNode node1 = minHeap.peek(); // store the first node
            minHeap.poll(); //delete the first node
            minHeap.add(merge(node1, minHeap.peek())); //add in the merged node
            minHeap.poll(); // delete the new first node (which is the second node)
        }
        return minHeap.peek(); //return the remaining node in the queue as this is the parent node.
    }

    /**Method that assigns binary codes to characters
     *
     * @param rootNode the node to start at, the top node of the tree
     * @param string input the binary code created so far
     */
    public static void encode(HuffmanNode rootNode, String string) {
        if (rootNode.getLeft() == null) { //if the node is not a parent node
            Character c = rootNode.getInChar();
            encodingMap.put(c, string); //assign the character a binary encoding in a hash map
            System.out.println(escapeSpecialCharacter(c) + " : " + hashMap.get(c) + " : " + string); //print the character, frequency, and encoding
            hashMap.replace(c, string.length()); //store the length of the string along with the character in the other hashmap
        }
        else {
            encode(rootNode.getLeft(), string + "0"); //recurse with a 0 going left, and a 1 going right
            encode(rootNode.getRight(), string + "1");
        }
    }

    public static void main(String[] args){
        System.out.println(huffmanCoder(args[0], args[1]));
    }
}





