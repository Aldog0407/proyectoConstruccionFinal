package practicasprofesionalespf.controller.coordinator;

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

public class FXMLCoordinatorHomepageController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onProjectButtonClicked(ActionEvent event) {
        try {
            Stage projectStage = new Stage();
            Parent view = FXMLLoader.load(PracticasProfesionalesPF.class.getResource("view/coordinator/FXMLProjects.fxml"));
            Scene scene = new Scene(view);
            projectStage.setScene(scene);
            projectStage.setTitle("Administrar Proyectos");
            projectStage.initModality(Modality.APPLICATION_MODAL);
            projectStage.showAndWait();
        } catch (IOException ex) {
            Utils.showSimpleAlert(Alert.AlertType.ERROR, "Error con la interfaz", "No se pudo abrir la ventana, intentalo más tarde");
        }
    }

    @FXML
    private void onManagerButtonClicked(ActionEvent event) {
        try {
            Stage managerStage = new Stage();
            Parent view = FXMLLoader.load(PracticasProfesionalesPF.class.getResource("view/coordinator/FXMLAdminProjectManager.fxml"));
            Scene scene = new Scene(view);
            managerStage.setScene(scene);
            managerStage.setTitle("Administrador de Responsable de Proyecto");
            managerStage.initModality(Modality.APPLICATION_MODAL);
            managerStage.showAndWait();
        } catch(IOException ex) {
            Utils.showSimpleAlert(Alert.AlertType.ERROR, "Error con la interfaz", "No se pudo abrir la ventana, intentalo más tarde");
        }
    }
    
    @FXML
    private void onAssignProjectButtonClicked(ActionEvent event) {
    }

    @FXML
    private void onLogoutButtonClicked(ActionEvent event) {
    }
    
    @FXML
    private void btnScheduleDeliverableClicked(ActionEvent event) {
        try {
            Stage scheduleStage = new Stage();
            Parent view = FXMLLoader.load(PracticasProfesionalesPF.class.getResource("view/coordinator/FXMLScheduleDeliverable.fxml"));
            Scene scene = new Scene(view);
            scheduleStage.setScene(scene);
            scheduleStage.setTitle("Programar Entregable");
            scheduleStage.initModality(Modality.APPLICATION_MODAL);
            scheduleStage.showAndWait();
        } catch(IOException ex) {
            Utils.showSimpleAlert(
                Alert.AlertType.ERROR, 
                "Error con la interfaz", 
                "No se pudo abrir la ventana, inténtalo más tarde"
            );
        }
    }
}
