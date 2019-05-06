package projekti.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
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
public class Image extends AbstractPersistable<Long> {
    
    private String description;
    
    @CreationTimestamp
    private LocalDateTime creationDate;
    
    @Column(length = 20971520)
    private byte[] content;
    
    @ManyToOne
    private Account imagePoster;
    
    @ManyToMany(mappedBy = "likers")
    private List<Image> test = new ArrayList<>();
    
    @ManyToMany
    private List<Account> likers = new ArrayList<>();
    
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();
}
