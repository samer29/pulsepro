/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Callback;
import tools.AlertMaker;
import tools.LocalStorage;
import static tools.PDFPrintingExample.printPDF;
import tools.myConnectionPP;
import static tools.myConnectionPP.LastEnterySQL;
import static tools.myConnectionPP.delete;
import static tools.myConnectionPP.editCell;
import static tools.myConnectionPP.ex2;
import static tools.myConnectionPP.price;
import tools.myFunctionsPP;
import static tools.myFunctionsPP.closeStage;
import static tools.myFunctionsPP.fillTableSortedByIDASCENDING;
import static tools.myFunctionsPP.fillcombox;
import static tools.myFunctionsPP.fillculms;
import static tools.myConnectionPP.passe;
import static tools.myFunctionsPP.OnlyIntegersForTextField;
import static tools.myFunctionsPP.calculateDateOfBirth;
import static tools.myFunctionsPP.converLocalDateToString;
import static tools.myFunctionsPP.fillTableWithConditionDESCENDING;
import static tools.myFunctionsPP.loadWebpage;
import static tools.myFunctionsPP.loadWindow;

/**
 * FXML Controller class
 *
 * @author Samer
 */
public class FirstWindowController implements Initializable {

    public static String theme;
    @FXML
    private StackPane PanePatients;
    @FXML
    private JFXTextField txtSearchPatients;
    @FXML
    private TableView TablePatients;
    @FXML
    private TableColumn clmOrdrePatient;
    @FXML
    private TableColumn clmNomPatients;
    @FXML
    private TableColumn clmPrenomPatient;
    @FXML
    private TableColumn clmSexePatients;
    @FXML
    private TableColumn clmDateNaissancePatients;
    @FXML
    private TableColumn clmAgePatients;
    @FXML
    private TableColumn clmDateConsultationPatient;
    @FXML
    private TableColumn clmResumePatient;
    @FXML
    private TableColumn clmDeletePatients;
    @FXML
    private StackPane PaneAjouterPatient;
    @FXML
    private JFXTextField txtNomAddPati;
    @FXML
    private JFXTextField txtPrenoAddPati;
    @FXML
    private JFXComboBox comboSexeAddPati;
    @FXML
    private JFXDatePicker txtDateNaissanceAddPati;
    @FXML
    private JFXTextField txtAgeAddPati;
    @FXML
    private JFXDatePicker txtDateConsultAddPati;
    @FXML
    private JFXTextField txtResumeAddPati;
    @FXML
    private StackPane rootStackPane;
    @FXML
    private AnchorPane rootAnchorPane;
    public static final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;-fx-text-fill: -fx-primary ;-fx-border-color:transparent";
    public static final String HOVERED_BUTTON_STYLE = "-fx-background-color: derive(-fx-primary,20%);-fx-text-fill: -fx-primary;-fx-border-color:transparent";
    ObservableList<Object> data;
    Stage stage = new Stage();
    public static String Nom, Prenom, DateConsultationDate, Sexe;
    public static int age, ordre;
    @FXML
    private JFXButton btnOrdonance;
    String IdforBL;
    @FXML
    private StackPane PaneOrdonances;
    @FXML
    private JFXTextField txtSearchOrdoances;
    @FXML
    private TableView TableOrdonances;
    @FXML
    private TableColumn clmIDOrdnance;
    @FXML
    private TableColumn clmNomOrdanance;
    @FXML
    private TableColumn clmPrenomOrdonances;
    @FXML
    private TableColumn clmDeleteOrdonances;
    @FXML
    private TableColumn clmIDArticle;
    @FXML
    private TableColumn clmArticle;
    @FXML
    private TableColumn clmQtArticle;
    @FXML
    private TableColumn clmDetailOrdonances;
    @FXML
    private TableColumn clmDeleteArticle;
    @FXML
    private JFXButton btnVisualiserOrdonance;
    @FXML
    private JFXButton btnPrintOrdonances;
    @FXML
    private TableView TableArticlesDetails;
    int testprinting = 0;
    String filename;
    private boolean isLightMode = true;

