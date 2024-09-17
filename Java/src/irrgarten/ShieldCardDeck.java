/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author jorge
 */
public class ShieldCardDeck extends CardDeck<Shield>{
    @Override
    protected void addCards(){
        int max=10;
        for(int i=0; i<max; i++){
            this.addCard(new Shield(Dice.shieldPower(),Dice.usesLeft()));
        }
    }
}
