#encoding: UTF-8

require_relative 'Dice'
require_relative 'CombatElement'

module Irrgarten
	class Weapon < CombatElement
		def initialize(power,uses)
			super(power,uses)
		end
		
		public
		def attack
			produce_effect
		end
		
		def to_string
			"W"+super
		end
		
		public_class_method :new
	end
end
