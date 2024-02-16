package org.project.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

	@Query("SELECT x FROM Movie x WHERE x.imdbId = ?1")
	Optional<Movie> findByImdbId (Long id);

}
