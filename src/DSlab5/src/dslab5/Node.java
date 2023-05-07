/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dslab5;

/**
 *
 * @author 22867
 */
public class Node {
    String name;
    Node next;
    Node(String s)
    {
        name=s;
    }
    public Node givenext()
    {
        return next;
    }
    public String givename()
    {
        return name;
    }
    
}
