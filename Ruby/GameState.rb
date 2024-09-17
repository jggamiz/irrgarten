#encoding: UTF-8

module Irrgarten
	
	class GameState
		def initialize(labrv, jugadores, monstruos, jugando_ahora, w, more)
			@labyrinthv=labrv;
		   	@players=jugadores;
		   	@monsters=monstruos;
		   	@current_player=jugando_ahora;
		   	@winner=w;
		   	@log=more;
		end
		
		attr_accessor :labyrinth
		attr_accessor :players
		attr_accessor :monsters
		attr_accessor :current_player
		attr_accessor :winner
		attr_accessor :log
		
		
		def get_labyrinthv()
			@labyrinthv
		end
		
		def set_labyrinthv(labrv)
			@labyrinthv = labvr
		end
		
		def get_players()
			@players
		end
		
		def set_players(jugadores)
			@players=jugadores;
		end
		
		def get_monsters()
			@monsters
		end
		
		def get_winner()
			if (@winner) then return "There is a winner"
			else return "There ain't no winner yet"
			end
		end
		
		def set_players(monstruos)
			@monsters=monstruos;
		end
		
		def get_current_player()
			@current_player
		end
		
		def set_current_player(jugando_ahora)
			@current_player=jugando_ahora
		end	
		
		def get_log()
			@log
		end
		
		def set_log(more)
			@log=more
		end
	end	
end
