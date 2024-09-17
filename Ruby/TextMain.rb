#encoding: UTF-8

require_relative 'Game'
require_relative 'Controller'
require_relative 'TextUI'

module Irrgarten
	module TextMain
		class TextMain
			def initialize
			end
			
			def main
				game = Game.new(2)
				view = UI::TextUI.new()
				controller = Control::Controller.new(game,view)
			
				controller.play
			end
		end
	end
end

Irrgarten::TextMain::TextMain.new.main
#juego=TextMain.new
#juego.main
