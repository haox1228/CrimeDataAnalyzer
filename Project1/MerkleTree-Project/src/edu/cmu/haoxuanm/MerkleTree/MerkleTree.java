package edu.cmu.haoxuanm.MerkleTree;

import edu.cmu.haoxuanm.linkedlist.SinglyLinkedList;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Project1 Task3
 *
 * @author Nick Ma, haoxuanm
 * This is the class to generate a merkle tree and get the hash value of the merkle root.
 * <p>
 * The merkle root for smallFile.txt is
 * 756DE255D5716B6D6BD7BC885D61C43FAFA2524A10B8339B30145D008BBFECDC
 * The merkle root for CrimeLatLonXY.csv is
 * A5A74A770E0C3922362202DAD62A97655F8652064CCCBE7D3EA2B588C7E07B58
 * The merkle root for CrimeLatLonXY1990_Size2.csv is
 * DDD49991D04273A7300EF24CFAD21E2706C145001483D161D53937D90F76C001
 * The merkle root for CrimeLatLonXY1990_Size3.csv is
 * 313A2AD830ED85B5203C8C2A9895ADFA521CD4ABB74B83C25DA2C6A47AE08818
 * CrimeLatLonXY.csv is the answer we want!
 */
public class MerkleTree {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Scanner prompt = new Scanner(System.in);
        System.out.println("Please type the file you want to check: ");
        //ask user for file, wrong file names will be catched at the read method
        String s = prompt.nextLine();
        //the target root
        String target = "A5A74A770E0C3922362202DAD62A97655F8652064CCCBE7D3EA2B588C7E07B58";
        //get a list of lines
        ArrayList<String> lines = read(s);
        //populate the first two lists and put it in a list of list
        SinglyLinkedList bigList = createFirstAndSecondList(lines);
        //build the tree until reach to the root
        while (((SinglyLinkedList) bigList.getObjectAt(bigList.countNodes() - 1)).countNodes() != 1) {
            bigList.addAtEndNode(concatHash((SinglyLinkedList) bigList.getObjectAt(bigList.countNodes() - 1)));
        }
        //get the root
        String root = ((SinglyLinkedList) bigList.getObjectAt(bigList.countNodes() - 1)).getObjectAt(0).toString();
        System.out.println("The merkle root for " + s + " is \n" + root);
        //check if the root is the target
        if (root.equals(target)) {
            System.out.println(s + " is the answer we want!");
        }
    }

    /**
     * This method can create the first linklist which contains the lines of file in string, and the second
     * linklist which contains the hash value for each nodes in the first linked-list.
     *
     * @param input An arraylist of string of file lines
     * @return A list of list contains first and second list
     * @throws NoSuchAlgorithmException
     */
    public static SinglyLinkedList createFirstAndSecondList(ArrayList<String> input) throws NoSuchAlgorithmException {
        //check even
        if (input.size() % 2 != 0) {
            input.add(input.get(input.size() - 1));
        }
        //generate, populate and add
        SinglyLinkedList first = new SinglyLinkedList();
        SinglyLinkedList second = new SinglyLinkedList();
        for (String s : input) {
            first.addAtEndNode(s);
            second.addAtEndNode(h(s));
        }
        SinglyLinkedList list = new SinglyLinkedList();
        list.addAtEndNode(first);
        list.addAtEndNode(second);
        return list;
    }

    /**
     * This method will concat and calculate hash two by two
     *
     * @param list linkedlist of hash value
     * @return the concated linklist, which will be half of length than the input one
     * @throws NoSuchAlgorithmException
     */
    public static SinglyLinkedList concatHash(SinglyLinkedList list) throws NoSuchAlgorithmException {
        //check even
        if (list.countNodes() % 2 != 0) {
            list.addAtEndNode(list.getObjectAt(list.countNodes() - 1));
        }
        //concat and build
        SinglyLinkedList res = new SinglyLinkedList();
        String cat;
        int index = 0;
        while (index < list.countNodes()) {
            cat = h(((String) list.getObjectAt(index)) + ((String) list.getObjectAt(index + 1)));
            res.addAtEndNode(cat);
            index += 2;
        }
        return res;
    }

    /**
     * Read file
     *
     * @param file the file name
     * @return an arraylist of each line as a string
     */
    public static ArrayList<String> read(String file) {
        String prefix = "/Users/mahaoxuan/Documents/Spring 2023/Data Structure/Project1/MerkleTree-Project/src/edu/cmu/haoxuanm/MerkleTree/";
        ArrayList<String> res = new ArrayList<>();
        Scanner sc = null;
        try {
            sc = new Scanner(new File(prefix + file));
            while (sc.hasNextLine()) {
                String thisLine = sc.nextLine();
                res.add(thisLine);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No such file");
        }
        return res;
    }

    /**
     * compute hash, from text
     *
     * @param text input
     * @return hash
     * @throws NoSuchAlgorithmException
     */
    public static String h(String text) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash =
                digest.digest(text.getBytes(StandardCharsets.UTF_8));
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i <= 31; i++) {
            byte b = hash[i];
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}
