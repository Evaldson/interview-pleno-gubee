package br.com.gubee.interview.core.api.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class HeroComparisonAttributesDTO {
	private UUID id;
    private int strength;
    private int agility;
    private int dexterity;
    private int intelligence;

    public HeroComparisonAttributesDTO() {}

    public HeroComparisonAttributesDTO(UUID id , int strength, int agility, int dexterity, int intelligence) {
        this.id = id;
    	this.strength = strength;
        this.agility = agility;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
    }

}
