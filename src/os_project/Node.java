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
public class Node 
{
  Node next;
  ProcessControlBlock pb1;
  Node(ProcessControlBlock pb1)
  {
   this.pb1=pb1;
   next=null;
  }
}
