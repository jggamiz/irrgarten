#encoding: UTF-8

require_relative 'Dice'
require_relative 'Weapon'
require_relative 'Shield'
require_relative 'LabyrinthCharacter'

module Irrgarten
	class Monster < LabyrinthCharacter
		@@INITIAL_HEALTH=5
		
		public	
		def initialize(n,i,s)
			super(n,i,s,@@INITIAL_HEALTH)
		end
		
		def attack()
			Dice.intensity(@strength)
		end
		
		def to_string()
			"Monster"+super
		end
		
		def defend(received_attack)
			is_dead=self.dead()
			if (!is_dead)
				defensive_energy = Dice.intensity(@intelligence)
				if (defensive_energy < received_attack)
					self.got_wounded()
					is_dead=self.dead()
				end
			end
			
			is_dead
		end
		
		public_class_method :new
	end
end
