/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 * @author jorge
 */

public class GameState {
    private String labyrinthv;
    private String players;
    private String monsters;
    private int currentPlayer;
    private boolean winner;
    private String log;
    
    
    // Constructor
    public GameState(String labrv, String jugadores, String monstruos, int jugandoAhora, boolean w, String more){
        labyrinthv=labrv;
        players=jugadores;
        monsters=monstruos;
        currentPlayer=jugandoAhora;
        winner=w;
        log=more;
    }
    
    
    // Consultores/Modificadores
    public String getLabyrinthv(){
        return labyrinthv;
    }
    
    public void setLabyrinthv(String labrv){
        labyrinthv=labrv;        
    }
    
    public String getPlayers(){
        return players;
    }
    
    public void setPlayers(String jugadores){
        players=jugadores;
    }
    
    public String getMonsters(){
        return monsters;
    }
    
    public void setMonsters(String monstruos){
        monsters=monstruos;
    }
    
    public int getCurrentPlayer(){
        return currentPlayer;
    }
    
    public void setCurrentPlayer(int i){
        currentPlayer=i;
    }
    
    public String getWinner(){
        if (winner) return "There is a winner";
        else return "There ain't no winner yet";
    }
    
    public String getLog(){
        return log;
    }
    
    public void setLog(String more){
        log=more;
    }
}
