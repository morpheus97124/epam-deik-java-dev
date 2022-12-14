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
    void login() {
        //Given
        Optional<AccountDto> accountDto = Optional.of(new AccountDto("kisbela19", Permission.USER, true));
        //When
        final Optional<AccountDto> actualAccountDto = underTest.login("kisbela19", "lamborghini");
        //Then
        Assertions.assertEquals(accountDto, actualAccountDto);
    }

    @Test
    void loginPriviliged() {
    }

    @Test
    void isAdminOnline() {
    }

    @Test
    void signOutAdmin() {
    }
}