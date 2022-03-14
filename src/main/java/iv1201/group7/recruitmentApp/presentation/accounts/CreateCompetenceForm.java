/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iv1201.group7.recruitmentApp.presentation.accounts;

import java.util.ArrayList;

/**
 *
 * @author jonat
 */
public class CreateCompetenceForm
{
    ArrayList<Integer> yearsOfExperience;
    
    public void addYears(int years)
    {
        yearsOfExperience.add(years);
    }
    
    public ArrayList<Integer> getYearsOfExperience()
    {
        return yearsOfExperience;
    }
}
