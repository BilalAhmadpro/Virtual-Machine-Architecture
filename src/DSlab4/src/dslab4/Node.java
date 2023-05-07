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
public class Node<T extends Comparable<T>>
{
   private T data;
   private Node next;
   Node()
   {
       this.data=null;
       this.next=null;
   }
   Node(T data)
   {
       this.data=data;
       next=null;
   }
   public Node getNext()
   {
       return next;
   }
   public T getData()
   {
       return data;
   }
   public  void setNext(Node nex)
   {
       next=nex;
   }
   
   
          
}
