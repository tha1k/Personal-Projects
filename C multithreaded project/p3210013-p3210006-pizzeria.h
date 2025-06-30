#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
//variable declaration
int Ncook=2;
int Noven=15;
int Npacker=2;
int Ndeliverer=10;
int Torderlow=1;//time between orders
int Torderhigh=3;
int Norderlow=1;//# pizzas
int Norderhigh=5;
int Pplain=60;//probability of plain pizza
int Tpaymentlow=1;
int Tpaymenthigh=3;//payment processing time 
int Pfail=10;
int Cplain=10;
int Cspecial=12;
int Tprep=1;//cook's time 
int Tbake=10;//oven time
int Tpack=1;//packers time 
int Tdellow=5;//delivers' time range 
int Tdelhigh=15;
//other varibales for output
int successfull_orders = 0;
int unsuccessfull_orders = 0;
int revenue = 0;
int globalclocktime = 0;
int number_of_plain_pizzas_sold = 0;
int number_of_special_pizzas_sold = 0;
int total_xronos_e3yphrethshs = 0;
int megistos_xronos_e3yphrethshs = 0;
int total_xronos_krywmatos = 0;
int megistos_xronos_krywmatos = 0;
//command line args
int Ncust;
unsigned int seed;
//mutexes
pthread_mutex_t revenue_mutex;//gia lefta kai synolikes pizzes sold
pthread_mutex_t cook_count;
pthread_mutex_t packers_count;
pthread_mutex_t deliverer_count;
pthread_mutex_t oven_lock;
pthread_mutex_t paraggeliaMutex;
pthread_mutex_t num_of_ordersMutex;
pthread_mutex_t xronos_e3yphrethshsMutex;//kai krywmatos
//cond_variables 
pthread_cond_t ovens=PTHREAD_COND_INITIALIZER; 
pthread_cond_t no_cooks=PTHREAD_COND_INITIALIZER;
pthread_cond_t delivers;
pthread_cond_t packers;
