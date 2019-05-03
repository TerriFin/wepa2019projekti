package projekti.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequest extends AbstractPersistable<Long> {
    
    @CreationTimestamp
    private LocalDateTime creationDate;
    
    @ManyToOne
    private Account requester;
    
    @ManyToOne
    private Account target;
}
