#include "button.h"
#include "configure.h"
void Button::update()
{//checkmouse state
	graphics::MouseState mouse;
	graphics::getMouseState(mouse);
	float mx = graphics::windowToCanvasX(mouse.cur_pos_x);
	float my = graphics::windowToCanvasX(mouse.cur_pos_y);
	if (mouse.button_left_pressed && dist(this->posx, this->posy, mx, my) < S_b_dim/1.5f /*&& uid = 10*/)//the uid instance variable specifies the particualr button. Thus its use
	{
		pressed++;
		if (pressed % 2 == 0) {
			selected = true;//pressed once so its active 
		}
		else selected = false;//pressed an even amount of times to be deactivated
	
	}


}
unsigned int Button::id = 0;//3210013
void Button::draw()
{
	graphics::Brush  B_br;
	//draw button 
	graphics::setFont(std::string(ASSETS_p) + "font.ttf");
	B_br.outline_color[0] = 0.98f;
	B_br.outline_color[1] = 0.63f;
	B_br.outline_color[2] = 0.1f;
	if (!selected) {
		B_br.outline_opacity = 0.0f;
		B_br.texture = std::string(ASSETS_p) + "button_texture.png";
		graphics::drawRect(posx, posy, S_b_dim, S_b_dim, B_br);
		graphics::setFont(std::string(ASSETS_p) + "font.ttf");
		if (u_id == 0)
			graphics::drawText(posx + S_b_dim, posy + 5, 12, "Drama", B_br);
		else if (u_id == 1)
			graphics::drawText(posx + S_b_dim, posy + 5, 12, "Action", B_br);
		else if (u_id == 2)
			graphics::drawText(posx + S_b_dim, posy + 5, 12, "Comdedy", B_br);
		else if (u_id == 3)
			graphics::drawText(posx + S_b_dim, posy + 5, 12, "Thriller/Horror", B_br);
		else if (u_id=4)
			graphics::drawText(posx + S_b_dim, posy + 5, 12, "Animation", B_br);
	}
	if (selected) {
		B_br.outline_opacity = 1.0f;
		B_br.texture = std::string(ASSETS_p) + "button_texture.png";
		graphics::drawRect(posx, posy, S_b_dim, S_b_dim, B_br);
		graphics::setFont(std::string(ASSETS_p) + "font.ttf");
		B_br.fill_color[0] = 0.98f;
		B_br.fill_color[1] = 0.62f;
		B_br.fill_color[2] = 0.1f;
		
		if (u_id == 0)
			graphics::drawText(posx + S_b_dim, posy + 5, 14, "Drama", B_br);
		else if (u_id == 1)
			graphics::drawText(posx + S_b_dim, posy + 5, 14, "Action", B_br);
		else if (u_id == 2)
			graphics::drawText(posx + S_b_dim, posy + 5, 14, "Comedy", B_br);
		else if (u_id == 3)
			graphics::drawText(posx + S_b_dim, posy + 5, 14, "Thriller/Horror", B_br);
		else
			graphics::drawText(posx + S_b_dim, posy + 5, 14, "Animation", B_br);
	}
	
}
Button::Button() {

	u_id = id;
	posx = 110;
	posy = 180+2*u_id*S_b_dim;
	id++;//position relative to other buttons

}
void Button::init()
{
}
