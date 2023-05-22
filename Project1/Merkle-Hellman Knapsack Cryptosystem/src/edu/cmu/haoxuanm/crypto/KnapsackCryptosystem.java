package edu.cmu.haoxuanm.crypto;
import edu.colorado.nodes.ObjectNode;
import edu.cmu.haoxuanm.linkedlist.SinglyLinkedList;

import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;


public class KnapsackCryptosystem {
    public static void main(String[] args) {
        KnapsackCryptosystem kc = new KnapsackCryptosystem();
        SinglyLinkedList w = kc.generateW();
        System.out.println(w.countNodes());
        Scanner sc = new Scanner(System.in);
        String input = "";
        System.out.println("Enter a string and I will encrypt it as single large integer.");
        input = sc.nextLine();
        if(input.length() > 80){
            System.out.println("please type a string shorter than 80");
            return;
        }
        System.out.println("Clear text:\n"+input+"\nNumber of clear text bytes = "+input.length());
        BigInteger q = kc.generateQ(kc.sumW(w));
        //System.out.println(q);
        BigInteger r = kc.generateR(q);
        SinglyLinkedList b = kc.generateB(w,q,r);
        //System.out.println(b);
        BigInteger encMes = kc.encrypt(input,b);
        System.out.println(input+" is encrypted as \n"+encMes);
        System.out.println("result of decryption: "+ kc.decrypt(r,q,w,encMes,input));

    }
    public SinglyLinkedList generateW(){
        SinglyLinkedList w = new SinglyLinkedList();
        BigInteger seven = new BigInteger("7");
        for(int i = 1; i<= 640; i++){
            w.addAtEndNode(seven.pow(i));
        }
        return w;
    }
    public BigInteger sumW(SinglyLinkedList w){
        BigInteger sumW = BigInteger.ZERO;
        w.reset();
        while (w.hasNext()){
            sumW = sumW.add((BigInteger) w.next());
        }
        return sumW;
    }
    public BigInteger generateQ(BigInteger sumW){
        return sumW.add(BigInteger.TWO);
    }
    public BigInteger generateR(BigInteger q){
        return q.add(BigInteger.ONE);
    }
    public SinglyLinkedList generateB(SinglyLinkedList w, BigInteger q, BigInteger r){
        w.reset();
        SinglyLinkedList b = new SinglyLinkedList();
        while(w.hasNext()){
            BigInteger thisBig = new BigInteger(w.next().toString());
            b.addAtEndNode(thisBig.multiply(r).mod(q));
        }
        return b;
    }
    public BigInteger encrypt(String str, SinglyLinkedList b){
        String bi = stringToBi(str);
        System.out.println("bi len"+ bi.length());
        BigInteger res = BigInteger.ZERO;
        for(int i =0; i<bi.length();i++){
            BigInteger thisBig = new BigInteger(b.getObjectAt(i).toString());
            res = res.add(thisBig.multiply(new BigInteger(String.valueOf(bi.charAt(i)))));
        }
        return res;

    }
    //code revised from: https://mkyong.com/java/java-convert-string-to-binary/
    private String stringToBi(String str){
        char[] chars = str.toCharArray();
        System.out.println("char len"+ chars.length);
        StringBuilder sb = new StringBuilder();
        for(char c: chars){
            sb.append(String.format("%8s", Integer.toBinaryString(c))   // char -> int, auto-cast
                    .replaceAll(" ", "0"));
        }
        System.out.println("------: "+sb.toString());
        return sb.toString();
    }
    public String decrypt(BigInteger r, BigInteger q, SinglyLinkedList w, BigInteger input, String raw){
        BigInteger rPie = r.modInverse(q);
        BigInteger cPie = input.multiply(rPie).mod(q);
        System.out.println("--------------------------");
        System.out.println(cPie);
        int index = w.countNodes()-1;
        StringBuilder sb = new StringBuilder();
        while(index >=0){
            BigInteger thisBig = new BigInteger(w.getObjectAt(index).toString());
            if(thisBig.compareTo(cPie) <= 0){
                cPie = cPie.subtract(thisBig);
                sb.append(1);
            }else{
                sb.append(0);
            }
            index--;
        }

        String ansBi = String.copyValueOf(sb.reverse().toString().toCharArray(),0,raw.length()*8);

        return biToString(ansBi);
    }
    private String biToString(String ansBi){
        StringBuilder sb = new StringBuilder();
        int index = 0;
        while(index < ansBi.length()/8){
            sb.append((char) Integer.parseInt(ansBi.substring(index*8,(index+1)*8),2));
            index++;
        }
        return sb.toString();
    }

}
