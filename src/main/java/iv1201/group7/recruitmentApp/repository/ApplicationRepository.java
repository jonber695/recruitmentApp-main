package iv1201.group7.recruitmentApp.repository;

import iv1201.group7.recruitmentApp.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Group 7
 */
@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface ApplicationRepository extends JpaRepository<Application, String>
{
    
    /**
     * Finds an application by application ID
     * @param id the id of the application that is requested
     * @return A Application object with the application's attributes, or null
     * if no such application exists.
     */
    Application findApplicationById(int id);
    
    /**
     * Finds an application by Applicant ID
     * @param id the id of the applicant whos application is requested
     * @return A Application object with the application's attributes, or null
     * if no such application exists.
     */
    Application findApplicationByApplicantId(int id);
    
    @Override
    Application save(Application application);
}
