#include "Widget.h"
#include "graphics.h"
#include "Moovie.h"
#include "configure.h"
void Widget::update()
{
	/*if (st_W == INIT) {
		return;
	}
	if (st_W == load) {
		init();
		st_W = idle; return;
	}*/
	if (graphics::getGlobalTime() > 500 /* && !b*/) {//wrong statement continual initialization of button
		/* && !exist variable(boolean)
		initialise and construct*/
		//initialize buttons 
		for (auto i : b) { i->update(); }
		if (sl1) {
			sl1->update();

		}

		if (st_W == INIT || st_W == NO_params) {
			init();

		}

		//iterate through buttons using u_id of buttons and setting them as the values of the respective if state other than no params
		for (auto i : b) {
			unsigned int j = i->Get_id();
			active[j] = i->GET_STATE();//uids start from 1 to 5

		}/*int count = 0;
		for (int i = 0; i < 5; i++) {
			if (active[i]) { count++; }
		}if (sl1 != nullptr) {
			if (count > 0 || sl1->Get_curr() <= 1990) { st_W = NO_params; }
			else { st_W = param_S; }




		}*/
		/*int count = 0;
		for (int i = 0; i < 5; i++) {
			if (active[i]) { count++; }
		}
		if (sl1) {
			if (count > 0 || sl1->Get_curr() != 1990) { st_W = param_S; }
			else { st_W = NO_params; }
		}

		if (st_W == param_S) {
			Slider* s = sl1;
			moo.remove_if([s](Moovie* m)->bool {return m->Get_year() < s->Get_curr(); });
		}*/
		/*if (sl1) {
			Slider* s = sl1;
			moo.remove_if([s](Moovie* m)->bool {return m->Get_year() < s->Get_curr();  });
		}*/ //deletes moovies irreversibly

		if (sl1) {
			//if (m_year > sl1->Get_curr()) {
			Slider* s = sl1;
			//m_year = s->Get_curr();
			moo.remove_if([s](Moovie* m)->bool {return m->Get_year() < s->Get_curr();  });
			//m_year = s->Get_curr();


		}
		if (sl1) {
			if (m_year - sl1->Get_curr() != 0) {
				//printf("here");
				for (auto it : moo) { delete it; }
				moo.clear();
				moo_init = false;
				init();
				Slider* s = sl1;
				moo.remove_if([s](Moovie* m)->bool {return m->Get_year() < s->Get_curr();  });
				//m_year = s->Get_curr();
			}

		}
		
		//deletes older moovies based on slider
		/**/for (int i = 0; i < 5; i++) {//selects one genre 
			if (active[i]) {
				int j = i;
				for (auto iter : b) { if (iter->Get_id() == j)last_act = iter; }
				moo.remove_if([j](Moovie* m)->bool
				{if (j == 0) { if ((m->Get_g() == "Drama") == 1) { return false; } else true; }
				if (j == 1) { if ((m->Get_g() == "Action") == 1)return false; else true; }
				if (j == 2) { if ((m->Get_g() == "Comedy") == 1)return false; else true; }
				if (j == 3) { if ((m->Get_g() == "Thriller") == 1)return false; else true; }
				if (j == 4) { if ((m->Get_g() == "Animation") == 1)return false; else true; }
				else true;
					});
				
			}
			



		}//kane stoiba pou na kanei append ta buttons kai kanei thn idia diadiakasia Set_select(false) se ayta 
		
		for (auto deact : b) {
			//check.push(deact);
			if (deact != last_act)deact->Set_select(false);

		}//disables previous button if next Genre is selected
		
		/*for (list<Button*>::reverse_iterator it = b.rbegin(); it != b.rend(); ++it) {
			if ((*it) != last_act) { (*it)->Set_select(false); }
		}
		for (auto deact : b) { if (deact != last_act)deact->Set_select(false);
		}//disables previous button if next Genre is selected*/
		//list<Button*>::reverse_iterator revit;
		//for (revit = b.rbegin(); revit != b.rend(); revit++){ if (*revit == last_act) (*revit)->Set_select(false); }//disable next button if previous is selected
		//printf("%S\n", selected_butt);
		
		//printf("%d %d %d %d %d\n", active[0], active[1], active[2], active[3], active[4]);
		/*string Genres[5] = {"Drama","Action","Comedy","Thriller","Animation"};
		for (int i = 0; i < 5; i++) {
			for (auto it : moo) { delete it; }
			moo.clear();
			moo_init = false;
			init();
			if (!active[i]) {
				printf("%s", Genres[i]);
				string X[5]= { "Drama","Action","Comedy","Thriller","Animation" };
				int j = i;
				moo.remove_if([j,X](Moovie* m)->bool {if (m->Get_g() == X[j])return false; else true; });
			}
		}
		printf("\n");*/

		/*int count_FIELDS = 0;
		vector<int> fields;
		for (int i = 0; i < 5; i++) {
			if (active[i]) {
				//fields.push_back(i);
				count_FIELDS++;

			}
		}
		if (count_FIELDS == 2) {
			for (auto it : moo) { delete it; }
			moo.clear();
			moo_init = false;
			init();
			for (int i = 0; i < 5; i++) {
				if (active[i])
					fields.push_back(i);

				string Genres[5] = { "Drama","Action","Comedy","Thriller","Animation" };
				//const string a; string b;
				if (fields[0] >= 0 && fields[0] < 5 && fields[1] < 5 && fields[1] >= 0) {

					string a = Genres[fields[0]];
					string b = Genres[fields[1]];

					//std::strcpy(a, (char *)Genres[fields[0]]);

					moo.remove_if([a, b](Moovie* m)->bool {if (((m->Get_g() == a) == 1) || ((m->Get_g() == b) == 1)) return true; else return false; });

				}
				//printf("%d %d", fields[0], fields[1]);


			}
		}if (count_FIELDS == 3) {
				for (auto it : moo) { delete it; }
				moo.clear();
				moo_init = false;
				init();
				for (int i = 0; i < 5; i++) {
					if (active[i])
						fields.push_back(i);

					string Genres[5] = { "Drama","Action","Comedy","Thriller","Animation" };
					//const string a; string b;
					if (fields[0] > 0 && fields[0] < 5 && fields[1] < 5 && fields[1]>0&& fields[2] > 0 && fields[2] < 5) {
						string a = Genres[fields[0]];
						string b = Genres[fields[1]];
						string c = Genres[fields[2]];

						//std::strcpy(a, (char *)Genres[fields[0]]);

						moo.remove_if([a, b,c](Moovie* m)->bool {if (((m->Get_g() == a) == 1) || ((m->Get_g() == b) == 1)|| ((m->Get_g() == c) == 1)) return true; else return false; });
					}
				}
			}if (count_FIELDS == 4) {
				for (auto it : moo) { delete it; }
				moo.clear();
				moo_init = false;
				init();
				for (int i = 0; i < 5; i++) {
					if (active[i])
						fields.push_back(i);

					string Genres[5] = { "Drama","Action","Comedy","Thriller","Animation" };
					//const string a; string b;
					if (fields[0] > 0 && fields[0] < 5 && fields[1] < 5 && fields[1]>0 && fields[2] > 0 && fields[2] < 5 && fields[3] > 0 && fields[3] < 5) {
						string a = Genres[fields[0]];
						string b = Genres[fields[1]];
						string c = Genres[fields[2]];
						string d = Genres[fields[3]];
						//std::strcpy(a, (char *)Genres[fields[0]]);

						moo.remove_if([a, b, c,d](Moovie* m)->bool {if (((m->Get_g() == a) == 1) || ((m->Get_g() == b) == 1) || ((m->Get_g() == c) == 1)|| ((m->Get_g() == d) == 1)) return true; else return false; });
					}
				}
			}
			else {
				for (auto it : moo) { delete it; }
				moo.clear();
				moo_init = false;
				init();
			}*/

				//printf("%d %d", fields[0], fields[1]);


		}
			//moo.remove_if([Genres](Moovie* m)->bool {});

			//moo.remove_if([](Moovie* m){return m->Get_g() ==  })

		/*else if(count_FIELDS==3){
		}
		else if(count_FIELDS==4) {

		}else{
			for (auto it : moo) { delete it; }
			moo.clear();
			moo_init = false;
			init();
		}*/

}

	
	


