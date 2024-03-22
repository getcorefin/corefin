package com.corefin.server.v1;

import com.corefin.server.exception.CorefinException;
import com.corefin.server.v1.request.MakePaymentRequest;
import com.corefin.server.v1.response.GetLoanResponse;
import org.corefin.calculator.Actuarial365Calculator;
import org.corefin.dao.LoanDao;
import org.corefin.dao.LoanInstallmentDao;
import org.corefin.dao.PaymentDao;
import org.corefin.dto.LoanInstallmentDto;
import org.corefin.dto.PaymentDto;
import org.corefin.model.common.InstallmentStatus;
import org.corefin.model.common.PaymentType;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class PaymentResourceManager {

    private static final Logger LOGGER = Logger.getLogger(PaymentResourceManager.class.getName());
    private Actuarial365Calculator calculator;
    private LoanDao loanDao;
    private LoanInstallmentDao loanInstallmentDao;
    private PaymentDao paymentDao;
    private LoanResourceManager loanResourceManager;

    @Inject
    public PaymentResourceManager(LoanDao loanDao,
                                  LoanInstallmentDao loanInstallmentDao,
                                  PaymentDao paymentDao,
                                  @Lazy LoanResourceManager loanResourceManager) {
        this.calculator = new Actuarial365Calculator();
        this.loanDao = loanDao;
        this.loanInstallmentDao = loanInstallmentDao;
        this.paymentDao = paymentDao;
        this.loanResourceManager = loanResourceManager;
    }

    // TODO: Handle off-cycle payments
    // TODO: do calculator integration for early, late payments
    // TODO: Run updateInstallments.
    // TODO: Compute outstandingPrincipal, check if it's == 0 then close the loan.
    // TODO: Pin to Loan's zone time (paymentDto)
    // TODO: Transactions
    public GetLoanResponse doMakePayment(String loanId, MakePaymentRequest makePaymentRequest) {
        validateMakePaymentRequest(loanId);
        List<LoanInstallmentDto> loanInstallmentDtoList = loanInstallmentDao.findByLoanId(loanId);
        Optional<LoanInstallmentDto> firstUnpaidInstallmentOptional =
                loanInstallmentDtoList.stream()
                        .filter(c -> c.status() == InstallmentStatus.OWED)
                        .findFirst();
        if (!firstUnpaidInstallmentOptional.isPresent()) {
            throw new CorefinException("No unpaid installments");
        }
        LoanInstallmentDto firstUnpaidInstallment = firstUnpaidInstallmentOptional.get();
//        validateMakePaymentRequestDate(makePaymentRequest, firstUnpaidInstallment);
        BigDecimal installmentAmount = firstUnpaidInstallment.interestAmount()
                .add(firstUnpaidInstallment.principalAmount());
//        validateMakePaymentRequestAmount(installmentAmount, makePaymentRequest.amount());
        String paymentId = UUID.randomUUID().toString();
        PaymentDto paymentDto = new PaymentDto(
                paymentId,
                loanId,
                makePaymentRequest.amount(),
                PaymentType.valueOf(makePaymentRequest.paymentType()),
                ZonedDateTime.now()
        );

        paymentDao.insert(paymentDto);
        loanInstallmentDao.updateInstallmentForPayment(
                new LoanInstallmentDto(
                        firstUnpaidInstallment.installmentId(),
                        InstallmentStatus.PAID,
                        firstUnpaidInstallment.loanId(),
                        firstUnpaidInstallment.numTerm(),
                        firstUnpaidInstallment.principalAmount(),
                        firstUnpaidInstallment.interestAmount(),
                        firstUnpaidInstallment.startDate(),
                        firstUnpaidInstallment.dueDate(),
                        firstUnpaidInstallment.endDate()
                )
        );

        return loanResourceManager.doGetLoan(loanId);
    }

    private void validateMakePaymentRequestDate(MakePaymentRequest makePaymentRequest,
                                                LoanInstallmentDto firstUnpaidInstallment) {
        if (!makePaymentRequest.paymentDateTime().toLocalDate().equals(firstUnpaidInstallment.dueDate())) {
            LOGGER.severe("Payment date %s and due date %s don't match. Your version of" +
                    "Corefin currently only supports on-time payments.".formatted(
                            makePaymentRequest.paymentDateTime(),
                            firstUnpaidInstallment.dueDate()
                    ));
            throw new CorefinException("Payment amount doesn't equal installment amount");
        }
    }
    private void validateMakePaymentRequestAmount(BigDecimal installmentAmount,
                                                  BigDecimal paymentAmount) {
        if (installmentAmount.compareTo(paymentAmount) != 0) {
            LOGGER.severe("Installment amount doesn't match with payment amount. " +
                    "Installment amount %s. Payment Amount: %s".formatted(installmentAmount, paymentAmount));
            throw new CorefinException("Payment amount doesn't equal installment amount");
        }
    }

    private void validateMakePaymentRequest(String loanId) {
        // Validate loanId exists
        try {
            loanDao.findById(loanId);
        } catch (Exception e) {
            LOGGER.severe("Could not find loan with id " + loanId);
            throw new CorefinException("Invalid loan id", e);
        }
    }

}
