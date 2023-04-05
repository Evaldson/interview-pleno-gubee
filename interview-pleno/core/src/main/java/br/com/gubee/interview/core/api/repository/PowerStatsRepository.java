package br.com.gubee.interview.core.api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gubee.interview.core.domain.model.PowerStats;

public interface PowerStatsRepository extends JpaRepository<PowerStats, UUID> {
}
