package edu.gatech.seclass.jobcompare6300;

import java.io.Serializable;

public class Job implements Serializable {

    private int id;
    private String title;
    private String company;
    private String city;
    private String state;
    private int COL;
    private float salary;
    private float signBonus;
    private float yearBonus;
    private float benefits;
    private int leaveTime;
    private boolean isOffer;

    //constructor
    public Job(int id, String title, String company, String city, String state, int COL,
    float salary, float signBonus, float yearBonus, float benefits, int leaveTime, boolean isOffer) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.city = city;
        this.state = state;
        this.COL = COL;
        this.salary = salary;
        this.signBonus = signBonus;
        this.yearBonus = yearBonus;
        this.benefits = benefits;
        this.leaveTime = leaveTime;
        this.isOffer = isOffer;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", company='" + company + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", COL=" + COL +
                ", salary=" + salary +
                ", signBonus=" + signBonus +
                ", yearBonus=" + yearBonus +
                ", benefits=" + benefits +
                ", leaveTime=" + leaveTime +
                ", isOffer=" + isOffer +
                '}';
    }

    public Job() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getCOL() {
        return COL;
    }

    public void setCOL(int COL) {
        this.COL = COL;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public float getSignBonus() {
        return signBonus;
    }

    public void setSignBonus(float signBonus) {
        this.signBonus = signBonus;
    }

    public float getYearBonus() {
        return yearBonus;
    }

    public void setYearBonus(float yearBonus) {
        this.yearBonus = yearBonus;
    }

    public float getBenefits() {
        return benefits;
    }

    public void setBenefits(float benefits) {
        this.benefits = benefits;
    }

    public int getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(int leaveTime) {
        this.leaveTime = leaveTime;
    }

    public boolean isOffer() {
        return isOffer;
    }

    public void setOffer(boolean offer) {
        isOffer = offer;
    }
}
