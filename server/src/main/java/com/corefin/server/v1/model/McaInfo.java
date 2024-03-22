package com.corefin.server.v1.model;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record McaInfo (
        String loanId,
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
        String state,
        List<LoanInstallmentInfo> loanInstallments) {
}
