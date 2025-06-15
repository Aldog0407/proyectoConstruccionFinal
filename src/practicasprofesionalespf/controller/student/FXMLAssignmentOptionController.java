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
import practicasprofesionalespf.utils.WindowUtils;

public class FXMLAssignmentOptionController implements Initializable {
    
    Student student;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void initializeData(Student student){
        this.student = student;
    }

    @FXML
    private void onSubmitInitialDocClicked(ActionEvent event) {
        try{
            Stage chooseAssignmentStage = new Stage();
            FXMLLoader loader = new FXMLLoader(PracticasProfesionalesPF.class.getResource("view/student/FXMLChooseAssignment.fxml"));
            Parent view = loader.load();
            
            FXMLChooseAssignmentController controller = loader.getController();
            controller.initializeData(student);
            Scene scene = new Scene(view);
            chooseAssignmentStage.setScene(scene);
            chooseAssignmentStage.setTitle("Elige la entrega");
            chooseAssignmentStage.initModality(Modality.APPLICATION_MODAL);
            chooseAssignmentStage.showAndWait();
            
        }catch(IOException ex){
           Utils.showSimpleAlert(Alert.AlertType.ERROR, "Error con la interfaz", 
                    "No se pudo abrir la ventana, intentalo más tarde"); 
        }
    }

    @FXML
    private void onSubmitReportClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/practicasprofesionalespf/view/student/FXMLPendingDeliveries.fxml"));
            Parent view = loader.load();

        
            
            Stage stage = new Stage();
            stage.setScene(new Scene(view));
            stage.setTitle("Entregas Pendientes");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            Utils.showSimpleAlert(Alert.AlertType.ERROR, "Error de Interfaz", "No se pudo abrir la ventana.");
        }
    }

    @FXML
    private void onSubmitFinalDocClicked(ActionEvent event) {
    }

    @FXML
    private void onCancelButtonClicked(ActionEvent event) {
        WindowUtils.whenCancelClicked(event);
    }
    
}
