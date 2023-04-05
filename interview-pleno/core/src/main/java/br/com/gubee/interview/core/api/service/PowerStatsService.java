package br.com.gubee.interview.core.api.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gubee.interview.core.api.repository.PowerStatsRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PowerStatsService {

	private PowerStatsRepository powerStatsRepository;
	
	@Transactional
	public void excluir(UUID heroId) {
		powerStatsRepository.deleteById(heroId);
	} 
}
