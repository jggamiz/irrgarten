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
public class Game {
    private static final int MAX_ROUNDS = 10;
    
    private int currentPlayerIndex;
    private String log;
    
    private Player currentPlayer;
    ArrayList<Player> players;
    ArrayList<Monster> monsters;
    Labyrinth labyrinth;
       
    // Constructor
    public Game(int nplayers){
        this.players = new ArrayList<>();
        this.monsters = new ArrayList<>();
        
        for (int i=0; i<nplayers; i++){
            char c = Character.forDigit(i, nplayers);
            players.add(i, new Player(c,Dice.randomIntelligence(),Dice.randomStrength()));
        }
        
        currentPlayerIndex = Dice.whoStarts(nplayers);
        currentPlayer = players.get(currentPlayerIndex);
        
        labyrinth = null;
        this.configureLabyrinth();
        
        Player arrayPlayers[]= new Player[nplayers];
        for (int i=0; i<nplayers; i++){
            arrayPlayers[i]=players.get(i);
        }
        
        labyrinth.spreadPlayers(arrayPlayers);
       // labyrinth[this.currentPlayer.getRow()][this.currentPlayer.getCol()] = (char)this.currentPlayerIndex;
        
        log="Let the Game begin\n";
    }
    
    
    // Métodos públicos
    public boolean finished(){
        return labyrinth.haveAWinner();
    }
    
    public boolean nextStep(Directions preferredDirection){
        log="";
        
        if (!currentPlayer.dead()){
            Directions direction = this.actualDirection(preferredDirection);
            
            if (direction!=preferredDirection) 
                this.logPlayerNoOrders();
            
            Monster monster=labyrinth.putPlayer(direction, currentPlayer);
            
            if (monster==null) 
                this.logNoMonster();
            else{
                GameCharacter winner=this.combat(monster);
                this.manageReward(winner);
            }
        } else {
            this.manageResurrection();
        }
        
        boolean endGame=this.finished();
        if (!endGame) this.nextPlayer();
        
        return endGame;
    }
    
    public GameState getGameState(){
        String players_str="";
        for (Player p : players){
            players_str+=p.toString() + "\n";
        }
        
        String monsters_str="";
        for (Monster m : monsters){
            monsters_str+=m.toString() + "\n";
        }
        
        return (new GameState(labyrinth.toString(),players_str, monsters_str, currentPlayerIndex, finished(), log));
    }
    
    
    // Métodos privados
    private void configureLabyrinth(){
        
        // Para inicializar las dimensiones del laberinto
        int rows=8, cols=12;
        int exitRow=2, exitCol=1;      
        labyrinth = new Labyrinth(rows,cols,exitRow,exitCol);
        
        // Añadimos los bloques
        labyrinth.addBlock(Orientation.HORIZONTAL, 7, 2, 3);
        labyrinth.addBlock(Orientation.VERTICAL, 1, 10, 1);
        labyrinth.addBlock(Orientation.HORIZONTAL, 3, 6, 4);
        labyrinth.addBlock(Orientation.VERTICAL, 8, 4, 3);
        labyrinth.addBlock(Orientation.HORIZONTAL, 8, 1, 2);
        labyrinth.addBlock(Orientation.VERTICAL,4 , 1, 4);
        labyrinth.addBlock(Orientation.VERTICAL,5 , 5, 1);
        
        // Creamos y añadimos los monstruos
        Monster m1 = new Monster("-Easy",1.4f,2.0f);
        monsters.add(m1);
        labyrinth.addMonster(10,5, m1);
        
        Monster m2 = new Monster("-Easy",2.6f,2.2f);
        monsters.add(m2);
        labyrinth.addMonster(1,3, m2);
        
        Monster m3 = new Monster("-Medium",5.3f,4.8f);
        monsters.add(m3);
        labyrinth.addMonster(5,10, m3);
        
        Monster m4 = new Monster("-Difficult",8.1f,9.0f);
        monsters.add(m4);
        labyrinth.addMonster(11,10, m4);
        
        Monster m5 = new Monster("-Difficult",9.4f,9.7f);
        monsters.add(m5);
        labyrinth.addMonster(0,3, m5);
    
        Monster m6 = new Monster("-Impossible",200f,200f);
        monsters.add(m6);
        labyrinth.addMonster(4,9, m6);
    }
    
    private void nextPlayer(){
        /*if (currentPlayerIndex < players.size()-1)
            currentPlayerIndex++;
        else if (currentPlayerIndex == players.size()-1)
            currentPlayerIndex=0;
        */
        
        this.currentPlayerIndex = (this.currentPlayerIndex+1)%(this.players.size());
        this.currentPlayer = players.get(currentPlayerIndex);
    }
    
    private Directions actualDirection(Directions preferredDirection){
        int currentRow = currentPlayer.getRow();
        int currentCol = currentPlayer.getCol();
        
        ArrayList<Directions> validMoves = this.labyrinth.validMoves(currentRow, currentCol);
       // Directions[] validMoves = labyrinth.validMoves(currentRow, currentCol);
        return currentPlayer.move(preferredDirection, validMoves);
    }
    
    private GameCharacter combat(Monster monster){
        int rounds=0;
        GameCharacter winner = GameCharacter.PLAYER;
        boolean lose = monster.defend(currentPlayer.attack());
        
        while ((!lose) && (rounds<MAX_ROUNDS)){
            winner = GameCharacter.MONSTER;
            rounds++;
            lose = currentPlayer.defend(monster.attack());
            
            if (!lose) {
                winner = GameCharacter.PLAYER;
                lose = monster.defend(currentPlayer.attack());
            }
        }
        
        this.logRounds(rounds, MAX_ROUNDS);
        return winner;
    }
    
    private void manageReward(GameCharacter winner){
        if (winner==GameCharacter.PLAYER){
            currentPlayer.receiveReward();
            this.logPlayerWon();
        } else { 
            this.logMonsterWon();
        }
}
 
    private void manageResurrection(){
        if (Dice.resurrectPlayer()){
            currentPlayer.resurrect();
            this.convertToFuzzyPlayer();
            this.logResurrected();
        } else {
            this.logPlayerSkipTurn();
        }
    }
    
    private void convertToFuzzyPlayer(){
        FuzzyPlayer fp = new FuzzyPlayer(currentPlayer);
        currentPlayer=fp;
        players.set(currentPlayerIndex, fp);
        labyrinth.putFuzzyPlayer(currentPlayer.getRow(), currentPlayer.getCol(), fp);
    }
    
    private void logPlayerWon(){
        log+="Player"+String.valueOf(this.currentPlayerIndex)+" won the combat\n";
    }
    
    private void logMonsterWon(){
        log+="Monster won the combat\n";
    }
    
    private void logResurrected(){
        log+="Player"+String.valueOf(this.currentPlayerIndex)+" resurrected\n";
    }
    
    private void logPlayerSkipTurn(){
        log+="Player"+String.valueOf(this.currentPlayerIndex)+" lost turn\n";
    }
    
    private void logPlayerNoOrders(){
        log+="It wasn't possible for Player"+String.valueOf(this.currentPlayerIndex)+ " to follow orders\n";
    }
    
    private void logNoMonster(){
        log+="Player"+String.valueOf(this.currentPlayerIndex)+" whether moved to an empty char or couldn't move\n";
    }
    
    private void logRounds(int rounds, int max){
        log+=String.valueOf(rounds)+" out of "+String.valueOf(max)+" combat rounds have taken place\n";
    }
}
