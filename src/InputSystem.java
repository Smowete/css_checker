//package com.han;
 
import java.io.*;

public class InputSystem{
    public static void main(String args[]){
        byte[] buffer=new byte[512];
        try {
            System.out.print("«Îƒ„ ‰»Î: ");
            System.in.read(buffer);//input your data, and ends with a "Return" key.
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String str=new String(buffer);
         
        System.out.println("what you input is : "+str);
    }
}