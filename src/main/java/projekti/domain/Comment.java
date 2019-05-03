package projekti.domain;

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
public class Comment extends AbstractPersistable<Long> {

    private String comment;
    
    @CreationTimestamp
    private LocalDateTime creationDate;
    
    @ManyToOne
    private Account commentor;
}
