/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import irrgarten.Directions;
import irrgarten.Orientation;

import irrgarten.Monster;
import irrgarten.Player;
import irrgarten.Dice;

import java.util.ArrayList; // import the ArrayList class
        
/**
 *
 * @author jorge
 */
public class Labyrinth {
    private static final char BLOCK_CHAR='X';
    private static final char EMPTY_CHAR = '-';
    private static final char MONSTER_CHAR = 'M';
    private static final char COMBAT_CHAR = 'C';
    private static final char EXIT_CHAR = 'E';
    private static final int ROW = 0;
    private static final int COL = 1;
    
    private int nRows;
    private int nCols;
    private int exitRow;
    private int exitCol;
    
    Monster monsters[][];
    Player players[][];
    char labyrinth[][];
    
    
    // Constructor
    public Labyrinth(int numRows, int numCols, int exRow, int exCol){
        this.nRows = numRows;
        this.nCols = numCols;
        this.exitRow = exRow;
        this.exitCol = exCol;
        
        this.monsters = new Monster[nRows][nCols];
        this.players = new Player[nRows][nCols];
        this.labyrinth = new char[nRows][nCols];
        
        for (int i=0; i<this.nRows; i++){
            for (int j=0; j<this.nCols; j++){
                this.monsters[i][j]=null;
                this.players[i][j]=null;
                this.labyrinth[i][j]=EMPTY_CHAR;
            }   
        }
        
        this.labyrinth[exitRow][exitCol]=EXIT_CHAR;
    }


    // Métodos públicos
    public void spreadPlayers(Player[] players){
        for (int i=0; i<players.length; ++i){
            Player p=players[i];
            int[] pos = randomEmptyPos();
            Monster m = putPlayer2D(-1,-1,pos[ROW],pos[COL],p);
        }
    }
    
    public boolean haveAWinner(){
       return players[exitRow][exitCol] != null;
    }
    
    public String toString(){
        String lab="Labyrinth: "+String.valueOf(this.nRows)+"x"+String.valueOf(this.nCols)+"\n\n";
        for (int i=0; i<this.nRows; i++) {
            for (int j = 0; j < this.nCols; j++) {
                lab += labyrinth[i][j];
            }
            lab += "\n";
        }

        return lab;
    }
    
    public void addMonster(int row, int col, Monster monster){
        if (posOK(row,col) && emptyPos(row,col)){
            labyrinth[row][col]=MONSTER_CHAR;
            monsters[row][col]=monster;
            monster.setPos(row, col);
        }
    }
    
    public Monster putPlayer(Directions direction, Player player){
        int oldRow = player.getRow();
        int oldCol = player.getCol();
        int[] newPos = this.dir2Pos(oldRow, oldCol, direction);
        
        return putPlayer2D(oldRow, oldCol, newPos[ROW], newPos[COL], player);
    }
    
    public void addBlock(Orientation orientation, int startRow, int startCol, int length){
        int incRow;
        int incCol;
        
        if (orientation==Orientation.VERTICAL){
            incRow=1;
            incCol=0;
        } else {
            incRow=0;
            incCol=1;
        }
        
        int row = startRow;
        int col = startCol;
        
        while ((posOK(row,col)) && (emptyPos(row,col)) && (length>0)){
            labyrinth[row][col] = BLOCK_CHAR;
            length--;
            row+=incRow;
            col+=incCol;
        }
    }
    
    public ArrayList<Directions> validMoves(int row, int col){
        ArrayList<Directions> output = new ArrayList<>();
        
        if (canStepOn(row+1,col))
            output.add(Directions.DOWN);
                
        if (canStepOn(row-1,col))
            output.add(Directions.UP);
        
        if (canStepOn(row,col+1))
            output.add(Directions.RIGHT);
        
        if (canStepOn(row,col-1))
            output.add(Directions.LEFT);        
        
        return output;
    }
    
    
    // Métodos privados
    private boolean posOK(int row, int col){
        return (0<=row && row<nRows && 0<=col && col<nCols);
    }
    
    private boolean emptyPos(int row, int col){
        return (labyrinth[row][col] == EMPTY_CHAR);
    }
    
    private boolean monsterPos(int row, int col){
        return (labyrinth[row][col] == MONSTER_CHAR);
    }
    
    private boolean exitPos(int row, int col){
        return (labyrinth[row][col] == EXIT_CHAR);
    }
    
    private boolean combatPos(int row, int col){
        return (labyrinth[row][col] == COMBAT_CHAR);
    }
    
    private boolean canStepOn(int row, int col){
        /*if (!posOK(row,col)) return false;
        else {
            if (exitPos(row,col) || monsterPos(row,col) || emptyPos(row,col)) return true;   
        }
        return false;*/
        
        return (posOK(row,col) && (emptyPos(row,col) || monsterPos(row,col) || exitPos(row,col)));
    }
    
    private void updateOldPos(int row, int col){
        if (posOK(row,col)){
            if (combatPos(row,col)) labyrinth[row][col] = MONSTER_CHAR;
            else labyrinth[row][col] = EMPTY_CHAR;
        }
    }

    private int[] dir2Pos(int row, int col, Directions direction){
        int[] v = new int[2]; 
        
        switch(direction){
            case RIGHT:
                col++;
                break;
                
            case LEFT: 
                col--;
                break;
                
            case UP:
                row--;
                break;
                
            case DOWN:
                row++;
                break;
        }
        
        v[ROW]=row;
        v[COL]=col;
                
        return v;
    }
    
    private int[] randomEmptyPos(){
      /*  boolean sigue=true;
        int r,c;
        int[] v = new int[2];
        
        while (sigue){
            r=Dice.randomPos(nRows);
            c=Dice.randomPos(nCols);
            if(emptyPos(r,c)){
                sigue=false;
                v[ROW]=r;
                v[COL]=c;
            }
        }*/
      
        int[] v = new int[2];
        do{
            v[ROW] = Dice.randomPos(nRows);
            v[COL] = Dice.randomPos(nCols);
        } while(!emptyPos(v[ROW],v[COL]));
        
        return v;
    }
    
    private Monster putPlayer2D(int oldRow, int oldCol, int row, int col, Player player){
        Monster output=null;
        if (canStepOn(row,col)){
            if (posOK(oldRow,oldCol)){
                Player p=players[oldRow][oldCol];
                
                if (p==player){
                    this.updateOldPos(oldRow, oldCol);
                    players[oldRow][oldCol]=null;
                }
            }
            
            if (monsterPos(row,col)){
                labyrinth[row][col]=COMBAT_CHAR;
                output=monsters[row][col];
            } else {
                labyrinth[row][col] = player.getNumber();
            }
            
            players[row][col]=player;
            player.setPos(row, col);
        }
        
        return output;
    }
    
    public void putFuzzyPlayer(int row, int col, FuzzyPlayer fp){
        players[row][col] = fp;
    }
}
