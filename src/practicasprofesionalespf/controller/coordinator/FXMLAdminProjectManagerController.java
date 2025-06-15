package practicasprofesionalespf.controller.coordinator;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import practicasprofesionalespf.PracticasProfesionalesPF;
import practicasprofesionalespf.interFace.INotification;
import practicasprofesionalespf.model.dao.ProjectManagerDAO;
import practicasprofesionalespf.model.pojo.ProjectManager;
import practicasprofesionalespf.utils.Utils;

public class FXMLAdminProjectManagerController implements Initializable, INotification {

    @FXML
    private TableView<ProjectManager> tvProjectManagers;
    @FXML
    private TableColumn tcName;
    @FXML
    private TableColumn tcLastNameFather;
    @FXML
    private TableColumn tcLastNameMother;
    @FXML
    private TableColumn tcLinkedOrganization;
    @FXML
    private TableColumn tcEmail;
    @FXML
    private TableColumn tcPosition;
    
    private ObservableList<ProjectManager> projectManagers;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUpTable();
        loadTableData();
    }

    private void setUpTable(){
        tcName.setCellValueFactory(new PropertyValueFactory("firstName"));
        tcLastNameFather.setCellValueFactory(new PropertyValueFactory("lastNameFather"));
        tcLastNameMother.setCellValueFactory(new PropertyValueFactory("lastNameMother"));
        tcLinkedOrganization.setCellValueFactory(new PropertyValueFactory("linkedOrganization"));
        tcEmail.setCellValueFactory(new PropertyValueFactory("email"));
        tcPosition.setCellValueFactory(new PropertyValueFactory("position"));
        
    }
    
    private void loadTableData(){
        try{
            projectManagers = FXCollections.observableArrayList();
            ArrayList<ProjectManager> projectManagersDAO = ProjectManagerDAO.obtainProjectManager();
            projectManagers.addAll(projectManagersDAO);
            tvProjectManagers.setItems(projectManagers);
        }catch(SQLException ex){
            Utils.showSimpleAlert(Alert.AlertType.ERROR, "Error al cargar la tabla", "Lo sentimos, "
                    + "por el momento no se puede mostrar la información de los responsables de proyecto. "
                    + "Por favor inténtelo más tarde");
            closeWindow();
        }
    }
    
    private void closeWindow(){
        ((Stage) tvProjectManagers.getScene().getWindow()).close();
    }

    @FXML
    private void onRegisterButtonClicked(ActionEvent event) {
        goToProjectManagerForm(false, null);
    }

    @FXML
    private void onUpdateButtonClicked(ActionEvent event) {
    }

    @FXML
    private void onComeBackButtonClicked(ActionEvent event) {
    }
    
    private void goToProjectManagerForm(boolean isUpdate, ProjectManager projectManagerUpdate){
        try{
            Stage formStage = new Stage();
            FXMLLoader loader = new FXMLLoader(PracticasProfesionalesPF.class.getResource("view/coordinator/FXMLProjectManagerRegistrationForm.fxml"));
            Parent view = loader.load();
            
            FXMLProjectManagerRegistrationFormController controller = loader.getController();
            
            controller.initializeInformation(isUpdate, projectManagerUpdate, this);
            Scene scene = new Scene(view);
            formStage.setTitle("Formulario de registro de Responsable de Proyecto");
            formStage.setScene(scene);
            formStage.initModality(Modality.APPLICATION_MODAL);
            formStage.showAndWait();
        }catch(IOException ex){
            Utils.showSimpleAlert(Alert.AlertType.ERROR, "Error con la interfaz", 
                    "No se pudo abrir la ventana de confirmación, intentalo más tarde");
        }
    }

    @Override
    public void successfulOperation(String type, String name) {
        System.out.println("Operation: " + type + " with the Project Manager: " + name);
        loadTableData();
    }
    
}
