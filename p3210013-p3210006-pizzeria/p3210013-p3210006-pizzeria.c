#include "p3210013-p3210006-pizzeria.h"

void* order_func(void* iod){
	int rc;
	int* id = iod;//order id
    int num_of_pizzas;//total number of pizzas
    int plain = 0;//num of plain per customer
    int special = 0;//num of special per customer
	struct timespec arxhT, telosPaketarismatos, telos_delivery, telosPshsimatos;
	
	//mutex for total time
	rc=pthread_mutex_lock(&paraggeliaMutex);
	if (rc != 0) {
    		printf("ERROR: return code from pthread_lock() is %d\n", rc);
       		exit(-1);
       	}
	clock_gettime(CLOCK_REALTIME, &arxhT);
	sleep((rand_r(&seed) % (Torderhigh - Torderlow + 1)) + Torderlow);
	rc=pthread_mutex_unlock(&paraggeliaMutex);
	if (rc != 0) {
    		printf("ERROR: return code from pthread_unlock() is %d\n", rc);
       		exit(-1);
       	}
    
    int total_cost_of_order = 0;


	//number of total pizzas per order, plain and special
	num_of_pizzas= (rand_r(&seed) % (Norderhigh - Norderlow + 1)) + Norderlow;
    for(int i=0;i<num_of_pizzas;i++){
        int eidos_pizzas = rand_r(&seed)%100+1;
        if(eidos_pizzas<=Pplain){
            total_cost_of_order += Cplain;
            ++plain;
        }else{
        	++special;
            total_cost_of_order += Cspecial;
        }
    }
    
	//waiting for payment
	sleep(rand_r(&seed) % (Tpaymenthigh - Tpaymentlow + 1) + Tpaymentlow);

	int pi8anothta_fail_h_oxi = rand_r(&seed)%100+1;//payment fail probability
	
	//mutex for total number of orders
	rc=pthread_mutex_lock(&num_of_ordersMutex);
	if (rc != 0) {
    		printf("ERROR: return code from pthread_lock() is %d\n", rc);
       		exit(-1);
       	}
	if(pi8anothta_fail_h_oxi <= Pfail){
		printf("H KARTA DEN EGINE DEKTH, H PARAGGELIA ME ARI8MO %d AKYRW8HKE\n", *id);
		unsuccessfull_orders++;
		pthread_mutex_unlock(&num_of_ordersMutex);
		pthread_exit(NULL);
	}
	else{
		printf("H PARAGGELIA ME ARI8MO %d EGINE DEKTH\n", *id);
		successfull_orders++;
	}
	rc=pthread_mutex_unlock(&num_of_ordersMutex);
	if (rc != 0) {
    		printf("ERROR: return code from pthread_unlock() is %d\n", rc);
       		exit(-1);
       	}


	//mutex for total revenue and total pizzas sold, plain or special
	rc=pthread_mutex_lock(&revenue_mutex);
    if (rc != 0) {
    		printf("ERROR: return code from pthread_lock() is %d\n", rc);
       		exit(-1);
       	}
	revenue += total_cost_of_order;
	number_of_plain_pizzas_sold += plain;
	number_of_special_pizzas_sold += special;

    rc=pthread_mutex_unlock(&revenue_mutex);
	if (rc != 0) {
    		printf("ERROR: return code from pthread_unlock() is %d\n", rc);
       		exit(-1);
       	}

	//mutex for cooks so we assign the order to a cook
	rc=pthread_mutex_lock(&cook_count);
	if (rc != 0) {
    		printf("ERROR: return code from pthread_lock() is %d\n", rc);
       		exit(-1);
       	}

    while(Ncook<=0){
        pthread_cond_wait(&no_cooks, &cook_count);
    }
	--Ncook;
//ρεπορτ έως εδώ 
	rc=pthread_mutex_unlock(&cook_count);
	if (rc != 0) {
    		printf("ERROR: return code from pthread_unlock() is %d\n", rc);
       		exit(-1);
       	}

	//waiting for cook to prepare the pizzas
	sleep(Tprep*num_of_pizzas);

	//mutex for ovens so we occupy ovens for each pizza
	rc=pthread_mutex_lock(&oven_lock);
	if (rc != 0) {
    		printf("ERROR: return code from pthread_lock() is %d\n", rc);
       		exit(-1);
       	}

	while(Noven < num_of_pizzas){
		rc=pthread_cond_wait(&ovens, &oven_lock);
		if (rc != 0) {
    		printf("ERROR: return code from pthread_cond_wait() is %d\n", rc);
       		exit(-1);
       	}
	}
	//εδω το ψησιμο 
	Noven -= num_of_pizzas;
	rc=pthread_mutex_unlock(&oven_lock);
	if (rc != 0) {
    		printf("ERROR: return code from pthread_unlock() is %d\n", rc);
       		exit(-1);
       	}

	//mutex for cooks so we make the cook available again
	rc=pthread_mutex_lock(&cook_count);
	if (rc != 0) {
    		printf("ERROR: return code from pthread_lock() is %d\n", rc);
       		exit(-1);
       	}	
	Ncook++;
	rc=pthread_mutex_unlock(&cook_count);
	if (rc != 0) {
    		printf("ERROR: return code from pthread_unlock() is %d\n", rc);
       		exit(-1);
       	}
	rc=pthread_cond_signal(&no_cooks);
	if (rc != 0) {
    		printf("ERROR: return code from pthread_signal() is %d\n", rc);
       		exit(-1);
       	}

	//waiting for the pizzas to bake
	sleep(Tbake);
	
	//end of baking time
	clock_gettime(CLOCK_REALTIME, &telosPshsimatos);

	//mutex for packers so we assign the order to a packer
	rc=pthread_mutex_lock(&packers_count);
	if (rc != 0) {
    		printf("ERROR: return code from pthread_lock() is %d\n", rc);
       		exit(-1);
       	}
	while(Npacker == 0){
		rc=pthread_cond_wait(&packers, &packers_count);
		if (rc != 0) {
    		printf("ERROR: return code from pthread_cond_wait() is %d\n", rc);
       		exit(-1);
       	}
	}
	Npacker--;
	pthread_mutex_unlock(&packers_count);

	//waiting for pizzas to be packed
	sleep(num_of_pizzas * Tpack);

	//end of packing
	clock_gettime(CLOCK_REALTIME, &telosPaketarismatos);
	int xronos_apo_efmanish_pelath_mexri_telos_paketarismatos = (telosPaketarismatos.tv_sec-arxhT.tv_sec);
	printf("H PARAGGELIA ME ARI8MO %d ETOIMASTHKE SE %d LEPTA\n", *id, xronos_apo_efmanish_pelath_mexri_telos_paketarismatos);

	//mutex for packers so we make the packer available again
	rc=pthread_mutex_lock(&packers_count);
	if (rc != 0) {
    		printf("ERROR: return code from pthread_lock() is %d\n", rc);
       		exit(-1);
       	}
	//mutex for ovens so we make the ovens available again
	rc=pthread_mutex_lock(&oven_lock);
	if (rc != 0) {
    		printf("ERROR: return code from pthread_lock() is %d\n", rc);
       		exit(-1);
       	}
	Npacker++;
	Noven+= num_of_pizzas;
	rc=pthread_mutex_unlock(&packers_count);
	if (rc != 0) {
    		printf("ERROR: return code from pthread_unlock() is %d\n", rc);
       		exit(-1);
       	}
	rc=pthread_mutex_unlock(&oven_lock);
	if (rc != 0) {
    		printf("ERROR: return code from pthread_unlock() is %d\n", rc);
       		exit(-1);
       	}
	rc=pthread_cond_signal(&ovens);
	if (rc != 0) {
    		printf("ERROR: return code from pthread_cond_signal() is %d\n", rc);
       		exit(-1);
       	}
	rc=pthread_cond_signal(&packers);
	if (rc != 0) {
    		printf("ERROR: return code from pthread_cond_signal() is %d\n", rc);
       		exit(-1);
       	}

	//mutex for delivery so we assign an order to a delivery man
	rc=pthread_mutex_lock(&deliverer_count);
	if (rc != 0) {
    		printf("ERROR: return code from pthread_lock() is %d\n", rc);
       		exit(-1);
       	}
	while(Ndeliverer == 0){
		rc=pthread_cond_wait(&delivers, &deliverer_count);
		if (rc != 0) {
    		printf("ERROR: return code from pthread_cond_wait() is %d\n", rc);
       		exit(-1);
       	}
	}
	Ndeliverer--;
	rc=pthread_mutex_unlock(&deliverer_count);
	if (rc != 0) {
    		printf("ERROR: return code from pthread_unlock() is %d\n", rc);
       		exit(-1);
       	}

	int xronos_paradoshs = (rand_r(&seed) % (Tdelhigh - Tdellow + 1)) + Tdellow;

	//waiting for delivery man to arrive at the destination
	sleep(xronos_paradoshs);
	clock_gettime(CLOCK_REALTIME, &telos_delivery);
	int xronos_paradoshs_apo_thn_emfanisei_tou_pelath = (telos_delivery.tv_sec-arxhT.tv_sec);
	int xronos_krywmatos = (telos_delivery.tv_sec-telosPshsimatos.tv_sec);

	//mutex for total service time and total pizza sitting time(pizza getting cold)
	rc=pthread_mutex_lock(&xronos_e3yphrethshsMutex);
	if (rc != 0) {
    		printf("ERROR: return code from pthread_lock() is %d\n", rc);
       		exit(-1);
       	}
	total_xronos_e3yphrethshs += xronos_paradoshs_apo_thn_emfanisei_tou_pelath;

	if(xronos_paradoshs_apo_thn_emfanisei_tou_pelath > megistos_xronos_e3yphrethshs){
		megistos_xronos_e3yphrethshs = xronos_paradoshs_apo_thn_emfanisei_tou_pelath;
	}

	total_xronos_krywmatos += xronos_krywmatos;
	if(xronos_krywmatos > megistos_xronos_krywmatos){
		megistos_xronos_krywmatos = xronos_krywmatos;
	}
	rc=pthread_mutex_unlock(&xronos_e3yphrethshsMutex);
	if (rc != 0) {
    		printf("ERROR: return code from pthread_unlock() is %d\n", rc);
       		exit(-1);
       	}

	printf("H PARAGGELIA ME ARI8MO %d PARADW8HKE SE %d LEPTA\n", *id, xronos_paradoshs_apo_thn_emfanisei_tou_pelath);

	//waiting for delivery man to arrive back at the shop
	sleep(xronos_paradoshs);

	//mutex for delivery so we can make the delivery man available again
	rc=pthread_mutex_lock(&deliverer_count);
	if (rc != 0) {
    		printf("ERROR: return code from pthread_lock() is %d\n", rc);
       		exit(-1);
       	}
	Ndeliverer++;
	rc=pthread_mutex_unlock(&deliverer_count);
	if (rc != 0) {
    		printf("ERROR: return code from pthread_unlock() is %d\n", rc);
       		exit(-1);
       	}
	rc=pthread_cond_signal(&delivers);
	if (rc != 0) {
    		printf("ERROR: return code from pthread_unlock() is %d\n", rc);
       		exit(-1);
       	}

}

