import java.sql.*;
import java.util.Scanner;

public class dataBaseClient
{
	private Connection con = null;
	private PreparedStatement stmt_selectProjektas;
	private PreparedStatement stmt_selectProjektasByCategory;
	private PreparedStatement stmt_insertProjektas;
	private PreparedStatement stmt_insertProjektasWithoutValanduSkaicius;
   	private PreparedStatement stmt_deleteProjektas;
   	private PreparedStatement stmt_updateProjektas;
   	private PreparedStatement stmt_updateProjektasWithoutValanduSkaicius;
   	private PreparedStatement stmt_insertVadovas;
   	private PreparedStatement stmt_insertVadovaujami_projektai;
   	private PreparedStatement stmt_updateVadovaujami_projektai;
	
	public dataBaseClient(String url, String user, String pass) {
        try 
        {
            loadDriver();
            getConnection(url, user, pass);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
    
    private void loadDriver() throws Exception {
        try 
        {
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException cnfe) 
        {
            throw new Exception("Unable to load the driver class!");
        }
    }
    
    private Connection getConnection(String url, String user, String pass) throws Exception {
        try 
        {
            con = DriverManager.getConnection(url, user, pass);
        }
        catch (SQLException sqle) 
        {
            con = null;
            throw new Exception("Couldn't get connection!");
        }
        return con;
    }
    
	public boolean isConnected() 
	{
        if (con != null) 
        {
            return true;
        }  
        return false;
    }
    
    public void closeConnection() throws SQLException 
    {
        con.close();
    }    
    
    public void initPreparedStatements()
    {
    	try
    	{
    	 this.stmt_selectProjektasByCategory = this.con.prepareStatement("SELECT ID as \"ID\", Pavadinimas as \"Pavadinimas\", Kategorija as \"Kategorija\", Prioritetine_Svarba as \"Prioritetine_Svarba\", Valandu_skaicius as \"Valandu_skaicius\" FROM Projektai WHERE kategorija = ?");
         this.stmt_selectProjektas = this.con.prepareStatement("SELECT ID as \"ID\", Pavadinimas as \"Pavadinimas\", Kategorija as \"Kategorija\", Prioritetine_Svarba as \"Prioritetine_Svarba\", Valandu_skaicius as \"Valandu_skaicius\" FROM Projektai WHERE ID = ?");         
         this.stmt_deleteProjektas = this.con.prepareStatement("DELETE FROM Projektai WHERE ID = ?");
         this.stmt_insertProjektas = this.con.prepareStatement("INSERT INTO Projektai (Pavadinimas, Kategorija, Prioritetine_Svarba, Valandu_skaicius) VALUES(?, ?, ?, ?)");
         this.stmt_insertProjektasWithoutValanduSkaicius = this.con.prepareStatement("INSERT INTO Projektai (Pavadinimas, Kategorija, Prioritetine_Svarba) VALUES(?, ?, ?)"); 
         this.stmt_updateProjektas = this.con.prepareStatement("UPDATE Projektai SET Pavadinimas = ?, Kategorija = ?, Prioritetine_Svarba = ?, Valandu_skaicius = ? WHERE ID = ?");
         this.stmt_updateProjektasWithoutValanduSkaicius = this.con.prepareStatement("UPDATE Projektai SET Pavadinimas = ?, Kategorija = ?, Prioritetine_Svarba = ? WHERE ID = ?");
         this.stmt_insertVadovas = this.con.prepareStatement("INSERT INTO Vadovas (Asmens_kodas, Vardas, Pavarde, Gimimo_data) VALUES(?, ?, ?, ?)");
         this.stmt_insertVadovaujami_projektai = this.con.prepareStatement("INSERT INTO Vadovaujami_projektai VALUES(?, ?)");
         this.stmt_updateVadovaujami_projektai = this.con.prepareStatement("DELETE FROM Vadovaujami_projektai WHERE Vadovo_ID = ? AND Projekto_ID = ?");
    	 //this.stmt_selectAllProjektas = this.con.prepareStatement("SELECT * FROM Projektas");
    	}
    	catch (SQLException sqle) 
    	{
         System.out.println(sqle);
      }
    }
    public void closePreparedStatements() 
	 {
      try 
		{
			this.stmt_selectProjektas.close();
			this.stmt_selectProjektasByCategory.close();
			this.stmt_deleteProjektas.close();
			this.stmt_insertProjektas.close();
			this.stmt_insertProjektasWithoutValanduSkaicius.close();
			this.stmt_updateProjektas.close();
			this.stmt_updateProjektasWithoutValanduSkaicius.close();
			this.stmt_insertVadovas.close();
			this.stmt_insertVadovaujami_projektai.close();
			this.stmt_updateVadovaujami_projektai.close();
			//this.stmt_selectAllProjektas.close();
      } 
		catch (SQLException sqle) 
		{
         System.out.println(sqle);
      }
    }
    
    public ResultSet selectProjektasByCategory(String kategorija) throws SQLException
    {
    	this.stmt_selectProjektasByCategory.setString(1, kategorija);
    	ResultSet result = this.stmt_selectProjektasByCategory.executeQuery();
    	if (result.isBeforeFirst()) 
      {
          return result;
      } 
      else 
      {
          return null;      
      }
    }
    public ResultSet selectProjektas(int ID) throws SQLException
    {
    	this.stmt_selectProjektas.setInt(1, ID);
    	ResultSet result = this.stmt_selectProjektas.executeQuery();
    	if (result.isBeforeFirst()) 
      {
          return result;
      } 
      else 
      {
          return null;      
      }
    }
    public ResultSet selectAllProjektas() throws SQLException
    {
    	Statement stmt = this.con.createStatement();
      ResultSet result = stmt.executeQuery("SELECT * FROM Projektai");
      if (result.isBeforeFirst()) 
      {
          return result;
      } 
      else 
      {
          return null;      
      }
      
    }
    public ResultSet selectVadovuVykdomiProjektai() throws SQLException
    {
    	Statement stmt = this.con.createStatement();
      ResultSet result = stmt.executeQuery("SELECT * FROM Vadovas V JOIN Vadovaujami_projektai Vp ON V.ID = Vp.Vadovo_ID JOIN Projektai P ON P.ID = Vp.Vadovo_ID");
      if (result.isBeforeFirst()) 
      {
          return result;
      } 
      else 
      {
          return null;      
      }
      
    }
    public void deleteProjektas (int ID) throws SQLException 
    {
        this.stmt_deleteProjektas.setInt(1, ID);
        this.stmt_deleteProjektas.executeUpdate();
    }
    public void insertProjektas(String pavadinimas, String kategorija, String prioritetine_svarba, int valandu_skaicius) throws SQLException 
    {
        this.stmt_insertProjektas.setString(1, pavadinimas);
        this.stmt_insertProjektas.setString(2, kategorija);
        this.stmt_insertProjektas.setString(3, prioritetine_svarba);
        this.stmt_insertProjektas.setInt(4, valandu_skaicius);
        this.stmt_insertProjektas.executeUpdate();
    }
    public void insertProjektasWithoutValanduSkaicius(String pavadinimas, String kategorija, String prioritetine_svarba) throws SQLException 
    {
        this.stmt_insertProjektasWithoutValanduSkaicius.setString(1, pavadinimas);
        this.stmt_insertProjektasWithoutValanduSkaicius.setString(2, kategorija);
        this.stmt_insertProjektasWithoutValanduSkaicius.setString(3, prioritetine_svarba);
        this.stmt_insertProjektasWithoutValanduSkaicius.executeUpdate();
    }
    public void updateProjektas(String pavadinimas, String kategorija, String prioritetine_svarba, int valandu_skaicius, int ID) throws SQLException 
    {
        this.stmt_updateProjektas.setString(1, pavadinimas);
        this.stmt_updateProjektas.setString(2, kategorija);
        this.stmt_updateProjektas.setString(3, prioritetine_svarba);
        this.stmt_updateProjektas.setInt(4, valandu_skaicius);
        this.stmt_updateProjektas.setInt(5, ID);
        this.stmt_updateProjektas.executeUpdate();
    }
    public void updateProjektasWithoutValanduSkaicius(String pavadinimas, String kategorija, String prioritetine_svarba, int ID) throws SQLException 
    {
        this.stmt_updateProjektasWithoutValanduSkaicius.setString(1, pavadinimas);
        this.stmt_updateProjektasWithoutValanduSkaicius.setString(2, kategorija);
        this.stmt_updateProjektasWithoutValanduSkaicius.setString(3, prioritetine_svarba);
        this.stmt_updateProjektas.setInt(4, ID);
        this.stmt_updateProjektasWithoutValanduSkaicius.executeUpdate();
    }
    public int insertVadovas(long asmens_kodas, String vardas, String pavarde, String gimimo_data) throws SQLException
    {
        this.stmt_insertVadovas.setLong(1, asmens_kodas);
        this.stmt_insertVadovas.setString(2, vardas);
        this.stmt_insertVadovas.setString(3, pavarde);
        this.stmt_insertVadovas.setDate(4, Date.valueOf(gimimo_data));
        this.stmt_insertVadovas.executeUpdate();
        System.out.println("Vadovas sekmingai pridetas");
        Statement stmt = this.con.createStatement();
        ResultSet r = stmt.executeQuery("SELECT * FROM Vadovas WHERE Asmens_kodas = " + asmens_kodas);
        r.next();
        int ID = r.getInt("ID");
        return ID;
        //System.out.println("Vadovas pridetas, ID = " + ID);
    }
    public void insertVadovaujami_projektai(int ID, int projekto_ID) throws SQLException 
    {
        this.stmt_insertVadovaujami_projektai.setInt(1, ID);
        this.stmt_insertVadovaujami_projektai.setInt(2, projekto_ID);
        this.stmt_insertVadovaujami_projektai.executeUpdate();
        System.out.println("Projektas sekmingai pridetas");
    }
    public void sukeistiProjektus(int vadovo_ID1, int projekto_ID1, int vadovo_ID2, int projekto_ID2) throws SQLException 
    {
    	Statement stmt = this.con.createStatement();
        con.setAutoCommit(false);
        try
        {
        	 this.stmt_updateVadovaujami_projektai.setInt(1, vadovo_ID1);
        	 this.stmt_updateVadovaujami_projektai.setInt(2, projekto_ID1);
        	 this.stmt_updateVadovaujami_projektai.executeUpdate();
        	 
        	 this.stmt_insertVadovaujami_projektai.setInt(1, vadovo_ID1);
        	 this.stmt_insertVadovaujami_projektai.setInt(2, projekto_ID2);
        	 this.stmt_insertVadovaujami_projektai.executeUpdate();
        	 System.out.println("Pirmam vadovui projektas sekmingai sukeistas");
        	 
        	 this.stmt_updateVadovaujami_projektai.setInt(1, vadovo_ID2);
        	 this.stmt_updateVadovaujami_projektai.setInt(2, projekto_ID2);
        	 this.stmt_updateVadovaujami_projektai.executeUpdate();
        	 
        	 this.stmt_insertVadovaujami_projektai.setInt(1, vadovo_ID2);
        	 this.stmt_insertVadovaujami_projektai.setInt(2, projekto_ID1);
        	 this.stmt_insertVadovaujami_projektai.executeUpdate();
        	 System.out.println("Antram vadovui projektas sekmingai sukeistas");
        	 
        	 con.commit();
    		 con.setAutoCommit(true);
        }
        catch(Exception e)
        {
          System.err.println( e.getClass().getName()+": "+ e.getMessage());
		    con.rollback();
		    con.setAutoCommit(true);
        }
    }
}
