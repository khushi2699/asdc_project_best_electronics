package com.best.electronics.register;

import com.best.electronics.database.AdminMockDatabasePersistence;
import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.model.Admin;
import com.best.electronics.state.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdminRegisterHandlerTest {

    private static IDatabasePersistence mockDatabasePersistence;

    @BeforeAll
    public static void init() {
        mockDatabasePersistence = new AdminMockDatabasePersistence();
    }

    @Test
    public void registerSuccessTest() throws Exception {
        RegisterAuthHandler registerHandler = new EmailRegisterValidation("{call get_admin_emailAddress()}", mockDatabasePersistence);
        registerHandler.setNextHandler(new UserNameRegisterValidation()).setNextHandler(new PasswordRegisterValidation());

        Admin admin = new Admin();
        admin.setFirstName("Test");
        admin.setLastName("Admin");
        admin.setEmailAddress("test@gmail.com");
        admin.setPassword("Newuser@123");
        admin.setConfirmPassword("Newuser@123");
        admin.setGender("Female");

        State registerState = registerHandler.validate(admin, "admin");
        Assertions.assertEquals(registerState.getNextPage(), "adminRegisterSuccess");
        Assertions.assertEquals(registerState.getStatus(), "Registered Successfully");
    }

    @Test
    public void registerFailTestExistingEmail() throws Exception {
        RegisterAuthHandler registerHandler = new EmailRegisterValidation("{call get_admin_emailAddress()}", mockDatabasePersistence);
        registerHandler.setNextHandler(new UserNameRegisterValidation()).setNextHandler(new PasswordRegisterValidation());

        Admin admin = new Admin();
        admin.setFirstName("Test");
        admin.setLastName("Admin");
        admin.setEmailAddress("admin@gmail.com");
        admin.setPassword("Newuser@123");
        admin.setConfirmPassword("Newuser@123");
        admin.setGender("Female");

        State registerState = registerHandler.validate(admin, "admin");
        Assertions.assertEquals(registerState.getNextPage(), "adminRegistrationForm");
        Assertions.assertEquals(registerState.getStatus(), "Email address already Exists!");
    }

    @Test
    public void registerFailTestWrongFirstName() throws Exception {
        RegisterAuthHandler registerHandler = new EmailRegisterValidation("{call get_admin_emailAddress()}", mockDatabasePersistence);
        registerHandler.setNextHandler(new UserNameRegisterValidation()).setNextHandler(new PasswordRegisterValidation());

        Admin admin = new Admin();
        admin.setFirstName("T");
        admin.setLastName("User");
        admin.setEmailAddress("test@gmail.com");
        admin.setPassword("Newuser@123");
        admin.setConfirmPassword("Newuser@123");
        admin.setGender("Female");

        State registerState = registerHandler.validate(admin, "admin");
        Assertions.assertEquals(registerState.getNextPage(), "adminRegistrationForm");
        Assertions.assertEquals(registerState.getStatus(), "Either firstName or lastName are not in correct format!");
    }

    @Test
    public void registerFailTestWrongLastName() throws Exception {
        RegisterAuthHandler registerHandler = new EmailRegisterValidation("{call get_admin_emailAddress()}", mockDatabasePersistence);
        registerHandler.setNextHandler(new UserNameRegisterValidation()).setNextHandler(new PasswordRegisterValidation());

        Admin admin = new Admin();
        admin.setFirstName("Test");
        admin.setLastName("U");
        admin.setEmailAddress("test@gmail.com");
        admin.setPassword("Newuser@123");
        admin.setConfirmPassword("Newuser@123");
        admin.setGender("Female");

        State registerState = registerHandler.validate(admin, "admin");
        Assertions.assertEquals(registerState.getNextPage(), "adminRegistrationForm");
        Assertions.assertEquals(registerState.getStatus(), "Either firstName or lastName are not in correct format!");
    }

    @Test
    public void registerFailInvalidPassword() throws Exception {
        RegisterAuthHandler registerHandler = new EmailRegisterValidation("{call get_admin_emailAddress()}", mockDatabasePersistence);
        registerHandler.setNextHandler(new UserNameRegisterValidation()).setNextHandler(new PasswordRegisterValidation());

        Admin admin = new Admin();
        admin.setFirstName("Test");
        admin.setLastName("User");
        admin.setEmailAddress("test@gmail.com");
        admin.setPassword("Newuser123");
        admin.setConfirmPassword("Newuser123");
        admin.setGender("Female");

        State registerState = registerHandler.validate(admin, "admin");
        Assertions.assertEquals(registerState.getNextPage(), "adminRegistrationForm");
        Assertions.assertEquals(registerState.getStatus(), "Password must contain at least one " +
                "uppercase, lowercase, number and special character. Min 8 length!");
    }

    @Test
    public void registerFailMismatchPassword() throws Exception {
        RegisterAuthHandler registerHandler = new EmailRegisterValidation("{call get_admin_emailAddress()}", mockDatabasePersistence);
        registerHandler.setNextHandler(new UserNameRegisterValidation()).setNextHandler(new PasswordRegisterValidation());

        Admin admin = new Admin();
        admin.setFirstName("Test");
        admin.setLastName("User");
        admin.setEmailAddress("test@gmail.com");
        admin.setPassword("Newuser@123");
        admin.setConfirmPassword("Newuser@125");
        admin.setGender("Female");

        State registerState = registerHandler.validate(admin, "admin");
        Assertions.assertEquals(registerState.getNextPage(), "adminRegistrationForm");
        Assertions.assertEquals(registerState.getStatus(), "The re-entered password and password are not matching!");
    }

}
