package com.loanmanagement;

import com.loanmanagement.dao.LoanApplicationDao;
import com.loanmanagement.dao.LoanDao;
import com.loanmanagement.dao.LoanTypeDao;
import com.loanmanagement.model.Loan;
import com.loanmanagement.model.LoanApplication;
import com.loanmanagement.model.LoanType;
import com.loanmanagement.service.impl.LoanServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceImplTest {

    @Mock
    private LoanDao loanDaoMock;
    @Mock
    private LoanApplicationDao applicationDaoMock;
    @Mock
    private LoanTypeDao loanTypeDaoMock;

    @InjectMocks
    private LoanServiceImpl loanService;

    @Test
    void addLoan_ApprovedApplication_CalculatesInterestAndSaves() {
        Loan loan = new Loan();
        loan.setApplicationId(1);
        loan.setLoanTypeId(1);
        loan.setPrincipalAmount(10000);
        loan.setTenureMonths(12);

        LoanApplication mockApp = new LoanApplication();
        mockApp.setStatus("APPROVED");

        LoanType mockType = new LoanType();
        mockType.setInterestRate(10.0);

        when(applicationDaoMock.getLoanApplicationById(anyInt())).thenReturn(mockApp);
        when(loanTypeDaoMock.getLoanTypeById(anyInt())).thenReturn(mockType);

        loanService.addLoan(loan);

        verify(loanDaoMock, times(1)).addLoan(loan);
        assertEquals("ACTIVE", loan.getStatus());
        assertEquals(11000.0, loan.getTotalPayable());
    }
}