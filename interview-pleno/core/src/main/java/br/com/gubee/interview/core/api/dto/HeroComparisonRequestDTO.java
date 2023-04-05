package br.com.gubee.interview.core.api.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class HeroComparisonRequestDTO {
	private UUID hero1;
	private UUID hero2;
}