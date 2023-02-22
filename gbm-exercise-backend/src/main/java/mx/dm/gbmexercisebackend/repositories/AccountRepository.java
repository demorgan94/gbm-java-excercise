package mx.dm.gbmexercisebackend.repositories;

import mx.dm.gbmexercisebackend.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
