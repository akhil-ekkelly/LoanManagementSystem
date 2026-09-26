package com.loanmanagement;

import com.loanmanagement.dao.impl.LoanApplicationDaoImpl;
import com.loanmanagement.model.LoanApplication;
import com.loanmanagement.util.DBConnection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanApplicationDaoImplTest {

    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    @InjectMocks
    private LoanApplicationDaoImpl applicationDao;

    @Test
    void getLoanApplicationById_ValidId_ReturnsApp() throws SQLException {
        try (MockedStatic<DBConnection> mockedDb = mockStatic(DBConnection.class)) {
            mockedDb.when(DBConnection::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);

            // Use lenient() and anyString() to satisfy all ResultSet column fetches
            lenient().when(mockResultSet.getInt(anyString())).thenReturn(1);
            lenient().when(mockResultSet.getDouble(anyString())).thenReturn(5000.0);
            lenient().when(mockResultSet.getString(anyString())).thenReturn("APPROVED");

            LoanApplication result = applicationDao.getLoanApplicationById(1);

            assertNotNull(result);
            assertEquals("APPROVED", result.getStatus());
        }
    }
}