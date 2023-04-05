package br.com.gubee.interview.core.api.dto;

import lombok.Data;

@Data
public class HeroComparisonDTO {
    private HeroComparisonAttributesDTO hero1;
    private HeroComparisonAttributesDTO hero2;

    public HeroComparisonDTO() {}

    public HeroComparisonDTO(HeroComparisonAttributesDTO hero1, HeroComparisonAttributesDTO hero2) {
        this.hero1 = hero1;
        this.hero2 = hero2;
    }
        

}





