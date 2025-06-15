package practicasprofesionalespf.model.dao;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import practicasprofesionalespf.model.DBConnection;
import practicasprofesionalespf.model.pojo.LinkedOrganization;

public class LinkedOrganizationDAO {
    
    public static ArrayList<LinkedOrganization> obtainLinkedOrganizations() throws SQLException{
        ArrayList<LinkedOrganization> linkedOrganizations = new ArrayList<>();
        
        String sqlQuery = "SELECT idLinkedORganization, name, status, address, phone FROM linkedorganization";
        
        try(Connection dbConnection = new DBConnection().createConnection();
            PreparedStatement query = dbConnection.prepareStatement(sqlQuery)){
            
            try(ResultSet result = query.executeQuery()){
                while(result.next()){
                    linkedOrganizations.add(convertLinkedOrganization(result));
                }
            }
        }
        
        return linkedOrganizations;
    }
    
    private static LinkedOrganization convertLinkedOrganization(ResultSet result) throws SQLException{
        LinkedOrganization linkedOrganization = new LinkedOrganization();
        linkedOrganization.setIdLinkedOrganization(result.getInt("idLinkedOrganization"));
        linkedOrganization.setName(result.getString("name"));
        linkedOrganization.setStatus(result.getBoolean("status"));
        linkedOrganization.setPhone(result.getString("phone"));
        
        return linkedOrganization;
    }
}
