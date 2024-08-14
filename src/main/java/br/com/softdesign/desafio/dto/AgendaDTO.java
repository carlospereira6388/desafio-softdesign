package br.com.softdesign.desafio.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AgendaDTO {
	
    @NotEmpty(message = "O campo nome é obrigatório")
    private String name;
    
    @NotEmpty(message = "O campo descrição é obrigatório")
    private String description;
}