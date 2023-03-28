package com.epam.mentoring.auth.cache;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Eduard_Sardyka
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CachedValue {

    private int attempts;

    private LocalDateTime blockedTimestamp;

}