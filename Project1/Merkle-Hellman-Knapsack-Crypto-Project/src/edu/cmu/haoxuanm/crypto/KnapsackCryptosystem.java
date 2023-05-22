package edu.cmu.haoxuanm.crypto;
import edu.cmu.haoxuanm.linkedlist.SinglyLinkedList;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Project 1 Task 2
 * @author Nick Ma, haoxuanm
 * This is the class that used to encrypt and decrypt in merkl hellman way.
 */

public class KnapsackCryptosystem {
    public static void main(String[] args) {
        KnapsackCryptosystem kc = new KnapsackCryptosystem();
        //generate w
        SinglyLinkedList w = kc.generateW();
        //ask for text
        Scanner sc = new Scanner(System.in);
        String input = "";
        System.out.println("Enter a string and I will encrypt it as single large integer.");
        input = sc.nextLine();
        if(input.length() > 80){
            System.out.println("please type a string shorter than 80");
            return;
        }
        System.out.println("Clear text:\n"+input+"\nNumber of clear text bytes = "+input.length());
        //generate q r b
        BigInteger q = kc.generateQ(kc.sumW(w));
        BigInteger r = kc.generateR(q);
        SinglyLinkedList b = kc.generateB(w,q,r);
        //encrypt
        BigInteger encMes = kc.encrypt(input,b);
        System.out.println(input+" is encrypted as \n"+encMes);
        //decrypt
        System.out.println("result of decryption:"+ kc.decrypt(r,q,w,encMes,input));

    }

    /**
     * Generate W
     * @return a list contains values of W
     */
    public SinglyLinkedList generateW(){
        SinglyLinkedList w = new SinglyLinkedList();
        BigInteger seven = new BigInteger("7");
        for(int i = 1; i<= 640; i++){
            w.addAtEndNode(seven.pow(i));
        }
        return w;
    }

    /**
     * Generate sum of w
     * @param w the list of w
     * @return the sum of the list w
     */
    public BigInteger sumW(SinglyLinkedList w){
        BigInteger sumW = BigInteger.ZERO;
        w.reset();
        while (w.hasNext()){
            sumW = sumW.add((BigInteger) w.next());
        }
        return sumW;
    }

    /**
     * Generate q, which just add 2 at sum of w
     * @param sumW sum of w
     * @return q
     */
    public BigInteger generateQ(BigInteger sumW){
        return sumW.add(BigInteger.TWO);
    }

    /**
     * Generate r, add 1 at q is r, because for any n, n and n+1 are coprime
     * @param q q
     * @return r
     */
    public BigInteger generateR(BigInteger q){
        return q.add(BigInteger.ONE);
    }

    /**
     * Generate B
     * @param w w
     * @param q q
     * @param r w
     * @return b
     */
    public SinglyLinkedList generateB(SinglyLinkedList w, BigInteger q, BigInteger r){
        w.reset();
        SinglyLinkedList b = new SinglyLinkedList();
        while(w.hasNext()){
            BigInteger thisBig = new BigInteger(w.next().toString());
            b.addAtEndNode(thisBig.multiply(r).mod(q));
        }
        return b;
    }

    /**
     * Encrypt the given string
     * @param str given input
     * @param b b
     * @return the encrypted string
     *
     */
    public BigInteger encrypt(String str, SinglyLinkedList b){
        //convert string to binary format each letter has 8bit
        String bi = stringToBi(str);
        BigInteger res = BigInteger.ZERO;
        //use the formula to encrypt
        for(int i =0; i<bi.length();i++){
            res = res.add(new BigInteger(b.getObjectAt(i).toString()).multiply(new BigInteger(String.valueOf(bi.charAt(i)))));
        }
        return res;
    }

    /**
     * Convert String into binary representation in 8-bits
     * @param str input
     * @return input String in binary
     */
    private String stringToBi(String str){
        //convert to char array
        char[] chars = str.toCharArray();
        //use stringbuilder collect output
        StringBuilder sb = new StringBuilder();
        //loop all the char, each char will be converted and added to the result
        for(char c: chars){
            //code revised from: https://mkyong.com/java/java-convert-string-to-binary/
            sb.append(String.format("%8s", Integer.toBinaryString(c))
                    .replaceAll(" ", "0"));
        }
        return sb.toString();
    }

    /**
     * Decrypt the encrypted string back
     * @param r r
     * @param q q
     * @param w w
     * @param input input in binary
     * @param raw original input
     * @return string in original
     */
    public String decrypt(BigInteger r, BigInteger q, SinglyLinkedList w, BigInteger input, String raw){
        //find the required in the formula
        BigInteger rPie = r.modInverse(q);
        BigInteger cPie = input.multiply(rPie).mod(q);
        //inverse loop the linkedlist
        int index = w.countNodes()-1;
        //use a stringbuilder to collect results
        StringBuilder sb = new StringBuilder();
        while(index >=0){
            BigInteger thisBig = new BigInteger(w.getObjectAt(index).toString());
            //compare with w with c', if w is smaller than c', add a 1 at this index
            if(thisBig.compareTo(cPie) <= 0){
                cPie = cPie.subtract(thisBig);
                sb.append(1);
            //else add a 0
            }else{
                sb.append(0);
            }
            index--;
        }
        //reverse the string back, and just copy the substring which have actual meaning into it
        //since each letter is 8 bit, so the answer in binary format should have length*8 are meaningful
        String ansBi = String.copyValueOf(sb.reverse().toString().toCharArray(),0,raw.length()*8);
        //return the converted back string
        return biToString(ansBi);
    }

    /**
     * change binary back to string
     * @param ansBi the binary string
     * @return the string
     */
    private String biToString(String ansBi){
        StringBuilder sb = new StringBuilder();
        int index = 0;
        //the index should increase with 8 times for each loop, since eight bit represents one char
        while(index < ansBi.length()/8){
            //Integer.parseInt with radix of two can convert binary back to number, and cast it back to char
            //and append it
            sb.append((char) Integer.parseInt(ansBi.substring(index*8,(index+1)*8),2));
            index++;
        }
        return sb.toString();
    }

}
