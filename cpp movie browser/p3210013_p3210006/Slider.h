#pragma once
#include "Widget_item.h"
#include "graphics.h"

class Slider : public Widget_item {
	float posx, posy;
	int current_value;
	bool active= false; 
public:
	void update() override;
	void draw() override;
	void init() override;
	unsigned int Get_curr() { return (unsigned int)current_value; }
	Slider();
	~Slider();
};