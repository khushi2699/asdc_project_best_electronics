package com.best.electronics.email;

import com.best.electronics.repository.PasswordRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

@SpringBootTest
public class EmailControllerPinStoreHandlerTest {

    EmailControllerPinStoreHandler emailControllerPinStoreHandler;

    @Before
    public void init(){
        emailControllerPinStoreHandler = new EmailControllerPinStoreHandler();
    }
    @Test
    public void storeToDBSuccessAdminTest(){
        try (MockedConstruction<PasswordRepository> mocked = Mockito.mockConstruction(PasswordRepository.class,
                (mock, context) -> when(mock.storePinToDB(123456, "admin@gmail.com","Admin")).thenReturn(true))) {
            boolean status = emailControllerPinStoreHandler.storePinToDB(123456,"admin@gmail.com","Admin");
            Assertions.assertEquals(true,status);
        }
    }

    @Test
    public void storeToDBFailureAdminTest(){
        try (MockedConstruction<PasswordRepository> mocked = Mockito.mockConstruction(PasswordRepository.class,
                (mock, context) -> when(mock.storePinToDB(123456, "admin@gmail.com","Admin")).thenReturn(false))) {
            boolean status = emailControllerPinStoreHandler.storePinToDB(123456,"admin@gmail.com","Admin");
            Assertions.assertEquals(false,status);
        }
    }

    @Test
    public void storeToDBSuccessUserTest(){
        try (MockedConstruction<PasswordRepository> mocked = Mockito.mockConstruction(PasswordRepository.class,
                (mock, context) -> when(mock.storePinToDB(123456, "user@gmail.com","User")).thenReturn(true))) {
            boolean status = emailControllerPinStoreHandler.storePinToDB(123456,"user@gmail.com","User");
            Assertions.assertEquals(true,status);
        }
    }

    @Test
    public void storeToDBFailureUserTest(){
        try (MockedConstruction<PasswordRepository> mocked = Mockito.mockConstruction(PasswordRepository.class,
                (mock, context) -> when(mock.storePinToDB(123456, "user@gmail.com","User")).thenReturn(false))) {
            boolean status = emailControllerPinStoreHandler.storePinToDB(123456,"user@gmail.com","User");
            Assertions.assertEquals(false,status);
        }
    }
}
