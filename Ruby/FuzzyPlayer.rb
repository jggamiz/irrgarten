#encoding: UTF-8

require_relative 'Dice'
require_relative 'Player'

module Irrgarten
	class FuzzyPlayer < Player
		public
		def initialize(other)
			copy(other)
			@health = Dice.health_reward
		end
		
		def move(direction, valid_moves)
     		pref=super(direction, valid_moves)
     		Dice.next_step(pref,valid_moves,@intelligence)
		end

		def attack()
			sum_weapons+Dice.intensity(@strength)
		end
		
		def defensive_energy()
			sum_shields+Dice.intensity(@intelligence)
		end
		
		def to_string()
			"Fuzzy"+super
		end
		
		#public_class_method :new
	end
end
