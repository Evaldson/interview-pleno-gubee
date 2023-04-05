package br.com.gubee.interview.core.api.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.gubee.interview.core.api.dto.HeroComparisonDTO;
import br.com.gubee.interview.core.api.dto.HeroComparisonRequestDTO;
import br.com.gubee.interview.core.api.dto.HeroDTO;
import br.com.gubee.interview.core.api.service.HeroService;
import br.com.gubee.interview.core.api.service.PowerStatsService;
import br.com.gubee.interview.core.domain.model.Hero;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/heros")
public class HeroController {

	private HeroService heroService;
	private PowerStatsService powerStatsService;

	@GetMapping("/{id}")
	public ResponseEntity<Hero> findById(@PathVariable UUID id) {
		Optional<Hero> optionalHero = heroService.findHero(id);
		if (optionalHero.isPresent()) {
			Hero hero = optionalHero.get();
			return ResponseEntity.ok(hero);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping()
	public ResponseEntity<Hero> findByName(@RequestParam String name) {
		Optional<Hero> optionalHero = heroService.findHeroName(name);
		if (optionalHero.isPresent()) {
			Hero hero = optionalHero.get();
			return ResponseEntity.ok(hero);
		} else {
			return ResponseEntity.ok().build();
		}
	}

	@GetMapping("/comparison")
	public ResponseEntity<HeroComparisonDTO> compareHeroes(@RequestBody HeroComparisonRequestDTO hero) {
		Optional<Hero> hero1 = heroService.findHero(hero.getHero1());
		Optional<Hero> hero2 = heroService.findHero(hero.getHero2());

		if (hero1.isPresent() && hero2.isPresent()) {
			HeroComparisonDTO heroComparison = heroService.compareHeroes(hero1.get(), hero2.get());
			return ResponseEntity.ok(heroComparison);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Hero addHero(@RequestBody HeroDTO hero) {
		return heroService.save(hero);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Hero> upgradeHero(@PathVariable UUID id, @RequestBody HeroDTO hero) {
		Optional<Hero> optionalHero = heroService.findHero(id);
		if (optionalHero.isPresent()) {
			heroService.upgradeHero(id, hero, optionalHero.get());
			return ResponseEntity.ok(optionalHero.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteHero(@PathVariable UUID id) {
		Optional<Hero> optionalHero = heroService.findHero(id);
		if (optionalHero.isPresent()) {
			heroService.delete(id);
			powerStatsService.excluir(optionalHero.get().getPowerStats().getId());
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}