package mx.dm.gbmexercisebackend.controllers;

import mx.dm.gbmexercisebackend.models.Account;
import mx.dm.gbmexercisebackend.models.BusinessError;
import mx.dm.gbmexercisebackend.models.Order;
import mx.dm.gbmexercisebackend.models.OrderResponse;
import mx.dm.gbmexercisebackend.services.AccountService;
import mx.dm.gbmexercisebackend.services.BusinessErrorsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final BusinessErrorsHelper businessErrorsHelper;

    @Autowired
    public AccountController(AccountService accountService, BusinessErrorsHelper businessErrorsHelper) {
        this.accountService = accountService;
        this.businessErrorsHelper = businessErrorsHelper;
    }

    @PostMapping
    public ResponseEntity<?> createInvestmentAccount(@RequestBody Account cash) {
        if (businessErrorsHelper.checkClosedMarket()) {
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setBusinessErrors(List.of(BusinessError.CLOSED_MARKET));
            return ResponseEntity.badRequest().body(orderResponse);
        }

        Optional<Account> _account = Optional.ofNullable(accountService.createInvestmentAccount(cash));
        return _account.map(account -> ResponseEntity.ok().body(account))
                .orElse(ResponseEntity.internalServerError().build());
    }

    @PostMapping("/{id}/orders")
    public ResponseEntity<?> sendOrdersToAccountWithId(@PathVariable Long id, @RequestBody Order order) {
        Optional<OrderResponse> _orderResponse = Optional.ofNullable(accountService.applyOrderToAccount(id, order));
        return _orderResponse.map(orderResponse -> ResponseEntity.ok().body(_orderResponse))
                .orElse(ResponseEntity.internalServerError().build());
    }
}
