package net.farid.project_test.Model;

/**
 * Created by Farid on 4/1/2017.
 */

public class manage_client_item {
    private String clientName;
    private String clientEmail;

    public manage_client_item(String clientEmail, String clientName) {
        this.clientEmail = clientEmail;
        this.clientName = clientName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }
}
