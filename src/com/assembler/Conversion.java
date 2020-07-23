package com.assembler;


// in this class hexadecimal and binary conversion methods are held.
public class Conversion {

    // takes hexadecimal string as an argument and converts it to binary
    public static String hexadecimal(String s){

        String list = "0123456789ABCDEF";
        String binNum = "";

        for(int i=0; i<s.length(); i++){

            String value = "";
            String tmp = "";
            value += s.charAt(i);
            int index = list.indexOf(value);
            if(index == 0) {
                tmp += "0";
            } else {
                tmp = "" + binary(index);
            }
            while(tmp.length() < 4){
                tmp = 0 + tmp;
            }
            binNum = binNum + tmp;
        }
        return binNum;
    }


    // this method takes a decimal value and converts it to binary number.
    public static int binary ( int decimal){
        int binNum;
        int number = decimal;

        String s = "";
        String ns = "";
        if(decimal == 0) {
            return 0;
        }
        // continues until number is 1
        while (number != 1) {
            // divides the number by 2 and takes the remainder
            int num = number % 2;
            // adds the remainder to string s
            s = s + num;
            // updates the number by dividing it by2
            number /= 2;
        }

        // when the number is 1, it adds 1 to the string s
        if (number == 1) {
            s = s + number;
        }

        // inverts the string s
        for (int i = s.length() - 1; i >= 0; i--) {
            // adds the ith element of s to the string ns
            ns = ns + s.charAt(i);
        }
        // takes the string of binaries and converts is to binary as integer
        binNum = Integer.parseInt(ns);
        return binNum;
    }


}
