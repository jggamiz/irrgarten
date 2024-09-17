/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author jorge
 */
public abstract class LabyrinthCharacter {
    private String name;
    private float intelligence;
    private float strength;
    private float health;
    private int row;
    private int col;
    
    LabyrinthCharacter(String n, float i, float s, float h){
        name = n;
        intelligence = i;
        strength = s;
        health = h;
        row=col=-1;
    }
    
    LabyrinthCharacter(LabyrinthCharacter other){
        this.name=other.name;
        this.intelligence=other.intelligence;
        this.strength=other.strength;
        this.health=other.health;
        this.row = other.row;
        this.col = other.col;
    }
    
    public boolean dead(){
        return health<=0;
    }
    
    public int getRow(){
        return row;
    }
    
    public int getCol(){
        return col;
    }
    
    protected float getIntelligence(){
        return intelligence;
    }
    
    protected float getStrength(){
        return strength;
    }
    
    protected float getHealth(){
        return health;
    }
    
    protected void setHealth(float h){
        health = h;
    }
    
    public void setPos(int r, int c){
        row=r;
        col=c;
    }
    
    public String toString(){
        return name+", i:"+String.valueOf(intelligence)+", s:"+String.valueOf(strength)+", h:"+String.valueOf(health) + ", (row,col)=("+row+","+col+")";
    }
    
    protected void gotWounded(){
        health--;
    }
    
    public abstract float attack();
    public abstract boolean defend(float attack);     
}
