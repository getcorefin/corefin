package com.corefin.server.v1;

import com.corefin.server.transform.LoanInstallmentTransformer;
import com.corefin.server.transform.LoanTransformer;
import com.corefin.server.v1.model.McaInfo;
import com.corefin.server.v1.request.CreateLoanRequest;
import com.corefin.server.v1.request.CreateMcaRequest;
import com.corefin.server.v1.response.GetLoanResponse;
import org.corefin.calculator.Actuarial365Calculator;
import org.corefin.calculator.model.Installment;
import org.corefin.dao.LoanDao;
import org.corefin.dao.LoanInstallmentDao;
import org.corefin.dao.McaDao;
import org.corefin.dao.PaymentDao;
import org.corefin.dto.LoanDto;
import org.corefin.dto.LoanInstallmentDto;
import org.corefin.dto.McaDto;
import org.corefin.model.common.InstallmentStatus;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class McaResourceManager {

    private PaymentDao paymentDao;
    private McaDao mcaDao;
    private LoanDao loanDao;
    private Actuarial365Calculator calculator;
    private LoanInstallmentDao loanInstallmentDao;

    @Inject
    public McaResourceManager(PaymentDao paymentDao,
                              McaDao mcaDao,
                              LoanInstallmentDao loanInstallmentDao) {
        this.mcaDao = mcaDao;
        this.paymentDao = paymentDao;
        this.loanInstallmentDao = loanInstallmentDao;
        this.calculator = new Actuarial365Calculator();
    }

    public String doGetMca(String loanId) {
        // if mca type == amount, then we return installments
        // if mca type is variable, then no installments (since it's unlimited term)
        return "";
    }

    public McaInfo createMca(CreateMcaRequest createMcaRequest) {

        String mcaId = UUID.randomUUID().toString();
        McaDto mcaDto = new McaDto(
                mcaId,
                createMcaRequest.term(),
                createMcaRequest.originatedAmount(),
                createMcaRequest.currency(),
                createMcaRequest.factorRate(),
                createMcaRequest.holdbackAmount(),
                createMcaRequest.holdbackPercentage(),
                createMcaRequest.holdbackType(),
                createMcaRequest.externalReference(),
                createMcaRequest.startDate(),
                createMcaRequest.endDate(),
                "CREATED",
                createMcaRequest.timezone(),
                createMcaRequest.region(),
                createMcaRequest.state()
        );

        List<Installment> newInstallments = calculator.newInstallments(
                LoanTransformer.transformForNewInstallmentsForMca(createMcaRequest));
        List<LoanInstallmentDto> loanInstallmentDtos = LoanInstallmentTransformer.transform(newInstallments, loanId);
        loanInstallmentDtos.forEach(
                loanInstallmentDto ->
                        loanInstallmentDao.insert(loanInstallmentDto)
        );
        mcaDao.insert(mcaDto);

    }








//    }
}
