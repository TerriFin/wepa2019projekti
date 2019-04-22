package projekti;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account extends AbstractPersistable<Long> {

    private String username;
    private String password;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> authorities = new ArrayList<>();
    
    @OneToMany(mappedBy = "poster")
    private List<Post> posts = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "FRIENDS")
    private Set<Account> accounts = new HashSet<>();
    
    @ManyToMany(mappedBy = "accounts")
    private Set<Account> friends = new HashSet<>();
}