int main(int argc, char *argv[]){
	int i, rc; 
	
	if(argc!=3){
	printf("Incorrect amount of arguments, please enter numeber of customers and seed.\n");
	exit(-1);}
	Ncust=atoi(argv[1]);
	seed= atoi(argv[2]);
	int order_id[Ncust];//create a table of order ids each corresponding to a customer.
	pthread_t *threads;
	threads= malloc(sizeof(pthread_t)*Ncust);//create a dynamic table of threads

	//initializing mutexes
	rc = pthread_mutex_init(&revenue_mutex, NULL);
	if (rc != 0) {	
		printf("ERROR: return code from pthread_mutex_init() is %d\n", rc);
		exit(-1);
	}
	rc = pthread_mutex_init(&cook_count, NULL);
	if (rc != 0) {	
		printf("ERROR: return code from pthread_mutex_init() is %d\n", rc);
		exit(-1);
	}
	rc = pthread_mutex_init(&oven_lock, NULL);
	if (rc != 0) {	
		printf("ERROR: return code from pthread_mutex_init() is %d\n", rc);
		exit(-1);
	}
	rc = pthread_mutex_init(&packers_count, NULL);
	if (rc != 0) {	
		printf("ERROR: return code from pthread_mutex_init() is %d\n", rc);
		exit(-1);
	}
	rc = pthread_mutex_init(&deliverer_count, NULL);
	if (rc != 0) {	
		printf("ERROR: return code from pthread_mutex_init() is %d\n", rc);
		exit(-1);
	}
	rc = pthread_mutex_init(&paraggeliaMutex, NULL);
	if (rc != 0) {	
		printf("ERROR: return code from pthread_mutex_init() is %d\n", rc);
		exit(-1);
	}
	rc = pthread_mutex_init(&num_of_ordersMutex, NULL);
	if (rc != 0) {	
		printf("ERROR: return code from pthread_mutex_init() is %d\n", rc);
		exit(-1);
	}
	rc = pthread_mutex_init(&xronos_e3yphrethshsMutex, NULL);
	if (rc != 0) {	
		printf("ERROR: return code from pthread_mutex_init() is %d\n", rc);
		exit(-1);
	}

	//creating threads
	for(i=0; i<Ncust;i++){
		order_id[i]=i+1;
		rc = pthread_create(&threads[i], NULL, &order_func, &order_id[i]);
		if (rc != 0) {
    		printf("ERROR: return code from pthread_create() is %d\n", rc);
       		exit(-1);
       	}
    }

	//joining threads
	void *status;       		
    for (i = 0; i < Ncust; i++) {
		rc = pthread_join(threads[i], &status);	
		if (rc != 0) {
			printf("ERROR: return code from pthread_join() is %d\n", rc);
			exit(-1);		
		}
	}
	
	//freeing threads
	free(threads);
	
	//printing final statements
	printf("SYNOLIKA ESODA HMERAS: %d$\n#PLAIN_SOLD: %d\n#SPECIAL_SOLD: %d\n#SUCCESFULL_ORDERS: %d\n#UNSUCCESFUL_ORDERS: %d\n", revenue, number_of_plain_pizzas_sold,
	number_of_special_pizzas_sold, successfull_orders, unsuccessfull_orders);
	printf("MESOS XRONOS E3YPHRETHSHS: %d LEPTA\nMEGISTOS XRONOS E3YPHRETHSHS: %d LEPTA\n", total_xronos_e3yphrethshs/successfull_orders, megistos_xronos_e3yphrethshs);
	printf("MESOS XRONOS KRYWMATOS: %d LEPTA\nMEGISTOS XRONOS KRYWMATOS: %d LEPTA\n", total_xronos_krywmatos/successfull_orders, megistos_xronos_krywmatos);

	return 0;
}
