package practicasprofesionalespf.controller.teacher;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import practicasprofesionalespf.model.dao.FinalDocumentDAO;
import practicasprofesionalespf.model.dao.InitialDocumentDAO;
import practicasprofesionalespf.model.dao.ReportDAO;
import practicasprofesionalespf.model.pojo.FinalDocument;
import practicasprofesionalespf.model.pojo.InitialDocument;
import practicasprofesionalespf.model.pojo.OperationResult;
import practicasprofesionalespf.model.pojo.Report;
import practicasprofesionalespf.model.pojo.Student;
import practicasprofesionalespf.model.wrapper.DocumentWrapper;
import practicasprofesionalespf.utils.ConvertionUtils;
import practicasprofesionalespf.utils.Utils;
import practicasprofesionalespf.utils.ValidationUtils;
import practicasprofesionalespf.utils.WindowUtils;
import practicasprofesionalespf.validation.FormValidator;

public class FXMLValidateDocumentController implements Initializable {

    @FXML
    private Label lbDocumentName;
    @FXML
    private Label lbDocumentDate;
    @FXML
    private Label lbProjectName;
    @FXML
    private TextField tfGrade;
    @FXML
    private TextArea taComments;
    
    private Student student;
    private DocumentWrapper document;
    private String projectName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void initializeData(Student student, DocumentWrapper document, String projectName){
        this.student = student;
        this.document = document;
        this.projectName = projectName;
        loadData();
    }
    
    private void loadData(){
        lbDocumentName.setText(document.getName());
        lbDocumentDate.setText(document.getDate());
        lbProjectName.setText(projectName);
    }
    
    private OperationResult updateDocument(double grade, String observations) throws SQLException{
        
        OperationResult result = new OperationResult();
        switch(document.getType()){
            case INITIAL:
                InitialDocument initialDoc = (InitialDocument) document.getOriginalDocument();
                initialDoc.setGrade(grade);
                initialDoc.setObservations(observations);
                result = InitialDocumentDAO.updateDocument(initialDoc);
                break;
            
            case REPORT:
                Report report = (Report) document.getOriginalDocument();
                report.setGrade(grade);
                result = ReportDAO.updateDocument(report);
                break;
            
            case FINAL:
                FinalDocument finalDoc = (FinalDocument) document.getOriginalDocument();
                finalDoc.setGrade(grade);
                finalDoc.setObservations(observations);
                result = FinalDocumentDAO.updateDocument(finalDoc);
                break;
        }
        
        return result;
    }

    @FXML
    private void onViewDocumentButtonClicked(ActionEvent event) {
        if (document == null || document.getFilePath() == null) {
            Utils.showSimpleAlert(Alert.AlertType.ERROR, "Error", 
                    "No se encontró el documento seleccionado.");
            return;
        }
        
        File originalFile = new File(document.getFilePath());
        if (!originalFile.exists()) {
            Utils.showSimpleAlert(Alert.AlertType.ERROR, "Archivo no encontrado", 
                    "No se encontró el archivo en la ruta original.");
            return;
        }
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar documento como");
        fileChooser.setInitialFileName(originalFile.getName());
        
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf")
        );
        
        Window stage = ((Node) event.getSource()).getScene().getWindow();
        File destination = fileChooser.showSaveDialog(stage);
        
        if (destination != null) {
    
            if (!destination.getName().toLowerCase().endsWith(".pdf")) {
                destination = new File(destination.getAbsolutePath() + ".pdf");
            }

            try {
                Files.copy(originalFile.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                Utils.showSimpleAlert(Alert.AlertType.INFORMATION, "Éxito", "El archivo se guardó correctamente.");
            } catch (IOException ex) {
                Utils.showSimpleAlert(Alert.AlertType.ERROR, "Error al guardar", "No se pudo guardar el archivo.");
                ex.printStackTrace();
            }
        }
    }
    
    private void closeWindow(){
      ((Stage) lbDocumentDate.getScene().getWindow()).close();
    }

    @FXML
    private void onAcceptButtonClicked(ActionEvent event) {
        if(!FormValidator.isTextAreaEmpty(taComments.getText()) 
                && !FormValidator.isTextFieldEmpty(tfGrade.getText())){
                
                try{
                   double grade = ConvertionUtils.convertToDouble(tfGrade.getText());
                   
                   if(ValidationUtils.isBetween0And10(grade)){
                       OperationResult result = new OperationResult();
                       result = updateDocument(grade, taComments.getText());
                       
                       if (!result.isError()) {
                         Utils.showSimpleAlert(Alert.AlertType.INFORMATION, "Éxito", result.getMessage());
                         closeWindow();
                        } else {
                            Utils.showSimpleAlert(Alert.AlertType.ERROR, "Error al validar el documento", result.getMessage());
                            closeWindow();
                        }
                       
                   }else{
                      Utils.showSimpleAlert(Alert.AlertType.WARNING, "Número fuera de rango", 
                              "Debe estar entre 0 y 10."); 
                   }
                }catch(NumberFormatException e){
                    Utils.showSimpleAlert(Alert.AlertType.ERROR, "Formato inválido", 
                        "Por favor introduce un número válido para la calificación.");
                } catch (SQLException ex) {
                    Utils.showSimpleAlert(Alert.AlertType.ERROR, "Error de conexión", 
                        "No hay conexión con la base de datos, inténtelo más tarde"); 
                    closeWindow();
                }  
        }else{
           Utils.showSimpleAlert(Alert.AlertType.WARNING, "Datos incompletos", "Existen datos incompletos, por favor corrige");
                
        }
    }

    @FXML
    private void onCancelButtonClicked(ActionEvent event) {
        WindowUtils.whenCancelClicked(lbProjectName);
    }
    
}
