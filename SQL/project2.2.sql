/*to 9
tainies opou to rating einai akribws 2,5*/
select distinct m.title
from dbo.movie m 
join dbo.ratings r on r.movie_id = m.id
where r.rating = 2.5;

--to 12 meso budget gia tainia analoga thn glwssa 
select avg(m.budget), m.original_language
from dbo.movie m 
group by m.original_language;

--to 11 family tainies me rating megalytero tou 4
select m.title
from dbo.movie m 
join dbo.ratings r on r.movie_id = m.id
where r.rating >= 4.0 and m.id in (
    select hg.movie_id 
    from dbo.hasGenre hg
    join dbo.genre g on g.id = hg.genre_id
    where g.name = '_amily'
);

--to 10 poses fores to ka8e keyword
select count(*) as movies_used_in, k.name
from dbo.Keyword k 
join dbo.hasKeywords hk on hk.keyword_id = k.id
group by k.name;
