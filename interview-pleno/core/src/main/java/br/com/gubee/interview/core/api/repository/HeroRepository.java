package br.com.gubee.interview.core.api.repository;




import org.springframework.stereotype.Repository;


import br.com.gubee.interview.core.domain.model.Hero;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface HeroRepository extends JpaRepository<Hero, UUID> {
	
	Optional<Hero> findByName(String name);
}
