package net.farid.project_test.Model;

/**
 * Created by Farid on 4/29/2017.
 */

public class company_flight_item {
    private String f_id;
    private String f_from;
    private String f_to;
    private String f_departure_date;
    private String f_departure_time;
    //private String f_arrival_date;
    //private String f_arrival_time;
    private String f_duration;
    private String f_econ_seats;
    private String f_first_seats;
    private String f_seats;
    String price;
    private String f_econ_price;
    private String f_first_price;
    private String f_child_price;
    private String f_status;
    private String f_total_weight_value;
    private String f_extra_weight_price;
    private String f_company_id;
    private String c_type;

    public String getC_type() {
        return c_type;
    }

    public void setC_type(String c_type) {
        this.c_type = c_type;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    String c_name;
    String companyIcon;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public company_flight_item(String f_id, String f_from, String f_to, String f_departure_date,
                               String f_departure_time, String f_duration, String f_econ_seats, String f_first_seats, String f_seats,
                               String f_econ_price, String f_first_price, String f_child_price, String f_status,
                               String f_total_weight_value, String f_extra_weight_price, String f_company_id, String companyIcon) {
        this.f_id = f_id;
        this.f_from = f_from;
        this.f_to = f_to;
        this.f_departure_date = f_departure_date;
        this.f_departure_time = f_departure_time;
        this.f_duration = f_duration;
        this.f_econ_seats = f_econ_seats;
        this.f_first_seats = f_first_seats;
        this.f_seats = f_seats;
        this.f_econ_price = f_econ_price;
        this.f_first_price = f_first_price;
        this.f_child_price = f_child_price;
        this.f_status = f_status;
        this.f_total_weight_value = f_total_weight_value;
        this.f_extra_weight_price = f_extra_weight_price;
        this.f_company_id = f_company_id;
        this.companyIcon=companyIcon;
    }

    public company_flight_item() {

    }

    public String getCompanyIcon() {
        return companyIcon;
    }

    public void setCompanyIcon(String companyIcon) {
        this.companyIcon = companyIcon;
    }

    public String getF_id() {
        return f_id;
    }

    public void setF_id(String f_id) {
        this.f_id = f_id;
    }

    public String getF_from() {
        return f_from;
    }

    public void setF_from(String f_from) {
        this.f_from = f_from;
    }

    public String getF_to() {
        return f_to;
    }

    public void setF_to(String f_to) {
        this.f_to = f_to;
    }

    public String getF_departure_date() {
        return f_departure_date;
    }

    public void setF_departure_date(String f_departure_date) {
        this.f_departure_date = f_departure_date;
    }

    public String getF_departure_time() {
        return f_departure_time;
    }

    public void setF_departure_time(String f_departure_time) {
        this.f_departure_time = f_departure_time;
    }

    public String getF_duration() {
        return f_duration;
    }

    public void setF_duration(String f_duration) {
        this.f_duration = f_duration;
    }

    public String getF_econ_seats() {
        return f_econ_seats;
    }

    public void setF_econ_seats(String f_econ_seats) {
        this.f_econ_seats = f_econ_seats;
    }

    public String getF_first_seats() {
        return f_first_seats;
    }

    public void setF_first_seats(String f_first_seats) {
        this.f_first_seats = f_first_seats;
    }

    public String getF_seats() {
        return f_seats;
    }

    public void setF_seats(String f_seats) {
        this.f_seats = f_seats;
    }

    public String getF_econ_price() {
        return f_econ_price;
    }

    public void setF_econ_price(String f_econ_price) {
        this.f_econ_price = f_econ_price;
    }

    public String getF_first_price() {
        return f_first_price;
    }

    public void setF_first_price(String f_first_price) {
        this.f_first_price = f_first_price;
    }

    public String getF_child_price() {
        return f_child_price;
    }

    public void setF_child_price(String f_child_price) {
        this.f_child_price = f_child_price;
    }

    public String getF_status() {
        return f_status;
    }

    public void setF_status(String f_status) {
        this.f_status = f_status;
    }

    public String getF_total_weight_value() {
        return f_total_weight_value;
    }

    public void setF_total_weight_value(String f_total_weight_value) {
        this.f_total_weight_value = f_total_weight_value;
    }

    public String getF_extra_weight_price() {
        return f_extra_weight_price;
    }

    public void setF_extra_weight_price(String f_extra_weight_price) {
        this.f_extra_weight_price = f_extra_weight_price;
    }

    public String getF_company_id() {
        return f_company_id;
    }

    public void setF_company_id(String f_company_id) {
        this.f_company_id = f_company_id;
    }
}
