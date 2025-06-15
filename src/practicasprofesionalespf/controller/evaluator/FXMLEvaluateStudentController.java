package practicasprofesionalespf.controller.evaluator;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import practicasprofesionalespf.interFace.INotification;
import practicasprofesionalespf.model.dao.PresentationEvaluationDAO;
import practicasprofesionalespf.model.dao.RecordDAO;
import practicasprofesionalespf.model.pojo.OperationResult;
import practicasprofesionalespf.model.pojo.PresentationEvaluation;
import practicasprofesionalespf.model.pojo.Student;
import practicasprofesionalespf.utils.Utils;
import practicasprofesionalespf.utils.WindowUtils;
import practicasprofesionalespf.validation.FormValidator;

public class FXMLEvaluateStudentController implements Initializable {

    @FXML
    private Label lbEnrollmentId;
    @FXML
    private Label lbStudentName;
    @FXML
    private CheckBox cbCriteria1;
    @FXML
    private CheckBox cbCriteria2;
    @FXML
    private CheckBox cbCriteria3;
    @FXML
    private CheckBox cbCriteria4;
    @FXML
    private CheckBox cbCriteria5;
    @FXML
    private CheckBox cbCriteria6;
    @FXML
    private CheckBox cbCriteria7;
    @FXML
    private CheckBox cbCriteria8;
    @FXML
    private CheckBox cbCriteria9;
    @FXML
    private TextArea taObservations;
    
    private List<CheckBox> listCriteria;
    @FXML
    private CheckBox cbCriteria10;
    
    private double grade;
    private PresentationEvaluation evaluation;
    private Student student;
    INotification observer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        obtainListOfCriteria();
    }
    
    public void initializeData(Student student, INotification observer){
       this.student = student;
       this.observer = observer;
       obtainListOfCriteria();
       loadInfo();
    }
    
    private void obtainListOfCriteria(){
        this.listCriteria = Arrays.asList(cbCriteria1, cbCriteria2, cbCriteria3,
                cbCriteria4, cbCriteria5, cbCriteria6, cbCriteria7,
                cbCriteria8, cbCriteria9, cbCriteria10);
    }
    
    private void loadInfo(){
       lbEnrollmentId.setText(student.getEnrollment());
       lbStudentName.setText(student.getFirstName() + " " + 
               student.getLastNameFather() + " " + student.getLastNameMother());
    }
    

    @FXML
    private void onAcceptButtonClicked(ActionEvent event) {
        calculateGrade();
        
        if(!FormValidator.isTextAreaEmpty(taObservations.getText()))
          safeEvaluation();
        else
           Utils.showSimpleAlert(Alert.AlertType.ERROR, "Datos incompletos", 
                "Existen campos con datos incompletos, verifica tu informacion");
    }
    
    private void calculateGrade(){
        for(CheckBox criteria : listCriteria){
            if(criteria.isSelected()){
               this.grade+=10; 
            }
        }
    }
    
    private int obtainRecord(){
        int idRecord = 0;
        
        try {
            idRecord = RecordDAO.obtainIDRecordWithStudentId(student.getIdStudent());
            
        } catch (SQLException ex) {
            Utils.showSimpleAlert(Alert.AlertType.ERROR, "Error de conexión", 
                    "No hay conexión con la base de datos, inténtelo más tarde"); 
        }
      
        return idRecord;
    }
    
    private void safeEvaluation(){
        int idRecord = obtainRecord();
        String title = String.format("Evaluacion: %s - %s", lbStudentName.getText(), 
            LocalDate.now().toString());
        String dateString = LocalDate.now().toString();
    
        evaluation = new PresentationEvaluation();
        evaluation.setTitle(title);
        evaluation.setDate(dateString);
        evaluation.setGrade(grade);
        evaluation.setObservations(taObservations.getText());
        evaluation.setIdRecord(idRecord);
        
        try{
            OperationResult insertResult = PresentationEvaluationDAO.safeEvaluation(evaluation);
            if(!insertResult.isError()){
                Utils.showSimpleAlert(Alert.AlertType.CONFIRMATION, "Evaluación completada", insertResult.getMessage());
                Utils.getStageComponent(cbCriteria1).close();
                observer.successfulOperation("Insert evaluation", title);
            }else{
                Utils.showSimpleAlert(Alert.AlertType.ERROR, "Error al guardar la evaluación", insertResult.getMessage());
            }
        }catch(SQLException ex){
           Utils.showSimpleAlert(Alert.AlertType.ERROR, "Error de conexión", 
                   "No hay conexión con la base de datos, inténtelo más tarde"); 
        }
    }

    @FXML
    private void onCancelButtonClicked(ActionEvent event) {
        WindowUtils.whenCancelClicked(cbCriteria1);
    }
    
}
