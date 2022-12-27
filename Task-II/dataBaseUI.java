import java.sql.*;
import java.util.Scanner;

public class dataBaseUI
{
	public static void start(dataBaseClient db)
	{
		if( db != null ) 
		{
            int option = -1;
            Scanner sin = new Scanner(System.in);
            sin.useDelimiter("\n");

            ResultSet result = null;

            while (option != 0) 
            {
                showMenu();
                try 
                {
                	System.out.print(">");
                  option = sin.nextInt();
                  switch (option)
                  {
                  	case 1:
                  	       System.out.print("Iveskite kategorija: ");
                            String kategorija = sin.next();
                            result = db.selectProjektasByCategory(kategorija);
                            if (result != null)
                            {
                                displayResultSet(result);
                            }
                            else
                            {
                                System.out.println("Klaidingai ivesta kategorija arba " + kategorija + " kategorija neturi projektu");
                            }
                  	       break;
                  	case 2:
                  	       System.out.println("Visi imones projektai");
                            result = db.selectAllProjektas();
                            displayResultSet(result);
                  	       break;
                  	case 3:
                  	       System.out.print("Iveskite projekto pavadinima: ");
                            String pavadinimas = sin.next();
                            System.out.print("Iveskite kategorija (iki 3 simboliu): ");
                            kategorija = sin.next();
                            System.out.print("Iveskite projekto prioritetine svarba (Didele, Vidutine arba Maza): ");
                            String prioritetine_svarba = sin.next();
                            System.out.print("Ar norite ivesti valandu skaiciu? (t/n) ");
                            String pasirinkimas = sin.next();
                            while(!(pasirinkimas.equals("t") || pasirinkimas.equals("n")))
                            {
                            	System.out.print("Atsakykite t arba n! ");
                            	System.out.print("Ar norite ivesti valandu skaiciu? (t/n) ");
                              pasirinkimas = sin.next();
                            }
                            if (pasirinkimas.equals("t"))
                            {
                            	System.out.print("Iveskite projekto valandu skaiciu: ");
                              int valandu_skaicius = sin.nextInt();
                              db.insertProjektas(pavadinimas, kategorija, prioritetine_svarba, valandu_skaicius);
                            }
                            else
                            {
                            	db.insertProjektasWithoutValanduSkaicius(pavadinimas, kategorija, prioritetine_svarba);
                            }
                            System.out.println("Projektas pridetas");
                  	       break;
                  	case 4:
                  	       result = db.selectAllProjektas();
                            displayResultSet(result);
                            System.out.print("Projekto ID: ");
                            int ID = sin.nextInt();
                            result = db.selectProjektas(ID);
                            if (result != null) {
                                db.deleteProjektas(ID);
                                System.out.println("Projektas " + ID + " buvo sekmingai istrintas.");
                            } 
                            else 
                            {
                                System.out.println("Projektas su tokiu ID nebuvo rastas!");
                                break;
                            }
                  	       break;
                  	case 5:
                            result = db.selectAllProjektas();
                            displayResultSet(result);
                            System.out.print("Projekto ID: ");
                            ID = sin.nextInt();
                            result = db.selectProjektas(ID);
                            if (result != null) 
                            {
                            	System.out.print("Iveskite Projekto pavadinima: ");
                              pavadinimas = sin.next();
                              System.out.print("Iveskite kategorija (iki 3 simboliu): ");
                              kategorija = sin.next();
                              System.out.print("Iveskite projekto prioritetine svarba (Didele, Vidutine arba Maza): ");
                              prioritetine_svarba = sin.next();
                              System.out.print("Ar norite keisti valandu skaiciu? (t/n) ");
                              pasirinkimas = sin.next();
                              while(!(pasirinkimas.equals("t") || pasirinkimas.equals("n")))
                              {
                            	  System.out.print("Atsakykite t arba n! ");
                            	  System.out.print("Ar norite keisti valandu skaiciu? (t/n) ");
                                pasirinkimas = sin.next();
                              }
                              if (pasirinkimas.equals("t"))
                              {
                            	  System.out.print("Iveskite projekto valandu skaiciu: ");
                                int valandu_skaicius = sin.nextInt();
                                db.updateProjektas(pavadinimas, kategorija, prioritetine_svarba, valandu_skaicius, ID);
                              }
                              else
                              {
                            	  db.updateProjektasWithoutValanduSkaicius(pavadinimas, kategorija, prioritetine_svarba, ID);
                              }
                              System.out.println("Projektas pakeistas");
                            }
                            else 
                            {
                                System.out.println("Projektas su tokiu ID nebuvo rastas!");
                                break;
                            }
                  	       break;
                  	case 6:
                  	       System.out.print("Iveskite vadovo asmens koda: ");
                            long asmens_kodas = sin.nextLong();
                            System.out.print("Iveskite vadovo varda: ");
                            String vardas = sin.next();
                            System.out.print("Iveskite vadovo pavarde: ");
                            String pavarde = sin.next();
                            System.out.print("Iveskite vadovo gimimo data (nuo 18 metu) formatu (yyyy-mm-dd): ");
                            String gimimo_data = sin.next();
                            try 
                            {
                                Date date = Date.valueOf(gimimo_data);
                            } 
                            catch (IllegalArgumentException e) 
                            {
                                System.out.println("Neteisingas datos formatas!");
                                break;
                            }
                            ID = db.insertVadovas(asmens_kodas, vardas, pavarde, gimimo_data);
                            System.out.println("Kiek projektu norite prideti siam vadovui?");
                            int nr = sin.nextInt();
                            for (int i = 0; i < nr; ++i)
                            {
                            	result = db.selectAllProjektas();
                            	displayResultSet(result);
                              System.out.print("Projekto ID: ");
                              int projekto_ID = sin.nextInt();
                              result = db.selectProjektas(projekto_ID);
                              if (result != null) 
                              {
                            	  db.insertVadovaujami_projektai(ID, projekto_ID);
                              }
                              else 
                              {
                                System.out.println("Projektas su tokiu ID nebuvo rastas!");
                                break;
                              }
                            }
                  	       break;
                  	case 7:
                  	       result = db.selectVadovuVykdomiProjektai();
                            displayResultSet(result);
                            System.out.println("Kokiems dviem vadovams norite sukeisti projektus (ID)?");
                            int vadovoID1 = sin.nextInt();
                            int vadovoID2 = sin.nextInt();
                            System.out.println("Kokius projektus norite sukeisti (ID)?");
                            int projektoID1 = sin.nextInt();
                            int projektoID2 = sin.nextInt();
                            db.sukeistiProjektus(vadovoID1, projektoID1, vadovoID2, projektoID2);
                  	       break;
                  	case 0:
                  			 break;
                  	default:
                            System.out.println("Tokio pasirinkimo nera!");
                            break;
                  }
                }
                catch (java.util.InputMismatchException ine) 
                {
                  System.out.println("Klaidingai ivesti duomenys arba pasirinkta. Meginkite is naujo!");
                  sin.next();
                  sin.reset();
                } 
                catch (SQLException sqle) 
                {
                  System.out.println(sqle.getMessage());
              	 }
              pause(sin);
            }
            sin.close();
       }
       
	}
	 public static void showMenu() 
	 {
            System.out.println("1. Parodyti visus projektus vienai kategorijai");
            System.out.println("2. Parodyti visus projektus");
            System.out.println("3. Prideti nauja projekta");
            System.out.println("4. Istrinti projekta");
            System.out.println("5. Atnaujinti projekta");
            System.out.println("6. Prideti nauja vadova ir priskirti jam projektus");
            System.out.println("7. Sukeisti projektus vadovams");
            System.out.println("0. Baigti darba");
    }
    
