package com.arslankucukkafa.labormarketauth.util.citizenship.turkey;

public class TCCtizenshipRequestModel {

    public String ctizenshipId;
    public String firstName;
    public String lastName;
    public int birthYear;

    public TCCtizenshipRequestModel(String ctizenshipId, String firstName, String lastName, int birthYear) {
        this.ctizenshipId = ctizenshipId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthYear = birthYear;
    }

    public String getCtizenshipId() {
        return ctizenshipId;
    }

    public void setCtizenshipId(String ctizenshipId) {
        this.ctizenshipId = ctizenshipId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

}
