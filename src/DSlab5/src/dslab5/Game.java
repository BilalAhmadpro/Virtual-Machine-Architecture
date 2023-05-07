/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dslab5;

import java.util.Random;

/**
 *
 * @author 22867
 */
public class Game {
     Node head;
     public void insert(String player) { 
        Node newNode = new Node(player);
        Node curr;
        if(head==null){
            head=newNode;
            newNode.next=head;
        }
        else{
            for(curr=head;curr.next !=head;curr=curr.next){
                
            }
            curr.next=newNode;
            newNode.next=head;
                    
        }
     }
     public String playGame(){
         Random rand=new Random();
         int r;
         int counter;
         Node curr;
         Node prev;
         
         
while(head.next!=head){
    curr=head;
    prev=head;
    counter=1;
// R=generate random number
r=rand.nextInt(10)+1;
System.out.println(r);
//move pointer in list R time
while(counter<r){
    counter++;
    prev=curr;
    curr=curr.next;
}
// delete node where pointer stop
delete(prev,curr);
}
 return head.name; // winner
} 
     
       public void delete(Node prev,Node temp){ 
           if(prev==temp){
               Node curr;
             for(curr=head;curr.next !=head;curr=curr.next){
                
            }
             head=head.next;
             curr.next=head;
           }
           else if(prev.next==head){
               head=head.next;
               prev.next=head;
           }
           else{
               prev.next=temp.next;
           }
       }
   public String toString(){ 
       String str="";
       Node curr;
       for(curr=head;curr.next !=head;curr=curr.next){
             str=str+curr.name+"";   
            }
       return str;
}
     
}
