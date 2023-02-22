package mx.dm.gbmexercisebackend;

import mx.dm.gbmexercisebackend.models.Account;
import mx.dm.gbmexercisebackend.models.Order;
import mx.dm.gbmexercisebackend.services.AccountService;
import mx.dm.gbmexercisebackend.services.BusinessErrorsHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
public class AccountTests {

    private final AccountService accountService;
    private final BusinessErrorsHelper businessErrorsHelper;

    static Account account;
    static Order order1;
    static Order order2;

    public AccountTests(AccountService accountService, BusinessErrorsHelper businessErrorsHelper) {
        this.accountService = accountService;
        this.businessErrorsHelper = businessErrorsHelper;
    }

    @BeforeAll
    static void init() {
        order1 = new Order(1L,
                Timestamp.valueOf("2023-02-12 13:33:45"),
                "BUY",
                "NTFX",
                2,
                100);
        order2 = new Order(2L,
                Timestamp.valueOf("2023-02-12 15:17:55"),
                "BUY",
                "AMZ",
                4,
                80);
        account = new Account(1L, 1000, List.of(order1, order2));
    }

    @Test
    public void should_check_for_insufficient_funds() {
        Order order = new Order(null,
                Timestamp.valueOf("2023-02-12 13:42:11"),
                "BUY",
                "ASD",
                5,
                500);
        assertTrue("Insufficient Funds", businessErrorsHelper.checkInsufficientFunds(account, order));
    }

    @Test

    public void should_check_for_insufficient_stocks() {
        Order order = new Order(1L,
                Timestamp.valueOf("2023-02-12 12:22:22"),
                "SELL",
                "NTFX",
                4,
                100);
        assertTrue("Insufficient Stocks", businessErrorsHelper.checkInsufficientStocks(account, order));
    }

    @Test
    public void should_check_for_duplicated_operation() {
        Order order = new Order(1L,
                Timestamp.valueOf("2023-02-12 13:34:22"),
                "BUY",
                "NTFX",
                2,
                100);
        assertTrue("Duplicated Operation", businessErrorsHelper.checkDuplicatedOperation(account, order));
    }
}
