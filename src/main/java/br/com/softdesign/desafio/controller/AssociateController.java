package br.com.softdesign.desafio.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.softdesign.desafio.dto.AssociateDTO;
import br.com.softdesign.desafio.dto.AssociateResponseDTO;
import br.com.softdesign.desafio.service.IAssociateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/associates")
@Api(tags = "Associates")
public class AssociateController {
	
    @Autowired
    private IAssociateService associateService;
    
    @PostMapping
    @ApiOperation(value = "Criar um novo associado", notes = "Cria um novo associado.")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Associado criado com sucesso"),
        @ApiResponse(code = 400, message = "Solicitação inválida"), 
        @ApiResponse(code = 422, message = "CPF já cadastrado")
    })
    public ResponseEntity<AssociateResponseDTO> createAssociate(@Valid @RequestBody AssociateDTO associateDTO) {
    	AssociateResponseDTO createdAssociate = associateService.createAssociate(associateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAssociate);
    }
}