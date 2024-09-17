/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.Random;
import java.util.ArrayList;

/**
 * @author jorge
 */

public class Dice {
    private static final int MAX_USES = 5; // número máximo de usos de armas y escudos
    private static final float MAX_INTELLIGENCE = 10.0f; // valor máximo para la inteligencia de jugadores y monstruos
    private static final float MAX_STRENGTH = 10.0f; // valor máximo para la fuerza de jugadores y monstruos
    private static final float RESURRECT_PROB = 0.3f; // probabilidad de que un jugador sea resucitado en cada turno
    private static final int WEAPONS_REWARD = 2; // número máximo de armas recibidas al ganar un combate
    private static final int SHIELDS_REWARD = 3; // número máximo de escudos recibidos al ganar un combate
    private static final int HEALTH_REWARD = 5; // número máximo de unidades de salud recibidas al ganar un combate
    private static final int MAX_ATTACK = 3; // máxima potencia de armas
    private static final int MAX_SHIELD = 2; // máxima potencia de escudos
    
    private static Random generator = new Random();
 
    
    // Métodos públicos
    public static int randomPos(int max){
        return generator.nextInt(max);
    }
    
    public static int whoStarts(int nplayers){
        return generator.nextInt(nplayers);
    }
    
    public static float randomIntelligence(){
        return MAX_INTELLIGENCE*generator.nextFloat();
    }
    
    public static float randomStrength(){
        return MAX_STRENGTH*generator.nextFloat();
    }
    
    public static boolean resurrectPlayer(){
        // return generator.nextBoolean();
        return generator.nextFloat()<RESURRECT_PROB;      
    }
    
    public static int weaponsReward(){
        return generator.nextInt(WEAPONS_REWARD+1);
    }
    
    public static int shieldsReward(){
        return generator.nextInt(SHIELDS_REWARD+1);
    }
    
    public static int healthReward(){
        return generator.nextInt(HEALTH_REWARD+1);
    }
    
    public static float weaponPower(){
        return MAX_ATTACK*generator.nextFloat();
    }
    
    public static float shieldPower(){
        return MAX_SHIELD*generator.nextFloat();
    }
    
    public static int usesLeft(){
        return generator.nextInt(MAX_USES+1);
    }
    
    public static float intensity(float competence){
        return competence*generator.nextFloat();
    }
    
    public static boolean discardElement(int usesLeft){
        float prob = (float) usesLeft/MAX_USES;
        return (prob < generator.nextFloat());
    }
    
    public static Directions nextStep(Directions preference, ArrayList<Directions> validMoves, float intelligence){
        if(generator.nextFloat()*MAX_INTELLIGENCE < intelligence){
            return preference;
        } else{
            return validMoves.get(generator.nextInt(validMoves.size()));
        }
    }
}