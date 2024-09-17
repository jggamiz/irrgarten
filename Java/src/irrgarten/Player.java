/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.ArrayList; // import the ArrayList class

/**
 *
 * @author jorge
 */
public class Player extends LabyrinthCharacter{
    private static final int MAX_WEAPONS = 2;
    private static final int MAX_SHIELDS = 3;
    private static final int INITIAL_HEALTH = 10;
    private static final int HITS2LOSE = 3;
    
    private char number;
    private int consecutiveHits = 0;
    
    private ArrayList<Weapon> weapons;
    private ArrayList<Shield> shields;
    
    private WeaponCardDeck weaponCardDeck;
    private ShieldCardDeck shieldCardDeck;
    
    // Constructor
    public Player(char n, float i, float s){
        super("Player"+n,i,s,INITIAL_HEALTH);
        this.number = n;
        
        weapons = new ArrayList<>();
        shields = new ArrayList<>();
        
        weaponCardDeck = new WeaponCardDeck();
        shieldCardDeck = new ShieldCardDeck();
    }
    
    public Player(Player other){
        super(other);
        
        this.number = other.number;
        this.weapons= other.weapons;
        this.shields= other.shields;
        this.consecutiveHits= other.consecutiveHits;
    }
    
    // Métodos públicos
    public void resurrect(){
        weapons.clear();
        shields.clear();
        this.setHealth(INITIAL_HEALTH);
        this.resetHits();
    }
      
    public char getNumber(){
        return number;
    }
        
    public Directions move(Directions direction, ArrayList<Directions> validMoves){
        if (validMoves.size()>0 && !validMoves.contains(direction)){
            return validMoves.get(0);
        } else {
            return direction;
        }
    }
    
    @Override
    public float attack(){
        return getStrength()+this.sumWeapons();
    }
    
    @Override
    public boolean defend(float receivedAttack){
        return this.manageHit(receivedAttack);
    }
    
    public void receiveReward(){
        int wReward = Dice.weaponsReward();
        int sReward = Dice.shieldsReward();
         
        for (int i=1; i<wReward; i++){
            weapons.add(weaponCardDeck.nextCard());
        }
        
        for (int i=1; i<sReward; i++){
            shields.add(shieldCardDeck.nextCard());
        }
        
        this.setHealth(getHealth()+Dice.healthReward());
    }
    
    @Override
    public String toString(){
        String str = super.toString();
        str +="  hits: "+ String.valueOf(consecutiveHits);
        
        str+="\nweapons: ";
        for (Weapon w : weapons){
            str+= w.toString() + " ";
        }
        
        str+="\nshields: ";
        for (Shield s : shields){
            str+= s.toString() + " ";
        }
        
      //  str+= weapons.toString() + " " + shields.toString();
        str+="\n";        
        
        return str;
    }
    
    
    // Métodos privados
    private void receiveWeapon(Weapon w){
        ArrayList<Weapon> aux = new ArrayList<>(weapons);
        for(Weapon wi : weapons){
            if(wi.discard()){
                aux.remove(wi);
            }
        }
        
        weapons = aux;
        if (weapons.size()<MAX_WEAPONS) weapons.add(w);
    }

    private void receiveShield(Shield s){
        ArrayList<Shield> aux = new ArrayList<>(shields);
        for(Shield si : shields){
            if(si.discard()){
                aux.remove(si);
            }
        }
        
        shields = aux;
        if (shields.size()<MAX_SHIELDS) shields.add(s);
    }
    
    private Weapon newWeapon(){
        Weapon w = new Weapon(Dice.weaponPower(), Dice.usesLeft());
        weapons.add(w);
        return w;
    }
    
    private Shield newShield(){
        Shield s = new Shield(Dice.shieldPower(), Dice.usesLeft());
        shields.add(s);
        return s;
    }
    
    protected float sumWeapons(){
        float total=0f;
        for (Weapon w : weapons){
            total+=w.attack();
        }
        return total;
    }
    
    protected float sumShields(){
        float total=0f;
        for (Shield s : shields){
            total+=s.protect();
        }
        return total;
    }
    
    protected float defensiveEnergy(){
        return this.getIntelligence()+this.sumShields();
    }
    
    private boolean manageHit(float receivedAttack){
        if (defensiveEnergy()<receivedAttack){
            gotWounded();
            incConsecutiveHits();
        } else {
            resetHits();
        }
        
        if ((consecutiveHits == HITS2LOSE) || dead()){
            resetHits();
            return true;
        } else {
            return false;
        }
    }
    
    private void resetHits(){
        consecutiveHits=0;
    }
    
    private void incConsecutiveHits(){
        consecutiveHits++;
    }
}