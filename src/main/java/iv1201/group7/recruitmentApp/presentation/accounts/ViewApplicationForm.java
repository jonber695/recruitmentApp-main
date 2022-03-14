package iv1201.group7.recruitmentApp.presentation.accounts;

import javax.validation.constraints.NotBlank;

/**
 * @author Group 7
 * 
 * Represents a form for viewing the selected application.
 */
public class ViewApplicationForm {
    private int applicantId;

    /**
     * Gets the surname of the user.
     * @return The surname.
     */
    public int getApplicantId() {
        return applicantId;
    }

    /**
     * Sets the surname of the user.
     * @param surname The surname of the searched applicant.
     */
    public void setApplicantId(int applicantId) {
        this.applicantId = applicantId;
    }
}

