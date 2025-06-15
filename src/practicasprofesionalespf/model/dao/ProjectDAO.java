package practicasprofesionalespf.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import practicasprofesionalespf.model.DBConnection;
import practicasprofesionalespf.model.pojo.Project;

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
    
     public static ArrayList<Project> obtainProjects() throws SQLException {
        ArrayList<Project> projects = new ArrayList<>();
        String sqlQuery = "SELECT idProject, idRecord, idProjectManager, idLinkedOrganization, idCoordinator, name, department, description, methodology, availability FROM Project";
        try (Connection dbConnection = new DBConnection().createConnection();
             PreparedStatement query = dbConnection.prepareStatement(sqlQuery);
             ResultSet result = query.executeQuery()) {
            while (result.next()) {
                projects.add(convertProject(result));
            }
        }
        return projects;
    }
     
      private static Project convertProject(ResultSet result) throws SQLException {
        Project project = new Project();
        project.setIdProject(result.getInt("idProject"));
        project.setIdRecord(result.getInt("idRecord"));
        project.setIdProjectManager(result.getInt("idProjectManager"));
        project.setIdLinkedOrganization(result.getInt("idLinkedOrganization"));
        project.setIdCoordinator(result.getInt("idCoordinator"));
        project.setName(result.getString("name"));
        project.setDeparment(result.getString("department")); 
        project.setDescription(result.getString("description"));
        project.setMethodology(result.getString("methodology"));
        project.setAvailability(result.getInt("availability"));
        return project;
    }
     
}
