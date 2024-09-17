/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author jorge
 */
public class Monster extends LabyrinthCharacter{
    private static final int INITIAL_HEALTH = 5;
    
    public Monster(String nombre, float i, float s){
        super(nombre,i,s,INITIAL_HEALTH);
    }
       
    @Override
    public float attack(){
        return Dice.intensity(getStrength());
    }
    
    @Override
    public String toString(){
        return "Monster"+super.toString();
    }
    
    @Override
    public boolean defend(float receivedAttack){
        boolean isDead = dead();
        
        if (!isDead){
            float defensiveEnergy = Dice.intensity(getIntelligence());
            if (defensiveEnergy < receivedAttack){
                gotWounded();
                isDead=dead();
            }
        }
        
        return isDead;
    }
}