    @FXML
    private FontAwesomeIconView btnToggle;
    @FXML
    private StackPane PaneAbout;
    @FXML
    private Circle about_us_pic;
    private static final String LINKED_IN = "https://www.linkedin.com/in/samerelouissi/";
    private static final String FACEBOOK = "https://www.facebook.com/samer.elouissi";
    private static final String GITHUB = "https://github.com/samer29";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //myConnectionPP.readfromfileandinsertintoDb();
        OnlyIntegersForTextField(txtAgeAddPati);
        txtDateConsultAddPati.setValue(LocalDate.now());
        fillDataPatients();
        deadline();
        PanePatients.setOpacity(1);
        PaneAjouterPatient.setOpacity(0);
        PaneOrdonances.setOpacity(0);
        PaneAbout.setOpacity(0);
        rootAnchorPane.getChildren().removeAll(PanePatients, PaneAjouterPatient, PaneOrdonances, PaneAbout);
        rootAnchorPane.getChildren().addAll(PaneOrdonances, PaneAjouterPatient, PanePatients, PaneAbout);
        btnOrdonance.setDisable(true);

        fillcombox(Arrays.asList("HOMME", "FEMME"), comboSexeAddPati, "HOMME");
        SetTheme();
    }

    public void SetTheme() {
        LocalStorage storage = new LocalStorage();

        String theme = storage.getData("mode", "daymode");
        if (theme.equals("daymode")) {
            rootStackPane.getStylesheets().remove("/css/pulseProthemeDARK.css");
            rootStackPane.getStylesheets().add("/css/pulseProtheme.css");
        } else {
            rootStackPane.getStylesheets().remove("/css/pulseProtheme.css");
            rootStackPane.getStylesheets().add("/css/pulseProthemeDARK.css");
        }
    }

    public void deadline() {
        String datedeadline = price("deadline", "ID", "1", "deadline");
        LocalDate deadline = LocalDate.parse(datedeadline);
        LocalDate nowdate = LocalDate.now();
        if (deadline.compareTo(nowdate) < 0) {
            JFXButton btn = new JFXButton("OK");
            AlertMaker.showMaterialDialogError(rootStackPane, (Node) rootAnchorPane, Arrays.asList(new JFXButton[]{
                btn}), "SESSION TERMINEE", "La période d'essai est terminé");
            btn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseevent
                    -> {
                closeStage(rootAnchorPane);
            });
        }
    }

    @FXML
    private void SearchPatient(KeyEvent event) {
        searchInStockTable(txtSearchPatients, TablePatients);
    }

    public void searchInStockTable(JFXTextField textField, TableView myTable) {
        String searchText = textField.getText();
        if (searchText != null && !searchText.isEmpty()) {
            ObservableList<ObservableList<String>> filteredData = FXCollections.observableArrayList();
            for (Object row : myTable.getItems()) {
                if (row instanceof ObservableList) {
                    ObservableList<?> rowData = (ObservableList<?>) row;
                    boolean match = false;
                    for (Object cell : rowData) {
                        if (cell != null && cell.toString().toLowerCase().contains(searchText.toLowerCase())) {
                            match = true;
                            break;
                        }
                    }
                    if (match) {
                        filteredData.add((ObservableList<String>) rowData);
                    }
                }
            }
            // Set the filtered data to display in the table
            myTable.setItems(filteredData);
        } else {
            // If the search text is empty, refill the table with the original data
            fillDataPatients();
            fillDataOrdonances();
        }
    }

    @FXML
    public void fillDataPatients() {
        TablePatients.setEditable(true);
        fillculms(clmOrdrePatient, 0);
        fillculms(clmNomPatients, 1);
        fillculms(clmPrenomPatient, 2);
        fillculms(clmSexePatients, 3);
        fillculms(clmDateNaissancePatients, 4);
        fillculms(clmAgePatients, 5);
        fillculms(clmDateConsultationPatient, 6);
        fillculms(clmResumePatient, 7);
        //Here for editing Stock 
        clmNomPatients.setCellFactory(TextFieldTableCell.forTableColumn());
        clmNomPatients.setOnEditCommit(event -> editCommit((TableColumn.CellEditEvent<ObservableList<String>, String>) event, "nom", "patients", "ID"));

        clmPrenomPatient.setCellFactory(TextFieldTableCell.forTableColumn());
        clmPrenomPatient.setOnEditCommit(event -> editCommit((TableColumn.CellEditEvent<ObservableList<String>, String>) event, "Prenom", "patients", "ID"));

        clmSexePatients.setCellFactory(TextFieldTableCell.forTableColumn());
        clmSexePatients.setOnEditCommit(event -> editCommit((TableColumn.CellEditEvent<ObservableList<String>, String>) event, "sexe", "patients", "ID"));

        clmDateNaissancePatients.setCellFactory(TextFieldTableCell.forTableColumn());
        clmDateNaissancePatients.setOnEditCommit(event -> editCommit((TableColumn.CellEditEvent<ObservableList<String>, String>) event, "DateNaissance", "patients", "ID"));

        clmAgePatients.setCellFactory(TextFieldTableCell.forTableColumn());
        clmAgePatients.setOnEditCommit(event -> editCommit((TableColumn.CellEditEvent<ObservableList<String>, String>) event, "age", "patients", "ID"));

        clmDateConsultationPatient.setCellFactory(TextFieldTableCell.forTableColumn());
        clmDateConsultationPatient.setOnEditCommit(event -> editCommit((TableColumn.CellEditEvent<ObservableList<String>, String>) event, "DateConsultation", "patients", "ID"));

        clmResumePatient.setCellFactory(TextFieldTableCell.forTableColumn());
        clmResumePatient.setOnEditCommit(event -> editCommit((TableColumn.CellEditEvent<ObservableList<String>, String>) event, "Resume", "patients", "ID"));

        clmDeletePatients.setCellFactory((Callback) new Callback<TableColumn<ObservableList, String>, TableCell<ObservableList, String>>() {
            public TableCell<ObservableList, String> call(TableColumn<ObservableList, String> param) {
                return new DeleteButtonCellStock();
            }
        });
        fillTableSortedByIDASCENDING(data, TablePatients, "patients", 1, 8, clmOrdrePatient);

    }

    @FXML
    public void fillDataOrdonances() {
        TableOrdonances.setEditable(true);
        fillculms(clmIDOrdnance, 0);
        fillculms(clmNomOrdanance, 1);
        fillculms(clmPrenomOrdonances, 2);
        clmDeleteOrdonances.setCellFactory((Callback) new Callback<TableColumn<ObservableList, String>, TableCell<ObservableList, String>>() {
            public TableCell<ObservableList, String> call(TableColumn<ObservableList, String> param) {
                return new DeleteButtonCellOrdanances();
            }
        });
        fillTableSortedByIDASCENDING(data, TableOrdonances, "patients", 1, 3, clmIDOrdnance);

    }

    @FXML
    private void SelectOrdonance(ActionEvent event) {
        try {
            Object row2 = TablePatients.getSelectionModel().getSelectedItems().get(0);
            IdforBL = row2.toString().split(",")[0].substring(1);
            ResultSet rs = myConnectionPP.inst3("Patients", "ID", IdforBL);
            while (rs.next()) {
                ordre = rs.getInt(1);
                Nom = rs.getString(2);
                Prenom = rs.getString(3);
                Sexe = rs.getString(4);
                age = rs.getInt(6);
                DateConsultationDate = rs.getString(7);
            }
            loadWindow(this.getClass().getResource("/views/viewOrdonExam.fxml"), "Crée une Ordonance", stage, "no");

        } catch (SQLException ex) {
            Logger.getLogger(FirstWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void CalcuclateDateOfBirth(KeyEvent event) {
        LocalDate dateOfBirth = calculateDateOfBirth(Integer.parseInt(txtAgeAddPati.getText()), txtDateNaissanceAddPati);
        txtDateNaissanceAddPati.setValue(dateOfBirth);
    }

    @FXML
    private void EnableOrdonance(MouseEvent event) {
        btnOrdonance.setDisable(false);
    }

    @FXML
    private void settings(ActionEvent event) {

        loadWindow(this.getClass().getResource("/views/viewSettings.fxml"), "Parametres", stage, "no");

    }

    @FXML
    private void SearchOrdonances(KeyEvent event) {
        searchInStockTable(txtSearchOrdoances, TableOrdonances);

    }

    @FXML
    private void TableOrdonancesClicked(MouseEvent event) {
        btnPrintOrdonances.setDisable(false);
        btnVisualiserOrdonance.setDisable(false);
        fillDataForArticles();
    }

    @FXML
    private void visualiserOrdonnance(ActionEvent event) {
        testprinting = 0;
        printOrdoFunction();

    }

    @FXML
    private void printOrdonnance(ActionEvent event) {
        testprinting = 1;
        printOrdoFunction();

    }

    public void printOrdoFunction() {
        // Get the selected row from the TableView
        Object row2 = TableOrdonances.getSelectionModel().getSelectedItems().get(0);
        // Extract the ID from the selected row
        String id = row2.toString().split(",")[0].substring(1);
        // Define the file path based on the ID
        filename = "reports/ordonnance/ordonnance_N" + id + ".pdf";
        File file = new File(this.filename);

        // Check if the file exists
        if (file.exists()) {
            try {
                if (testprinting == 1) {
                    printPDF(filename);
                } else {
                    Desktop.getDesktop().open(file);
                }
            } catch (IOException ex) {
                Logger.getLogger(OrdonExamController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            // File does not exist, show a message
            JFXButton btn = new JFXButton("OK");
            AlertMaker.showMaterialDialog(
                    this.rootStackPane,
                    this.rootAnchorPane,
                    Arrays.asList(new JFXButton[]{btn}),
                    null,
                    "L'ordonnance n'est pas créée. Veuillez la créer dans l'onglet des patients."
            );
        }
    }

    private void fillDataForArticles() {
        Object row2 = TableOrdonances.getSelectionModel().getSelectedItems().get(0);
        String idforarticle = row2.toString().split(",")[0].substring(1);
        fillculms(clmIDArticle, 0);
        fillculms(clmArticle, 2);
        fillculms(clmQtArticle, 3);
        fillculms(clmDetailOrdonances, 4);
        clmDeleteArticle.setCellFactory((Callback) new Callback<TableColumn<ObservableList, String>, TableCell<ObservableList, String>>() {
            public TableCell<ObservableList, String> call(TableColumn<ObservableList, String> param) {
                return new DeleteButtonCellArticlesOrdonances();
            }
        });
        fillTableWithConditionDESCENDING("ordonnance", "IDPatient", idforarticle, TableArticlesDetails, 5, clmIDArticle);
    }

    @FXML
    private void changeMode(MouseEvent event) {
        // Toggle mode state
        isLightMode = !isLightMode;

        // Apply the appropriate mode
        if (isLightMode) {
            setLightMode();
        } else {
            setDarkMode();
        }
    }

    @FXML
    private void ongithubclicked(MouseEvent event) {
        loadWebpage(GITHUB);
    }

    @FXML
    private void onFacebookClicked(MouseEvent event) {
        loadWebpage(FACEBOOK);
    }

    @FXML
    private void onLinkedinClicked(MouseEvent event) {
        loadWebpage(LINKED_IN);

    }

   public void fillCircleWithImage() {
    InputStream in = getClass().getResourceAsStream("/icons/dev_pic.jpg");
    if (in != null) {
        Image imgUsr = new Image(in);
        about_us_pic.setFill(new ImagePattern(imgUsr));
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } else {
        System.out.println("Image not found!");
    }
}


    private class DeleteButtonCellStock extends TableCell<ObservableList, String> {

        final HBox cellButton = new HBox();
        final Button cellButton2 = new Button("");

        DeleteButtonCellStock() {
            cellButton2.setFocusTraversable(false);
            cellButton2.setPadding(new Insets(0.0));
            cellButton.getChildren().addAll(new Node[]{
                cellButton2
            });
            cellButton2.setStyle(IDLE_BUTTON_STYLE);
            cellButton2.setOnMouseExited(e -> cellButton2.setStyle(IDLE_BUTTON_STYLE));
            cellButton2.setOnMouseEntered(e -> cellButton2.setStyle(HOVERED_BUTTON_STYLE));
            MaterialDesignIconView dd = new MaterialDesignIconView(MaterialDesignIcon.DELETE);
            dd.setSize("2em");
            cellButton2.setGraphic((Node) dd);
        }

        protected void updateItem(String t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                cellButton2.setOnAction((EventHandler) new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent t2) {
                        ObservableList rowList = (ObservableList) TablePatients.getItems().get(DeleteButtonCellStock.this.getIndex());
                        JFXButton btnyes = new JFXButton("Oui");
                        JFXButton btnno = new JFXButton("Non");
                        btnyes.addEventHandler(MouseEvent.MOUSE_CLICKED, event1
                                -> {
                            String ID = null;
                            ID = rowList.get(0).toString();
                            delete(ID, "patients", "ID");

                            fillDataPatients();
                            JFXButton button = new JFXButton("Terminer!");
                            AlertMaker.showMaterialDialog(rootStackPane, (Node) rootAnchorPane, Arrays.asList(new JFXButton[]{
                                button
                            }), null, "Patient Bien Supprimée");
                        });
                        btnno.addEventHandler(MouseEvent.MOUSE_CLICKED, event1
                                -> {
                            JFXButton button = new JFXButton("OK");
                            AlertMaker.showMaterialDialog(rootStackPane, (Node) rootAnchorPane, Arrays.asList(new JFXButton[]{
                                button
                            }), null, "Suppression annulée");
                        });
                        AlertMaker.showMaterialDialog(rootStackPane, (Node) rootAnchorPane, Arrays.asList(new JFXButton[]{
                            btnyes, btnno
                        }), "Confirmation", "Êtes-vous sûr de vouloir supprimer définitivement ce patient ?");
                    }
                });
                setGraphic((Node) cellButton2);
            } else {
                setGraphic(null);
            }
        }
    }

    private class DeleteButtonCellOrdanances extends TableCell<ObservableList, String> {

        final HBox cellButton = new HBox();
        final Button cellButton2 = new Button("");

        DeleteButtonCellOrdanances() {
            cellButton2.setFocusTraversable(false);
            cellButton2.setPadding(new Insets(0.0));
            cellButton.getChildren().addAll(new Node[]{
                cellButton2
            });
            cellButton2.setStyle(IDLE_BUTTON_STYLE);
            cellButton2.setOnMouseExited(e -> cellButton2.setStyle(IDLE_BUTTON_STYLE));
            cellButton2.setOnMouseEntered(e -> cellButton2.setStyle(HOVERED_BUTTON_STYLE));
            MaterialDesignIconView dd = new MaterialDesignIconView(MaterialDesignIcon.DELETE);
            dd.setSize("2em");
            cellButton2.setGraphic((Node) dd);
        }

        protected void updateItem(String t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                cellButton2.setOnAction((EventHandler) new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent t2) {
                        ObservableList rowList = (ObservableList) TableOrdonances.getItems().get(DeleteButtonCellOrdanances.this.getIndex());
                        JFXButton btnyes = new JFXButton("Oui");
                        JFXButton btnno = new JFXButton("Non");
                        btnyes.addEventHandler(MouseEvent.MOUSE_CLICKED, event1
                                -> {
                            String ID = null;
                            ID = rowList.get(0).toString();
                            delete(ID, "ordonnance", "IDPatient");

                            fillDataOrdonances();
                            JFXButton button = new JFXButton("Terminer!");
                            AlertMaker.showMaterialDialog(rootStackPane, (Node) rootAnchorPane, Arrays.asList(new JFXButton[]{
                                button
                            }), null, "Ordonnance Bien Supprimée");
                        });
                        btnno.addEventHandler(MouseEvent.MOUSE_CLICKED, event1
                                -> {
                            JFXButton button = new JFXButton("OK");
                            AlertMaker.showMaterialDialog(rootStackPane, (Node) rootAnchorPane, Arrays.asList(new JFXButton[]{
                                button
                            }), null, "Suppression annulée");
                        });
                        AlertMaker.showMaterialDialog(rootStackPane, (Node) rootAnchorPane, Arrays.asList(new JFXButton[]{
                            btnyes, btnno
                        }), "Confirmation", "Êtes-vous sûr de vouloir supprimer définitivement cette Ordonnance ?");
                    }
                });
                setGraphic((Node) cellButton2);
            } else {
                setGraphic(null);
            }
        }
    }

    private class DeleteButtonCellArticlesOrdonances extends TableCell<ObservableList, String> {

        final HBox cellButton = new HBox();
        final Button cellButton2 = new Button("");

        DeleteButtonCellArticlesOrdonances() {
            cellButton2.setFocusTraversable(false);
            cellButton2.setPadding(new Insets(0.0));
            cellButton.getChildren().addAll(new Node[]{
                cellButton2
            });
            cellButton2.setStyle(IDLE_BUTTON_STYLE);
            cellButton2.setOnMouseExited(e -> cellButton2.setStyle(IDLE_BUTTON_STYLE));
            cellButton2.setOnMouseEntered(e -> cellButton2.setStyle(HOVERED_BUTTON_STYLE));
            MaterialDesignIconView dd = new MaterialDesignIconView(MaterialDesignIcon.DELETE);
            dd.setSize("2em");
            cellButton2.setGraphic((Node) dd);
        }

        protected void updateItem(String t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                cellButton2.setOnAction((EventHandler) new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent t2) {
                        ObservableList rowList = (ObservableList) TableArticlesDetails.getItems().get(DeleteButtonCellArticlesOrdonances.this.getIndex());
                        JFXButton btnyes = new JFXButton("Oui");
                        JFXButton btnno = new JFXButton("Non");
                        btnyes.addEventHandler(MouseEvent.MOUSE_CLICKED, event1
                                -> {
                            String ID = null;
                            ID = rowList.get(0).toString();
                            delete(ID, "ordonnance", "ID");

                            fillDataForArticles();
                            JFXButton button = new JFXButton("Terminer!");
                            AlertMaker.showMaterialDialog(rootStackPane, (Node) rootAnchorPane, Arrays.asList(new JFXButton[]{
                                button
                            }), null, "Article Bien Supprimée");
                        });
                        btnno.addEventHandler(MouseEvent.MOUSE_CLICKED, event1
                                -> {
                            JFXButton button = new JFXButton("OK");
                            AlertMaker.showMaterialDialog(rootStackPane, (Node) rootAnchorPane, Arrays.asList(new JFXButton[]{
                                button
                            }), null, "Suppression annulée");
                        });
                        AlertMaker.showMaterialDialog(rootStackPane, (Node) rootAnchorPane, Arrays.asList(new JFXButton[]{
                            btnyes, btnno
                        }), "Confirmation", "Êtes-vous sûr de vouloir supprimer définitivement cet Article ?");
                    }
                });
                setGraphic((Node) cellButton2);
            } else {
                setGraphic(null);
            }
        }
    }

    public void editCommit(TableColumn.CellEditEvent<ObservableList<String>, String> event, String clm, String table, String id2) {
        ObservableList<String> rowValue = event.getRowValue();
        String id = rowValue.get(0);
        String DCI = event.getNewValue();
        editCell(id2, clm, table, DCI, id);
        fillDataPatients();
    }

    public void editCommitOrdonnances(TableColumn.CellEditEvent<ObservableList<String>, String> event, String clm, String table, String id2) {
        ObservableList<String> rowValue = event.getRowValue();
        String id = rowValue.get(0);
        String DCI = event.getNewValue();
        editCell(id2, clm, table, DCI, id);
        fillDataPatients();
    }

    private void setLightMode() {
        LocalStorage storage = new LocalStorage();
        storage.saveData("mode", "daymode");
        btnToggle.setGlyphName("TOGGLE_ON");
        rootStackPane.getStylesheets().remove("/css/pulseProthemeDARK.css");
        rootStackPane.getStylesheets().add("/css/pulseProtheme.css");
    }

    private void setDarkMode() {
        LocalStorage storage = new LocalStorage();
        storage.saveData("mode", "nightmode");
        btnToggle.setGlyphName("TOGGLE_OFF");
        rootStackPane.getStylesheets().remove("/css/pulseProtheme.css");
        rootStackPane.getStylesheets().add("/css/pulseProthemeDARK.css");
    }

    @FXML
    private void CalculateAge(ActionEvent event) {
        myFunctionsPP.calculateAgeFunction(txtDateNaissanceAddPati, txtAgeAddPati);
    }

    @FXML
    private void addNewPatient(ActionEvent event) {
        String Resume;
        LocalDate DateNaissance, DateConsultationLocalDate;
        Nom = txtNomAddPati.getText();
        Prenom = txtPrenoAddPati.getText();
        Sexe = comboSexeAddPati.getSelectionModel().getSelectedItem().toString();
        Resume = txtResumeAddPati.getText();
        age = Integer.parseInt(txtAgeAddPati.getText());
        DateConsultationLocalDate = txtDateConsultAddPati.getValue();
        DateConsultationDate = converLocalDateToString(DateConsultationLocalDate);
        DateNaissance = txtDateNaissanceAddPati.getValue();
        passe = 0;
        myConnectionPP.addPatient(Nom, Prenom, Sexe, DateNaissance, age, DateConsultationLocalDate, Resume);
        if (passe == 1) {
            JFXButton btn = new JFXButton("OK");
            AlertMaker.showMaterialDialog(rootStackPane, (Node) rootAnchorPane, Arrays.asList(new JFXButton[]{
                btn
            }), null, "ENREGISTREMENT AVEC SUCCESS");
            btn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseevent
                    -> {
                redemaragedata();

            });

        } else {
            JFXButton btn = new JFXButton("OK");
            AlertMaker.showMaterialDialogError(rootStackPane, (Node) rootAnchorPane, Arrays.asList(new JFXButton[]{
                btn
            }), "ERREUR D'AJOUT", ex2 + "");
            btn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseevent
                    -> {
            });
        }
    }

    public void redemaragedata() {
        fillDataPatients();
        ordre = LastEnterySQL("ID", "patients");
        txtNomAddPati.setText(null);
        txtPrenoAddPati.setText(null);
        txtAgeAddPati.setText(null);
        txtResumeAddPati.setText(null);
        txtDateConsultAddPati.setValue(LocalDate.now());
        txtDateNaissanceAddPati.setValue(null);
        loadWindow(this.getClass().getResource("/views/viewOrdonExam.fxml"), "Crée une Ordonance", stage, "no");
        System.out.println("Last Entery" + ordre);

    }

    @FXML
    private void OnPatientsClicked(ActionEvent event) {
        PanePatients.setOpacity(1);
        PaneAjouterPatient.setOpacity(0);
        PaneOrdonances.setOpacity(0);
        PaneAbout.setOpacity(0);
        rootAnchorPane.getChildren().removeAll(PanePatients, PaneAjouterPatient, PaneOrdonances, PaneAbout);
        rootAnchorPane.getChildren().addAll(PaneAbout, PaneOrdonances, PaneAjouterPatient, PanePatients);
        btnOrdonance.setDisable(true);
        fillDataPatients();
    }

    @FXML
    private void OnAddCliqued(ActionEvent event) {
        PanePatients.setOpacity(0);
        PaneOrdonances.setOpacity(0);
        PaneAbout.setOpacity(0);
        PaneAjouterPatient.setOpacity(1);
        rootAnchorPane.getChildren().removeAll(PaneAjouterPatient, PanePatients, PaneOrdonances, PaneAbout);
        rootAnchorPane.getChildren().addAll(PaneOrdonances, PaneAbout, PanePatients, PaneAjouterPatient);
    }

    @FXML
    private void OnOrdonCliqued(ActionEvent event) {
        PanePatients.setOpacity(0);
        PaneAbout.setOpacity(0);
        PaneOrdonances.setOpacity(1);
        PaneAjouterPatient.setOpacity(0);
        rootAnchorPane.getChildren().removeAll(PaneOrdonances, PaneAjouterPatient, PanePatients, PaneAbout);
        rootAnchorPane.getChildren().addAll(PaneAjouterPatient, PanePatients, PaneAbout, PaneOrdonances);
        fillDataOrdonances();
    }

    @FXML
    private void onAboutUsClicked(ActionEvent event) {
        PanePatients.setOpacity(0);
        PaneOrdonances.setOpacity(0);
        PaneAbout.setOpacity(1);
        PaneAjouterPatient.setOpacity(0);
        rootAnchorPane.getChildren().removeAll(PaneAbout, PaneAjouterPatient, PanePatients, PaneOrdonances);
        rootAnchorPane.getChildren().addAll(PaneOrdonances, PanePatients, PaneAjouterPatient, PaneAbout);
        fillCircleWithImage();
    }

    @FXML
    private void closewindow(MouseEvent event) {
        myFunctionsPP.closeStage(rootAnchorPane);
    }

    @FXML
    private void minimizewindow(MouseEvent event) {
        myFunctionsPP.minimizeScene(rootAnchorPane);
    }

}
