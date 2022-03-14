package iv1201.group7.recruitmentApp.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import iv1201.group7.recruitmentApp.domain.RecruiterAccount;

/**
 * @author Group 7
 */
@Repository
@Transactional(propagation = Propagation.MANDATORY)
/**
 * Handles database calls.
 */
public interface RecruiterAccountRepository extends JpaRepository<RecruiterAccount, String> {

    /**
     * Finds an account by username.
     * @param username The username of the requested user.
     * @return A RecruiterAccount object with the user's attributes, or null
     *         if no such user exists.
     */
    RecruiterAccount findRecruiterByUsername(String username);

    @Override
    List<RecruiterAccount> findAll();

    @Override
    RecruiterAccount save(RecruiterAccount acct);
}
