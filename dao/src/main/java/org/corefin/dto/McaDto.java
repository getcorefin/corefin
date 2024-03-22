package org.corefin.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
public record McaDto(String mcaId,
                     int term,
                     BigDecimal originatedAmount,
                     String currency,
                     BigDecimal factorRate,
                     BigDecimal holdbackAmount,
                     BigDecimal holdbackPercentage,
                     String holdbackType,
                     String externalReference,
                     LocalDate startDate,
                     LocalDate endDate,
                     String status,
                     String timezone,
                     String region,
                     String state
) {
}
