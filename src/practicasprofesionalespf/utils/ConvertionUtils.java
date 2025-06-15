package practicasprofesionalespf.utils;

public class ConvertionUtils {
    
    public static double convertToDouble(String numberStr){
        numberStr = numberStr.replace(",", ".");
        return Double.parseDouble(numberStr); 
    }
    
}
