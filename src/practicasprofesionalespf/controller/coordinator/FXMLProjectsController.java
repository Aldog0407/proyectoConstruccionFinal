package practicasprofesionalespf.controller.coordinator;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import practicasprofesionalespf.interFace.INotification;
import practicasprofesionalespf.model.dao.ProjectDAO;
import practicasprofesionalespf.model.pojo.Project;
import practicasprofesionalespf.utils.Utils;

/**
 * FXML Controller class
 *
 * @author migue
 */
public class FXMLProjectsController implements Initializable, INotification {

    @FXML
    private TableView<Project> tvProjects;
    @FXML
    private TableColumn<Project, String> tcName;
    @FXML
    private TableColumn<Project, String> tcDepartment;
    @FXML
    private TableColumn<Project, String> tcDescription;
    @FXML
    private TableColumn<Project, String> tcMethodology;
    @FXML
    private TableColumn<Project, Integer> tcAvailability;

    private ObservableList<Project> projects;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUpTable();
        loadTableData();
    }    

    private void setUpTable() {
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcDepartment.setCellValueFactory(new PropertyValueFactory<>("deparment"));
        tcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tcMethodology.setCellValueFactory(new PropertyValueFactory<>("methodology"));
        tcAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));
    }


    private void loadTableData() {
        try {
            projects = FXCollections.observableArrayList();
            ArrayList<Project> projectsFromDB = ProjectDAO.obtainProjects();
            if (projectsFromDB != null) {
                projects.addAll(projectsFromDB);
                tvProjects.setItems(projects);
            }
        } catch (SQLException ex) {
            Utils.showSimpleAlert(Alert.AlertType.ERROR, "Error en la conexión", 
                "Hubo un error al conectar con la base de datos. Por favor, inténtelo más tarde.");
            closeWindow();
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) tvProjects.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onRegisterButtonClicked(ActionEvent event) {
        Utils.showSimpleAlert(Alert.AlertType.INFORMATION, "Función no implementada", 
            "Aquí se abrirá el formulario para registrar un nuevo proyecto.");
    }

    @FXML
    private void onUpdateButtonClicked(ActionEvent event) {
        Project selectedProject = tvProjects.getSelectionModel().getSelectedItem();
        if (selectedProject != null) {
            // Lógica para abrir la ventana de actualización con los datos del proyecto seleccionado (a implementar)
            Utils.showSimpleAlert(Alert.AlertType.INFORMATION, "Función no implementada", 
                "Aquí se abrirá el formulario para actualizar el proyecto: " + selectedProject.getName());
        } else {
            Utils.showSimpleAlert(Alert.AlertType.WARNING, "Selección requerida", 
                "Por favor, seleccione un proyecto de la tabla para poder actualizarlo.");
        }
    }

    @FXML
    private void onComeBackButtonClicked(ActionEvent event) {
        closeWindow();
    }

    @Override
    public void successfulOperation(String type, String name) {
        loadTableData();
    }
}
