#include "graphics.h"
#include "Widget.h"
#include "configure.h"
// The custom callback function that the library calls 
// to check for and set the current application state.
void update(float ms)
{
    Widget* w = reinterpret_cast<Widget*>(graphics::getUserData());
    w->update();
 
}

// The window content drawing function.
void draw()
{
    Widget* w = reinterpret_cast<Widget*>(graphics::getUserData());
    w->draw();
    
}

int main()
{
    

    graphics::createWindow(WIDTH, HEIGHT, "3210013_3210006");
    Widget mywidg;
    //mywidg.init();
    graphics::setUserData(&mywidg);

    graphics::setDrawFunction(draw);
    graphics::setUpdateFunction(update);

    graphics::setCanvasSize(Canvas_width, Canvas_height);
    graphics::setCanvasScaleMode(graphics::CANVAS_SCALE_FIT);

   //mywidg.init();
    graphics::startMessageLoop();

    return 0;
}