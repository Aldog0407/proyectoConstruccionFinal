package practicasprofesionalespf.controller.coordinator;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea; // << CAMBIO: import añadido
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import practicasprofesionalespf.model.dao.DeliveryDAO;
import practicasprofesionalespf.model.dao.RecordDAO;
import practicasprofesionalespf.model.enums.DeliveryType;
import practicasprofesionalespf.model.pojo.Delivery;
import practicasprofesionalespf.model.pojo.OperationResult;
import practicasprofesionalespf.model.pojo.Record;
import practicasprofesionalespf.model.pojo.Student;
import practicasprofesionalespf.utils.Utils;

public class FXMLDataDeliverableController implements Initializable {

    @FXML
    private TextField tfDeliveryName;
    @FXML
    private TextArea taDescription; 
    @FXML
    private DatePicker dpDueDate;
    @FXML
    private Button btnSubmit;

    private Student selectedStudent;
    private DeliveryType selectedType;
    

    @FXML
    private void btnSubmitClicked(ActionEvent event) {
        if (!validateFields()) {
            return;
        }

        try {
            Record studentRecord = RecordDAO.getRecordByStudent(selectedStudent.getIdStudent());
            if (studentRecord == null) {
                Utils.showSimpleAlert( Alert.AlertType.ERROR, "Error", "No se pudo encontrar el expediente del estudiante...");
                return;
            }

            Delivery newDelivery = new Delivery();
            newDelivery.setName(tfDeliveryName.getText().trim());
            newDelivery.setDescription(taDescription.getText().trim()); // << CAMBIO: Se asigna la descripción
            newDelivery.setDeliveryType(selectedType);
            newDelivery.setIdRecord(studentRecord.getIdRecord());
            
            LocalDateTime startDate = LocalDateTime.now();
            LocalDateTime endDate = LocalDateTime.of(dpDueDate.getValue(), LocalTime.MAX); 
            
            newDelivery.setStartDate(startDate);
            newDelivery.setEndDate(endDate);
            
            OperationResult result = DeliveryDAO.scheduleDelivery(newDelivery);
            
            if (!result.isError()) {
                Utils.showSimpleAlert(Alert.AlertType.ERROR, "Confirmación", result.getMessage());
                closeWindow();
            } else {
                Utils.showSimpleAlert(Alert.AlertType.ERROR, "Error", result.getMessage());
            }

        } catch (SQLException e) {
            Utils.showSimpleAlert(Alert.AlertType.ERROR, "Error de Conexión", "ERROR: No hay conexión...");
        }
    }
    
    // ... (el resto de la clase se queda igual) ...
    public void initializeData(Student student, DeliveryType type) {
        this.selectedStudent = student;
        this.selectedType = type;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Método de inicialización
    }    
    
    private boolean validateFields() {
        if (tfDeliveryName.getText().trim().isEmpty() || dpDueDate.getValue() == null) {
            Utils.showSimpleAlert(Alert.AlertType.ERROR,"Campos Vacíos", "El nombre del entregable y la fecha límite son obligatorios.");
            return false;
        }
        if (dpDueDate.getValue().isBefore(LocalDate.now())) {
            Utils.showSimpleAlert(Alert.AlertType.ERROR, "Fecha Inválida", "La fecha límite no puede ser anterior a la fecha actual.");
            return false;
        }
        return true;
    }

    @FXML
    private void btnExitClicked(ActionEvent event) {
        if (Utils.showConfirmationAlert("¿Seguro que quieres salir?", "¿Salir?")) {
            closeWindow();
        }
    }
    
    private void closeWindow(){
        Stage stage = (Stage) btnSubmit.getScene().getWindow();
        stage.close();
    }
}