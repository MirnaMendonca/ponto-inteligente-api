package com.example.ponto_inteligente.api.repositories;

import java.util.List;

import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.ponto_inteligente.api.entities.Lancamento;

@Transactional(readOnly = true)
@Repository
@NamedQueries({
        @NamedQuery(name = "LancamentoRepository.findByFuncionarioId",
                query = "SELECT lanc FROM Lancamento lanc WHERE lanc.funcionario.id = :funcionarioId") })
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    List<Lancamento> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId);

    Page<Lancamento> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId, Pageable pageable);
}