package com.loanmanagement;

import com.loanmanagement.dao.LoanTypeDao;
import com.loanmanagement.model.LoanType;
import com.loanmanagement.service.impl.LoanTypeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanTypeServiceImplTest {

    @Mock
    private LoanTypeDao loanTypeDaoMock;

    @InjectMocks
    private LoanTypeServiceImpl loanTypeService;

    @Test
    void getLoanTypeById_ValidId_ReturnsType() {
        LoanType mockType = new LoanType();
        mockType.setLoanTypeId(1);
        mockType.setName("Home Loan");

        when(loanTypeDaoMock.getLoanTypeById(anyInt())).thenReturn(mockType);

        LoanType result = loanTypeService.getLoanTypeById(1);

        assertNotNull(result);
        assertEquals("Home Loan", result.getName());
    }
}