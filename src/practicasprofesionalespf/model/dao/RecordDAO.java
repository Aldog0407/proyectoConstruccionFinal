package practicasprofesionalespf.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import practicasprofesionalespf.model.DBConnection;


public class RecordDAO {
    
    public static int obtainIDRecordWithStudentId(int idStudent) throws SQLException{
       int idRecord = 0;
       String sqlQuery = "SELECT idRecord FROM Record WHERE idStudent = ?";
       
       try(Connection dbConnection = new DBConnection().createConnection();
            PreparedStatement query = dbConnection.prepareStatement(sqlQuery)){
           
           query.setInt(1, idStudent);
           
           try(ResultSet result = query.executeQuery()){
               if(result.next())
                   idRecord = result.getInt("idRecord");
           }
       }
       
       return idRecord;
    }
    
}
