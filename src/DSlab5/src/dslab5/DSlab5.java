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
public class DSlab5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        
       Game play = new Game();
        
        play.insert("Basit");
        play.insert("Khalil");
        play.insert("Ahmad Illahi");
        play.insert("Soorty");
        play.insert("Tirmizi");
        play.insert("Mustufa");
        
        System.out.println(play);
        
        System.out.println(play.playGame());
    }    
    }
    

