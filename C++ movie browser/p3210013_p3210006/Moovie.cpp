#include "Moovie.h"
#include "configure.h"
//#include <algorithm>
//#include <iostream>
#include <string.h>
Moovie::Moovie(string title, string genre, string director, string protag, unsigned int y)
{
    
    title = title;
    Genre = genre;
    director = director;
    protag = protag;
    year = y;
    if (a <5) {
     posx = Canvas_width/3.2f+a* Canvas_width / 6.85f;
     posy = Canvas_height * 0.24f; }
    else if (a >=5 && a<=10) {
    posx = Canvas_width / 3.2f + (a-5) * Canvas_width / 6.85f;//position relative to other moovies 
    posy = Canvas_height * 0.7f; }
    a++;
    if (a == 10) { a = 0; }//object pointers might be deleted to be filter with new parameters.Relative posotion reset 
}
int Moovie::a = 0;
void Moovie::update()
{
}

void Moovie::draw()
{
    graphics::Brush brush;
    //cout << this->GetTitle();
   // printf("%s \n", this->GetTitle());
    /*const char* key = (this->title).c_str();
    const char* data = "Finding Nemo";
    printf("%f%",(float)this->year);
    if (strcmp(key,data) != 0)*/
    if(this->year==2003) {
        brush.texture = std::string(ASSETS_p) + "Finding Nemo cover.png";
    }
    else if(this->year==2004) {
        brush.texture = std::string(ASSETS_p) + "Million Dollar Baby cover.png";
    }
    else if (this->year == 1990) {
        brush.texture = std::string(ASSETS_p) + "Rocky V cover.png";
    }
    else if (this->year == 1993) {
        brush.texture = std::string(ASSETS_p) + "Tombstone cover.png";
    }
    else if (this->year == 1996) {
        brush.texture = std::string(ASSETS_p) + "Space Jam cover.png";
    }
    else if (this->year == 2008) {
        brush.texture = std::string(ASSETS_p) + "The Dark Knight cover.png";
    }
    else if (this->year == 2019) {
        brush.texture = std::string(ASSETS_p) + "Parasite cover.png";
    }
    else if (this->year == 2002) {
        brush.texture = std::string(ASSETS_p) + "My Big Fat Greek Wedding cover.png";
    }
    else if (this->year == 2018) {
        brush.texture = std::string(ASSETS_p) + "Avengers Infinity War cover.png";
    }
    else if (this->year == 2022) {
        brush.texture = std::string(ASSETS_p) + "Purple Hearts cover.png";
    }
    brush.outline_opacity = 0.5f;
    graphics::drawRect(this->posx, this->posy, Canvas_width / 7, Canvas_height / 2.2f, brush);
    /*brush.texture = std::string(ASSETS_p) + "black.png";
    graphics::drawRect(Canvas_width * 5 / 6, Canvas_height / 2, Canvas_width / 3, Canvas_height, brush);*/
    graphics::setFont(std::string(ASSETS_p) + "font.ttf");
    brush.fill_color[0] = 1.f;
    brush.fill_color[1] = 0.0f;
    brush.fill_color[2] = 0.0f;
    brush.fill_opacity = 0.7f;
    graphics::drawText(posx, posy, 12, "Made in:" + std::to_string(this->year), brush);
    /*graphics::drawText(700, 0, 12, "Title: " + this->title, brush);
    graphics::drawText(700, 100, 12, "Made in:            " + std::to_string(this->year), brush);
    graphics::drawText(700, 200, 12, "Director: " + this->director, brush);
    graphics::drawText(700, 300, 12, "Protagonist: " + this->protag, brush);
    graphics::drawText(700, 400, 12, "Genre:" + this->Genre, brush);*/

}

void Moovie::init()
{
}