    public static void displayResultSet (ResultSet rs) throws SQLException 
    {
        if (rs != null) 
        {
            ResultSetMetaData md = rs.getMetaData ( );
            int ncols = md.getColumnCount ( );
            int nrows = 0;
            int[ ] width = new int[ncols + 1];       
            StringBuilder b = new StringBuilder ( ); 

            
            for (int i = 1; i <= ncols; i++)
            {
                width[i] = md.getColumnDisplaySize (i);
                if (width[i] < md.getColumnName (i).length ( ))
                    width[i] = md.getColumnName (i).length ( );
                
                if (width[i] < 4 && md.isNullable (i) != 0)
                    width[i] = 4;
            }

            
            b.append ("+");
            for (int i = 1; i <= ncols; i++)
            {
                for (int j = 0; j < width[i]; j++)
                    b.append ("-");
                b.append ("+");
            }

            
            System.out.println (b.toString ( ));
            System.out.print ("|");
            for (int i = 1; i <= ncols; i++)
            {
                System.out.print (md.getColumnName (i));
                for (int j = md.getColumnName (i).length ( ); j < width[i]; j++)
                    System.out.print (" ");
                System.out.print ("|");
            }
            System.out.println ( );
            System.out.println (b.toString ( ));

            

            while (rs.next())
            {
                ++nrows;
                System.out.print ("|");
                for (int i = 1; i <= ncols; i++)
                {
                    String s = rs.getString(i);
                    if (rs.wasNull())
                        s = "Nera";
                    System.out.print (s);
                    for (int j = s.length(); j < width[i]; j++)
                        System.out.print(" ");
                    System.out.print("|");
                }
                System.out.println();
            }

            
            System.out.println (b.toString ( ));
            System.out.println (nrows + " irasai");
        } 
        else 
        {
            throw new SQLException("Tokiu duomenu nera!");
        }
    }

    private static void pause(Scanner sin) 
    {
        sin.nextLine();
        System.out.println("Paspauskite ENTER klavisa, kad testumete darba...");
        sin.nextLine();
        sin.reset();
    }
}