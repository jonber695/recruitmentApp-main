package iv1201.group7.recruitmentApp.repository;

import iv1201.group7.recruitmentApp.domain.CompetenceProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jonat
 */
@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface CompetenceProfileRepository extends JpaRepository<CompetenceProfile, String>
{    
    @Override
    CompetenceProfile save(CompetenceProfile competenceProfile);
}
