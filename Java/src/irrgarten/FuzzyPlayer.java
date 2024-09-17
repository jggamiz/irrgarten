/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.ArrayList;

/**
 *
 * @author jorge
 */
public class FuzzyPlayer extends Player{
    FuzzyPlayer(Player other){
        super(other);
        this.setHealth(getHealth()+Dice.healthReward());
    }
    
    
    @Override
    public Directions move(Directions direction, ArrayList<Directions> validMoves){
        Directions pref = super.move(direction, validMoves);
        return Dice.nextStep(pref, validMoves, this.getIntelligence());
    }
    
    @Override
    public float attack(){
        return (this.sumWeapons()+Dice.intensity(this.getStrength()));
    }
    
    @Override
    protected float defensiveEnergy(){
        return (this.sumShields()+Dice.intensity(this.getIntelligence()));
    }
    
    @Override
    public String toString(){
        return "Fuzzy"+super.toString();
    }
}