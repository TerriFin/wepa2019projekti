package projekti.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import projekti.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    
}
