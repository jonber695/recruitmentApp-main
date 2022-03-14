package iv1201.group7.recruitmentApp.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import iv1201.group7.recruitmentApp.domain.RecruiterAccountDTO;
import iv1201.group7.recruitmentApp.repository.RecruiterAccountRepository;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Group 7
 */
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
@Service
/**
 * Provides methods used by the Recruiter domain.
 */
public class RecruiterService {
    @Autowired
    private RecruiterAccountRepository recruiterAccountRepo;
    
    /**
     *
     * Finds a recruiter with the specified username and password.
     * 
     * @param username The specified username.
     * @param password The specified password.
     * @return A RecruiterAccountDTO with the attributes of the found user, or 
     * null if the user is not found.
     * 
     */
    public UserDetails findRecruiter(String username, String password) {
        //RecruiterAccountDTO recruiterAcct = recruiterAccountRepo.findRecruiterByUsername(username);
        UserDetails recruiterAcct = recruiterAccountRepo.findRecruiterByUsername(username);
        System.out.println("------inside RecruiterService--------------");
        if (recruiterAcct == null || !(recruiterAcct.getPassword().equals(password))){
            return null;
        }
        return recruiterAcct;
    }
}
