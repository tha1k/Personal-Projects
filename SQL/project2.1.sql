/*1)Βρες ταινίες με άριστες αξιολογήσεις(5/5) μαζί με τα keywords τους
output= 275*/
/*select title, Keyword.name
from Keyword,
(select five_star.title as title, kw.keyword_id as kw_id
from(
SELECT m.title as title , m.id as id--, avg(r.rating) as avgRating
FROM movie m
INNER JOIN ratings r
ON m.id = r.movie_id
GROUP BY m.id, m.title
HAVING avg(r.rating)=5) as five_star join hasKeywords kw on kw.moovie_id=five_star.id ) as kw_ids
where kw_ids.kw_id= Keyword.id;*/
--join condition

/*2)Επέστρεψε μου ταινιες δράσης με την λέξη  battle στον τίλτο οι οποίες έχουν διάρκεια 1 μιση με 2 ώρες και την διάρκεια κάθε μίας
output= 5  */
/*select DISTINCT m.title, m.runtime
from movie m ,(
select distinct hg.movie_id as id
from hasGenre hg 
inner join genre g on g.id=hg.genre_id
where g.name like 'Action') as act_m
where m.title like '%Battle%' And m.id= act_m.id and m.runtime BETWEEN 60 and 120;*/



/*3)Βρς όλες τις ταινίες με τον Tom Hanks και προβαλέ τες απο την παλαιότερη στην πιο καινουργια
cardinality=35*/
/*select distinct m.title,mc.name , m.release_date
from movie m
JOIN movie_cast mc on m.id=mc.movie_id
where mc.name like '_om _anks'
order by m.release_date asc;*/

/*4 βρες την δημοφιλέστερη ταινία pixar(τίτλο) 
output= 1*/
/*select movie.title 
from movie, 
(select max(m.popularity) as pop
from movie m , movie m2 
where m.id in (
select hpr.movie_id 
from productioncompany p_c full outer join hasProductioncompany hpr on hpr.pc_id=p_c.id --return popularity even if there isnt a production company listed for this movie
where p_c.name like 'Pixar%')) as m1
where movie.popularity=m1.pop;*/


/*5 επέστρεψε αγγλόφωνες ταινίες με τα id τους οι οποίες δεν άλλαξαν τίτλο. Θα συμπεριλάβουμε και τις ταινίες χωρίς άρχικό τίτλο.Ταξινομημένες από τον κωδικό τους
output:8210 */
/*select m.id, m.original_title, m.title
from movie m left outer join movie m1 on m.original_title=m1.title
where m.original_language like 'en'
group by m.id , m.original_title, m.title
order by m.id
*/


/*6 κατάταξε τα είδη ταινιών με βάση το πλήθος ταινιών ανά είδος. NOTE το genre.csv έχει 33 είδη πολλά από αυτά δεν εμφανίζονται σε καμία τανία
Output=9 */
/*select g.name, sum_of_movies
from genre g join (
select hg.genre_id as ID, count(m.id) as sum_of_movies
from movie m join hasGenre hg on m.id=hg.movie_id
group by hg.genre_id
) as moo on g.id=moo.ID
order by sum_of_movies desc*/
/*7βρες τις 10 ταινίες που χάσαν τα περισσότερα χρήματα ενώ είχαν budget ενός εκατομυρίου*/
select top(10) m.id, m.title, (m.revenue-m.budget) as loss, ((m.revenue-m.budget)/budget)*100 as ROI_percent
from movie m 
where m.budget>1000000 and m.revenue>10000
order by (m.revenue-m.budget)

/*8*/
/*9*/
/*10*/
/*11*/
/*12*/