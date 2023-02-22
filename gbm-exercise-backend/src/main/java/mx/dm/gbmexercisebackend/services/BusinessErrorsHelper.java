package mx.dm.gbmexercisebackend.services;

import mx.dm.gbmexercisebackend.models.Account;
import mx.dm.gbmexercisebackend.models.BusinessError;
import mx.dm.gbmexercisebackend.models.Order;
import mx.dm.gbmexercisebackend.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BusinessErrorsHelper {

    private final AccountRepository accountRepository;

    @Autowired
    private BusinessErrorsHelper(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<BusinessError> checkErrors(Long accountId, Order order) {
        List<BusinessError> businessErrors = new ArrayList<>();
        Optional<Account> _account = accountRepository.findById(accountId);
        if (_account.isEmpty()) {
            businessErrors.add(BusinessError.INVALID_OPERATION);
        } else {
            if (checkInsufficientFunds(_account.get(), order)) {
                businessErrors.add(BusinessError.INSUFFICIENT_BALANCE);
            }

            if (checkInsufficientStocks(_account.get(), order)) {
                businessErrors.add(BusinessError.INSUFFICIENT_STOCKS);
            }

            if (checkDuplicatedOperation(_account.get(), order)) {
                businessErrors.add(BusinessError.DUPLICATED_OPERATION);
            }
        }

        return businessErrors;
    }

    public boolean checkClosedMarket() {
        LocalTime now = LocalTime.now();
        LocalTime before = LocalTime.parse("05:59:59");
        LocalTime after = LocalTime.parse("15:00:00");

        return now.isBefore(before) || now.isAfter(after);
    }

    public boolean checkInsufficientFunds(Account account, Order order) {
        return account.getCash() < (order.getSharePrice() * order.getTotalShares());
    }

    public boolean checkInsufficientStocks(Account account, Order order) {
        Optional<Order> _order = findOrderByIssuerName(account, order);
        return _order.isPresent() && _order.get().getTotalShares() < order.getTotalShares();
    }

    public boolean checkDuplicatedOperation(Account account, Order order) {
        Optional<Order> _order = findOrderByIssuerName(account, order);
        return _order.isPresent()
                && _order.get().getTotalShares() == order.getTotalShares()
                && order.getTimestamp().toLocalDateTime().isBefore(_order.get().getTimestamp().toLocalDateTime().plusMinutes(5));
    }

    public Optional<Order> findOrderByIssuerName(Account account, Order order) {
        return account.getIssuers().stream()
                .filter(issuer -> issuer.getIssuerName().equals(order.getIssuerName()))
                .findFirst();
    }
}
