package com.example.ponto_inteligente.api.controllers;

import java.security.NoSuchAlgorithmException;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ponto_inteligente.api.dtos.CadastroPJDto;
import com.example.ponto_inteligente.api.entities.Empresa;
import com.example.ponto_inteligente.api.entities.Funcionario;
import com.example.ponto_inteligente.api.enums.PerfilEnum;
import com.example.ponto_inteligente.api.response.Response;
import com.example.ponto_inteligente.api.services.EmpresaService;
import com.example.ponto_inteligente.api.services.FuncionarioService;
import com.example.ponto_inteligente.api.utils.PasswordUtils;

@RestController
@RequestMapping("/api/cadastrar-pj")
@CrossOrigin(origins = "*")
public class CadastroPJController {

    private static final Logger log = LoggerFactory.getLogger(CadastroPJController.class);

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private EmpresaService empresaService;

    public CadastroPJController() {
    }

    /**
     * Cadastra uma pessoa jurídica no sistema.
     *
     * @param cadastroPJDto
     * @param result
     * @return ResponseEntity<Response < CadastroPJDto>>
     * @throws NoSuchAlgorithmException
     */
    @PostMapping
    public ResponseEntity<Response<CadastroPJDto>> cadastrar(@Valid @RequestBody CadastroPJDto cadastroPJDto,
                                                             BindingResult result) throws NoSuchAlgorithmException {
        log.info("Cadastrando PJ: {}", cadastroPJDto.toString());
        Response<CadastroPJDto> response = new Response<CadastroPJDto>();

        this.empresaService.conferirDuplicatas(cadastroPJDto, result);
        this.funcionarioService.conferirDuplicatas(cadastroPJDto, result);

        if (result.hasErrors()) {
            log.error("Erro validando dados de cadastro PJ: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        Empresa empresa = this.empresaService.persistir(cadastroPJDto);
        Funcionario funcionario = this.funcionarioService.persistir(cadastroPJDto, empresa);

        response.setData(this.funcionarioService.converterPraDto(funcionario));
        return ResponseEntity.ok(response);
    }
}