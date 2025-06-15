package practicasprofesionalespf.controller.coordinator;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import practicasprofesionalespf.interFace.INotification;
import practicasprofesionalespf.model.dao.ProjectManagerDAO;
import practicasprofesionalespf.model.pojo.OperationResult;
import practicasprofesionalespf.model.pojo.ProjectManager;
import practicasprofesionalespf.utils.Utils;
import practicasprofesionalespf.utils.WindowUtils;

public class FXMLConfirmDataController implements Initializable {

    @FXML
    private Label lbName;
    @FXML
    private Label lbLastNameFather;
    @FXML
    private Label lbLastNameMother;
    @FXML
    private Label lbPosition;
    @FXML
    private Label lbEmail;
    @FXML
    private Label lbPhone;
    @FXML
    private Label lbLinkedOrganization;
    
    private ProjectManager projectManager;
    INotification observer;
    private Stage formStage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void initializeData(ProjectManager projectManager, INotification observer, Stage formStage){
        this.projectManager = projectManager;
        this.observer = observer;
        this.formStage = formStage;
        loadInfo();
    }
    
    private void loadInfo(){
        lbName.setText(projectManager.getFirstName());
        lbLastNameFather.setText(projectManager.getLastNameFather());
        lbLastNameMother.setText(projectManager.getLastNameMother());
        lbPosition.setText(projectManager.getPosition());
        lbEmail.setText(projectManager.getEmail());
        lbPhone.setText(projectManager.getPhone());
        lbLinkedOrganization.setText(projectManager.getLinkedOrganization());
    }

    @FXML
    private void onModifyButtonClicked(ActionEvent event) {
        WindowUtils.closeCurrentWindow(event);
    }

    @FXML
    private void onAcceptButtonClick(ActionEvent event) {
        safeProjectManager();
        WindowUtils.closeCurrentWindow(event);
        
        if(formStage != null){
            formStage.close();
        }
    }
    
    private void safeProjectManager(){
        try {
            OperationResult insertResult = ProjectManagerDAO.registerManager(projectManager);
            if(!insertResult.isError()){
                Utils.showSimpleAlert(Alert.AlertType.INFORMATION, "Registro exitoso", insertResult.getMessage());
                Utils.getStageComponent(lbPhone).close();
                observer.successfulOperation("Insert", projectManager.getFirstName());
            }else{
                Utils.showSimpleAlert(Alert.AlertType.ERROR, "Error al registrar", insertResult.getMessage());
            }
        } catch (SQLException ex) {
            Utils.showSimpleAlert(Alert.AlertType.ERROR, "Error de conexión", 
                    "No hay conexión con la base de datos, inténtelo más tarde");
            closeWindow();
        }
    }
    
    private void closeWindow(){
      ((Stage) lbEmail.getScene().getWindow()).close();
    }
    
}
