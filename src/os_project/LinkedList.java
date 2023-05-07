/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os_project;

/**
 *
 * @author Bilal Ahmad
 */
public class LinkedList {
    Node head;
    Node tail;
    int length=0;
      public void addNode(ProcessControlBlock data) {    
        //Create a new node    
        Node newNode = new Node(data);    
            
        //Checks if the list is empty    
        if(head == null) {    
            //If list is empty, both head and tail will point to new node    
            head = newNode;    
            tail = newNode;    
        }    
        else {    
            //newNode will be added after tail such that tail's next will point to newNode    
            tail.next = newNode;    
            //newNode will become new tail of the list    
            tail = newNode;    
        }    
        length++;
    }
      public void deleteNode(ProcessControlBlock data)
      {
          if(head.pb1==data)
          {
              head=head.next;
          }
          else{
              
          Node temp=head;
          Node prev=head;
          while(temp!=null || temp.pb1!=data)
          {
              prev=temp;
              temp=temp.next;
          }
          if(temp!=null)
          {
              prev.next=temp.next;
              
          }
          
          }
          length--;
      }
}
