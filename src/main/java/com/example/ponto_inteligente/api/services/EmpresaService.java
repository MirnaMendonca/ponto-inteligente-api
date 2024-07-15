package com.example.ponto_inteligente.api.services;

import java.util.Optional;

import com.example.ponto_inteligente.api.dtos.CadastroPJDto;
import com.example.ponto_inteligente.api.entities.Empresa;
import com.example.ponto_inteligente.api.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface EmpresaService {

    /**
     * Retorna uma empresa dado um CNPJ.
     *
     * @param cnpj
     * @return Optional<Empresa>
     */
    Optional<Empresa> buscarPorCnpj(String cnpj);

    /**
     * Cadastra uma nova empresa na base de dados.
     *
     * @param empresa
     * @return Empresa
     */
    Empresa persistir(Empresa empresa);

    Empresa persistir(CadastroPJDto cadastroPJDto);

    void conferirDuplicatas(CadastroPJDto cadastroPJDto, BindingResult result);

}