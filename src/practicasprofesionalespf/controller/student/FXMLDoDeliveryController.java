package practicasprofesionalespf.controller.student;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import practicasprofesionalespf.model.dao.DeliveryDAO;
import practicasprofesionalespf.model.dao.InitialDocumentDAO;
import practicasprofesionalespf.model.dao.RecordDAO;
import practicasprofesionalespf.model.enums.Status;
import practicasprofesionalespf.model.pojo.Delivery;
import practicasprofesionalespf.model.pojo.InitialDocument;
import practicasprofesionalespf.model.pojo.OperationResult;
import practicasprofesionalespf.model.pojo.Student;
import practicasprofesionalespf.utils.Utils;
import practicasprofesionalespf.utils.WindowUtils;
import practicasprofesionalespf.validation.FormValidator;

public class FXMLDoDeliveryController implements Initializable {

    @FXML
    private Label lbDeliveryName;
    @FXML
    private Label lbStartDate;
    @FXML
    private Label lbEndDate;
    @FXML
    private TextArea taComments;
    
    private Student student;
    private Delivery delivery;
    File documentFile;
    File destinationDirectory;
    String filePath;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    public void initializeData(Student student, Delivery delivery){
        this.student = student;
        this.delivery = delivery;
        loadData();
        this.destinationDirectory = new File("docs");
    }
    
    public void loadData(){
        lbDeliveryName.setText(delivery.getName());
        lbStartDate.setText(delivery.getStartDate().toString());
        lbEndDate.setText(delivery.getEndDate().toString());
    }
    
    private void closeWindow(){
      ((Stage) lbEndDate.getScene().getWindow()).close();
    }

    @FXML
    private void onUploadButtonClicked(ActionEvent event) {
        showFileDialog();
    }
    
    private void showFileDialog(){
        FileChooser dialogSelection = new FileChooser();
        dialogSelection.setTitle("Selecciona un archivo");
        FileChooser.ExtensionFilter fileFilter = new FileChooser.ExtensionFilter("Archivos pdf (.pdf)", "*.pdf");
        dialogSelection.getExtensionFilters().add(fileFilter);
        documentFile = dialogSelection.showOpenDialog(Utils.getStageComponent(taComments));
        if(documentFile != null)
            safeFile();
        else
            Utils.showSimpleAlert(Alert.AlertType.ERROR, "Error con el archivo", 
                    "No se seleccionó ningún archivo");
    }
    
    private void safeFile(){
        
        try {
            if(!destinationDirectory.exists()){
                destinationDirectory.mkdirs();
            }
            
            Path destination = new File(destinationDirectory, documentFile.getName()).toPath();
            
            Files.copy(documentFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
            filePath = destination.toString();
        } catch (IOException ex) {
            Utils.showSimpleAlert(Alert.AlertType.ERROR, "Error al subir el archivo", 
                    "No se pudo subir el archivo, intentelo más tarde");
            closeWindow();
        }
    }

    @FXML
    private void onDeliverButtonClicked(ActionEvent event) {
        if(!FormValidator.isTextAreaEmpty(taComments.getText())){
            getDocumentInformation();
            WindowUtils.closeCurrentWindow(event);
        }else{
           Utils.showSimpleAlert(Alert.AlertType.WARNING, "No se proporcionó un comentario", 
                   "No se proporcionó un comentario, deja alguno por favor"); 
        }
    }
    
    
    
    private void getDocumentInformation(){
        
        InitialDocument initialDocument = new InitialDocument();
        
        initialDocument.setName(documentFile.getName());
        initialDocument.setDate(LocalDate.now().toString());
        initialDocument.setDelivered(true);
        initialDocument.setStatus(Status.ENTREGADO);
        initialDocument.setFilePath(filePath);
        
        try{
            OperationResult insertResult = InitialDocumentDAO.safeADocument(initialDocument);
            if(!insertResult.isError()){
                finishDelivery(initialDocument);
            }else{
               Utils.showSimpleAlert(Alert.AlertType.ERROR, "Error al guardar documento", insertResult.getMessage()); 
            }
        }catch(SQLException ex){
            Utils.showSimpleAlert(Alert.AlertType.ERROR, "Error de conexión", 
                   "No hay conexión con la base de datos, inténtelo más tarde");
            closeWindow();
            
        }
    }
    
    private void finishDelivery(InitialDocument initialDocument){
        try {
            int initialDocumentId = initialDocument.getIdInitialDocument();
            int idRecord = RecordDAO.obtainIDRecordWithStudentId(student.getIdStudent());
            
            if(!DeliveryDAO.isDuplicate(idRecord, delivery.getIdDelivery())){
               OperationResult updateResult = DeliveryDAO.makeADelivery(initialDocumentId, delivery.getIdDelivery(), idRecord);
               
               if (!updateResult.isError()) {
                    Utils.showSimpleAlert(Alert.AlertType.INFORMATION, "Éxito", "Documento entregado correctamente.");
                } else {
                    Utils.showSimpleAlert(Alert.AlertType.ERROR, "Error al realizar la entrega", updateResult.getMessage());
                } 
            }else{
                Utils.showSimpleAlert(Alert.AlertType.WARNING, "Entrega duplicada", "Ya entregaste un documento para esta entrega");
            }   
        } catch (SQLException ex) {
            Utils.showSimpleAlert(Alert.AlertType.ERROR, "Error de conexión", 
                   "No hay conexión con la base de datos, inténtelo más tarde"); 
            closeWindow();
        }
    }

    @FXML
    private void onCancelButtonClicked(ActionEvent event) {
        WindowUtils.whenCancelClicked(lbEndDate);
    }
    
}
