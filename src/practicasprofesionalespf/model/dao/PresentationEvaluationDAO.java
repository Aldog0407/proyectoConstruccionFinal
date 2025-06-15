package practicasprofesionalespf.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import practicasprofesionalespf.model.DBConnection;
import practicasprofesionalespf.model.pojo.OperationResult;
import practicasprofesionalespf.model.pojo.PresentationEvaluation;

public class PresentationEvaluationDAO {
    
    public static OperationResult safeEvaluation(PresentationEvaluation evaluation) throws SQLException{
        OperationResult result = new OperationResult();
        String sqlQuery = "INSERT INTO PresentationEvaluation (title, date, grade, observations, idRecord) VALUES (?,?,?,?,?)";
        
        try(Connection dbConnection = new DBConnection().createConnection();
            PreparedStatement insert = dbConnection.prepareStatement(sqlQuery)){
            insert.setString(1, evaluation.getTitle());
            insert.setString(2, evaluation.getDate());
            insert.setDouble(3, evaluation.getGrade());
            insert.setString(4, evaluation.getObservations());
            insert.setInt(5, evaluation.getIdRecord());
            int affectedRows = insert.executeUpdate();
            
            if(affectedRows == 1){
                result.setError(false);
                result.setMessage("Evaluacion completada con éxito");
            }else{
                result.setError(true);
                result.setMessage("Lo sentimos, no se pudo completar la evaluación");
            }
        }
        
        return result;
    }
    
}
