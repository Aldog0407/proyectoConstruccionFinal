package practicasprofesionalespf.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import practicasprofesionalespf.model.DBConnection;
import practicasprofesionalespf.model.pojo.Student;

public class StudentDAO {
    
    public static ArrayList<Student> obtainStudentWithoutEvaluation() throws SQLException{
        ArrayList<Student> students = new ArrayList<>();
        String sqlQuery = "SELECT s.idStudent, s.firstName, s.lastNameFather, s.lastNameMother, "
                + "s.enrollment FROM Student s JOIN Record r ON s.idStudent = r.idStudent "
                + "WHERE r.idRecord NOT IN (SELECT pe.idRecord FROM PresentationEvaluation pe);";
        
        try(Connection dbConnection = new DBConnection().createConnection();
            PreparedStatement query = dbConnection.prepareStatement(sqlQuery)){
            
            try(ResultSet result = query.executeQuery()){
                while(result.next()){
                    students.add(convertStudentRegistration(result));
                }
            }
        }
        
        return students;
    }
    
    public static ArrayList<Student> obtainStudent() throws SQLException{
       ArrayList<Student> students = new ArrayList<>();
       String sqlQuery = "SELECT idStudent, firstName, lastNameFather, lastNameMother, enrollment FROM Student"; 
       
       try(Connection dbConnection = new DBConnection().createConnection();
            PreparedStatement query = dbConnection.prepareStatement(sqlQuery)){
            
            
            try(ResultSet result = query.executeQuery()){
                while(result.next()){
                    students.add(convertStudentRegistration(result));
                }
            }
        }
       
       return students;
    }
    
    public static Student obtainStudentByUser(int idUser) throws SQLException{
        Student student = new Student();
        String sqlQuery = "SELECT idStudent, firstName, lastNameFather, lastNameMother, enrollment FROM Student WHERE idUser = ?";
        
        try(Connection dbConnection = new DBConnection().createConnection();
            PreparedStatement query = dbConnection.prepareStatement(sqlQuery)){
            
            query.setInt(1, idUser);
            
            try(ResultSet result = query.executeQuery()){
                if(result.next())
                    student = convertStudentRegistration(result);
            }
        }
        
        return student;
        
    }
    
    
            
    
        
   private static Student convertStudentRegistration(ResultSet result) throws SQLException{
       Student student = new Student();
       student.setIdStudent(result.getInt("idStudent"));
       student.setEnrollment(result.getString("enrollment"));
       student.setFirstName(result.getString("firstName"));
       student.setLastNameFather(result.getString("lastNameFather"));
       student.setLastNameMother(result.getString("lastNameMother"));
       
       return student;
   }
    
}
