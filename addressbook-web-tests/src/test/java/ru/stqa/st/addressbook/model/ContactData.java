package ru.stqa.st.addressbook.model;

public class ContactData {
    private int id;
    private String firstname;
    private String lastname;
    private String address;
    private String homePhone;
    private String mobilePhone;
    private String workPhone;
    private  String email;
    private  String email2;
    private  String email3;
    private String allEmails;
    private String allPhones;
    private String allAddresses;
    private String group;

    public int getId() {
        return id;
    }
    public String getAllPhones() {return allPhones;}
    public String getFirstname() {return firstname;}
    public String getLastname() {return lastname;}
    public String getAddress() {return address;}
    public String getHomePhone() {return homePhone;}
    public String getEmail() {return email;}
    public String getGroup() {return group;}
    public String getEmail2() {return email2;}
    public String getEmail3() {return email3;}
    public String getAllEmails(){return allEmails;}
    public String getAllAddresses(){return allAddresses;}
    public String getWorkPhone() {return workPhone;}
    public String getMobilePhone() {return mobilePhone;}

    public void setId(int id) {
        this.id = id;
    }

    public ContactData withAllPhones(String allPhones) {
        this.allPhones = allPhones;
        return this;
    }
    public ContactData withAllAddresses(String allAddresses) {
        this.allAddresses = allAddresses;
        return this;
    }

    public ContactData withId (int id){
        this.id = id;
        return this;
    }

    public ContactData withFirstName (String firstname){
        this.firstname = firstname;
        return this;
    }

    public ContactData withLastName (String lastname){
        this.lastname = lastname;
        return this;
    }

    public ContactData withAddress(String address){
        this.address = address;
        return this;
    }

    public ContactData withHomePhone (String homePhone){
        this.homePhone = homePhone;
        return this;
    }

    public ContactData withEmail (String email){
        this.email = email;
        return this;
    }

    public ContactData withEmail2(String email2) {
        this.email2 = email2;
        return this;
    }

    public ContactData withEmail3(String email3) {
        this.email3 = email3;
        return this;
    }

    public ContactData withAllEmails(String allEmails) {
        this.allEmails = allEmails;
        return this;
    }

    public ContactData withGroup (String group){
        this.group = group;
        return this;
    }

    public ContactData withMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public ContactData withWorkPhone(String workPhone) {
        this.workPhone = workPhone;
        return this;
    }


    @Override
    public String toString() {
        return "ContactData{" +
                "id='" + id + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactData that = (ContactData) o;

        if (id != that.id) return false;
        if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
        return lastname != null ? lastname.equals(that.lastname) : that.lastname == null;
    }
}
