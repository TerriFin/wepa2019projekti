package projekti.repositories;

import java.util.List;
import projekti.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.domain.Account;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByImagePoster(Account account);
}
