/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javafx.scene.control.Alert
 *  javafx.scene.control.Alert$AlertType
 */
package tools;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import static controllers.ServerController.testdatab;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import static tools.AlertMaker.showMaterialDialog;
import static tools.AlertMaker.showMaterialDialogError;

public class myConnectionPP {

    static Connection cnx;
    static Statement st;
    static ResultSet rst;
    public static int passe;
    static String url;
    static String user;
    static String password;
    public static SQLException ex2;
    public static String myerrorMessage;

    public static void delete(String id, String from, String where) {
        try {
            //DELETE FROM stock WHERE Quantite=0
            String query = "DELETE FROM " + from + " WHERE " + where + " = '" + id + "';";
            if (cnx == null) {
                cnx = connecterDB();
            }
            st = cnx.createStatement();
            st.executeUpdate(query);
            passe = 1;
            System.out.println(from + "bien supprimée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static ResultSet executeQuery(String query) throws SQLException {
        if (cnx == null) {
            cnx = connecterDB();
        }
        st = cnx.createStatement();
        return st.executeQuery(query);
    }

    private static void executeUpdate(String query) throws SQLException {
        if (cnx == null) {
            cnx = connecterDB();
        }
        st = cnx.createStatement();
        st.executeUpdate(query);
    }

    public static void deleteTwoCondition(String id, String where, String id2, String where2, String from) {
        try {
            String query = "DELETE FROM " + from + " WHERE "
                    + where + " = '" + id + "'"
                    + " AND "
                    + where2
                    + "= '"
                    + id2
                    + "' "
                    + " ;";
            if (cnx == null) {
                cnx = connecterDB();
            }
            st = cnx.createStatement();
            st.executeUpdate(query);
            passe = 1;
            System.out.println(from + " bien supprimée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ResultSet fillCombo(String element, String fromTable) {
        try {
            if (cnx == null) {
                cnx = connecterDB();
            }
            st = cnx.createStatement();
            rst = st.executeQuery("SELECT " + element + " FROM " + fromTable);
        } catch (SQLException ex) {
        }
        return rst;
    }

    public static ResultSet fillCombo(String fromTable) {
        try {
            if (cnx == null) {
                cnx = connecterDB();
            }
            st = cnx.createStatement();
            rst = st.executeQuery("SELECT * FROM " + fromTable);
        } catch (SQLException sQLException) {
            // empty catch block
            Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, sQLException);

        }
        return rst;
    }

    public static ResultSet fillComboDISTINCT(String fromTable, String columnName) {
        try {
            if (cnx == null) {
                cnx = connecterDB();
            }
            st = cnx.createStatement();
            rst = st.executeQuery("SELECT DISTINCT " + columnName + " FROM " + fromTable);
        } catch (SQLException sQLException) {
            Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, sQLException);
        }
        return rst;
    }

    public static ResultSet fillComboWithConditionwithoutDuplicatedData(String select, String from, String where, String what, String DatePeremp) {
        try {
            if (cnx == null) {
                cnx = connecterDB();
            }
            st = cnx.createStatement();
            rst = st.executeQuery("SELECT DISTINCT " + select + " FROM " + from + " WHERE " + where + "='" + what + "'" + "ORDER BY " + DatePeremp);

        } catch (SQLException ex) {
            Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rst;
    }

    public static ResultSet fillComboWithConditionDuplicatedDataAndSorted(String select, String from, String where, String what, String DatePeremp) {
        try {
            if (cnx == null) {
                cnx = connecterDB();
            }
            st = cnx.createStatement();
            rst = st.executeQuery("SELECT  " + select + " FROM " + from + " WHERE " + where + "='" + what + "'" + "ORDER BY " + DatePeremp);

        } catch (SQLException ex) {
            Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rst;
    }

    public static ResultSet fillCombo2(String fromTable, String where, String clm) {
        try {
            if (cnx == null) {
                cnx = connecterDB();
            }
            st = cnx.createStatement();
            rst = st.executeQuery("SELECT  * FROM " + fromTable + " WHERE " + where + " LIKE '%" + clm + "%'");
        } catch (SQLException sQLException) {
            // empty catch block
            Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, sQLException);

        }
        return rst;
    }

    public static ResultSet checkfromDB(String tableName, String fieldName, String where) {
        try {
            if (cnx == null) {
                cnx = connecterDB();
            }
            st = cnx.createStatement();
            rst = st.executeQuery("SELECT * FROM " + tableName + " WHERE " + fieldName + " = '" + where + "';");
            if (rst.next()) {
                // Field exists
                System.out.println("Field '" + where + "' exists in table '" + tableName + "'");
            } else {
                // Field does not exist
                System.out.println("Field '" + where + "' does not exist in table '" + tableName + "'");
                String query = "insert into " + tableName + " (" + fieldName + ") values('"
                        + where + "' )";
                PreparedStatement ps = cnx.prepareStatement(query);
                ps.execute();
            }

        } catch (SQLException sQLException) {
            // empty catch block
            Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, sQLException);

        }
        return rst;
    }

    public static ResultSet inst(String table) {
        try {
            if (cnx == null) {
                cnx = connecterDB();
            }
            st = cnx.createStatement();
            rst = st.executeQuery("SELECT * FROM " + table);
        } catch (SQLException sQLException) {
            // empty catch block
            Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, sQLException);

        }
        return rst;
    }

    public static ResultSet inst2(String d, String table, String clm) {
        try {
            if (cnx == null) {
                cnx = connecterDB();
            }
            st = cnx.createStatement();
            rst = st.executeQuery("SELECT * FROM  " + table + " where " + clm + " LIKE '%" + d + "%'");
        } catch (SQLException sQLException) {
            // empty catch block
            System.err.println("ERROR SQL " + sQLException);
            Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, sQLException);

        }
        return rst;
    }

