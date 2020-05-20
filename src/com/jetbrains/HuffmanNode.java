package com.jetbrains;
/**
 * @author Vivek Kapur
 */
public class HuffmanNode extends HuffmanCompressor{

    private Character inChar;
    private Integer frequency;
    private HuffmanNode left;
    private HuffmanNode right;

    /**Huffman Node Constructor
     *
     * @param inChar character represented
     * @param frequency frequency of character
     * @param left left child
     * @param right right child
     */
    public HuffmanNode(Character inChar, int frequency, HuffmanNode left, HuffmanNode right){
        this.inChar = inChar;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    // getter and setter methods.
    public Character getInChar() {
        return inChar;
    }

    public void setInChar(Character inChar) {
        this.inChar = inChar;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public HuffmanNode getLeft() {
        return left;
    }

    public void setLeft(HuffmanNode left) {
        this.left = left;
    }

    public HuffmanNode getRight() {
        return right;
    }

    public void setRight(HuffmanNode right) {
        this.right = right;
    }

}
