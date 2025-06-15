package practicasprofesionalespf.validation;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import practicasprofesionalespf.utils.ValidationUtils;

public class FormValidator {
    
    public static Set<String> checkEmptyFields(Map<String, String> fields){
        Set<String> emptyFields = new HashSet<>();
        
        for (Map.Entry<String, String> field : fields.entrySet()){
            if(ValidationUtils.isEmpty(field.getValue().trim()))
                emptyFields.add(field.getKey());
        }
        
        return emptyFields;
    }
    
    public static Set<String> checkInvalidFieldsProjectManager(Map<String, String> fields){
        Set<String> invalidFields = new HashSet<>();
        
        String managerName = fields.get("tfName");
        String managerLastNameFather = fields.get("tfLastNameFather");
        String managerLastNameMother = fields.get("tfLastNameMother");
        String managerPosition = fields.get("tfPosition");
        String managerEmail = fields.get("tfEmail");
        String managerPhone = fields.get("tfPhone");
        
        if (!isLengthValid(managerName, 60) || !ValidationUtils.isOnlyLetters(managerName)) {
            invalidFields.add("tfName");
        }
        
        if(!isLengthValid(managerLastNameFather, 60) || !ValidationUtils.isOnlyLetters(managerLastNameFather)){
            invalidFields.add("tfLastNameFather");
        }
        
        if(!isLengthValid(managerLastNameMother, 60) || !ValidationUtils.isOnlyLetters(managerLastNameMother)){
            invalidFields.add("tfLastNameMother");
        }
        
        if (!isLengthValid(managerPosition, 60) || !ValidationUtils.isOnlyLetters(managerPosition)) {
            invalidFields.add("tfPosition");
        }
        
        if (!isLengthValid(managerEmail, 100) || !ValidationUtils.isValidEmail(managerEmail)) {
            invalidFields.add("tfEmail");
        }
        
        if (!isLengthValid(managerPhone, 10) || !ValidationUtils.isOnlyNumber(managerPhone)) {
            invalidFields.add("tfPhone");
        }
        
        return invalidFields;
    }
    
    public static boolean isLengthValid(String value, int maxLength){
        return value != null && value.length() <= maxLength;
    }
    
    public static boolean isTextAreaEmpty(String longComment){
        return longComment != null && longComment.trim().isEmpty();
    }
    
    public static boolean isTextFieldEmpty(String text){
        return text != null && text.trim().isEmpty();
    }
    
}
