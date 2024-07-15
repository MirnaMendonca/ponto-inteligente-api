package com.example.ponto_inteligente.api.services;

import java.util.Optional;

import com.example.ponto_inteligente.api.dtos.CadastroPJDto;
import com.example.ponto_inteligente.api.entities.Funcionario;
import com.example.ponto_inteligente.api.entities.Empresa;
import org.springframework.validation.BindingResult;

public interface FuncionarioService {

    /**
     * Persiste um funcionário na base de dados.
     *
     * @param funcionario
     * @return Funcionario
     */
    Funcionario persistir(Funcionario funcionario);
    Funcionario persistir(CadastroPJDto cadastroPJDto, Empresa empresa);

    CadastroPJDto converterPraDto(Funcionario funcionario);

    void conferirDuplicatas(CadastroPJDto cadastroPJDto, BindingResult result);

    /**
     * Busca e retorna um funcionário dado um CPF.
     *
     * @param cpf
     * @return Optional<Funcionario>
     */
    Optional<Funcionario> buscarPorCpf(String cpf);

    /**
     * Busca e retorna um funcionário dado um email.
     *
     * @param email
     * @return Optional<Funcionario>
     */
    Optional<Funcionario> buscarPorEmail(String email);

    /**
     * Busca e retorna um funcionário por ID.
     *
     * @param id
     * @return Optional<Funcionario>
     */
    Optional<Funcionario> buscarPorId(Long id);

}