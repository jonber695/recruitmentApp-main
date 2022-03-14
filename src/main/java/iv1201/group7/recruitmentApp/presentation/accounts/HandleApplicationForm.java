package iv1201.group7.recruitmentApp.presentation.accounts;

import javax.validation.constraints.NotBlank;

/**
 * @author Group 7
 *
 * Represents a form for viewing the selected application.
 */
public class HandleApplicationForm {

    private int applicantId;
    private String newStatus;

    public int getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(int applicantId) {
        this.applicantId = applicantId;
    }

    public String getNewStatus() {
        return newStatus;
    }

    /**
     * Sets the surname of the user.
     *
     * @param surname The surname of the searched applicant.
     */
    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }
}
