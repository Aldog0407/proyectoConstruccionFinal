package practicasprofesionalespf.utils;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.stage.Stage;

public class WindowUtils {
    
    public static void closeCurrentWindow(ActionEvent event){
        Stage confirmStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        confirmStage.close();
    }
    
    public static void whenCancelClicked(Control component){
        boolean confirmCancel = Utils.showConfirmationAlert("Cancelar", "¿Estás seguro que deseas cancelar el registro?");
       
       if(confirmCancel)
           Utils.getStageComponent(component).close();
    }
    
    public static void whenCancelClicked(ActionEvent event){
        boolean confirmCancel = Utils.showConfirmationAlert("Cancelar", "¿Estás seguro que deseas cancelar el registro?");
       
       if(confirmCancel){
         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
         stage.close();  
       }
           
    }
    
}
