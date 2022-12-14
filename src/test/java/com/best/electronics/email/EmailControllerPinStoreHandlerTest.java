//package com.best.electronics.email;
//
//import com.best.electronics.properties.ReportProperties;
//import com.best.electronics.report.generator.GenerateCSVReport;
//import com.best.electronics.report.generator.ReportGeneratorService;
//import com.best.electronics.repository.PasswordRepository;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.jupiter.api.Assertions;
//import org.mockito.MockedConstruction;
//import org.mockito.Mockito;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//public class EmailControllerPinStoreHandlerTest {
//
//    EmailControllerPinStoreHandler emailControllerPinStoreHandler;
//
//    @Before
//    public void init(){
//        emailControllerPinStoreHandler = new EmailControllerPinStoreHandler();
//    }
//    @Test
//    public void storeToDBSuccessTest(){
//        try (MockedConstruction<PasswordRepository> mocked = Mockito.mockConstruction(PasswordRepository.class,
//                (mock, context) -> when(mock.storePinToDB(123456, "admin@gmail.com")).thenReturn(true))) {
//            boolean status = emailControllerPinStoreHandler.storePinToDB(123456,"admin@gmail.com");
//            Assertions.assertEquals(true,status);
//        }
//    }
//
//    @Test
//    public void storeToDBFailureTest(){
//        try (MockedConstruction<PasswordRepository> mocked = Mockito.mockConstruction(PasswordRepository.class,
//                (mock, context) -> when(mock.storePinToDB(123456, "admin@gmail.com")).thenReturn(false))) {
//            boolean status = emailControllerPinStoreHandler.storePinToDB(123456,"admin@gmail.com");
//            Assertions.assertEquals(false,status);
//        }
//    }
//}
