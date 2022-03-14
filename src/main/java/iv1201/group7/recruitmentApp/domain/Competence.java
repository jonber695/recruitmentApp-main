/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iv1201.group7.recruitmentApp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author jonat
 */
@Entity
@Table(name = "COMPETENCE")
public class Competence implements CompetenceDTO
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "COMPETENCE_ID")
    private int id;
    
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "LANGUAGE")
    private String language;
    
    @Column(name = "DEFINITION_ID")
    private int def_id;
    
    @Override
    public int getId()
    {
        return id;
    }
    
    @Override
    public String getName()
    {
        return name;
    }
    
    @Override
    public String getLanguage()
    {
        return language;
    }
    
    @Override
    public int getDefId()
    {
        return def_id;
    }
    
    public void setDefId(int def_id){
        this.def_id = def_id;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setLanguage(String lang){
        this.language = lang;
    }
}
