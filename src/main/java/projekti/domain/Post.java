package projekti.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post extends AbstractPersistable<Long> {
    
    private String content;
    
    @CreationTimestamp
    private LocalDateTime creationDate;
    
    @ManyToOne
    private Account target;

    @ManyToOne
    private Account poster;
    
    @ManyToMany(mappedBy = "likers")
    private List<Post> test = new ArrayList<>();
    
    @ManyToMany
    private List<Account> likers = new ArrayList<>();
    
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();
}
