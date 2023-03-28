package com.epam.mentoring.auth.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.epam.mentoring.auth.model.Secret;
import com.epam.mentoring.auth.repository.SecretRepository;

import lombok.AllArgsConstructor;

/**
 * @author Eduard_Sardyka
 */
@Service
@AllArgsConstructor
public class SecretService {

    private SecretRepository secretRepository;

    public Secret findByIdAndDelete(String uuid) {
        UUID key = UUID.fromString(uuid);
        Secret secret = secretRepository.findById(key).orElse(new Secret(null, "Secret information has been deleted."));
        if(secret.getId() != null) {
            secretRepository.delete(secret);
        }
        return secret;
    }

    public Secret save(String secretMessage) {
        Secret secret = secretRepository.save(new Secret(null, secretMessage));
        return secret;
    }
}