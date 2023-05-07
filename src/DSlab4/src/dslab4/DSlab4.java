/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dslab4;

/**
 *
 * @author Bilal Ahmad
 */
public class DSlab4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      LinkedList<Integer> list=new LinkedList<>();
      list.InsertInOrder(0);
      list.InsertInOrder(10);
      list.InsertInOrder(40);
      list.InsertInOrder(5);
      list.InsertInOrder(1);
      list.InsertInOrder(20);
      LinkedList list2=new LinkedList();
      list2.InsertInOrder(-1);
      list2.InsertInOrder(4);
      list2.InsertInOrder(6);
      list2.InsertInOrder(9);
      list.print();
      System.out.println("The length of List is :"+list.Length() );
      System.out.println("The value stored at Head is : "+list.getheadValue());
      System.out.println("The value stored at Tail is : "+list.getTailValue());
      System.out.println("------------------------");
      System.out.println("Now the list is going to be Reverse");
      list.reverseList();
      list.print();
      System.out.println("The value stored at Head is : "+list.getheadValue());
      System.out.println("The value stored at Tail is : "+list.getTailValue());
      System.out.println("---------------------");
      list.reverseList();
      list.print();
      System.out.println("The value stored at Head is : "+list.getheadValue());
      System.out.println("The value stored at Tail is : "+list.getTailValue());
      System.out.println("-------------------");
      System.out.println(list.toString());
      System.out.println("---------------");
      list.addAll(list2);
      list.print();
      System.out.println("The value stored at Head is : "+list.getheadValue());
      System.out.println("The value stored at Tail is : "+list.getTailValue());
      System.out.println("Length of My List is  "+list.Length());
      System.out.println("--------------------");
      list.Remove(9);
      list.print();
    }
    
}
