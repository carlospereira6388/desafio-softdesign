package br.com.softdesign.desafio.service;

import br.com.softdesign.desafio.dto.AssociateDTO;
import br.com.softdesign.desafio.dto.AssociateResponseDTO;

public interface IAssociateService {
	
    AssociateResponseDTO createAssociate(AssociateDTO associateDTO);
}