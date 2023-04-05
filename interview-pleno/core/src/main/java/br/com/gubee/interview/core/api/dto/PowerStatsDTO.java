package br.com.gubee.interview.core.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PowerStatsDTO {
   
	@JsonProperty("strength")
	private short strength;
	
	@JsonProperty("agility")
    private short agility;
	
	@JsonProperty("dexterity")
    private short dexterity;
	
	@JsonProperty("intelligence")
	private short intelligence;
    
}
