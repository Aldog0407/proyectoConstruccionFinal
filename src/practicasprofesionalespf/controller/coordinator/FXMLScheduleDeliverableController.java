package practicasprofesionalespf.controller.coordinator;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import practicasprofesionalespf.model.dao.StudentDAO;
import practicasprofesionalespf.model.enums.DeliveryType;
import practicasprofesionalespf.model.pojo.Student;
import practicasprofesionalespf.utils.Utils;
import practicasprofesionalespf.utils.WindowUtils;

public class FXMLScheduleDeliverableController implements Initializable {

    @FXML
    private TableView<Student> tvStudents;
    @FXML
    private TableColumn<Student, String> tcStudentName;
    @FXML
    private TableColumn<Student, String> tcTuition;
    @FXML
    private ComboBox<DeliveryType> cbDeliveryType;
    @FXML
    private Button btnContinue;

    private ObservableList<Student> students;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        students = FXCollections.observableArrayList();
        configureTable();
        loadStudentsWithProject();
        cbDeliveryType.getItems().setAll(DeliveryType.values());
        
        ChangeListener<Object> listener = (obs, oldVal, newVal) -> 
                btnContinue.setDisable(tvStudents.getSelectionModel().isEmpty() || cbDeliveryType.getSelectionModel().isEmpty());
        
        tvStudents.getSelectionModel().selectedItemProperty().addListener(listener);
        cbDeliveryType.getSelectionModel().selectedItemProperty().addListener(listener);
        
        btnContinue.setDisable(true);
    }    
    
    private void configureTable() {
        tcStudentName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        tcTuition.setCellValueFactory(new PropertyValueFactory<>("enrollment"));
    }

    private void loadStudentsWithProject() {
        try {
            students.addAll(StudentDAO.getStudentsWithProject());
            tvStudents.setItems(students);
        } catch (SQLException e) {
            Utils.showSimpleAlert(Alert.AlertType.WARNING, 
                                   "ERROR: No hay conexión con la base de datos. Inténtelo nuevamente.", 
                                   "ERROR");
            closeWindow();
        }
    }

    @FXML
    private void btnContinueClicked(ActionEvent event) {
        Student selectedStudent = tvStudents.getSelectionModel().getSelectedItem();
        DeliveryType selectedType = cbDeliveryType.getSelectionModel().getSelectedItem();

        closeWindow();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/practicasprofesionalespf/view/coordinator/FXMLDataDeliverable.fxml"));
            Parent view = loader.load();

            FXMLDataDeliverableController datosController = loader.getController();
            datosController.initializeData(selectedStudent, selectedType);

            Stage datosStage = new Stage();
            Scene scene = new Scene(view);
            datosStage.setScene(scene);
            datosStage.setTitle("Datos del Entregable");
            datosStage.initModality(Modality.APPLICATION_MODAL);
            datosStage.showAndWait();

        } catch (IOException ex) {
             Utils.showSimpleAlert(Alert.AlertType.ERROR,
                    "Error con la interfaz",
                    "No se pudo abrir la ventana, inténtalo más tarde");
        }
    }

    @FXML
    private void btnExitClicked(ActionEvent event) {
        if (Utils.showConfirmationAlert("¿Seguro que quieres salir?", "¿Salir?")) {
            closeWindow();
        }
    }
    
    private void closeWindow(){
        Stage stage = (Stage) tvStudents.getScene().getWindow();
        stage.close();
    }
}