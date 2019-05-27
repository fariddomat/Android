package net.farid.project_test.Model;

/**
 * Created by Farid on 4/1/2017.
 */

public class manage_compnay_items {

    private int    cID;
    private String cName;
    private String cEmail;
    private String cDetails;
    private String cIcon;
    String cPassword;

    public manage_compnay_items(int cID, String cName, String cEmail,String cPassword, String cDetails, String cIcon) {
        this.cID = cID;
        this.cName = cName;
        this.cEmail = cEmail;
        this.cDetails = cDetails;
        this.cIcon = cIcon;
    }

    public manage_compnay_items() {

    }

    public String getcPassword() {
        return cPassword;
    }

    public void setcPassword(String cPassword) {
        this.cPassword = cPassword;
    }

    public int getcID() {
        return cID;
    }
    public String getcID2() {
        return String.valueOf(cID);
    }

    public void setcID(int cID) {
        this.cID = cID;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcEmail() {
        return cEmail;
    }

    public void setcEmail(String cEmail) {
        this.cEmail = cEmail;
    }

    public String getcDetails() {
        return cDetails;
    }

    public void setcDetails(String cDetails) {
        this.cDetails = cDetails;
    }

    public String getcIcon() {
        return cIcon;
    }

    public void setcIcon(String cIcon) {
        this.cIcon = cIcon;
    }
}
