package iv1201.group7.recruitmentApp.presentation.accounts;

import javax.validation.constraints.NotBlank;

/**
 * @author Group 7
 * 
 * Represents a form for searching applicants.
 */
public class SearchForm {
    @NotBlank(message = "{search.surname.missing}")
    private String surname;

    /**
     * Gets the surname of the user.
     * @return The surname.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname of the user.
     * @param surname The surname of the searched applicant.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }
}
