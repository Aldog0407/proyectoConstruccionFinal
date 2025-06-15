package practicasprofesionalespf.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import practicasprofesionalespf.model.DBConnection;

public class ProjectDAO {
    
    public static String obtainProjectName(int idStudent) throws SQLException{
        String sqlQuery = "SELECT p.name FROM project p JOIN record r "
                + "ON p.idRecord = r.idRecord WHERE r.idStudent = ?";
        String projectName = null;
        
        try(Connection dbConnection = new DBConnection().createConnection();
            PreparedStatement query = dbConnection.prepareStatement(sqlQuery)){
            
            query.setInt(1, idStudent);
            
            try(ResultSet result = query.executeQuery()){
                if(result.next()){
                    projectName = result.getString("name");
                }
            }
        }
        
        return projectName;
    }
    
}
