package com.corefin.server.v1.request;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

public record CreateLoanRequest(
        @NotNull
        int term,
        @NotNull
        BigDecimal originatedAmount,
        @NotNull
        String currency,
        @NotNull
        BigDecimal targetInterestRate,
        @NotNull
        BigDecimal effectiveInterestRate,
        @NotNull
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
        String state,

        McaConfiguration mcaConfiguration
) {}
