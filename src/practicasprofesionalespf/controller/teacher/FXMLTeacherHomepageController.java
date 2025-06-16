package practicasprofesionalespf.controller.teacher;

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
import practicasprofesionalespf.utils.Utils;

public class FXMLTeacherHomepageController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onValidateDocsButtonClicked(ActionEvent event) {
        try{
            Stage chooseStudentStage = new Stage();
            Parent view = FXMLLoader.load(PracticasProfesionalesPF.class.getResource("view/teacher/FXMLPickStudent.fxml"));
            Scene scene = new Scene(view);
            chooseStudentStage.setScene(scene);
            chooseStudentStage.setTitle("Elegir estudiante");
            chooseStudentStage.initModality(Modality.APPLICATION_MODAL);
            chooseStudentStage.showAndWait();
        }catch(IOException ex){
            Utils.showSimpleAlert(Alert.AlertType.ERROR, "Error con la interfaz", 
                    "No se pudo abrir la ventana, intentalo más tarde");
        }
    }
    
    // En FXMLTeacherHomepageController.java
    @FXML
    private void btnConsultRecordClicked(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent view = FXMLLoader.load(getClass().getResource("/practicasprofesionalespf/view/teacher/FXMLStudentRecord.fxml"));
            Scene scene = new Scene(view);
            stage.setScene(scene);
            stage.setTitle("Consultar Expediente de Estudiante");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch(IOException ex) {
            Utils.showSimpleAlert(Alert.AlertType.ERROR, "Error con la interfaz", "No se pudo abrir la ventana.");
        }
    }
    
}
