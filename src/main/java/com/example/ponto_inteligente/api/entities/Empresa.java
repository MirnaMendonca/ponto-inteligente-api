package com.example.ponto_inteligente.api.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Empresa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empresa implements Serializable {

    private static final long serialVersionUID = 3960436649365666213L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String razaoSocial;
    private String cnpj;

    @Column(name = "data_criacao", nullable = false)
    private Date dataCriacao;

    @Column(name = "data_atualizacao", nullable = false)
    private Date dataAtualizacao;

    @OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Funcionario> funcionarios;

    @PreUpdate
    public void preUpdate() {
        dataAtualizacao = new Date();
    }

    @PrePersist
    public void prePersist() {
        final Date atual = new Date();
        dataCriacao = atual;
        dataAtualizacao = atual;
    }
}