/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iv1201.group7.recruitmentApp.application;

import iv1201.group7.recruitmentApp.domain.Competence;
import iv1201.group7.recruitmentApp.domain.CompetenceDTO;
import iv1201.group7.recruitmentApp.repository.CompetenceRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jonat
 */
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
@Service
public class CompetenceService
{
    @Autowired
    private CompetenceRepository competenceRepo;
    
    /**
     * Finds the competence with the specified id
     * @param id the specified id
     * @return The comptence in question, or null if no such comptence exists
     */
    public CompetenceDTO findCompetenceById(int id)
    {
        return competenceRepo.getCompetenceById(id);
    }
    
    /**
     * Finds the competence with the specified name
     * @param name The specified name
     * @return The competence in question, or null if no such competence exists
     */
    public CompetenceDTO findCompetenceByName(String name)
    {
        return competenceRepo.getCompetenceByName(name);
    }
    
    /**
     * Finds all competences in a list
     * @return all the competences
     */
    public List<Competence> getAllCompetencesWithTheSameLanguage(String language)
    {
        return competenceRepo.findMultipleCompetenceByLanguage(language);
    }
}
