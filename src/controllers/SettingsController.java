/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import static controllers.FirstWindowController.HOVERED_BUTTON_STYLE;
import static controllers.FirstWindowController.IDLE_BUTTON_STYLE;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import tools.LocalStorage;
import tools.ThemeManager;
import static tools.myConnectionPP.addNewPara;
import static tools.myConnectionPP.delete;
import static tools.myConnectionPP.editSettings;
import static tools.myFunctionsPP.fillTable2;
import static tools.myFunctionsPP.fillculms;

/**
 * FXML Controller class
 *
 * @author Samer
 */
public class SettingsController implements Initializable {

    @FXML
    private StackPane rootStackPane;
    @FXML
    private JFXTextField txtSearch;
    @FXML
    private JFXTextField txtAddMedicament;
    @FXML
    private TableView tableMedicament;
    @FXML
    private TableColumn clmIDMedic;
    @FXML
    private TableColumn clmMedic;
    @FXML
    private TableColumn clmDeleteMedicament;
    @FXML
    private JFXTextField txtSearchExamen;
    @FXML
    private JFXTextField txtAddExamen;
    @FXML
    private TableView tableExamen;
    @FXML
    private TableColumn clmIDExamen;
    @FXML
    private TableColumn clmExamen;
    @FXML
    private TableColumn clmDeleteExamen;
    ObservableList<Object> data;
    @FXML
    private AnchorPane rootAnchorPane;

    private Stage primaryStage;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
 
        fillData(tableMedicament, clmIDMedic, clmMedic, clmDeleteMedicament, "medicaments ", txtSearch, "medicaments", "NomMed", "ID");
        fillData(tableExamen, clmIDExamen, clmExamen, clmDeleteExamen, "listexamens ", txtSearchExamen, "listexamens", "Examen", "ID");
        SetTheme();
        //  fillData(tableDCI, clmIDDCI, clmDCI, clmDeleteDCI, "dci", txtDCI, "DCI", "NomDCI", "ID");
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
    @FXML

    private void SearchMedic(KeyEvent event) {
        searchInTables(txtSearch, tableMedicament);
    }

    @FXML
    private void AddNewMedicament() {
        addNewPara("medicaments", "NomMed", txtAddMedicament, rootStackPane, rootAnchorPane);
        fillData(tableMedicament, clmIDMedic, clmMedic, clmDeleteMedicament, "medicaments ", txtSearch, "medicaments", "NomMed", "ID");

    }

    @FXML
    private void SearchExamen(KeyEvent event) {
        searchInTables(txtSearchExamen, tableExamen);
    }

    @FXML
    private void AddNewExamen() {
        addNewPara("listexamens", "Examen", txtAddExamen, rootStackPane, rootAnchorPane);
        fillData(tableExamen, clmIDExamen, clmExamen, clmDeleteExamen, "listexamens ", txtSearchExamen, "listexamens", "Examen", "ID");

    }