void Widget::draw()
{	
	//if (st_W == INIT) { return; }
		graphics::Brush br;
		br.texture = std::string(ASSETS_p) + "background.png";//background png
		br.outline_opacity = 0.0f;
		//background
		graphics::drawRect(Canvas_width / 2, Canvas_height / 2, Canvas_width, Canvas_width, br);
		//draw_widget_items
			//draw_buttons
		for (auto i : b) {

			i->draw();
		}//button.draw()
		//Moovie container
		
		for (auto i : moo) {
			
			i->draw();
		}
		if (sl1) sl1->draw();
		//SLIDERS
		

}

void Widget::init()
{
	Moovie* a1 = new Moovie("Purple Hearts", "Drama", "Elizabeth Allen Rosenbaum", "Sofia Carson", 2022);
	//moo.push_front(a1);
	Moovie* a2 = new Moovie("Avengers Infinity War", "Action", "Anthony Russo", "Robert Downey Jr.", 2018);
	//moo.push_front(a2);

	Moovie* a3 = new Moovie("My big fat greek wedding", "Comedy", "Joel Zwick", "Nia Vardalos", 2002);
	//moo.push_front(a3);
	Moovie* a4 = new Moovie("Parasite", "Thriller", "Bong Joon Ho", "Song Kang-ho", 2019);
	//moo.push_front(a4);
	Moovie* a5 = new Moovie("The Dark Knight", "Action", "Christopher Nolan", "Christian Bale", 2008);
	//moo.push_front(a5);
	Moovie* a6 = new Moovie("Space Jam", "Animation", "Joe Pytka", "Micheal Jordan", 1996);
	//moo.push_front(a6);
	Moovie* a7 = new Moovie("Tombstone", "Drama", "George P. Cosmatos", "Kurt Russell", 1993);
	//moo.push_front(a7);
	Moovie* a8 = new Moovie("Rocky V ", "Drama", "John G. Avildsen", "Sylvester Stallone", 1990);
	//moo.push_front(a8);
	Moovie* a9 = new Moovie("Million Dollar Baby", "Drama", "Clint Eastwood", "Hilary Swank", 2004);
	//moo.push_front(a9);
	Moovie* a0 = new Moovie("Finding Nemo", "Animation", "Andrew Stanton", "Albert Brooks", 2003);
	//moo.push_front(a0);
	if (!initialized) {
		initialized = true;//initial initialization
		//Button* b_not = new Button();
		Button* b0 = new Button();
		b.push_back(b0);
		Button* b1 = new Button();
		b.push_back(b1);
		Button* b2 = new Button();
		b.push_back(b2);
		Button* b3 = new Button();
		b.push_back(b3);
		Button* b4 = new Button();
		b.push_back(b4);
		
		sl1 = new Slider();
		Moovie* a1 = new Moovie("Purple Hearts", "Drama", "Elizabeth Allen Rosenbaum", "Sofia Carson", 2022);
		//moo.push_front(a1);
		Moovie* a2 = new Moovie("Avengers Infinity War", "Action", "Anthony Russo", "Robert Downey Jr.", 2018);
		//moo.push_front(a2);

		Moovie* a3 = new Moovie("My big fat greek wedding", "Comedy", "Joel Zwick", "Nia Vardalos", 2002);
		//moo.push_front(a3);
		Moovie* a4 = new Moovie("Parasite", "Thriller", "Bong Joon Ho", "Song Kang-ho", 2019);
		//moo.push_front(a4);
		Moovie* a5 = new Moovie("The Dark Knight", "Action", "Christopher Nolan", "Christian Bale", 2008);
		//moo.push_front(a5);
		Moovie* a6 = new Moovie("Space Jam", "Animation", "Joe Pytka", "Micheal Jordan", 1996);
		//moo.push_front(a6);
		Moovie* a7 = new Moovie("Tombstone", "Drama", "George P. Cosmatos", "Kurt Russell", 1993);
		//moo.push_front(a7);
		Moovie* a8 = new Moovie("Rocky V ", "Drama", "John G. Avildsen", "Sylvester Stallone", 1990);
		//moo.push_front(a8);
		Moovie* a9 = new Moovie("Million Dollar Baby", "Drama", "Clint Eastwood", "Hilary Swank", 2004);
		//moo.push_front(a9);
		Moovie* a0 = new Moovie("Finding Nemo", "Animation", "Andrew Stanton", "Albert Brooks", 2003);
		//moo.push_front(a0);
		if (!moo_init) {
			moo.push_front(a9);
			moo.push_front(a8);
			moo.push_front(a7);
			moo.push_front(a6);
			moo.push_front(a5);
			moo.push_front(a4);
			moo.push_front(a3);
			moo.push_front(a2);
			moo.push_front(a1);
			moo.push_front(a0);
			moo_init = true;
		}
	}if (a0 && a1 && a2 && a3 && a4 && a5 && a6 && a7 && a8 && a9 && !moo_init) {
		
		
			moo.push_front(a9);//reintiliase list to filter with new params
			moo.push_front(a8);
			moo.push_front(a7);
			moo.push_front(a6);
			moo.push_front(a5);
			moo.push_front(a4);
			moo.push_front(a3);
			moo.push_front(a2);
			moo.push_front(a1);
			moo.push_front(a0);
			moo_init = true;
		
	}
	/*if (!moo_init) {
		//sl1->update();
		moo_init = true;
		Moovie* a1 = new Moovie("Purple Hearts", "Drama", "Elizabeth Allen Rosenbaum", "Sofia Carson", 2022);
		moo.push_front(a1);
		Moovie* a2 = new Moovie("Avengers Infinity War", "Action", "Anthony Russo", "Robert Downey Jr.", 2018);
		moo.push_front(a2);

		Moovie* a3 = new Moovie("My big fat greek wedding", "Comedy", "Joel Zwick", "Nia Vardalos", 2002);
		moo.push_front(a3);
		Moovie* a4 = new Moovie("Parasite", "Thriller", "Bong Joon Ho", "Song Kang-ho", 2019);
		moo.push_front(a4);
		Moovie* a5 = new Moovie("The Dark Knight", "Action", "Christopher Nolan", "Christian Bale", 2008);
		moo.push_front(a5);
		Moovie* a6 = new Moovie("Space Jam", "Animation", "Joe Pytka", "Micheal Jordan", 1996);
		moo.push_front(a6);
		Moovie* a7 = new Moovie("Tombstone", "Drama", "George P. Cosmatos", "Kurt Russell", 1993);
		moo.push_front(a7);
		Moovie* a8 = new Moovie("Rocky V ", "Drama", "John G. Avildsen", "Sylvester Stallone", 1990);
		moo.push_front(a8);
		Moovie* a9 = new Moovie("Million Dollar Baby", "Drama", "Clint Eastwood", "Hilary Swank", 2004);
		moo.push_front(a9);
		Moovie* a0 = new Moovie("Finding Nemo", "Animation", "Andrew Stanton", "Albert Brooks", 2003);
		moo.push_front(a0);
	}*/

		
	
	//Widget::init();
	//if st_w== params_S iterate through moo and removeif( moo.getYear<sl1.Get_curr()&&(moo.get_Genre!=))
	
	
}

Widget::Widget() {
	
}

Widget::~Widget()
{
	//destruct Widget_item vectors
		//button 
	for (auto iter : moo) {
		delete iter;
		iter = nullptr;
	}moo.clear();
	for (auto iter : b) {
		delete iter;
		iter = nullptr;
	}b.clear();
	//delete[] fields;
	if (sl1) { delete sl1; sl1 = nullptr; }
	//if (last_act) { delete last_act;  }
	
}
/*active[0]=Drama /// active[1]=Action /// active[2]=Comedy /// active[3]=Thriller ///active[4]=Animation*/