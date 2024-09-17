/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten.controller;

import irrgarten.Directions;
import irrgarten.Game;
import irrgarten.UI.TextUI;

/**
 *
 * @author jorge
 */
public class ControllerP3 {
    
    private Game game;
    private TextUI view;
    
    public ControllerP3(Game game, TextUI view) {
        this.game = game;
        this.view = view;
    }
    
    public void play() {
        boolean endOfGame = false;
        while (!endOfGame) {
            view.showGame(game.getGameState()); 
            Directions direction = view.nextMove(); 
            endOfGame = game.nextStep(direction);
        }
      view.showGame(game.getGameState());        
    }
}
