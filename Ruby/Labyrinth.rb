#encoding: UTF-8

require_relative 'Directions'
require_relative 'Orientation'

require_relative 'Monster'
require_relative 'Player'
require_relative 'Dice'

module Irrgarten
	class Labyrinth
		@@BLOCK_CHAR='X'
		@@EMPTY_CHAR='-'
		@@MONSTER_CHAR='M'
		@@COMBAT_CHAR='C'
		@@EXIT_CHAR='E'
		@@ROW=0
		@@COL=1
=begin
		private
		def crear_matriz(rows, cols)
			matriz = Array.new(rows) { Array.new(cols) }
			return matriz
		end
=end			
		def initialize(num_rows, num_cols, exit_r, exit_c)
			@n_rows=num_rows
			@n_cols=num_cols
			@exit_row=exit_r
			@exit_col=exit_c
			
			#@monsters=crear_matriz(@n_rows, @n_cols)
			#@players=crear_matriz(@n_rows, @n_cols)
			#@labyrinth=crear_matriz(@n_rows, @n_cols)
			
			@monsters=Array.new(@n_rows) { Array.new(@n_cols) }
			@players=Array.new(@n_rows) { Array.new(@n_cols) }
			@labyrinth=Array.new(@n_rows) { Array.new(@n_cols, @@EMPTY_CHAR) }
			
			
			#for i in 0...@n_rows
			#	for j in 0...@n_cols
			#		@labyrinth[i][j] = @@EMPTY_CHAR
			#	 end
    			#end
    			
    			@labyrinth[@exit_row][@exit_col] = @@EXIT_CHAR
		end
		

		public
		def spread_players(players)
			players.each do |p|
				pos=random_empty_pos()
				m=put_player_2D(-1,-1,pos[@@ROW],pos[@@COL],p)
			end
		end
		
		def have_a_winner()			
			@players[@exit_row][@exit_col]!=nil
		end
		
		def to_string()
			lab = "Labyrinth: " + @n_rows.to_s + "x" + @n_cols.to_s + "\n\n"
			for i in 0...@labyrinth.length
				for j in 0...@labyrinth[i].length
					lab += @labyrinth[i][j]
				end
				lab+="\n"
			end
			lab+="\n"
			lab
		end
		
		def add_monster(row, col, monster)
			if (pos_OK(row,col) && empty_pos(row,col))
				@labyrinth[row][col] = @@MONSTER_CHAR
				@monsters[row][col] = monster
				monster.set_pos(row,col)
			end
		end
		
		def put_player(direction, player)
			old_row=player.row
			old_col=player.col
			
			new_pos=self.dir2pos(old_row,old_col,direction)
			monster=put_player_2D(old_row,old_col,new_pos[@@ROW],new_pos[@@COL],player)
			monster # no necesario creo
		end
		
		def add_block(orientation, start_row, start_col, length)
			if (orientation==Orientation::VERTICAL)
				inc_row=1
				inc_col=0
			else
				inc_row=0
				inc_col=1
			end
			
			row=start_row
			col=start_col
			
			while ((pos_OK(row,col)) && (empty_pos(row,col)) && (length>0))
				@labyrinth[row][col]=@@BLOCK_CHAR
				length-=1
				row+=inc_row
				col+=inc_col
			end
		end
		
		def valid_moves(row, col)
			output = Array.new(4)
			i=0
			
			if (can_step_on(row+1,col))
				output[i]=Directions::DOWN
				i+=1
			end
			
			if (can_step_on(row-1,col))
				output[i]=Directions::UP
				i+=1
			end
			
			if (can_step_on(row,col+1))
				output[i]=Directions::RIGHT
				i+=1
			end
			
			if (can_step_on(row,col-1))
				output[i]=Directions::LEFT
			end
			
			output
		end
		
		
		private
		def pos_OK(row, col)
			(0<=row && row<@n_rows && 0<=col && col<=@n_cols)
		end
		
		def empty_pos(row, col)
			@labyrinth[row][col]==@@EMPTY_CHAR
		end
		
		def monster_pos(row, col)
			@labyrinth[row][col]==@@MONSTER_CHAR
		end
		
		def exit_pos(row, col)
			@labyrinth[row][col]==@@EXIT_CHAR
		end
		
		def combat_pos(row, col)
			@labyrinth[row][col]==@@COMBAT_CHAR
		end
		
		def can_step_on(row, col)
			(pos_OK(row,col) && (empty_pos(row,col) || monster_pos(row.to_i,col) || exit_pos(row,col)))
		end
		
		def update_old_pos(row, col)
			if (pos_OK(row,col))
				if (combat_pos(row,col)) then @labyrinth[row][col]=@@MONSTER_CHAR
				else	@labyrinth[row][col]=@@EMPTY_CHAR
				end
			end
		end
		
		def dir2pos(row, col, direction)
			case direction
			when Directions::UP
				row=row-1
			when Directions::DOWN
				row=row+1
			when Directions::LEFT
				col=col-1
			when Directions::RIGHT
				col=col+1
			end
			
			array = [row,col]
		end
		
		def random_empty_pos()
			sigue=true
					
			while sigue
				r=Dice.random_pos(@n_rows)
				c=Dice.random_pos(@n_cols)
				if (empty_pos(r,c))
					sigue=false
					array = [r,c]
				end
			end
			
			array
		end
		
		def put_player_2D(old_row, old_col, row, col, player)
			output=nil
			
			if (can_step_on(row,col))
				if (pos_OK(old_row,old_col))
					p = @players[old_row][old_col]
					if (p==player)
						self.update_old_pos(old_row,old_col)
						@players[old_row][old_col]=nil
					end
				end
				
				monster_pos = self.monster_pos(row,col)
				if (monster_pos)
					@labyrinth[row][col]=@@COMBAT_CHAR
					output=@monsters[row][col]
				else
					number = player.number
					@labyrinth[row][col]=number.chr
				end
				
				@players[row][col]=player
				player.set_pos(row,col)
			end #if (can_step_on)
			
			output
		end

		public
		def put_fuzzy_player(row,col,fp)
			@players[row][col] = fp
		end
		
	end #class
end #module
