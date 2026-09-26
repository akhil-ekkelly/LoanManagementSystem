package com.loanmanagement;

import com.loanmanagement.dao.CustomerDao;
import com.loanmanagement.dao.LoanApplicationDao;
import com.loanmanagement.dao.LoanTypeDao;
import com.loanmanagement.model.Customer;
import com.loanmanagement.model.LoanApplication;
import com.loanmanagement.model.LoanType;
import com.loanmanagement.service.impl.ApplicationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanApplicationServiceImplTest {

    @Mock
    private LoanApplicationDao applicationDaoMock;
    @Mock
    private CustomerDao customerDaoMock;
    @Mock
    private LoanTypeDao loanTypeDaoMock;

    @InjectMocks
    private ApplicationServiceImpl applicationService;

    @Test
    void addApplication_ValidData_CallsDao() {
        LoanApplication app = new LoanApplication();
        app.setCustomerId(1);
        app.setLoanTypeId(1);
        app.setRequestedAmount(5000);
        app.setTenureMonths(12);

        Customer mockCustomer = new Customer();
        mockCustomer.setKycStatus("VERIFIED");

        LoanType mockLoanType = new LoanType();
        mockLoanType.setStatus("ACTIVE");
        mockLoanType.setMinAmount(1000);
        mockLoanType.setMaxAmount(10000);
        mockLoanType.setMaxTenureMonths(24);

        // Using anyInt() prevents null returns causing validation failures
        when(customerDaoMock.getCustomerById(anyInt())).thenReturn(mockCustomer);
        when(loanTypeDaoMock.getLoanTypeById(anyInt())).thenReturn(mockLoanType);

        applicationService.addApplication(app);

        verify(applicationDaoMock, times(1)).addLoanApplication(app);
        assertEquals("PENDING", app.getStatus());
    }
}