package projekti.repositories;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import projekti.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.domain.Account;

public interface PostRepository extends JpaRepository<Post, Long> {
    @EntityGraph(attributePaths = {"likers"})
    List<Post> findByTarget(Account target, Pageable pageable);
}
