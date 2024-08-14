package br.com.softdesign.desafio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.softdesign.desafio.dto.AgendaDTO;
import br.com.softdesign.desafio.dto.AgendaResponseDTO;
import br.com.softdesign.desafio.dto.ResultDTO;
import br.com.softdesign.desafio.dto.VoteDTO;
import br.com.softdesign.desafio.service.IVotingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/agendas")
@Api(tags = "Votação")
public class VotingController {
	
    @Autowired
    private IVotingService votingService;

    @PostMapping
    @ApiOperation(value = "Criar uma nova pauta", notes = "Cria uma nova pauta de votação.")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Pauta criada com sucesso"),
        @ApiResponse(code = 400, message = "Solicitação inválida. Verifique os dados enviados.")
    })
    public ResponseEntity<AgendaResponseDTO> createAgenda(@Validated @RequestBody AgendaDTO agendaDTO) {
    	AgendaResponseDTO agenda = votingService.createAgenda(agendaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(agenda);
    }
    
    @GetMapping("/{id}/sessions")
    @ApiOperation(value = "Abrir uma sessão de votação", notes = "Abre uma sessão de votação para uma pauta específica.")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Sessão de votação aberta com sucesso"),
        @ApiResponse(code = 404, message = "Pauta não encontrada")
    })
    public ResponseEntity<Void> openVotingSession(@PathVariable Long id, @RequestParam(required = false) Long duration) {
        votingService.openVotingSession(id, duration);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id}/votes")
    @ApiOperation(value = "Registrar um voto", notes = "Registra um voto para uma pauta específica.")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Voto registrado com sucesso"),
        @ApiResponse(code = 400, message = "Solicitação inválida. Verifique os dados enviados."),
        @ApiResponse(code = 404, message = "Pauta ou associado não encontrado"),
        @ApiResponse(code = 403, message = "Usuário não está autorizado")
    })
    public ResponseEntity<Void> vote(@PathVariable Long id, @Validated @RequestBody VoteDTO voteDTO) {
        votingService.vote(id, voteDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    @GetMapping("/{id}/results")
    @ApiOperation(value = "Obter resultado da votação", notes = "Obtém o resultado da votação para uma pauta específica.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Resultado da votação retornado com sucesso"),
        @ApiResponse(code = 404, message = "Pauta não encontrada")
    })
    public ResponseEntity<ResultDTO> getVotingResult(@PathVariable Long id) {
        ResultDTO result = votingService.getVotingResult(id);
        return ResponseEntity.ok(result);
    }
}