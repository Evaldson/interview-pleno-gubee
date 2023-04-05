package br.com.gubee.interview.core.api.service;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gubee.interview.core.api.dto.HeroComparisonAttributesDTO;
import br.com.gubee.interview.core.api.dto.HeroComparisonDTO;
import br.com.gubee.interview.core.api.dto.HeroDTO;
import br.com.gubee.interview.core.api.exception.HeroiExistenteException;
import br.com.gubee.interview.core.api.repository.HeroRepository;
import br.com.gubee.interview.core.api.repository.PowerStatsRepository;
import br.com.gubee.interview.core.domain.model.Hero;
import br.com.gubee.interview.core.domain.model.PowerStats;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class HeroService {

	private HeroRepository heroRepository;
	private PowerStatsRepository powerStatsRepository;

	@Transactional
	public void delete(UUID heroId) {
		heroRepository.deleteById(heroId);
	}

	@Transactional(readOnly = true)
	public Optional<Hero> findHero(UUID id) {
		return this.heroRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public Optional<Hero> findHeroName(String name) {
		return this.heroRepository.findByName(name);
	}

	@Transactional
	public Hero upgradeHero(UUID heroId, HeroDTO newAttributes, Hero hero) {

		preencherObjetosHeroEPowerStats(newAttributes, hero, hero.getPowerStats());

		hero.getPowerStats().setUpdatedAt(OffsetDateTime.now());

		powerStatsRepository.save(hero.getPowerStats());

		hero.setPowerStats(hero.getPowerStats());

		return heroRepository.save(hero);
	}

	@Transactional
	public Hero save(HeroDTO hero) {

		boolean nomeEmUso = heroRepository.findByName(hero.getName()).stream()
				.anyMatch(heroExistente -> !heroExistente.equals(hero));

		if (nomeEmUso) {
			throw new HeroiExistenteException(hero.getName());
		}

		Hero newHero = new Hero();
		PowerStats powerStats = new PowerStats();

		preencherObjetosHeroEPowerStats(hero, newHero, powerStats);

		powerStats.setCreatedAt(OffsetDateTime.now());
		powerStats.setUpdatedAt(OffsetDateTime.now());

		powerStatsRepository.save(powerStats);

		newHero.setPowerStats(powerStats);

		return heroRepository.save(newHero);
	}

	public HeroComparisonDTO compareHeroes(Hero hero1, Hero hero2) {
		int strengthDiff = hero1.getPowerStats().getStrength() - hero2.getPowerStats().getStrength();
		int agilityDiff = hero1.getPowerStats().getAgility() - hero2.getPowerStats().getAgility();
		int dexterityDiff = hero1.getPowerStats().getDexterity() - hero2.getPowerStats().getDexterity();
		int intelligenceDiff = hero1.getPowerStats().getIntelligence() - hero2.getPowerStats().getIntelligence();

		HeroComparisonAttributesDTO hero1Attributes = new HeroComparisonAttributesDTO(hero1.getId(), strengthDiff,
				agilityDiff, dexterityDiff, intelligenceDiff);
		HeroComparisonAttributesDTO hero2Attributes = new HeroComparisonAttributesDTO(hero2.getId(), -strengthDiff,
				-agilityDiff, -dexterityDiff, -intelligenceDiff);

		return new HeroComparisonDTO(hero1Attributes, hero2Attributes);
	}

	private void preencherObjetosHeroEPowerStats(HeroDTO hero, Hero newHero, PowerStats powerStats) {
		newHero.setName(hero.getName());
		newHero.setRace(hero.getRace());
		newHero.setEnabled(hero.getEnabled());
		newHero.setCreatedAt(OffsetDateTime.now());
		newHero.setUpdatedAt(OffsetDateTime.now());
		powerStats.setAgility(hero.getPowerStats().getAgility());
		powerStats.setDexterity(hero.getPowerStats().getDexterity());
		powerStats.setIntelligence(hero.getPowerStats().getIntelligence());
		powerStats.setStrength(hero.getPowerStats().getStrength());
	}
}
