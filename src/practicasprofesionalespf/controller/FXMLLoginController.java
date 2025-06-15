package practicasprofesionalespf.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import practicasprofesionalespf.PracticasProfesionalesPF;
import practicasprofesionalespf.controller.student.FXMLStudentHomepageController;
import practicasprofesionalespf.model.dao.LoginDAO;
import practicasprofesionalespf.model.dao.StudentDAO;
import practicasprofesionalespf.model.pojo.Student;
import practicasprofesionalespf.model.pojo.User;
import practicasprofesionalespf.utils.Utils;

public class FXMLLoginController implements Initializable {

    @FXML
    private TextField tfUser;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private Label lbUserError;
    @FXML
    private Label lbPasswordError;
    
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    

    @FXML
    private void onLoginButtonClicked(ActionEvent event) {
        String username = tfUser.getText();
        String password = pfPassword.getText();
        
        if(validateFields(username, password))
            validateCredentials(username, password);
    }
    
    private boolean validateFields(String username, String password){
        lbUserError.setText("");
        lbPasswordError.setText("");
        
        boolean validFields = true;
        
        if(username.isEmpty()){
            lbUserError.setText("Usuario requerido");
            validFields = false;
        }
        
        if(password.isEmpty()){
            lbPasswordError.setText("Contraseña requerida");
            validFields = false;
        }
        
        return validFields;
    }
    
    private void validateCredentials(String username, String password){
        try{
            User sessionUser = LoginDAO.verifyCredentials(username, password);
            
            if(sessionUser != null){
                Utils.showSimpleAlert(Alert.AlertType.INFORMATION, "Credenciales correctas", 
                        "Bienvenid@ al sistema");
                defineRole(sessionUser);
            }else{
                Utils.showSimpleAlert(Alert.AlertType.WARNING, "Credenciales incorrectas", 
                        "Usuario y/o contraseña incorrectos, por favor verifica tu informacion");
            }
        }catch(SQLException ex){
            String userFriendlyMessage = "Hubo un problema al intentar conectar con la base de datos. "
                    + "Por favor, inténtalo de nuevo más tarde.";
            Utils.showSimpleAlert(Alert.AlertType.ERROR, "Problemas de conexión", userFriendlyMessage);
        }
    }
    
    private void defineRole(User sessionUser){
        switch(sessionUser.getRole()){
            
            case "COORDINATOR":
                goToHomepage("view/coordinator/FXMLCoordinatorHomepage.fxml");
                break;
            
            case "EVALUATOR":
                goToHomepage("view/evaluator/FXMLEvaluatorHomepage.fxml");
                break;
            
            case "STUDENT":
                goToStudentHomepage(sessionUser);
                break;
                
            case "TEACHER":
                goToHomepage("view/teacher/FXMLTeacherHomepage.fxml");
                break;
        }
    }
    
    private void goToHomepage(String fxmlPath){
        try{
            Stage baseStage = (Stage) tfUser.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(PracticasProfesionalesPF.class.getResource(fxmlPath));
            Parent view = loader.load();
            
            Scene homepageScene = new Scene(view);
            baseStage.setScene(homepageScene);
            baseStage.setTitle("Home");
            baseStage.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    private void goToStudentHomepage(User sessionUser){
        try{
            Stage baseStage = (Stage) tfUser.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(PracticasProfesionalesPF.class.getResource("view/student/FXMLStudentHomepage.fxml"));
            Parent view = loader.load();
            
            Student student = obtainStudent(sessionUser.getIdUser());
            
            if(student != null){
                FXMLStudentHomepageController controller = loader.getController();
                controller.initializeData(student);
                Scene homepageScene = new Scene(view);
                baseStage.setScene(homepageScene);
                baseStage.setTitle("Home");
                baseStage.show();  
            }else{
                Utils.showSimpleAlert(Alert.AlertType.ERROR, "Error de inicio de sesión", 
                        "No se pudo realizar el inicio de sesión, no se encontraron"
                                + " datos de su registro de estudiante. Consulta con administración");
            }
            
            
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    private Student obtainStudent(int idUser){
        Student student = null;
        try {
            student = StudentDAO.obtainStudentByUser(idUser);
            
        } catch (SQLException ex) {
            Utils.showSimpleAlert(Alert.AlertType.ERROR, "Error de conexión", 
                    "No se pudo realizar el inicio de sesión por problemas en la base de datos");
        }
        
        return student;
    }
   

    @FXML
    private void onForgottenDataClick(ActionEvent event) {
        Utils.showSimpleAlert(Alert.AlertType.INFORMATION, "Datos olvidados", 
            "Por favor contacta con administración para recibir ayuda");
    }

    

}
