SELECT film.film_id, film.title, film.release_year, actor.actor_id, actor.first_name, actor.last_name 
FROM film 
join film_actor on film.film_id=film_actor.film_id 
join actor on film_actor.actor_id=actor.actor_id 
where title='%s'