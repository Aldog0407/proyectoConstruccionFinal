package practicasprofesionalespf.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import practicasprofesionalespf.model.DBConnection;
import practicasprofesionalespf.model.pojo.OperationResult;
import practicasprofesionalespf.model.pojo.Report;


public class ReportDAO {
    
    public static ArrayList<Report> obtainInitialDocument(int idRecord) throws SQLException{
        ArrayList<Report> reports = new ArrayList<>();
        
        String sqlQuery = "SELECT r.idReportDocument, r.name, r.date, r.filePath FROM ReportDocument "
                + "r JOIN Delivery d ON r.idReportDocument = d.idReportDocument WHERE d.idRecord = ?;";
        
        
        try(Connection dbConnection = new DBConnection().createConnection();
            PreparedStatement query = dbConnection.prepareStatement(sqlQuery)){
            
            query.setInt(1, idRecord);
            
            try(ResultSet result = query.executeQuery()){
                while(result.next()){
                    Report report = new Report();
                    report.setIdReport(result.getInt("idRecordDocument"));
                    report.setName(result.getString("name"));
                    report.setDate(result.getDate("date").toString());
                    report.setFilePath(result.getString("filePath"));
                    reports.add(report);
                }
                    
            }
        }
        
        return reports;
    }
    
    public static OperationResult updateDocument(Report report)throws SQLException{
        OperationResult result = new OperationResult();
        String sqlQuery = "UPDATE ReportDocument SET Grade = ?, "
                + "WHERE idReportDocument = ?";
        
        try(Connection dbConnection = new DBConnection().createConnection();
            PreparedStatement update = dbConnection.prepareStatement(sqlQuery)){
            
            update.setDouble(1, report.getGrade());
            update.setInt(2, report.getIdReport());
            
            int affectedRows = update.executeUpdate();
            
            if(affectedRows == 1){
                result.setError(false);
                result.setMessage("Documento validado correctamente");
            }else{
                result.setError(true);
                result.setMessage("Lo sentimos, no se pudo completar la validación");
            }
        }
        
        return result;
    }
    
}
