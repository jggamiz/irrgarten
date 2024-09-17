#encoding: UTF-8

require_relative 'Dice'
require_relative 'CombatElement'

module Irrgarten
	class Shield < CombatElement
		def initialize(protection,uses)
			super(protection,uses)
		end	
		
		public
		def protect()
			produce_effect()
		end
		
		def to_string
			"S"+super
		end
		
		public_class_method :new
	end
end
