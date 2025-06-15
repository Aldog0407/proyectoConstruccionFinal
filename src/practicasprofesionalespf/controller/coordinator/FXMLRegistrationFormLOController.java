package practicasprofesionalespf.controller.coordinator;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import practicasprofesionalespf.model.dao.LinkedOrganizationDAO;
import practicasprofesionalespf.model.pojo.LinkedOrganization;
import practicasprofesionalespf.utils.Utils;

public class FXMLRegistrationFormLOController implements Initializable {

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfPhone;
    @FXML
    private CheckBox cbStatus;
    
    private boolean confirmed = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) { }    

    @FXML
    private void btnContinueClicked(ActionEvent event) {
        if (!validateFields()) {
            return;
        }
        
        try {
            if(LinkedOrganizationDAO.organizationExists(tfName.getText().trim())) {
                Utils.showSimpleAlert( Alert.AlertType.ERROR, "Error", "La organización '" + tfName.getText().trim() + "' ya existe en el sistema.");
                return;
            }
            
            LinkedOrganization org = new LinkedOrganization();
            org.setName(tfName.getText().trim());
            org.setAddress(tfAddress.getText().trim());
            org.setPhone(tfPhone.getText().trim());
            org.setIsActive(cbStatus.isSelected());

            openConfirmationWindow(org);
            
            if (confirmed) {
                Stage currentStage = (Stage) tfName.getScene().getWindow();
                currentStage.close();
            }

        } catch(SQLException e) {
            Utils.showSimpleAlert( Alert.AlertType.ERROR, "Error de Conexión", "No hay conexión con la base de datos.");
        }
    }

    private void openConfirmationWindow(LinkedOrganization org) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/practicasprofesionalespf/view/coordinator/FXMLConfirmDataLO.fxml"));
            Parent view = loader.load();
            FXMLConfirmDataLOController controller = loader.getController();
            controller.initializeData(org, this);

            Stage stage = new Stage();
            stage.setScene(new Scene(view));
            stage.setTitle("Confirmar Datos");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            Utils.showSimpleAlert(Alert.AlertType.ERROR, "Error", "No se pudo abrir la ventana de confirmación");
        }
    }
    
    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    private boolean validateFields() {
        if (tfName.getText().trim().isEmpty() || tfAddress.getText().trim().isEmpty() || tfPhone.getText().trim().isEmpty()) {
            Utils.showSimpleAlert(Alert.AlertType.ERROR, "Campos Vacíos", "Todos los campos son obligatorios.");
            return false;
        }
        if (tfPhone.getText().trim().length() != 10 || !tfPhone.getText().trim().matches("\\d+")) {
             Utils.showSimpleAlert(Alert.AlertType.WARNING, "Dato Inválido", "El teléfono debe contener 10 dígitos numéricos.");
            return false;
        }
        return true;
    }

    @FXML
    private void btnExitClicked(ActionEvent event) {
        if(Utils.showConfirmationAlert("¿Seguro que quieres Salir?", "Salir")) {
            Stage stage = (Stage) tfName.getScene().getWindow();
            stage.close();
        }
    }
}