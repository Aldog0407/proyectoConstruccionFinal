package practicasprofesionalespf.model.pojo;

public class LinkedOrganization {
   private int idLinkedOrganization;
   private String name;
   private boolean isActive;
   private String address;
   private String phone;

    public LinkedOrganization() {
    }

    public LinkedOrganization(int idLinkedOrganization, String name, boolean isActive, String address, String phone) {
        this.idLinkedOrganization = idLinkedOrganization;
        this.name = name;
        this.isActive = isActive;
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

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
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
