package net.farid.project_test.Model;

/**
 * Created by Farid on 5/5/2017.
 */

public class one_way_result_item {
    int fid;
    int cid;
    String dep_date;
    String duration;
    String cname;
    String price;

    public one_way_result_item(int fid, int cid, String dep_date, String duration, String cname, String price) {
        this.fid = fid;
        this.cid = cid;
        this.dep_date = dep_date;
        this.duration = duration;
        this.cname = cname;
        this.price = price;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getDep_date() {
        return dep_date;
    }

    public void setDep_date(String dep_date) {
        this.dep_date = dep_date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
