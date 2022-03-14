/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iv1201.group7.recruitmentApp.repository;

import iv1201.group7.recruitmentApp.domain.Competence;
import java.util.List;
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
public interface CompetenceRepository extends JpaRepository<Competence, String>
{
    /**
     * Finds a competence by the specified id
     * @param id the specified ID
     * @return The specified competence, or null if no such competence exists
     */
    Competence getCompetenceById(int id);
    
    /**
     * Finds a competence by the specified name
     * @param name The specified name
     * @return The spcified competence, or null if no such competence exists
     */
    Competence getCompetenceByName(String name);
    
    /**
     * Finds all the competences that have the specified language
     * @param language 
     * @return A list of competences 
     */
    List<Competence> findMultipleCompetenceByLanguage(String language);
    
    @Override
    Competence save(Competence competence);
    
    @Override
    List<Competence> findAll();
}
