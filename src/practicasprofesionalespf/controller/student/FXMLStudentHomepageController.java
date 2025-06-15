package practicasprofesionalespf.controller.student;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import practicasprofesionalespf.PracticasProfesionalesPF;
import practicasprofesionalespf.model.pojo.Student;
import practicasprofesionalespf.utils.Utils;

public class FXMLStudentHomepageController implements Initializable {
    
    private Student student;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    } 
    
    public void initializeData(Student student){
        this.student = student;
    }

    @FXML
    private void onSubmitAssignmentClicked(ActionEvent event) {
        try {
            Stage assignmentStage = new Stage();
            FXMLLoader loader = new FXMLLoader(PracticasProfesionalesPF.class.getResource("view/student/FXMLAssignmentOption.fxml"));
            Parent view = loader.load();
            
            FXMLAssignmentOptionController controller = loader.getController();
            controller.initializeData(student);
            Scene scene = new Scene(view);
            assignmentStage.setScene(scene);
            assignmentStage.setTitle("Elegir el tipo de entrega");
            assignmentStage.initModality(Modality.APPLICATION_MODAL);
            assignmentStage.showAndWait();
        } catch (IOException ex) {
            Utils.showSimpleAlert(Alert.AlertType.ERROR, "Error con la interfaz", 
                    "No se pudo abrir la ventana, intentalo más tarde");
        }
        
    }
    
}
