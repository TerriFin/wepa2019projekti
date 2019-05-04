package projekti.repositories;

import java.util.List;
import projekti.domain.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.domain.Account;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findByTarget(Account account);
    FriendRequest findByTargetAndRequester(Account target, Account requester);
}
