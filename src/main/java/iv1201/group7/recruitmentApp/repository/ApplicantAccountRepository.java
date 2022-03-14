/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iv1201.group7.recruitmentApp.repository;

import iv1201.group7.recruitmentApp.domain.ApplicantAccount;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Group 7
 */
@Repository
@Transactional(propagation = Propagation.MANDATORY)
/**
 * Handles database calls.
 */
public interface ApplicantAccountRepository extends JpaRepository<ApplicantAccount, String> {
   
    /**
     * Finds an account by username.
     * @param email The username of the requested user.
     * @return An ApplicantAccount object with the user's attributes, or null
     *         if no such user exists.
     */
    ApplicantAccount findApplicantByEmail(String email);
    
    ApplicantAccount findApplicantBySurname(String surname);
    
    ArrayList<ApplicantAccount> findMultipleApplicantsBySurname(String surname);

    @Override
    List<ApplicantAccount> findAll();

    @Override
    ApplicantAccount save(ApplicantAccount acct);
}
