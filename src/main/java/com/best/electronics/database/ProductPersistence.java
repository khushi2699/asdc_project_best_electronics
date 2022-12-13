package com.best.electronics.database;
import java.util.ArrayList;
import java.util.Map;
import com.best.electronics.exceptions.DataNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class ProductPersistence {

    private static ProductPersistence single_instance = null;

    private ProductPersistence()
    {}

    Logger logger = (Logger) LoggerFactory.getLogger(ProductPersistence.class);
    public static ProductPersistence getInstance()
    {
        if (single_instance == null)
            single_instance = new ProductPersistence();

        return single_instance;
    }
    public ArrayList<Map<String, Object>> getDetails(IDatabasePersistence p) throws Exception {

        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        ArrayList<Map<String, Object>> result = new ArrayList<>();
       result = p.loadData("{call get_product_list()}", new ArrayList<>());
       if(result.isEmpty()){
           throw new DataNotFoundException("Unable to load product list");
       }
       else {
           return result;
       }
    }

    public ArrayList<Map<String, Object>> getAllUsersDetails(IDatabasePersistence p) throws Exception {

        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        result = p.loadData("{call get_all_user_details()}", new ArrayList<>());
        if(result.isEmpty()){
            throw new DataNotFoundException("Unable to load users list");
        }
        else {
            return result;
        }
    }
}
