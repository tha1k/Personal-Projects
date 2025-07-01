#pragma once
#include "Widget_item.h"
#include <string>
#include "graphics.h"
using namespace std;
class Moovie : public Widget_item {
	string title;
	string director;
	string Genre;
	string protag;
	unsigned int year;
	float posx, posy; 
	static int a ;//must be static variable 

public:
	string GetTitle() { return this->title; }
	string Get_g() { return this->Genre; }
	unsigned int Get_year() { return this->year; }
	Moovie(string title,string genre,string director, string protag, unsigned int y);
	//void SetX(float a) { posx = a; }
	//void SetY(float b) { posy = b; }
	void update() override;
	void draw() override;
	void init() override;
	bool GET_GENRE(const int& i) {
		if (i == 0) { if ((this->Get_g() == "Drama")==1)return true; else false; }
		if (i == 1) { if ((this->Get_g() == "Action")==1)return true; else false; }
		if (i == 2) { if ((this->Get_g() == "Comedy")==1)return true; else false; }
		if (i == 3) { if ((this->Get_g() == "Thriller")==1)return true; else false; }
		if (i == 4) { if ((this->Get_g() == "Animation")==1)return true; else false; }
		else return false;
	}
};//set get methods for sorting and searching of moovie object private fields