/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten.main;

import irrgarten.UI.TextUI;
import irrgarten.controller.ControllerP3;
import irrgarten.Game;

/**
 *
 * @author jorge
 */
public class TextMain {
    public static void main(String args[]){
        Game game = new Game(2);
        TextUI view = new TextUI();
        ControllerP3 controller = new ControllerP3(game,view);
        
        controller.play();
    }
}