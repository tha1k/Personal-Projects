#pragma once
#include "Widget_item.h"
#include "graphics.h"
class Button : public Widget_item {
	float posx;float  posy;
	static unsigned int id;
	bool selected = false;
	unsigned int pressed = 1;
	unsigned int u_id;//unique id of button is equal to the value of the static variable at that momment
//
public:
	void update() override;
	bool GET_STATE() { return selected; }
	void draw() override;
	void init() override;
	void Set_select(bool f) { selected = f; }
	unsigned int Get_id() { return u_id; }//used to update the boolean array in Widget as filter 
	Button();//3210013
};
//may need boolean state variables and costructor
//may need uid as static variable 