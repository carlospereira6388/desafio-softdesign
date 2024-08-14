package br.com.softdesign.desafio.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResultDTO {

    private Long agendaId;
    private String agendaName;
    private int totalVotes;
    private int yesVotes;
    private int noVotes;
    private String result;
}