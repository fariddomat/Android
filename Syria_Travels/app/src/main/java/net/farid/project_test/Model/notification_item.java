package net.farid.project_test.Model;

/**
 * Created by Farid on 7/7/2017.
 */

public class notification_item {
    String company_name,date,time,details,icon,m_is_read;

    public String getM_is_read() {
        return m_is_read;
    }

    public void setM_is_read(String m_is_read) {
        this.m_is_read = m_is_read;
    }

    public notification_item() {
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
