package org.project.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

	@Query("SELECT x FROM Image x WHERE x.uuid = ?1")
	Optional<Image> findByUUID (String id);

}
