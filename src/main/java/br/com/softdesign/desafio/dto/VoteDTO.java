package br.com.softdesign.desafio.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VoteDTO {
	
	@NotNull(message = "O campo id do associado é obrigatório")
    private Long associateId;

    @NotEmpty(message = "O campo voto é obrigatório")
    private String vote;
}