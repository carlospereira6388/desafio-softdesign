package br.com.softdesign.desafio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.softdesign.desafio.dto.AssociateDTO;
import br.com.softdesign.desafio.dto.AssociateResponseDTO;
import br.com.softdesign.desafio.exception.CustomException;
import br.com.softdesign.desafio.model.Associate;
import br.com.softdesign.desafio.repository.AssociateRepository;
import br.com.softdesign.desafio.utils.Mapper;

@Service
public class AssociateServiceImpl implements IAssociateService {
	
    @Autowired
    private AssociateRepository associateRepository;

    @Autowired
    private Mapper mapper;

    @Override
    public AssociateResponseDTO createAssociate(AssociateDTO associateDTO) {
        if (associateRepository.findByDocument(associateDTO.getDocument()).isPresent()) {
            throw new CustomException("CPF já cadastrado", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Associate associate = mapper.convert(associateDTO, Associate.class);
        Associate savedAssociate = associateRepository.save(associate);
        
        return mapper.convert(savedAssociate, AssociateResponseDTO.class);
    }
}
