/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iv1201.group7.recruitmentApp.application;

import iv1201.group7.recruitmentApp.domain.ApplicantAccountDTO;
import iv1201.group7.recruitmentApp.domain.Competence;
import iv1201.group7.recruitmentApp.domain.CompetenceProfile;
import iv1201.group7.recruitmentApp.repository.CompetenceProfileRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jonat
 */
@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
public class CompetenceProfileService
{
    @Autowired
    private CompetenceProfileRepository competenceProfileRepo;
    
    /**
     * Saves a list of competence profiles that are saved in the database
     * @param experience A list of doubles that represent the amount of experience an applicant has with the different competences
     * @param applicant The specified applicant
     * @param competences A list of Competence objects representing the competences in the database
     * @return  A list of Competences profiles that have been saved in the database, or null if none were saved
     */
    public List<CompetenceProfile> createCompetenceProfile(List<Double> experience, ApplicantAccountDTO applicant, List<Competence> competences)
    {
        ArrayList<CompetenceProfile> competenceProfile = new ArrayList<>();
        for(int i = 0; i < experience.size(); i++)
        {
            competenceProfile.add(new CompetenceProfile(competences.get(i).getId(), applicant.getId(), new BigDecimal(experience.get(i).toString())));
        }
            
        return competenceProfileRepo.saveAll(competenceProfile);
    }
}
