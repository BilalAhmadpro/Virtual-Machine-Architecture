/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os_project;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author Bilal Ahmad
 */
public class test {
    

public static void main(String[] args)
{
try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Khaleel Ahmad\\Documents\\5th-semester books\\OperatingSystem\\src\\operatingsystem\\flags.txt"))) {
    //System.out.println("hello123");
    String line;
    StringBuilder[] arr=new StringBuilder[50];
    int index=-1;
    while ((line = br.readLine()) != null) {
       
         StringBuilder hex = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            String theString=Integer.toHexString(line.charAt(i));
            if(theString.length()>2)
            {
              hex.append(theString.substring(theString.length() - 2) );
            }
            else{
                hex.append(theString);
            }
             hex.append(",");
            }
             
        
        arr[++index]=hex;
        
       }
    for(int i=0;i<arr.length;i++)
    {
        if(arr[i]!=null)
        {
            System.out.println(arr[i]);
            System.out.println("------------------------");
        }
    }
    
}
    catch(Exception e)
            {
                System.out.println("not found yaar do something");
            }

}
}
