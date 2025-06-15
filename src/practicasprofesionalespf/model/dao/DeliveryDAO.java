package practicasprofesionalespf.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import practicasprofesionalespf.model.DBConnection;
import practicasprofesionalespf.model.enums.DeliveryType;
import practicasprofesionalespf.model.pojo.Delivery;
import practicasprofesionalespf.model.pojo.OperationResult;

public class DeliveryDAO {
    
    public static ArrayList<Delivery> obtainDelivery() throws SQLException{
        ArrayList<Delivery> deliveries = new ArrayList<>();
        String sqlQuery = "SELECT idDelivery, name, startDate, "
                + "endDate, deliveryType FROM Delivery";
        
        try(Connection dbConnection = new DBConnection().createConnection();
            PreparedStatement query = dbConnection.prepareStatement(sqlQuery)){
            
            try(ResultSet result = query.executeQuery()){
                while(result.next())
                    deliveries.add(convertDelivery(result));
            }
        }
        
        return deliveries;
    }
        
    
    private static Delivery convertDelivery(ResultSet result) throws SQLException{
        Delivery delivery = new Delivery();
        delivery.setIdDelivery(result.getInt("idDelivery"));
        delivery.setName(result.getString("name"));
        delivery.setStartDate(result.getTimestamp("startDate")
                .toLocalDateTime());
        delivery.setEndDate(result.getTimestamp("endDate")
                .toLocalDateTime());
        
        String deliveryTypeStr = result.getString("deliveryType");
        
        if (deliveryTypeStr != null) {
            delivery.setDeliveryType(DeliveryType.valueOf(deliveryTypeStr.replace(" ", "_")));
        } else {
            delivery.setDeliveryType(null);
        }
        
        return delivery;
        
    }
    
    public static OperationResult makeADelivery(int idInitialDocument, int idDelivery, int idRecord) throws SQLException{
        OperationResult result = new OperationResult();
        String sqlQuery = "UPDATE Delivery SET idInitialDocument = ?, idRecord = ? WHERE idDelivery = ?";
        
        try(Connection dbConnection = new DBConnection().createConnection();
            PreparedStatement update = dbConnection.prepareStatement(sqlQuery)){
            
            update.setInt(1, idInitialDocument);
            update.setInt(2, idRecord);
            update.setInt(3, idDelivery);
            int affectedRows = update.executeUpdate();
            
            if(affectedRows == 1){
                result.setError(false);
                result.setMessage("Documento entregado correctamente");
            }else{
                result.setError(true);
                result.setMessage("No se pudo completar la entrega, "
                        + "inténtelo más tarde");
            }
        }
        
        return result;
    }
    
    public static boolean isDuplicate(int idRecord, int idDelivery) throws SQLException {
        String query = "SELECT COUNT(*) FROM Delivery WHERE idDelivery = ? AND idRecord = ? AND idInitialDocument IS NOT NULL";
    
        try (Connection conn = new DBConnection().createConnection();
            PreparedStatement check = conn.prepareStatement(query)) {
                check.setInt(1, idDelivery);
                check.setInt(2, idRecord);

            try (ResultSet result = check.executeQuery()) {
                if (result.next()) {
                    int count = result.getInt(1);
                    return count > 0;
            }
        }
    }

        return false;
    }
}
