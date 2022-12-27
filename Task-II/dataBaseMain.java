import java.sql.SQLException;

public class dataBaseMain {
    private static String url = "jdbc:postgresql://pgsql3.mif/studentu";
    private static String username = "my_username";
    private static String password = "my_password";
    
    public static void main(String[] args) {  
        dataBaseClient db = new dataBaseClient(url, username, password);
        if (db.isConnected()) {
            System.out.println("Successfully connected to Database"); 
            db.initPreparedStatements();
            dataBaseUI.start(db);
            db.closePreparedStatements();
            try {
                db.closeConnection(); 
            } catch (SQLException sqle) {
                System.out.println(sqle);
            }
        }
    } 
}
