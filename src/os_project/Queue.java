/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os_project;

/**
 *
 * @author Bilal Ahmad
 * @param <T>
 */
public class Queue
{
Node front;
Node rear;
   Queue()
   {
       front=rear=null;
   }
   public boolean IsEmpty()
   {
       return front==null;
   }
   public void Enquue(ProcessControlBlock pb)
   {
       if(IsEmpty())
       {
           front=rear=new Node(pb);
       }
       else
       {
           if(front.pb1.ProcessPriority<=pb.ProcessPriority)
           {
               Node temp=new Node(pb);
               temp.next=front;
               front=temp;
               return;
           }
           else if(rear.pb1.ProcessPriority>=pb.ProcessPriority)
           {
               Node temp=new Node(pb);
               rear.next=temp;
               rear=temp;
               return;
           }
           else
           {
               Node temp=front;
               Node prev=front;
               while(temp!=null && temp.pb1.ProcessPriority>=pb.ProcessPriority)
               {
                   prev=temp;
                   temp=temp.next;
               }
               Node temp1=new Node(pb);
               temp1.next=temp;
               prev.next=temp1;
               
               
           }
           
       }
   }
@Override
   public String toString()
   {
       Node temp=front;
       String str="";
       while(temp!=null)
       {
           str+=temp.pb1.ProcessPriority+" , ";
           temp=temp.next;
       }
       return str;
   }
   public ProcessControlBlock Dequue()
   {
       ProcessControlBlock temp=null;
      if(!IsEmpty())
      {
         temp=front.pb1;
          front=front.next;
          return temp;
      }
      return temp;
   }
           
}
