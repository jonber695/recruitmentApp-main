/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iv1201.group7.recruitmentApp.domain;

/**
 *
 * @author jonat
 */
public interface CompetenceDTO
{
    /**
     * Gets the ID of the competence
     * @return the id of the compentence
     */
    int getId();
    
    /**
     * Gets the name of the competence
     * @return The name of the compentence
     */
    String getName();
    
    /**
     * Gets the ID of the language the competence is written in
     * @return The ID of the language the competence is written in
     */
    String getLanguage();
    
    /**
     * Gets the ID of the definition of the competence
     * @return The ID of the definition of the competence
     */
    int getDefId();
}
