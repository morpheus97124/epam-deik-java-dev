package com.epam.training.ticketservice.core.accounts;

import com.epam.training.ticketservice.core.dto.AccountDto;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
class AccountServiceImplTest {

    AccountService underTest;

    @Mock
    AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new AccountServiceImpl(accountRepository);

    }

    @Test
    void loginWithCorrectCredentials() {
        //Given
        Account account = new Account("kisbela19", "lamborghini", false);
        account.setLoggedIn(true);
        BDDMockito.given(accountRepository.findByNameAndPassword("kisbela19", "lamborghini")).willReturn(Optional.of(account));//repository mocking
        Optional<AccountDto> expectedUser = Optional.of(new AccountDto("kisbela19", Permission.USER, true));
        //When
        final Optional<AccountDto> actualAccountDto = underTest.login("kisbela19", "lamborghini");
        //Then
        Assertions.assertEquals(expectedUser, actualAccountDto);
        Mockito.verify(accountRepository).save(account);
    }
    @Test
    void loginWithIncorrectCredentials() {
        //Given
        Account account = new Account("kisbela19", "lamborghini", false);
        account.setLoggedIn(true);
        BDDMockito.given(accountRepository.findByNameAndPassword("kisbela19", "lamborghini")).willReturn(Optional.of(account));//repository mocking
        Optional<AccountDto> expectedUser = Optional.empty();
        //When
        final Optional<AccountDto> actualAccountDto = underTest.login("kisbela1", "lamborghini");
        //Then
        Assertions.assertEquals(expectedUser, actualAccountDto);
    }
    @Test
    void loginWithPrivilegedCorrectCredentials() {
        //Given
        Account account = new Account("admin", "admin", true);
        account.setLoggedIn(true);
        BDDMockito.given(accountRepository.findByNameAndPassword("admin", "admin")).willReturn(Optional.of(account));
        Optional<AccountDto> expectedUser = Optional.of(new AccountDto("admin", Permission.ADMIN, true));
        //When
        final Optional<AccountDto> actualAccountDto = underTest.loginPriviliged("admin", "admin");
        //Then
        Assertions.assertEquals(expectedUser, actualAccountDto);
        Mockito.verify(accountRepository).save(account);
    }
    @Test
    void loginWithPrivilegedIncorrectCredentials() {
        //Given
        Account account = new Account("admin", "admin", true);
        account.setLoggedIn(true);
        BDDMockito.given(accountRepository.findByNameAndPassword("admin", "admin")).willReturn(Optional.of(account));//repository mocking
        Optional<AccountDto> expectedUser = Optional.empty();
        //When
        final Optional<AccountDto> actualAccountDto = underTest.loginPriviliged("admin", "12345");
        //Then
        Assertions.assertEquals(expectedUser, actualAccountDto);
    }
    @Test
    void isAdminOnlineTrue() {
        //Given
        Account account = new Account("admin", "admin", true);
        account.setLoggedIn(true);
        BDDMockito.given(accountRepository.findByNameAndPassword("admin", "admin")).willReturn(Optional.of(account));
        //When
        boolean actualBoolean = underTest.IsAdminOnline();
        //Then
        Assertions.assertEquals(true, actualBoolean);
    }
    @Test
    void isAdminOnlineFalse() {
        //Given
        Account account = new Account("admin", "admin", true);
        account.setLoggedIn(false);
        BDDMockito.given(accountRepository.findByNameAndPassword("admin", "admin")).willReturn(Optional.of(account));
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