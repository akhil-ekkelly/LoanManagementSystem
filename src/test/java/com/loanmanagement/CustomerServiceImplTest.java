package com.loanmanagement;

import com.loanmanagement.dao.CustomerDao;
import com.loanmanagement.model.Customer;
import com.loanmanagement.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerDao customerDaoMock;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void getCustomerById_ValidId_ReturnsCustomer() {
        Customer mockCustomer = new Customer();
        mockCustomer.setCustomerId(1);
        mockCustomer.setFullName("John Doe");

        when(customerDaoMock.getCustomerById(anyInt())).thenReturn(mockCustomer);

        Customer result = customerService.getCustomerById(1);

        assertNotNull(result);
        assertEquals("John Doe", result.getFullName());
    }
}