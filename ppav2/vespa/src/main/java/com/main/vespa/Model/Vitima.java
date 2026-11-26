package com.main.vespa.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Vitima{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String nome;
    @Column(length = 11, nullable = false)
    private String cpf;
    @Column(length = 200)
    private String endereco;
    @Column(length = 15)
    private String telefone;
    @Column(length = 100)
    private String contatoEmergencia;

    @JsonIgnore @ToString.Exclude @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "vitima")
    private List<MedidaProtetiva> medidasProtetivas;

    @JsonIgnore @ToString.Exclude @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "vitima")
    private List<Ronda> rondas;
}