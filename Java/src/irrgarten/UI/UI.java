/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten.UI;

import irrgarten.Directions;
import irrgarten.GameState;

/**
 *
 * @author jorge
 */
public interface UI {
    public Directions nextMove();
    public void showGame(GameState gameState);
}
