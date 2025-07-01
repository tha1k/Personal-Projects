#pragma once
#include "button.h"
#include "Moovie.h"
#include "Slider.h"
#include <list>//delete and append moovie pointers
//#include <stack>//lifo property

class Widget {
	vector<Button*> b;
	bool active[5] = { false,false,false,false,false };//each slot represents the bool var selected of each genre button 
	Slider* sl1;
	enum widget_state { INIT,NO_params,param_S };
	Button* last_act = nullptr;
	//string Genres[5] = { "Drama","Action","Comedy","Thriller","Animation" };
	bool initialized = false;//check in widget:: updates
	//std::stack<Button*> check;
	bool moo_init = false;
	unsigned int m_year;
	//vector of moovies
	widget_state st_W = INIT;
	std::list<Moovie*> moo;
	/*Moovie* a=new Moovie(9)*/
public:
	void update();
	void draw();
	void init();//initialization of pointer Widget items
	Widget();
	~Widget();
};