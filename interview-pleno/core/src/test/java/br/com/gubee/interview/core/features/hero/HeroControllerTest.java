package br.com.gubee.interview.core.features.hero;


import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.gubee.interview.core.api.controller.HeroController;
import br.com.gubee.interview.core.api.dto.HeroDTO;
import br.com.gubee.interview.core.api.dto.PowerStatsDTO;
import br.com.gubee.interview.core.api.service.HeroService;
import br.com.gubee.interview.core.api.service.PowerStatsService;
import br.com.gubee.interview.core.domain.model.Hero;
import br.com.gubee.interview.core.domain.model.PowerStats;

@SpringBootTest
class HeroControllerTest {

    @Mock
    private HeroService heroService;

    @Mock
    private PowerStatsService powerStatsService;

    @InjectMocks
    private HeroController heroController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByIdSuccess() {
        UUID heroId = UUID.randomUUID();
        Hero hero = new Hero();
        hero.setId(heroId);

        when(heroService.findHero(heroId)).thenReturn(Optional.of(hero));

        ResponseEntity<Hero> response = heroController.findById(heroId);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(hero, response.getBody());
    }

    @Test
    void testFindByIdNotFound() {
        UUID heroId = UUID.randomUUID();

        when(heroService.findHero(heroId)).thenReturn(Optional.empty());

        ResponseEntity<Hero> response = heroController.findById(heroId);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

    @Test
    public void testFindByName() {
        HeroService heroService = Mockito.mock(HeroService.class);
        Hero hero = new Hero();
        hero.setName("Batman");
        Mockito.when(heroService.findHeroName(Mockito.anyString())).thenReturn(Optional.of(hero));

        HeroController heroController = new HeroController(heroService, null);

        ResponseEntity<Hero> response = heroController.findByName("Batman");

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Assertions.assertEquals(hero, response.getBody());
    }
    
    @Test
    public void testFindByNameNotFound() {
        String name = "Non-existent Hero";
        when(heroService.findHeroName(name)).thenReturn(Optional.empty());

        ResponseEntity<Hero> response = heroController.findByName(name);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    public void testAddHero() {
        // given
        HeroDTO heroDTO = new HeroDTO();
        heroDTO.setName("Superman");
        heroDTO.setRace("HUMAN");
        heroDTO.setEnabled(true);
        PowerStatsDTO powers = new PowerStatsDTO();
        powers.setAgility((short) 1);
        powers.setDexterity((short) 1);
        powers.setIntelligence((short) 1);
        powers.setStrength((short) 1);
        heroDTO.setPowerStats(powers);
        
        
        Hero hero = new Hero();
        hero.setName("Superman");
        hero.setRace("HUMAN");
        hero.setEnabled(true);
        hero.setId(UUID.randomUUID());
        PowerStats powersStats = new PowerStats();
        powersStats.setAgility((short) 1);
        powersStats.setDexterity((short) 1);
        powersStats.setIntelligence((short) 1);
        powersStats.setStrength((short) 1);
        powersStats.setId(UUID.randomUUID());
        hero.setPowerStats(powersStats);
        
        when(heroService.save(heroDTO)).thenReturn(hero);

        Hero createdHero = heroController.addHero(heroDTO);

        Assertions.assertEquals(hero.getId(), createdHero.getId());
    }
    
    @Test
    public void testUpgradeHeroSuccess() {
        UUID id = UUID.randomUUID();
        HeroDTO heroDTO = new HeroDTO();
        Hero hero = new Hero();
        Optional<Hero> optionalHero = Optional.of(hero);
        when(heroService.findHero(id)).thenReturn(optionalHero);
        when(heroService.upgradeHero(id, heroDTO, hero)).thenReturn(hero);
        
        ResponseEntity<Hero> response = heroController.upgradeHero(id, heroDTO);
        
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(hero, response.getBody());
    }
    
    @Test
    public void testUpgradeHeroNotFound() {
        UUID id = UUID.randomUUID();
        HeroDTO heroDTO = new HeroDTO();
        Optional<Hero> optionalHero = Optional.empty();
        when(heroService.findHero(id)).thenReturn(optionalHero);
        
        ResponseEntity<Hero> response = heroController.upgradeHero(id, heroDTO);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    
    @Test
    public void testDeleteHeroSuccess() {
        UUID id = UUID.randomUUID();
        Hero hero = new Hero();
        PowerStats powerStats = new PowerStats();
        hero.setPowerStats(powerStats);
        Optional<Hero> optionalHero = Optional.of(hero);
        when(heroService.findHero(id)).thenReturn(optionalHero);
        
        ResponseEntity<HttpStatus> response = heroController.deleteHero(id);
               
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    public void testDeleteHeroNotFound() {
        UUID id = UUID.randomUUID();
        Optional<Hero> optionalHero = Optional.empty();
        when(heroService.findHero(id)).thenReturn(optionalHero);

        ResponseEntity<HttpStatus> response = heroController.deleteHero(id);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}


