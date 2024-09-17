/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author jorge
 */
public abstract class CombatElement {
    private float effect;
    private int uses;
    
    CombatElement(float e, int u){
        effect=e;
        uses=u;
    }
    
    protected float produceEffect(){
        if (uses>0){
            uses--;
            return effect;
        } else {
            return 0;
        }
    }
    
    public boolean discard(){
        return Dice.discardElement(uses);
    }
    
    @Override
    public String toString(){
       // return "["+String.valueOf(effect)+","+String.valueOf(uses)+"]";
       return "["+effect+","+uses+"]";
    }
}
