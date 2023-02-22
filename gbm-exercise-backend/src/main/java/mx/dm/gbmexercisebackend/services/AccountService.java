package mx.dm.gbmexercisebackend.services;

import mx.dm.gbmexercisebackend.models.Account;
import mx.dm.gbmexercisebackend.models.BusinessError;
import mx.dm.gbmexercisebackend.models.Order;
import mx.dm.gbmexercisebackend.models.OrderResponse;
import mx.dm.gbmexercisebackend.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final BusinessErrorsHelper businessErrorsHelper;

    @Autowired
    public AccountService(AccountRepository accountRepository, BusinessErrorsHelper businessErrorsHelper) {
        this.accountRepository = accountRepository;
        this.businessErrorsHelper = businessErrorsHelper;
    }

    public Account createInvestmentAccount(Account cash) {
        return accountRepository.save(cash);
    }

    public OrderResponse applyOrderToAccount(Long id, Order order) {
        Optional<Account> _account = accountRepository.findById(id);

        if (_account.isPresent()) {
            List<BusinessError> businessErrors = businessErrorsHelper.checkErrors(id, order);

            if (businessErrors.isEmpty()) {
                Optional<Order> _issuer = _account.get().getIssuers().stream()
                        .filter(issuer -> issuer.getIssuerName().equals(order.getIssuerName()))
                        .findFirst();

                if (order.getOperation().equals("BUY")) {
                    _account.get().setCash(_account.get().getCash() - (order.getSharePrice() * order.getTotalShares()));
                    if (_issuer.isEmpty()) _account.get().getIssuers().add(order);
                    else {
                        _account.get().getIssuers().forEach(issuer -> {
                            if (issuer.getIssuerName().equals(order.getIssuerName())) {
                                var sumShares = issuer.getTotalShares() + order.getTotalShares();
                                issuer.setTotalShares(sumShares);
                            }
                        });
                    }
                } else if(order.getOperation().equals("SELL")) {
                    _account.get().setCash(_account.get().getCash() + (order.getSharePrice() * order.getTotalShares()));
                    if (_issuer.isPresent()) {
                        _account.get().getIssuers().forEach(issuer -> {
                            if (issuer.getIssuerName().equals(order.getIssuerName())) {
                                var lessShares = issuer.getTotalShares() - order.getTotalShares();
                                issuer.setTotalShares(lessShares);
                            }
                        });
                    } else {
                        businessErrors.add(BusinessError.INVALID_OPERATION);
                    }
                } else {
                    businessErrors.add(BusinessError.INVALID_OPERATION);
                }

                accountRepository.save(_account.get());
                return new OrderResponse(_account.get(), null);
            }

            return new OrderResponse(_account.get(), businessErrors);
        }

        return null;
    }
}
