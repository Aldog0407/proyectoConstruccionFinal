package practicasprofesionalespf.controller.coordinator;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import practicasprofesionalespf.model.dao.LinkedOrganizationDAO;
import practicasprofesionalespf.model.pojo.LinkedOrganization;
import practicasprofesionalespf.model.pojo.OperationResult;
import practicasprofesionalespf.utils.Utils;

public class FXMLConfirmDataLOController implements Initializable {

    @FXML
    private Label lbName;
    @FXML
    private Label lbAddress;
    @FXML
    private Label lbPhone;
    @FXML
    private Label lbStatus;

    private LinkedOrganization org;
    private FXMLRegistrationFormLOController formController;

    @Override
    public void initialize(URL url, ResourceBundle rb) { }    

    public void initializeData(LinkedOrganization org, FXMLRegistrationFormLOController formController) {
        this.org = org;
        this.formController = formController;
        lbName.setText(org.getName());
        lbAddress.setText(org.getAddress());
        lbPhone.setText(org.getPhone());
        lbStatus.setText(org.getIsActive() == true ? "Activo" : "Inactivo");
    }

    @FXML
    private void btnContinueClicked(ActionEvent event) {
        try {
            OperationResult result = LinkedOrganizationDAO.saveLinkedOrganization(org);
            if (!result.isError()) {
                Utils.showSimpleAlert(Alert.AlertType.INFORMATION, "Registro Exitoso", result.getMessage());
                formController.setConfirmed(true);
                closeWindow();
            } else {
                 Utils.showSimpleAlert(Alert.AlertType.ERROR, "Error", result.getMessage());
            }
        } catch (SQLException e) {
            Utils.showSimpleAlert(Alert.AlertType.ERROR, "Error de Conexión", "No hay conexión con la base de datos.");
        }
    }

    @FXML
    private void btnModifyClicked(ActionEvent event) {
        closeWindow();
    }
    
    private void closeWindow() {
        Stage stage = (Stage) lbName.getScene().getWindow();
        stage.close();
    }
}