package br.com.softdesign.desafio.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.softdesign.desafio.model.Associate;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AssociateRepositoryTest {

    @Autowired
    private AssociateRepository associateRepository;

    @Test
    public void shouldFindAssociateByDocument() {
        Associate associate = new Associate();
        associate.setName("Associado 1");
        associate.setDocument("12345678901");

        associateRepository.save(associate);

        Associate foundAssociate = associateRepository.findByDocument("12345678901").orElse(null);
        
        assertThat(foundAssociate).isNotNull();
        assertThat(foundAssociate.getName()).isEqualTo("Associado 1");
    }
}
