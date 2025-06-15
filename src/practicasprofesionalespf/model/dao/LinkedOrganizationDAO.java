package practicasprofesionalespf.model.dao;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import practicasprofesionalespf.model.DBConnection;
import practicasprofesionalespf.model.pojo.LinkedOrganization;
import practicasprofesionalespf.model.pojo.OperationResult;

public class LinkedOrganizationDAO {
    
    public static ArrayList<LinkedOrganization> obtainLinkedOrganizations() throws SQLException{
        ArrayList<LinkedOrganization> linkedOrganizations = new ArrayList<>();
        
        String sqlQuery = "SELECT idLinkedORganization, name, isActive, address, phone FROM linkedorganization";
        
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
        linkedOrganization.setIsActive(result.getBoolean("isActive"));
        linkedOrganization.setPhone(result.getString("phone"));
        
        return linkedOrganization;
    }
    
    public static boolean organizationExists(String name) throws SQLException {
        boolean exists = false;
        String query = "SELECT COUNT(*) FROM LinkedOrganization WHERE name = ?";
        try (Connection connection = new DBConnection().createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, name);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    exists = rs.getInt(1) > 0;
                }
            }
        }
        return exists;
    }
    
    public static OperationResult saveLinkedOrganization(LinkedOrganization newLinkedOrganization) throws SQLException {
        OperationResult result = new OperationResult();
        result.setError(true);
        String query = "INSERT INTO LinkedOrganization (name, isActive, address, phone) VALUES (?, ?, ?, ?)";

        try (Connection connection = new DBConnection().createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, newLinkedOrganization.getName());
            preparedStatement.setBoolean(2, newLinkedOrganization.getIsActive());
            preparedStatement.setString(3, newLinkedOrganization.getAddress());
            preparedStatement.setString(4, newLinkedOrganization.getPhone());
            
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                result.setError(false);
                result.setMessage("La Organización Vinculada se registró con Éxito");
            } else {
                result.setMessage("No se pudo registrar la organización.");
            }
        }
        return result;
    }
    
    

}
