package com.example.ponto_inteligente.api.services.impl;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import com.example.ponto_inteligente.api.dtos.CadastroPJDto;
import com.example.ponto_inteligente.api.entities.Funcionario;
import com.example.ponto_inteligente.api.enums.PerfilEnum;
import com.example.ponto_inteligente.api.response.Response;
import com.example.ponto_inteligente.api.utils.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.ponto_inteligente.api.entities.Empresa;
import com.example.ponto_inteligente.api.repositories.EmpresaRepository;
import com.example.ponto_inteligente.api.services.EmpresaService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    private static final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);

    @Autowired
    private EmpresaRepository empresaRepository;

    @Override
    public Optional<Empresa> buscarPorCnpj(String cnpj) {
        log.info("Buscando uma empresa para o CNPJ {}", cnpj);
        return Optional.ofNullable(empresaRepository.findByCnpj(cnpj));
    }

    @Override
    public Empresa persistir(Empresa empresa) {
        log.info("Persistindo empresa: {}", empresa);
        return this.empresaRepository.save(empresa);
    }

    public Empresa persistir(CadastroPJDto cadastroPJDto) {
        log.info("Persistindo empresa: {}", cadastroPJDto);
        Empresa empresa = new Empresa();
        empresa.setCnpj(cadastroPJDto.getCnpj());
        empresa.setRazaoSocial(cadastroPJDto.getRazaoSocial());
        return this.empresaRepository.save(empresa);
    }

    public void conferirDuplicatas(CadastroPJDto cadastroPJDto, BindingResult result) {
        buscarPorCnpj(cadastroPJDto.getCnpj()).ifPresent(emp -> result.addError(new ObjectError("empresa", "Empresa já existente.")));
    }
}