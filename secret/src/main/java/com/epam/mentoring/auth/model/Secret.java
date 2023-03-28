package com.epam.mentoring.auth.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Eduard_Sardyka
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Secret {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char") @Column(length = 36)
    private UUID id;

    @Column(length = 30)
    private String secretMessage;

}