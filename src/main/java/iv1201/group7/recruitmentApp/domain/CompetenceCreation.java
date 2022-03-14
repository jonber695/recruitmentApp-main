/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iv1201.group7.recruitmentApp.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jonat
 */
public class CompetenceCreation
{
    List<Competence> competences;
    List<Double> yearsOfExperience;
    
    public void addCompetence(Competence competenceToBeAdded)
    {
        competences.add(competenceToBeAdded);
    }
    
    public CompetenceCreation(List<Competence> competence)
    {
        competences = competence;
        yearsOfExperience = new ArrayList<>();
        
    }
    
    public List<Competence> getCompetences()
    {
        return competences;
    }
    
    public void addYearsOfExperience(double year)
    {
        yearsOfExperience.add(year);
    }
    
    public List<Double> getYearsOfExperience()
    {
        return yearsOfExperience;
    }
}
