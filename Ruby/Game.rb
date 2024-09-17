#encoding: UTF-8

require_relative 'GameState'
require_relative 'GameCharacter'
require_relative 'Player'
require_relative 'Dice'
require_relative 'Monster'
require_relative 'Labyrinth'
require_relative 'FuzzyPlayer'

module Irrgarten
	class Game
		@@MAX_ROUNDS=10
	
		def initialize(nplayers)		
			@players=Array.new
			@monsters=Array.new
			
			for i in 0...nplayers do
				c=(i+'0'.ord).chr
				@players.push(Player.new(c,Dice.random_intelligence,Dice.random_strength))
			end
			
			@current_player_index = Dice.who_starts(nplayers)
			@current_player = @players.at(@current_player_index)
			
			#@labyrinth = Labyrinth.new(@NUM_ROWS, @NUM_COLS, Dice.random_pos(@NUM_ROWS), Dice.random_pos(@NUM_COLS))
			@labyrinth=nil
			configure_labyrinth
			
			@labyrinth.spread_players(@players)
			
			@log="Let the Game begin\n"
		end
		
		
		public
		def finished()
			@labyrinth.have_a_winner()
		end
		
		def next_step(preferred_direction)
			@log=""
			
			if (!@current_player.dead())
				direction = actual_direction(preferred_direction)
				
				if (direction != preferred_direction) 
					log_player_no_orders 
				end
				
				monster=@labyrinth.put_player(direction,@current_player)
				
				if (monster==nil)
					log_no_monster
				else
					winner = combat(monster)
					manage_reward(winner)
				end
			else
				manage_resurrection
			end
			
			end_game=finished

			if (!end_game)
				next_player
			end
			
			end_game
		end
		
		def get_game_state()
			players_str=""
			@players.each do |p|
               	players_str += p.to_string + "\n"
          	end

			monsters_str=""
			@monsters.each do |m|
               	monsters_str += m.to_string + "\n"
          	end
          				
	     	GameState.new(@labyrinth.to_string, players_str, monsters_str, @current_player_index, self.finished, @log);
		end
		
		
		private
		def configure_labyrinth()
			# Para crear el laberinto
			rows=8
			cols=12
			exit_r=2
			exit_c=1
			@labyrinth=Labyrinth.new(rows, cols, exit_r, exit_c)
			
			#Añadimos los bloques
			@labyrinth.add_block(Orientation::HORIZONTAL, 7, 2, 3);
			@labyrinth.add_block(Orientation::VERTICAL, 1, 10, 1);
			@labyrinth.add_block(Orientation::HORIZONTAL, 3, 6, 4);
			@labyrinth.add_block(Orientation::VERTICAL, 8, 4, 3);
			@labyrinth.add_block(Orientation::HORIZONTAL, 8, 1, 2);
			@labyrinth.add_block(Orientation::VERTICAL,4 , 1, 4);
			@labyrinth.add_block(Orientation::VERTICAL,5 , 5, 1);
			
			# Creamos y añadimos los monstruos
			m1 = Monster.new("-Easy",1.4,2.0);
		    	@monsters.push(m1);
			@labyrinth.add_monster(10,5, m1);
			   
			m2 = Monster.new("-Easy",2.6,2.2);
			@monsters.push(m2);
			@labyrinth.add_monster(1,3, m2);
			   
			m3 = Monster.new("-Medium",5.3,4.8);
			@monsters.push(m3);
			@labyrinth.add_monster(5,10, m3);
			   
			m4 = Monster.new("-Difficult",8.1,9.0);
			@monsters.push(m4);
			@labyrinth.add_monster(11,10, m4);
			  
			m5 = Monster.new("-Difficult",9.4,9.7);
			@monsters.push(m5);
			@labyrinth.add_monster(0,3, m5);
		    
			m6 = Monster.new("-Impossible",200,200);
			@monsters.push(m6);
			@labyrinth.add_monster(4,9, m6);
		end
	
		def next_player()
			@current_player_index = (@current_player_index + 1) % @players.size
			@current_player=@players.at(@current_player_index)
		end

		def actual_direction(preferred_direction)
			current_row = @current_player.row
			current_col = @current_player.col
			valid_moves = @labyrinth.valid_moves(current_row, current_col)
			output=@current_player.move(preferred_direction,valid_moves)
			output #comprobar si hace falta ponerlo
		end
		
		def combat(monster)
			rounds=0;
			winner=GameCharacter::PLAYER
			lose=monster.defend(@current_player.attack)
			
			while ((!lose) && (rounds<@@MAX_ROUNDS))
				winner=GameCharacter::MONSTER
				rounds+=1
				lose=@current_player.defend(monster.attack)
				
				if (!lose)
					winner=GameCharacter::PLAYER
					lose=monster.defend(@current_player.attack)
				end
			end
			
			self.log_rounds(rounds,@@MAX_ROUNDS)
			winner
		end
		
		def manage_reward(winner)
			if (winner==GameCharacter::PLAYER)
				@current_player.receive_reward
				self.log_player_won
			else
				self.log_monster_won
			end
		end
		
		def manage_resurrection()
			if (Dice.resurrect_player)
				@current_player.resurrect
				self.convert_to_fuzzy_player
				self.log_resurrected
			else
				self.log_player_skip_turn
			end
		end
		
		def convert_to_fuzzy_player()
			fp = FuzzyPlayer.new(@current_player)
			@current_player=fp
			@players[@current_player_index]= fp
			@labyrinth.put_fuzzy_player(@current_player.row, @current_player.col, @current_player)
		end
		
		def log_player_won()
			@log += "Player"+@current_player_index.to_s+" won the combat\n"
		end
		
		def log_monster_won()
			@log += "Monster won the combat\n"
		end
		
		def log_resurrected()
			@log += "Player"+@current_player_index.to_s+" resurrected\n"
		end
		
		def log_player_skip_turn()
			@log += "Player"+@current_player_index.to_s+" lost turn\n"
		end
		
		def log_player_no_orders()
			@log += "It wasn't possible for Player"+@current_player_index.to_s+" to follow orders\n"
		end
		
		def log_no_monster()
			@log += "Player"+@current_player_index.to_s+" whether moved to an empty char or couldn't move\n"
		end
		
		def log_rounds(rounds,max)
			@log += rounds.to_s+" out of "+max.to_s+" combat rounds have taken place\n"
		end	
		
	end #class
end #module
