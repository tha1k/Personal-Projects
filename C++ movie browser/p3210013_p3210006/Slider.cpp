#include "Slider.h"
#include "configure.h"
void Slider::update()
{
	graphics::MouseState mouse;
	graphics::getMouseState(mouse);
	float mx = graphics::windowToCanvasX(mouse.cur_pos_x);
	float my = graphics::windowToCanvasX(mouse.cur_pos_y);
	//printf("%f %f %f %f \n", mx, posx, my, posy);


	if ((mx>=80 && mx<=184)&& (my >= posy - 7.5f && my <= posy + 7.5f) && mouse.button_left_down) {//mouse.button_left_down
		
			this->posx = mx;
			if (this->posx <= 100) {
				this->posx = 100.0f;
				
			}
			else if (this->posx >= 164) {
				this->posx = 164.0f;
				

			}
			this->current_value = 1990 + (posx - 100) /2;
			//printf("here");
			
				//this->draw();

	}

}

void Slider::draw()
{
	graphics::Brush br;
	br.outline_opacity = 0.2f;
	br.fill_color[0] = 1.f;
	br.fill_color[1] = 1.f;
	br.fill_color[2] = 1.0f;
	graphics::drawLine(Canvas_width * 0.1, Canvas_height * 0.25, Canvas_width * 0.1+64, Canvas_height * 0.25,br);
	br.fill_color[0] = 1.f;
	br.fill_color[1] = 0.6f;
	br.fill_color[2] = 0.8f;
	br.outline_color[0] = 0.0f;
	br.outline_color[1] = 0.0f;
	br.outline_color[2] = 0.0f;
	graphics::drawRect(posx, posy, 10, 20, br);
	graphics::setFont(std::string(ASSETS_p) + "font.ttf");
	//graphics::drawText(100, 250,12, "posx"+std::to_string( this->posx), br);
	graphics::drawText(100, 165, 12, "year: " + std::to_string(this->current_value), br);
	//graphics::drawRect(this->posx, this->posy, 10, 10, br);
}

void Slider::init()
{
}

Slider::Slider()
{
	current_value = 1990;
	posx = Canvas_width * 0.1;
	posy = Canvas_height * 0.25;
}

Slider::~Slider()
{
}