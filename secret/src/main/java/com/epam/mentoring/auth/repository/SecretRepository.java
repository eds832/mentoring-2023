package com.epam.mentoring.auth.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.mentoring.auth.model.Secret;

/**
 * @author Eduard_Sardyka
 */
@Repository
public interface SecretRepository extends JpaRepository<Secret, UUID> {

}