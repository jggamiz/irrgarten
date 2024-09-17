/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten.main;

import irrgarten.UI.GraphicalUI;
import irrgarten.controller.Controller;
import irrgarten.Game;

/**
 *
 * @author jorge
 */
public class MainGUI {
    public static void main(String args[]){
        Game game = new Game(2);
        GraphicalUI gui = new GraphicalUI();
        Controller controller = new Controller(game,gui);
        
        controller.play();
    }
}
