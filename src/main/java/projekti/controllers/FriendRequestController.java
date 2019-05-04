package projekti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import projekti.domain.Account;
import projekti.domain.FriendRequest;
import projekti.repositories.AccountRepository;
import projekti.repositories.FriendRequestRepository;

@Controller
public class FriendRequestController {

    @Autowired
    FriendRequestRepository friendRequestRepository;

    @Autowired
    AccountRepository accountRepository;

    @PostMapping("/addfriendrequest/{identifier}")
    public String addFriendRequest(Authentication auth, @PathVariable String identifier) {
        Account adder = accountRepository.findByUsername(auth.getName());
        Account target = accountRepository.findByIdentifier(identifier);

        FriendRequest friendRequest = friendRequestRepository.findByTargetAndRequester(adder, target);

        if (friendRequest == null) {
            if (!adder.getFriends().contains(target) && friendRequestRepository.findByTargetAndRequester(target, adder) == null) {
                FriendRequest newFriendRequest = new FriendRequest();
                newFriendRequest.setRequester(adder);
                newFriendRequest.setTarget(target);

                friendRequestRepository.save(newFriendRequest);
            }

            return "redirect:/users/" + identifier + "/0";
        } else {
            adder.getFriends().add(target);
            target.getFriends().add(adder);
            
            adder.getTest().add(adder);
            target.getTest().add(target);

            accountRepository.save(adder);
            accountRepository.save(target);

            friendRequestRepository.delete(friendRequest);

            return "redirect:/users/" + identifier + "/0";
        }
    }
    
    @PostMapping("/declinefriend/{senderIdentifier}")
    public String declineFriendRequest(Authentication auth, @PathVariable String senderIdentifier) {
        Account decliner = accountRepository.findByUsername(auth.getName());
        Account adder = accountRepository.findByIdentifier(senderIdentifier);
        
        friendRequestRepository.delete(friendRequestRepository.findByTargetAndRequester(decliner, adder));
        
        return "redirect:/users/" + decliner.getIdentifier() + "/0";
    }
    
    @PostMapping("/addfriend/{senderIdentifier}")
    public String acceptFriendRequest(Authentication auth, @PathVariable String senderIdentifier) {
        Account accepter = accountRepository.findByUsername(auth.getName());
        Account adder = accountRepository.findByIdentifier(senderIdentifier);
        
        accepter.getFriends().add(adder);
        adder.getFriends().add(accepter);
        
        accepter.getTest().add(accepter);
        adder.getTest().add(adder);
        
        accountRepository.save(accepter);
        accountRepository.save(adder);
        
        friendRequestRepository.delete(friendRequestRepository.findByTargetAndRequester(accepter, adder));
        
        return "redirect:/users/" + accepter.getIdentifier() + "/0";
    }
}
