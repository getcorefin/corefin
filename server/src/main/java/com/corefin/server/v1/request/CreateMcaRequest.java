package com.corefin.server.v1.request;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateMcaRequest(
        int term,
        @NotNull
        BigDecimal originatedAmount,
        @NotNull
        String currency,
        @NotNull
        BigDecimal factorRate,
        BigDecimal holdbackAmount,
        @NotNull
        BigDecimal holdbackPercentage,
        @NotNull
        String holdbackType,
        String externalReference,
        @NotNull
        LocalDate startDate,
        @NotNull
        LocalDate endDate,
        @NotNull
        String timezone,
        @NotNull
        String region,
        @NotNull
        String state
) {}
