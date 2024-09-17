/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author jorge
 * @param <T>
 */
public  abstract class CardDeck <T>{
    private final ArrayList<T> cardDeck;
    
    CardDeck(){
        cardDeck = new ArrayList<>(); 
    }
    
    protected abstract void addCards();
    
    protected void addCard(T card){
        cardDeck.add(card);
    }
    
    public T nextCard(){
        if (cardDeck.isEmpty()){
            addCards();
            Collections.shuffle(cardDeck);
        }
        return cardDeck.remove(0);
    }
}