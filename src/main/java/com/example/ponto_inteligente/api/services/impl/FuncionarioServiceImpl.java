package com.example.ponto_inteligente.api.services.impl;

import java.util.Optional;

import com.example.ponto_inteligente.api.dtos.CadastroPJDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ponto_inteligente.api.entities.Funcionario;
import com.example.ponto_inteligente.api.repositories.FuncionarioRepository;
import com.example.ponto_inteligente.api.services.FuncionarioService;
import com.example.ponto_inteligente.api.utils.PasswordUtils;
import com.example.ponto_inteligente.api.enums.PerfilEnum;
import com.example.ponto_inteligente.api.entities.Empresa;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

    private static final Logger log = LoggerFactory.getLogger(FuncionarioServiceImpl.class);

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public Funcionario persistir(Funcionario funcionario) {
        log.info("Persistindo funcionário: {}", funcionario);
        return this.funcionarioRepository.save(funcionario);
    }

    public Funcionario persistir(CadastroPJDto cadastroPJDto, Empresa empresa) {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(cadastroPJDto.getNome());
        funcionario.setEmail(cadastroPJDto.getEmail());
        funcionario.setCpf(cadastroPJDto.getCpf());
        funcionario.setPerfil(PerfilEnum.ROLE_ADMIN);
        funcionario.setSenha(PasswordUtils.gerarBCrypt(cadastroPJDto.getSenha()));
        funcionario.setEmpresa(empresa);

        return this.funcionarioRepository.save(funcionario);
    }

    public CadastroPJDto converterPraDto(Funcionario funcionario) {
        CadastroPJDto cadastroPJDto = new CadastroPJDto();
        cadastroPJDto.setId(funcionario.getId());
        cadastroPJDto.setNome(funcionario.getNome());
        cadastroPJDto.setEmail(funcionario.getEmail());
        cadastroPJDto.setCpf(funcionario.getCpf());
        cadastroPJDto.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
        cadastroPJDto.setCnpj(funcionario.getEmpresa().getCnpj());

        return cadastroPJDto;
    }

    public void conferirDuplicatas(CadastroPJDto cadastroPJDto, BindingResult result) {
        buscarPorCpf(cadastroPJDto.getCpf()).ifPresent(func -> result.addError(new ObjectError("funcionario", "CPF já existente.")));
        buscarPorEmail(cadastroPJDto.getEmail()).ifPresent(func -> result.addError(new ObjectError("funcionario", "Email já existente.")));
    }

    public Optional<Funcionario> buscarPorCpf(String cpf) {
        log.info("Buscando funcionário pelo CPF {}", cpf);
        return Optional.ofNullable(this.funcionarioRepository.findByCpf(cpf));
    }

    public Optional<Funcionario> buscarPorEmail(String email) {
        log.info("Buscando funcionário pelo email {}", email);
        return Optional.ofNullable(this.funcionarioRepository.findByEmail(email));
    }

    public Optional<Funcionario> buscarPorId(Long id) {
        log.info("Buscando funcionário pelo ID {}", id);
        return this.funcionarioRepository.findById(id);
    }

}