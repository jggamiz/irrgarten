#encoding: UTF-8

require_relative 'Dice'
require_relative 'Weapon'
require_relative 'Shield'
require_relative 'LabyrinthCharacter'

module Irrgarten
	class Player < LabyrinthCharacter
		@@MAX_WEAPONS=2
		@@MAX_SHIELDS=3
		@@INITIAL_HEALTH=10
		@@HITS2LOSE=3
		
		public
		def initialize(n,i,s)
			super("Player"+n.to_s,i,s,@@INITIAL_HEALTH)
			@number=n
			@consecutive_hits = 0 
			
			@weapons = Array.new(@@MAX_WEAPONS)
          	@shields = Array.new(@@MAX_SHIELDS)
			#@weapons=[]
			#@shields=[]
		end
		
		def copy(other)
			super(other)
     		@number = other.number
          	@consecutive_hits = other.consecutive_hits
          	
          	@weapons = other.weapons
          	@shields = other.shields
		end
		
		def resurrect()
			@weapons.clear # @weapons = [] // alternativa
			@shields.clear
			@health=@@INITIAL_HEALTH
			self.reset_hits()
		end
		
		attr_reader :number,:weapons,:shields,:consecutive_hits
		
		def move(direction,valid_moves)
			contained=false
			#for dir in valid_moves
			#	if (dir==direction) contained=true
			#end
			for i in 0...valid_moves.length
  				if valid_moves[i] == direction
    					contained = true
    					break  # Salir del bucle si se encuentra el elemento
  				end
			end
			
			if (valid_moves.length>0 && !contained)
				first_element = valid_moves[0]
				return first_element
			else
				return direction
			end
		end
		
		def attack()
			@strength+self.sum_weapons
		end
		
		def defend(received_attack)
			self.manage_hit(received_attack)
		end
		
		def receive_reward()
			w_reward = Dice.weapons_reward()
			s_reward = Dice.shields_reward()
			
			w_reward.times do
				wnew = new_weapon
				receive_weapon(wnew)
			end
			
			s_reward.times do
				snew = new_shield
				receive_shield(snew)
			end
			
			@health += Dice.health_reward
		end
		
		def to_string()
			str=super+ "  hits: "+@consecutive_hits.to_s
			str+="\nweapons: "
			@weapons.each do |wi|
                	next if wi.nil?
                	str += wi.to_string
                end
                
                str +="\nshields: "
                @shields.each do |si|
                	next if si.nil?
                	str += si.to_string
                end
                
                str+="\n"
                str
		end
		
		
		private
		def receive_weapon(w)

			@weapons.reverse_each do |wi|
               	next if wi.nil?
               	if wi.discard
               		@weapons.delete(wi)
               	end
			end

			if @weapons.length < @@MAX_WEAPONS
	               #@weapons.push(w)
	               @weapons << w
			end
=begin
			@weapons = @weapons.reject { |wi| wi.discard }
			@weapons.push(w) if @weapons.size < @@MAX_WEAPONS
=end		
		end

		def receive_shield(s)

			@shields.reverse_each do |si|
               	next if si.nil?
               	if si.discard
               		@shields.delete(si)
               	end
            	end

			if @shields.length < @@MAX_SHIELDS
 	              @shields << s
	          end
=begin
			@shields = @shields.reject { |si| si.discard }
			@shields.push(s) if @shields.size < @@MAX_SHIELDS
=end		
		end
		
		def new_weapon()
			w=Weapon.new(Dice.weapon_power(), Dice.uses_left())
			@weapons << w
			w
		end
		
		def new_shield()
			s=Shield.new(Dice.shield_power(), Dice.uses_left())
			@shields << s
			s
		end
		
		def sum_weapons()
			sum=0.0
			# 0.upto(weapons.length()) do
			@weapons.each do |wi|
				next if wi.nil?
				sum+=wi.attack()
			end
			sum	
		end
		
		def sum_shields()
			sum=0.0
			@shields.each do |si|
				next if si.nil?
				sum+=si.protect()
			end
			sum
		end
		
		def defensive_energy()
			@intelligence+self.sum_shields()
		end
		
		def manage_hit(received_attack)
			defense = defensive_energy()
			lose=true
			
			if (defense<received_attack)
				self.got_wounded()
				self.inc_consecutive_hits()
			else
				self.reset_hits()
			end
			
			if ((@consecutive_hits==@@HITS2LOSE) || self.dead())
				self.reset_hits()
				lose=true
			else
				lose=false
			end
			
			lose
		end
		
		def reset_hits()
			@consecutive_hits=0
		end
			
		def inc_consecutive_hits()
			@consecutive_hits+=1
		end
		
		public_class_method :new
	end
end
