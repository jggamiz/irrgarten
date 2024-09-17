#encoding: UTF-8

require_relative 'Dice'
require_relative 'Directions'
require_relative 'Weapon'
require_relative 'Shield'

module Irrgarten
	class LabyrinthCharacter
		def initialize(name, intelligence, strength, health)
			@name = name
			@intelligence = intelligence
			@strength = strength
			@health = health
			@row = -1
			@col = -1
     	end
     	
     	def copy(other)
		    	@name = other.name
			@intelligence = other.intelligence
			@strength = other.strength
			@health = other.health
			@row = other.row
			@col = other.col
     	end
     	
     	public
     	def dead
     		@health<=0
     	end
     	
     	attr_reader :row,:col,:intelligence,:strength,:name
   		attr_accessor :health
   		
  		def set_pos(i,j)
			@row=i
			@col=j
		end
		
		def to_string()
			@name.to_s+", i:"+@intelligence.to_s+", s:"+@strength.to_s+", h:"+@health.to_s + ", (row,col)=(" + @row.to_s + "," + @col.to_s + ")"
		end
		
		def got_wounded
			@health -= 1
		end		
	end
end
