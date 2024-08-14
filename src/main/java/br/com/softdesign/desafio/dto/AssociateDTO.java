package br.com.softdesign.desafio.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AssociateDTO {
	
    @NotEmpty(message = "O campo nome é obrigatório")
    private String name;
    
    @NotEmpty(message = "O campo CPF é obrigatório")
    @Size(min = 11, max = 11, message = "O campo CPF deve ter 11 dígitos")
    @Pattern(regexp = "\\d{11}", message = "O campo CPF deve conter apenas números")
    private String document;
}