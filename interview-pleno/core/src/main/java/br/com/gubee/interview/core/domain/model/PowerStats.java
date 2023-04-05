package br.com.gubee.interview.core.domain.model;

import lombok.Data;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Data
public class PowerStats {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private short  strength;

    @Column(nullable = false)
    private short  agility;

    @Column(nullable = false)
    private short  dexterity;

    @Column(nullable = false)
    private short  intelligence;
    
    @JsonIgnore
    private OffsetDateTime createdAt;

    @JsonIgnore
    private OffsetDateTime updatedAt;
}