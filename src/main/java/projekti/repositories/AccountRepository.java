package projekti.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import projekti.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @EntityGraph(attributePaths = {"posts"})
    Account findByUsername(String username);

    @EntityGraph(attributePaths = {"posts"})
    Account findByIdentifier(String identifier);

    List<Account> findByUsernameContaining(String username);
}