    public void searchInTables(JFXTextField textField, TableView myTable) {
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
            fillData(tableMedicament, clmIDMedic, clmMedic, clmDeleteMedicament, "medicaments ", txtSearch, "medicaments", "NomMed", "ID");
            fillData(tableExamen, clmIDExamen, clmExamen, clmDeleteExamen, "listexamens ", txtSearchExamen, "listexamens", "Examen", "ID");

        }
    }

    private void fillData(TableView mytable, TableColumn clm1, TableColumn clm2, TableColumn clmDelete, String from, JFXTextField mytext, String produit, String clmSet, String clmWhat) {
        mytext.setText(null);
        data = null;
        mytable.setEditable(true);
        fillculms(clm1, 0);
        fillculms(clm2, 1);
        clmDelete.setCellFactory((Callback) new Callback<TableColumn<ObservableList, String>, TableCell<ObservableList, String>>() {
            @Override
            public TableCell<ObservableList, String> call(TableColumn<ObservableList, String> param) {
                return new DeleteButton(mytable, from, clm1, clm2, clmDelete, mytext, produit, clmSet, clmWhat);
            }
        });
        fillTable2(data, mytable, from, 1, 2);
        Callback<TableColumn, TableCell> cellFactory1 = new Callback<TableColumn, TableCell>() {

            public TableCell call(TableColumn p) {
                return new EditingCelInst(1, from, clmSet, clmWhat, mytable);
            }
        };
        clm2.setCellFactory((Callback) cellFactory1);
    }

    @FXML
    private void saveselectedmode(ActionEvent event) {

    }

    private class DeleteButton extends TableCell<ObservableList, String> {

        final HBox cellButton = new HBox();
        final Button cellButton2 = new Button("");
        TableView mytable2;
        String from2;
        TableColumn clm4, clm5, clm6;
        JFXTextField mytext2;
        String produit2, clmSet2, clmWhat2;

        DeleteButton(TableView mytable, String from, TableColumn clm1, TableColumn clm2, TableColumn clm3, JFXTextField mytext, String produit, String clmSet, String clmWhat) {
            cellButton2.setFocusTraversable(false);
            cellButton2.setPadding(new Insets(0.0));
            cellButton.getChildren().addAll(new Node[]{cellButton2});
            cellButton2.setStyle(IDLE_BUTTON_STYLE);
            cellButton2.setOnMouseExited(e -> cellButton2.setStyle(IDLE_BUTTON_STYLE));
            cellButton2.setOnMouseEntered(e -> cellButton2.setStyle(HOVERED_BUTTON_STYLE));
            MaterialDesignIconView dd = new MaterialDesignIconView(MaterialDesignIcon.DELETE);
            dd.setSize("2em");
            cellButton2.setGraphic((Node) dd);
            mytable2 = mytable;
            from2 = from;
            clm4 = clm1;
            clm5 = clm2;
            clm6 = clm3;
            mytext2 = mytext;
            produit2 = produit;
            clmSet2 = clmSet;
            clmWhat2 = clmWhat;

        }

        protected void updateItem(String t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                cellButton2.setOnAction((EventHandler) new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent t2) {
                        ObservableList rowList = (ObservableList) mytable2.getItems().get(DeleteButton.this.getIndex());

                        String ID = null;
                        ID = rowList.get(0).toString();
                        delete(ID, from2, "ID");
                        fillData(mytable2, clm4, clm5, clm6, from2, mytext2, produit2, clmSet2, clmWhat2);

                    }
                });
                setGraphic((Node) cellButton2);
            } else {
                setGraphic(null);
            }
        }
    }

    class EditingCelInst extends TableCell<ObservableList, String> {

        private TextField textField;
        int i;
        String clm3, clm4, from2;
        TableView mytable2;

        public EditingCelInst(int ii, String from, String clm1, String clm2, TableView mytable) {
            this.i = ii;
            clm3 = clm1;
            clm4 = clm2;
            from2 = from;
            mytable2 = mytable;
        }

        public void startEdit() {
            super.startEdit();
            this.createTextField();
            this.setGraphic((Node) this.textField);
            this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            this.textField.selectAll();
            //Object row = tableDCI.getSelectionModel().getSelectedItems().get(0);
        }

        public void cancelEdit() {
            super.cancelEdit();
            this.setText(String.valueOf(this.getItem()));
            this.setContentDisplay(ContentDisplay.TEXT_ONLY);
        }

        public void updateItem(String t, boolean empty) {
            super.updateItem(t, empty);
            if (empty) {
                this.setText(null);
                this.setGraphic(null);
            } else if (this.isEditing()) {
                if (this.textField != null) {
                    this.textField.setText(this.getString());
                }
                this.setGraphic((Node) this.textField);
                this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            } else {
                this.setText(this.getString());
                this.setContentDisplay(ContentDisplay.TEXT_ONLY);
            }
        }

        private void createTextField() {
            this.textField = new TextField(this.getString());
            this.textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2.0);
            this.textField.setOnKeyPressed((EventHandler) new EventHandler<KeyEvent>() {

                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        EditingCelInst.this.commitEdit(EditingCelInst.this.textField.getText());
                        ObservableList rowList = (ObservableList) mytable2.getItems().get(EditingCelInst.this.getIndex());
                        rowList.set(EditingCelInst.this.i, (Object) EditingCelInst.this.textField.getText());
                        String des = rowList.get(1) + "";
                        String SN = rowList.get(0) + "";
                        editSettings(from2, clm3, des, clm4, SN);
                        //ConnexionJM.editInstall(des, mod, ing, clien, date, obser, SN);
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        EditingCelInst.this.cancelEdit();
                    }
                }
            });
        }

        private String getString() {
            return this.getItem() == null ? "" : ((String) this.getItem()).toString();
        }
    }

}
