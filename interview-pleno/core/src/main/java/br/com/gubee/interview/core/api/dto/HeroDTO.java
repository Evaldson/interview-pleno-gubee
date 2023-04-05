package br.com.gubee.interview.core.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class HeroDTO {
	
	 private String name;
	 private String race;
	 private Boolean enabled = true;
	
	 @JsonProperty("powerStats")
	 private PowerStatsDTO powerStats;
	 
}
