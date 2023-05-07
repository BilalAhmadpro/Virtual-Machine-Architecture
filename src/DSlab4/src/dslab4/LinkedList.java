/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dslab4;

/**
 *
 * @author Bilal Ahmad
 * @param <T>
 */
public class LinkedList<T extends Comparable<T>>
{
   private Node head;
   private Node tail;
   private int Length;
   LinkedList()
   {
       head=tail=null;
       Length=0;
   }
   public void InsertInOrder(int data)
   {
       if(head==null)
       {
           head=tail=new Node(data);
       }
       else if(head.getData().compareTo(data)>=0)
       {
           Node temp=new Node(data);
           temp.setNext(head);
           head=temp;
       }
       else
       {
           Node temp1=head;
           Node temp=head.getNext();
           while(temp!=null && temp.getData().compareTo(data)<=0)
           {
               temp1=temp;
               temp=temp.getNext();
               
           }
           
           Node temp2=new Node(data);
           temp2.setNext(temp);
           temp1.setNext(temp2);
           if(temp==null)
           {
               tail=temp2;
           }
       }
       Length++;
       //   BIG-OH of this Method is O(n);
   }
   public int Length()
   {
       // BIG-OH of this Method is O(1);
       return Length;
   }
   public void print()
   {
      // BIG-OH of this Method is O(n);
       for(Node temp=head;temp!=null;temp=temp.getNext())
       {
           System.out.print(temp.getData()+" , ");
       }
       System.out.println();
   }
   public T getheadValue()
   {
     // BIG-OH of this Method is O(1);  
          return(T) head.getData();
      
   }
   public T getTailValue()
   {
        //BIG-OH of this Method is O(1);
       return (T) tail.getData();
   }
   public void reverseList()
{
   Node prev=null;
   Node current=head; 
   Node next=null;
   tail=current;
 while(current!=null)
 {
   next=current.getNext();
   current.setNext(prev);
   prev=current;
   current=next;
     
 }
 head=prev;
  //BIG-OH of this Method is O(n);
}
   public boolean IsEmpty()
   {
        // BIG-OH of this Method is O(1);
       return head==null;
   }
   public void ClearList()
   {
       // BIG-OH of this Method is O(1);
       Length=0;
       head=null;
   }
   public boolean Find(int data)
   {
        // BIG-OH of this Method is O(n);
       Node temp=head;
       while(temp!=null)
       {
           if(temp.getData().compareTo(data)==0)
           {
               return true;
           }
           temp=temp.getNext();
       }
       return false;
   }
   @Override
   public String toString()
   {
       // BIG-OH of this Method is O(n);
       String listform="";
       Node temp=head;
       while(temp!=null)
       {
           listform+=temp.getData()+" ,";
           temp=temp.getNext();
       }
       return listform;
   }
   public Node giveHead()
   {
     //   BIG-OH of this Method is O(1);
     return head;    
   }
   public Node giveTail()
   {
        // BIG-OH of this Method is O(1);
       return tail;
   }
   
   public void Remove(int data)
   {
       if(head.getData().compareTo(data)==0)
       {
           Length--;
           head=head.getNext();
       }
       else
       {
           Node temp1=head;
           Node temp=temp1;
           while(temp!=null && temp.getData().compareTo(data)!=0)
           {
               temp1=temp;
               temp=temp.getNext();
           }
           if(temp!=null)
           {
           Length--;
           temp1.setNext(temp.getNext());
           }
       }
       //BIg-Oh of this Method is O(n);
   }
   
   public void addAll(LinkedList appendlist)
   {
      if(head==null)
      {
          head=appendlist.giveHead();
          tail=appendlist.giveTail();
      }
      else
      {
       tail.setNext(appendlist.giveHead());
       tail=appendlist.giveTail();
      }
      Length+=appendlist.Length;
     //  BIG-OH of this Method is O(1);
   }
   
}