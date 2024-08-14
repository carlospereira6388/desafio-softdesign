package br.com.softdesign.desafio.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.softdesign.desafio.exception.CustomException;

@Service
public class CpfValidationServiceImpl implements ICpfValidationService {

    private static final Logger logger = LoggerFactory.getLogger(CpfValidationServiceImpl.class);
    private static final String USER_INFO_API_URL = "https://user-info.herokuapp.com/users/";

    @Retryable(
        value = { CustomException.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 2000))
    @Override
    public boolean canVote(String cpf) {
        RestTemplate restTemplate = new RestTemplate();
        
        try {
            String response = restTemplate.getForObject(USER_INFO_API_URL + cpf, String.class);
            boolean canVote = response != null && response.contains("ABLE_TO_VOTE");
            return canVote;
        } catch (HttpClientErrorException.NotFound e) {
            throw new CustomException("CPF inválido", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Erro ao validar CPF: {}", cpf, e);
            throw new CustomException("Erro ao validar CPF", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @CircuitBreaker(
        maxAttempts = 3,
        openTimeout = 5000,
        resetTimeout = 20000)
    public boolean canVoteWithCircuitBreaker(String cpf) {
        return canVote(cpf);
    }
}
