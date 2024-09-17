/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 * @author jorge
 */

public class Shield extends CombatElement{
    // Constructor
    public Shield (float protection, int uses){
        super(protection,uses);
    }
    
    
    // Métodos públicos
    public float protect (){
        return produceEffect();
    }
    
    @Override
    public String toString(){
        return "S"+super.toString();
    }
}