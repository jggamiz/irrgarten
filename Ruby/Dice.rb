#encoding: UTF-8

#require 'Random'

module Irrgarten
	class Dice
		@@MAX_USES = 5
		@@MAX_INTELLIGENCE = 10.0
		@@MAX_STRENGTH = 10.0
		@@RESURRECT_PROB = 0.3
		@@WEAPONS_REWARD = 2
		@@SHIELDS_REWARD = 3
		@@HEALTH_REWARD = 5
		@@MAX_ATTACK = 3
		@@MAX_SHIELD = 2
		
		@@generator = Random.new
		
		public
		def self.random_pos(max)
			@@generator.rand(max)
		end
		
		def self.who_starts(nplayers)
			@@generator.rand(nplayers)
		end
		
		def self.random_intelligence()
			@@generator.rand(@@MAX_INTELLIGENCE)
		end
		
		def self.random_strength()
			@@generator.rand(@@MAX_STRENGTH)
		end
		
		def self.resurrect_player()
			if (@@generator.rand < @@RESURRECT_PROB) 
				return true
			else 
				return false
			end
		end
		
		def self.weapons_reward()
			@@generator.rand(@@WEAPONS_REWARD+1)
		end
		
		def self.shields_reward()
			@@generator.rand(@@SHIELDS_REWARD+1)
		end
		
		def self.health_reward()
			@@generator.rand(@@HEALTH_REWARD+1)
		end
		
		def self.weapon_power()
			@@generator.rand(@@MAX_ATTACK)
		end
		
		def self.shield_power()
			@@generator.rand(@@MAX_SHIELD)
		end
		
		def self.uses_left()
			@@generator.rand(@@MAX_USES+1)
		end
		
		def self.intensity(competence)
			@@generator.rand(competence)
		end
		
		def self.discard_element(uses_left)
			prob=uses_left/@@MAX_USES
			prob<@@generator.rand()
		end
		
        	def self.next_step(preference, valid_moves, intelligence)
          	if ((@@generator.rand * @@MAX_INTELLIGENCE) < intelligence)
          		return preference
          	else
          		return valid_moves[@@generator.rand(valid_moves.size)]
          	end
     	end
	end
end
