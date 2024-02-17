package org.project.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

	@Query("SELECT x FROM Movie x WHERE x.imdbId = ?1")
	Optional<Movie> findByImdbId (Long id);

	@Query("SELECT x FROM Movie x WHERE x.title LIKE %?1% AND x.year >= ?2 AND x.year < ?3")
	List<Movie> filterByPatternAndYear (String title, Integer startYear, Integer endYear);

	@Query("SELECT x FROM Movie x WHERE x.title LIKE %?1%")
	List<Movie> filterByPattern (String title);

	@Query("SELECT x FROM Movie x WHERE x.year >= ?1 AND x.year < ?2")
	List<Movie> filterByYear (Integer startYear, Integer endYear);

}
