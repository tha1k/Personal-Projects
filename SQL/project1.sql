select YEAR(m.release_date) year , count(m.title) movies_per_year
from movie m 
where m.budget> 1000000
GROUP by YEAR(m.release_date);
--project query 2
select g.name genre, sum_of_movies movies_per_genre
from genre g join (
select hg.genre_id as ID, count(m.id) as sum_of_movies
from movie m join hasGenre hg on m.id=hg.movie_id
where m.budget> 1000000 and m.runtime>=120
group by hg.genre_id
) as moo on g.id=moo.ID;

--project query 3
select a.name as genre, year(dbo.movie.release_date) as year, count(dbo.movie.id) as movies_per_gy
from dbo.movie join(
    select *
    from dbo.genre join dbo.hasGenre on dbo.genre.id=dbo.hasGenre.genre_id
) as a on dbo.movie.id=a.movie_id
group by a.name, year(dbo.movie.release_date);


--query 4
select a.yyear year, a.summ revenues_per_year
from (
    select sum(dbo.movie.revenue) summ, year(dbo.movie.release_date) yyear
    from dbo.movie join dbo.movie_cast on dbo.movie.id=dbo.movie_cast.movie_id
    where dbo.movie_cast.name ='Valeria Golino'
    group by year(dbo.movie.release_date)
) as a;

--query 5
select year(dbo.movie.release_date) year, max(dbo.movie.budget) max_budget
from dbo.movie 
where dbo.movie.budget <> 0
group by year(dbo.movie.release_date);

--query 6 
select dbo.collection.name as trilogy_name
from dbo.collection 
join dbo.belongsTocollection on dbo.collection.id=dbo.belongsTocollection.collection_id
group by dbo.collection.name
having count(dbo.belongsTocollection.movie_id)=3;


--query 7
select avg(dbo.ratings.rating) as avg_rating, count(dbo.ratings.rating) as rating_count, ratings.user_id
from dbo.ratings 
group by dbo.ratings.user_id;

--query 8
select top(10) dbo.movie.title as movie_title, dbo.movie.budget as budget
from dbo.movie
order by budget desc;

--query 9
select year(dbo.movie.release_date) as year, dbo.movie.title as movies_with_max_revenue
from dbo.movie join(select year(dbo.movie.release_date) year, max(dbo.movie.budget) max_budget
    from dbo.movie 
    where dbo.movie.budget <> 0
    group by year(dbo.movie.release_date)) as a on year(dbo.movie.release_date) = a.year
where dbo.movie.budget = a.max_budget
order by year,movies_with_max_revenue; 


--query 10
with temp(id, t, p) as(
    select dbo.ratings.movie_id, dbo.movie.title t, dbo.movie.popularity p
    from dbo.ratings join dbo.movie on dbo.ratings.movie_id=dbo.movie.id
    where dbo.ratings.movie_id in(
        select distinct dbo.ratings.movie_id
        from dbo.ratings 
        where dbo.ratings.rating > 4
    )
    group by dbo.ratings.movie_id, dbo.movie.title, dbo.movie.popularity
    having count(dbo.ratings.user_id) > 10
)
select t1.t Movie1, t2.t Movie2, t1.p Popularity_Movie1, t2.p Popularity_Movie2
from temp t1, temp t2
where t1.id <> t2.id;