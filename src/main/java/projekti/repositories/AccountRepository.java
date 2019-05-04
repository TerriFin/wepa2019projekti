package projekti.repositories;

import java.util.List;
import projekti.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);
    Account findByIdentifier(String identifier);
    List<Account> findByUsernameContaining(String username);
}
