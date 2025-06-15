package practicasprofesionalespf.model.pojo;

public class LinkedOrganization {
   private int idLinkedOrganization;
   private String name;
   private boolean status;
   private String address;
   private String phone;

    public LinkedOrganization() {
    }

    public LinkedOrganization(int idLinkedOrganization, String name, boolean status, String address, String phone) {
        this.idLinkedOrganization = idLinkedOrganization;
        this.name = name;
        this.status = status;
        this.address = address;
        this.phone = phone;
    }

    public int getIdLinkedOrganization() {
        return idLinkedOrganization;
    }

    public void setIdLinkedOrganization(int idLinkedOrganization) {
        this.idLinkedOrganization = idLinkedOrganization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return  name;
    }
    
    
   
   
   
}
