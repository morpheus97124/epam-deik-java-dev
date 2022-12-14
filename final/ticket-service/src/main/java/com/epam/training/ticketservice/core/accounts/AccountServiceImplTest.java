package com.epam.training.ticketservice.core.accounts;

import com.epam.training.ticketservice.core.dto.AccountDto;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.OptionalInt;

//@ExtendWith(MockitoExtension.class)
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ComponentScan("com/epam/training/ticketservice/core/accounts")
@Component
@AllArgsConstructor
class AccountServiceImplTest {

    //@InjectMocks
    AccountService underTest;
    /*
    @Mock
    AccountRepository accountRepository;
    @BeforeEach
    void setUp() {
        accountRepository.save(new Account("kisbela19", "lamborghini", false));
        MockitoAnnotations.openMocks(this);
    }*/

    @Test
    void loginWithCorrectCredentials() {
        //Given
        Optional<AccountDto> accountDto = Optional.of(new AccountDto("kisbela19", Permission.USER, true));
        //When
        final Optional<AccountDto> actualAccountDto = underTest.login("kisbela19", "lamborghini");
        //Then
        Assertions.assertEquals(accountDto, actualAccountDto);
    }
    @Test
    void loginWithIncorrectCredentials() {
        //Given
        //When
        final Optional<AccountDto> actualAccountDto = underTest.login("kisbela9", "lamborghini");
        //Then
        Assertions.assertEquals(Optional.empty(), actualAccountDto);
    }
    @Test
    void loginWithPrivilegedCorrectCredentials() {
        //Given
        Optional<AccountDto> accountDto = Optional.of(new AccountDto("admin", Permission.ADMIN, true));
        //When
        final Optional<AccountDto> actualAccountDto = underTest.loginPriviliged("admin", "admin");
        //Then
        Assertions.assertEquals(accountDto, actualAccountDto);
    }
    @Test
    void loginWithPrivilegedIncorrectCredentials() {
        //Given
        //When
        final Optional<AccountDto> actualAccountDto = underTest.loginPriviliged("admin", "lamborghini");
        //Then
        Assertions.assertEquals(Optional.empty(), actualAccountDto);
    }
    @Test
    void isAdminOnlineTrue() {
        //Given
        underTest.loginPriviliged("admin", "admin");
        //When
        boolean actualBoolean = underTest.IsAdminOnline();
        //Then
        Assertions.assertEquals(true, actualBoolean);
    }
    @Test
    void isAdminOnlineFalse() {
        //Given
        //When
        boolean actualBoolean = underTest.IsAdminOnline();
        //Then
        Assertions.assertEquals(false, actualBoolean);
    }
    @Test
    void signOutAdmin() {
        //Given
        underTest.loginPriviliged("admin", "admin");
        //When
        underTest.signOutAdmin();
        boolean actualBoolean = underTest.IsAdminOnline();
        //Then
        Assertions.assertEquals(false, actualBoolean);
    }
}