    public static ResultSet inst3(String table, String clm, String nom) {
        try {
            if (cnx == null) {
                cnx = connecterDB();
            }
            st = cnx.createStatement();
            rst = st.executeQuery("SELECT * FROM  " + table + " where " + clm + "='" + nom + "'");
        } catch (SQLException sQLException) {
            // empty catch block
            Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, sQLException);

        }
        return rst;
    }

    public static ResultSet inst4(String table, String clm, String m, String y) {
        try {
            if (cnx == null) {
                cnx = connecterDB();
            }
            st = cnx.createStatement();
            rst = st.executeQuery("SELECT * FROM " + table + " WHERE MONTH(" + clm + ") = " + m + " AND YEAR(" + clm + ")=" + y + "");
        } catch (SQLException sQLException) {
            // empty catch block
            Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, sQLException);

        }

        return rst;
    }

    public static ResultSet inst5(String table, String clm, String m, String y, String clm2, String ing) {
        try {
            if (cnx == null) {
                cnx = connecterDB();
            }
            st = cnx.createStatement();
            if (ing.equals("TOUS")) {
                rst = st.executeQuery("SELECT * FROM " + table + " WHERE MONTH(" + clm + ") = " + m + " AND YEAR(" + clm + ")=" + y);
            } else {
                rst = st.executeQuery("SELECT * FROM " + table + " WHERE MONTH(" + clm + ") = " + m + " AND YEAR(" + clm + ")=" + y + " AND " + clm2 + "='" + ing + "' ");
            }

        } catch (SQLException sQLException) {
            // empty catch block
            Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, sQLException);

        }
        return rst;
    }

    public static ResultSet selectitemsfromrs(List<String> nbonValues, String Produit) {

        if (cnx == null) {
            cnx = connecterDB();
        }

        try {

            if (nbonValues != null && !nbonValues.isEmpty()) {
                StringBuilder queryBuilder = new StringBuilder();
                queryBuilder.append("SELECT * FROM bllocaldetails WHERE Produit = '" + Produit + "' AND NBon IN (");

                for (int i = 0; i < nbonValues.size(); i++) {
                    queryBuilder.append("'").append(nbonValues.get(i)).append("'");
                    if (i < nbonValues.size() - 1) {
                        queryBuilder.append(", ");
                    }
                }

                queryBuilder.append(")");

                String sqlQuery = queryBuilder.toString();
                st = cnx.createStatement();
                rst = st.executeQuery(sqlQuery);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rst;
    }

    public static ResultSet selectitemsfrom(List<String> nbonValues) {
        nbonValues = new ArrayList<>();

        if (cnx == null) {
            cnx = connecterDB();
        }
        try {
            st = cnx.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, ex);
        }
        // SELECT * FROM bllocaldetails WHERE Produit = 'Aténolol' AND NBon IN ('1212', '121212', '121213', '1244', '2121', '21212');
        // Check if there are any NBon values
        if (!nbonValues.isEmpty()) {
            // Build the SQL query for the second query
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("SELECT * FROM bllocaldetails ");
            queryBuilder.append("WHERE Produit = 'Aténolol' AND NBon IN (");

            for (int i = 0; i < nbonValues.size(); i++) {
                queryBuilder.append("'").append(nbonValues.get(i)).append("'");
                if (i < nbonValues.size() - 1) {
                    queryBuilder.append(", ");
                }
            }

            queryBuilder.append(")");

            String sqlQuery = queryBuilder.toString();

            try {
                // Execute the second SQL query

                rst = st.executeQuery(sqlQuery);

                //rst = st.executeQuery("SELECT * FROM " + table + " WHERE MONTH(" + clm + ") = " + m + " AND YEAR(" + clm + ")=" + y + " AND " + clm2 + "='" + ing + "' ");
                // empty catch block
            } catch (SQLException ex) {
                Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rst;

    }

    public static String selectPUfromStock(String table, String clm, String clmID, String ID) {
        String PU = null;
        try {
            if (cnx == null) {
                cnx = connecterDB();
            }
            st = cnx.createStatement();
            //SELECT PU FROM stock WHERE ID ='1';
            rst = st.executeQuery("SELECT " + clm + " FROM " + table + " WHERE " + clmID + " = " + ID);
            while (rst.next()) {
                PU = rst.getString(clm);
            }
        } catch (SQLException sQLException) {
            // empty catch block
            Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, sQLException);

        }
        return PU;
    }

    public static ResultSet inst8(String table, String clm, String m, String y) {
        try {
            if (cnx == null) {
                cnx = connecterDB();
            }
            st = cnx.createStatement();
            rst = st.executeQuery("SELECT * FROM " + table + " WHERE MONTH(" + clm + ") = " + m + " AND YEAR(" + clm + ")=" + y);
        } catch (SQLException sQLException) {
            // empty catch block
            Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, sQLException);

        }
        return rst;
    }

    public static ResultSet inst6(String table, String clm, LocalDate m, LocalDate y, String clm2, String ing) {
        try {
            if (cnx == null) {
                cnx = connecterDB();
            }
            st = cnx.createStatement();
            //SELECT * from bllocal where (`DateBon` BETWEEN '2021-07-01'AND '2021-08-29') 
            String sql = "SELECT * from " + table + " where (`" + clm + "` BETWEEN '" + m + "'AND '" + y + "') AND " + clm2 + "='" + ing + "'";
            rst = st.executeQuery(sql);
            //rst = st.executeQuery("SELECT * FROM " + table + " WHERE (" + clm + " BETWEEN  " + m + " AND " + y + ") AND " + clm2 + "='" + ing + "' ");
        } catch (SQLException sQLException) {
            Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, sQLException);
        }
        return rst;
    }

    public static ResultSet inst7(String table, String clm, LocalDate m, LocalDate y) {
        try {
            if (cnx == null) {
                cnx = connecterDB();
            }
            st = cnx.createStatement();
            //SELECT * from bllocal where (`DateBon` BETWEEN '2021-07-01'AND '2021-08-29') 
            String sql = "SELECT * from " + table + " where (`" + clm + "` BETWEEN '" + m + "'AND '" + y + "')";
            rst = st.executeQuery(sql);
            //rst = st.executeQuery("SELECT * FROM " + table + " WHERE (" + clm + " BETWEEN  " + m + " AND " + y + ") AND " + clm2 + "='" + ing + "' ");
        } catch (SQLException sQLException) {
            Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, sQLException);
        }
        return rst;
    }

    public static ResultSet inst9(String table, String clm1, String clm2, String clm3) {
        try {
            if (cnx == null) {
                cnx = connecterDB();
            }
            st = cnx.createStatement();
            String sql = "SELECT `" + clm1 + "`,`" + clm2 + "`,`" + clm3 + "` FROM `" + table + "`";
            rst = st.executeQuery(sql);
        } catch (SQLException sQLException) {
            Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, sQLException);
        }
        return rst;
    }

    public static ResultSet SearchTwoConditions(String d, String table, String clm, String clm2, String myclm) {
        try {
            if (cnx == null) {
                cnx = connecterDB();
            }
            st = cnx.createStatement();
            rst = st.executeQuery("SELECT * FROM " + table + " WHERE " + clm + " = '" + d + "' AND " + clm + "='" + myclm + "'");
        } catch (SQLException sQLException) {
            // empty catch block
            System.err.println("ERROR SQL " + sQLException);
            Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, sQLException);

        }
        return rst;
    }

    public static ResultSet fillComboWithoutDuplicateData(String fromTable, String where) {
        try {
            if (cnx == null) {
                cnx = connecterDB();
            }
            st = cnx.createStatement();
            rst = st.executeQuery("SELECT DISTINCT " + where + " FROM " + fromTable);
        } catch (SQLException sQLException) {
            // empty catch block
            Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, sQLException);

        }
        return rst;
    }

    //SELECT SUM(PU) FROM stock 
    public static String Somme(String table, String clm) {
        String price = null;
        try {
            if (cnx == null) {
                cnx = connecterDB();
            }
            st = cnx.createStatement();
            rst = st.executeQuery("SELECT SUM(" + clm + ") FROM  " + table);
            while (rst.next()) {
                price = rst.getString(1);
            }

        } catch (SQLException sQLException) {
            Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, sQLException);
        }
        return price;
    }

    public static String SommeCondition(String table, String clm, String where, String clm2) {
        String price = null;
        try {
            if (cnx == null) {
                cnx = connecterDB();
            }
            st = cnx.createStatement();
            //SELECT SUM(PT) FROM stock WHERE gamme='Medicament';

            rst = st.executeQuery("SELECT SUM(" + clm + ") FROM " + table + " WHERE " + clm2 + " = '" + where + "'");

            //  rst = st.executeQuery("SELECT SUM(" + clm + ") FROM  " + table +"WHERE "+ clm2+ " = ' "+where+" '");
            while (rst.next()) {
                price = rst.getString(1);
            }

        } catch (SQLException sQLException) {
            Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, sQLException);
        }
        return price;
    }

    public static String price(String table, String clm, String nom, String clmResult) {
        String price = null;
        try {
            if (cnx == null) {
                cnx = connecterDB();
            }
            st = cnx.createStatement();
            rst = st.executeQuery("SELECT * FROM  " + table + " where " + clm + "='" + nom + "'");
            while (rst.next()) {
                price = rst.getString(clmResult);
            }

        } catch (SQLException sQLException) {
            Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, sQLException);
        }
        return price;
    }

    public static String SelectStringFromTableWhereClmIs(String table, String clm, String nom, String targetTable, String clm2, String nlot) {
        String price = null;
        try {
            if (cnx == null) {
                cnx = connecterDB();
            }
            //SELECT `Quantite` FROM `stock` WHERE `DCI`='Pindolol';
            st = cnx.createStatement();
            rst = st.executeQuery("SELECT " + targetTable + " FROM  " + table + " where " + clm + "='" + nom + "' AND " + clm2 + " ='" + nlot + "'");

            while (rst != null && rst.next()) {
                price = rst.getString(1);
            }

        } catch (SQLException sQLException) {
            Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, sQLException);

        }
        return price;
    }

    public static void addPatient(String nom, String prenom, String sexe, LocalDate DateNaissance, int age, LocalDate DateConsult, String Resume) {
        if (cnx == null) {
            cnx = connecterDB();
        }
        try {
            String query = "insert into patients (nom,prenom,sexe,DateNaissance,age,DateConsultation,Resume) values('"
                    + nom + "','"
                    + prenom + "','"
                    + sexe + "','"
                    + DateNaissance + "','"
                    + age + "','"
                    + DateConsult + "','"
                    + Resume + "' )";
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.execute();
            passe = 1;
        } catch (SQLException ex) {
            Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int LastEnterySQL(String ID, String table) {
        int lastEntry = 0;

        try {
            if (cnx == null) {
                cnx = connecterDB();
            }
            String query = "SELECT MAX(" + ID + ") FROM " + table;
            st = cnx.createStatement();
            rst = st.executeQuery(query);
            if (rst.next()) {
                lastEntry = rst.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lastEntry;
    }
    public static int createNewOrdonnance(int IDPatient) {
    int IDOrdonnance = -1;
    try {
        if (cnx == null) {
            cnx = connecterDB();
        }
        String query = "INSERT INTO ordonnances (IDPatient, Date) VALUES (?, NOW())";
        PreparedStatement ps = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, IDPatient);
        ps.executeUpdate();

        // Get the generated IDOrdonnance
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            IDOrdonnance = rs.getInt(1);
        }
    } catch (SQLException ex) {
        Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, ex);
    }
    return IDOrdonnance;
}

public static void addToOrdonnance(int IDOrdonnance, String  Medicament, int Quantity, String Detail) {
    try {
        if (cnx == null) {
            cnx = connecterDB();
        }
        String query = "INSERT INTO ordonnance_details (IDOrdonnance, Medicament, Quantity, Detail) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = cnx.prepareStatement(query);
        ps.setInt(1, IDOrdonnance);
        ps.setString(2, Medicament);
        ps.setInt(3, Quantity);
        ps.setString(4, Detail);
        ps.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, ex);
    }
}


    public static void addToExamen(int IDPatient, String Examen) {
        if (cnx == null) {
            cnx = connecterDB();
        }
        try {
            // Check if the exam already exists for the given patient
            String queryCheck = "SELECT * FROM examenprescrit WHERE IDPatient = ? AND Examen = ?";
            PreparedStatement psCheck = cnx.prepareStatement(queryCheck);
            psCheck.setInt(1, IDPatient);
            psCheck.setString(2, Examen);
            ResultSet rs = psCheck.executeQuery();

            if (rs.next()) {
                // The exam already exists for the patient, display an error message
                myerrorMessage = "Error: The exam '" + Examen + "' has already been added for the patient.";
                System.out.println("Error: The exam '" + Examen + "' has already been added for the patient.");
            } else {
                // The exam does not exist, proceed with insertion
                String query = "INSERT INTO examenprescrit (IDPatient, Examen) VALUES (?, ?)";
                PreparedStatement ps = cnx.prepareStatement(query);
                ps.setInt(1, IDPatient);
                ps.setString(2, Examen);
                ps.execute();
                passe = 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void addUser(String usern, String password) {
        passe = 0;
        if (cnx == null) {
            cnx = connecterDB();
        }
        try {
            String query = "insert into login (username,password)values('" + usern + "','" + password + "')";
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.execute();
            passe = 1;
        } catch (SQLException ex) {
            Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, ex);
            ex2 = ex;
        }
    }

    public static void addNewPara(String table, String clm, JFXTextField mytext, StackPane rootStack, AnchorPane rootAnchor) {
        passe = 0;
        String NB = mytext.getText();
        JFXButton btn = new JFXButton("OK");
        SQLException ex2 = null;
        if (cnx == null) {
            cnx = connecterDB();
        }
        try {
            String query = "insert into " + table + " (" + clm + ") values('"
                    + NB + "' )";
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.execute();
            passe = 1;
        } catch (SQLException ex) {
            Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, ex);
            ex2 = ex;
        }
        if (passe == 1) {
            showMaterialDialog(rootStack, (Node) rootAnchor, Arrays.asList(new JFXButton[]{
                btn
            }), null, "ENREGISTREMENT AVEC SUCCESS");
            mytext.setText(null);
            passe = 0;
        } else {
            showMaterialDialogError(rootStack, (Node) rootAnchor, Arrays.asList(new JFXButton[]{
                btn
            }), ex2 + "", "ERREUR D'AJOUT");
            btn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseevent
                    -> {

            });
        }

    }

    public static void editCell(String id2, String clm, String table, String DCI, String ID) {
        try {
            String query = "UPDATE " + table + " SET "
                    + clm + "='" + DCI
                    + "' WHERE  " + id2 + "='" + ID + "'";
            if (cnx == null) {
                cnx = connecterDB();
            }
            st = cnx.createStatement();
            st.executeUpdate(query);
            System.out.println(clm + " bien modifier");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void readfromfileandinsertintoDb() {
        String filename = "medications.txt";

        try {
            if (cnx == null) {
                cnx = connecterDB();
            }
            st = cnx.createStatement();
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = br.readLine()) != null) {
                // Escape single quotes in the medication name
                String name = line.replace("'", "''");
                // SQL query to insert medication into the database
                String query = String.format("INSERT INTO medicaments (NomMed) VALUES ('%s')", name);
                // Execute the SQL query
                st.executeUpdate(query);
            }

        } catch (SQLException ex) {
            Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void editCellFormeNBLNF(String table, String forme, String NF, String NBL, String ID) {
        passe = 0;
        try {
            String query = "UPDATE "
                    + table
                    + " SET Forme = '" // Added assignment operator before Forme
                    + forme + "' , NBL = '"
                    + NBL + "' , NFACTURE ='"
                    + NF
                    + "' WHERE ID = '"
                    + ID
                    + "' ";
            if (cnx == null) {
                cnx = connecterDB();
            }
            st = cnx.createStatement();
            st.executeUpdate(query);
            System.out.println("Details bien modifiés");
            passe = 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void editBonTotalLivraison(double MT, String Nbon) {
        try {
            String query = "UPDATE bllocal SET "
                    + "MT='" + MT
                    + "' WHERE NBon='" + Nbon + "'";
            if (cnx == null) {
                cnx = connecterDB();
            }
            st = cnx.createStatement();
            st.executeUpdate(query);
            System.out.println("BL Local  bien modifier");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ResultSet instload2things(String from, String close1, String nom, String close2, String passowrd) {
        try {
            if (cnx == null) {
                cnx = connecterDB();
            }
            st = cnx.createStatement();
            String myQuery = "select * from " + from + " where " + close1 + " ='" + nom + "' and " + close2 + "='" + passowrd + "'";
            rst = st.executeQuery(myQuery);
        } catch (SQLException ex) {
            Logger.getLogger(myConnectionPP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rst;
    }

    public static void updateReactivation(LocalDate date) {
        try {
            String query = "UPDATE deadline SET date='" + date + "' WHERE id= 1 ";
            if (cnx == null) {
                cnx = connecterDB();
            }
            st = cnx.createStatement();
            st.executeUpdate(query);
            passe = 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void editSettings(String from, String clmSet, String whatSet, String clmWhere, String whatwhere) {
        passe = 0;
        SQLException e2 = null;
        try {
            String query = "UPDATE " + from + " SET " + clmSet + "='" + whatSet + "' WHERE " + clmWhere + " = " + "'" + whatwhere + "'";
            if (cnx == null) {
                cnx = connecterDB();
            }
            st = cnx.createStatement();
            st.executeUpdate(query);
            passe = 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e2 = e;
            ex2 = e;
        }
        if (passe == 1) {
            System.out.println("Modification Avec Succes");
        } else {
            System.err.println("" + e2);
        }
    }

    public static void editSettings2(String from, String clmSet, String whatSet, String clmSet2, String whatSet2, String clmWhere, String whatwhere) {
        passe = 0;
        SQLException e2 = null;
        try {
            String query = "UPDATE " + from + " SET "
                    + clmSet + "='" + whatSet + "',"
                    + clmSet2 + "='" + whatSet2 + "' "
                    + "WHERE " + clmWhere + " = " + "'" + whatwhere + "'";
            if (cnx == null) {
                cnx = connecterDB();
            }
            st = cnx.createStatement();
            st.executeUpdate(query);
            passe = 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e2 = e;
            ex2 = e;
        }
        if (passe == 1) {
            System.out.println("Modification Avec Succes");
        } else {
            System.err.println("" + e2);
        }
    }

    public static Connection connecterDB() {
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            //System.out.println("Driver oki");
//            /*
//            String url = "jdbc:mysql://sql151.main-hosting.eu.:3306/u708697835_gctes";
//            String user = "u708697835_root";
//            String password = "imgoingunder";*/
//            String url = "jdbc:mysql://127.0.0.1:3306/mypharm";
//            String user = "root";
//            String password = "";
//            Connection cnx = DriverManager.getConnection(url, user, password);
//            System.out.println("Connexion bien établié");
//            return cnx;
//        } catch (Exception e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "VERIFIER VOTRE CONNEXION", "ERREUR", JOptionPane.ERROR_MESSAGE);
//            return null;
//        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("test data base" + testdatab);
            if (testdatab.equals("Online")) {
                url = "jdbc:mysql://sql6.freemysqlhosting.net.:3306/sql6433725";
                user = "sql6433725";
                password = "7JmcRYeRKl";
            } else {
                url = "jdbc:mysql://127.0.0.1:3306/pulsepro";
                user = "root";
                password = "";
            }
            System.out.println("Driver oki");
            Connection cnx2 = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion bien Etablis");
            return cnx2;
        } catch (ClassNotFoundException | SQLException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERREUR DE COMMUNICATION");
            alert.setHeaderText(null);
            alert.setContentText("CONNECTER VOTRE SERVER /n" + e);
            Optional showAndWait = alert.showAndWait();
            return null;
        }
    }

    static {
        passe = 0;
        url = null;
        user = null;
        password = null;
    }
}
