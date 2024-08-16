/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import static controllers.FirstWindowController.HOVERED_BUTTON_STYLE;
import static controllers.FirstWindowController.IDLE_BUTTON_STYLE;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import tools.AlertMaker;
import static tools.myConnectionPP.addUser;
import static tools.myConnectionPP.delete;
import static tools.myConnectionPP.editSettings;
import static tools.myConnectionPP.ex2;
import static tools.myConnectionPP.passe;
import static tools.myFunctionsPP.closeStage;
import static tools.myFunctionsPP.fillTable2;
import static tools.myFunctionsPP.fillculms;
import static tools.myFunctionsPP.loadWindow;


/**
 * FXML Controller class
 *
 * @author samer
 */
public class HiddenMenuController implements Initializable {

    @FXML
    private StackPane rootStackPane;
    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    private TableView tblUsers;
    @FXML
    private TableColumn clmDelete,clmPsw,clmUserName,clmID;
    @FXML
    private JFXTextField txtPassword,txtUserName;
    @FXML
    private JFXDatePicker txtDateDeadline;
    JFXButton btn = new JFXButton("OK");
    ObservableList<Object> data;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillData(tblUsers, clmID, clmUserName, clmPsw,clmDelete, "login",txtUserName,"username","password","username","ID");
        // TODO
    }    

    @FXML
    private void addNewUser(ActionEvent event) {
        addUser(txtUserName.getText(), txtPassword.getText());
        if (passe == 1) {
            AlertMaker.showMaterialDialog(rootStackPane, (Node) rootAnchorPane, Arrays.asList(new JFXButton[]{btn}), null, "ENREGISTREMENT AVEC SUCCESS");
            btn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseevent -> {
             setNullValues();
            });
        } else {
            AlertMaker.showMaterialDialogError(rootStackPane, (Node) rootAnchorPane, Arrays.asList(new JFXButton[]{btn}), ex2 + "", "ERREUR D'AJOUT");
          
        }
        
    }

    @FXML
    private void NewUser(ActionEvent event) {
       setNullValues();
        
    }
    public void setNullValues(){
         txtUserName.setText(null);
        txtPassword.setText(null);
    }

    @FXML
    private void updatenewDeadline(ActionEvent event) {
        String date=(txtDateDeadline.getValue()).toString();
        editSettings("deadline", "deadline", date, "ID", "1");
        if (passe == 1) {
            AlertMaker.showMaterialDialog(rootStackPane, (Node) rootAnchorPane, Arrays.asList(new JFXButton[]{btn}), null, "ENREGISTREMENT AVEC SUCCESS");
            btn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseevent -> {
            txtDateDeadline.setValue(null);
            });
        } else {
            AlertMaker.showMaterialDialogError(rootStackPane, (Node) rootAnchorPane, Arrays.asList(new JFXButton[]{btn}), ex2 + "", "ERREUR D'AJOUT");
          
        }
    }

    @FXML
    private void ChangeServer(ActionEvent event) {
         loadWindow(this.getClass().getResource("/views/viewServer.fxml"), "Server ", null, "no");
    }
    private void fillData(TableView mytable, TableColumn clm1, TableColumn clm2,TableColumn clm3, TableColumn clmDelete, String from, JFXTextField mytext, String produit,String password, String clmSet, String clmWhat) {
        mytext.setText(null);
        data = null;
        mytable.setEditable(true);
        fillculms(clm1, 0);
        fillculms(clm2, 1);
        fillculms(clm3, 2);
        clmDelete.setCellFactory((Callback) new Callback<TableColumn<ObservableList, String>, TableCell<ObservableList, String>>() {
            @Override
            public TableCell<ObservableList, String> call(TableColumn<ObservableList, String> param) {
                return new DeleteButton(mytable, from, clm1, clm2, clmDelete,clm3, mytext, produit,password, clmSet, clmWhat);
            }
        });
        fillTable2(data, mytable, from, 1, 3);
        Callback<TableColumn, TableCell> cellFactory1 = new Callback<TableColumn, TableCell>() {

            public TableCell call(TableColumn p) {
                return new EditingCelInst(1, from, clmSet, clmWhat,mytable);
            }
        };
        clm2.setCellFactory((Callback) cellFactory1);
    }

    @FXML
    private void closeWindow(MouseEvent event) {
        closeStage(rootAnchorPane);
    }
    private class DeleteButton extends TableCell<ObservableList, String> {

        final HBox cellButton = new HBox();
        final Button cellButton2 = new Button("");
        TableView mytable2;
        String from2;
        TableColumn clm4, clm5, clm6,clm7;
        JFXTextField mytext2;
        String password2,produit2, clmSet2, clmWhat2;

        DeleteButton(TableView mytable, String from, TableColumn clm1, TableColumn clm2, TableColumn clm3,TableColumn clm33, JFXTextField mytext, String produit,String password, String clmSet, String clmWhat) {
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
            clm7 = clm33;
            mytext2 = mytext;
            produit2 = produit;
            clmSet2 = clmSet;
            clmWhat2 = clmWhat;
            password2=password;

        }

        protected void updateItem(String t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                cellButton2.setOnAction((EventHandler) new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent t2) {
                        ObservableList rowList = (ObservableList) mytable2.getItems().get(DeleteButton.this.getIndex());
                        JFXButton btnyes = new JFXButton("Oui");
                        JFXButton btnno = new JFXButton("Non");
                        btnyes.addEventHandler(MouseEvent.MOUSE_CLICKED, event1 -> {
                            String ID = null;
                            ID = rowList.get(0).toString();
                            delete(ID, from2, "ID");
                            fillData(mytable2, clm4, clm5, clm6,clm7, from2, mytext2, produit2,password2, clmSet2, clmWhat2);
                            JFXButton button = new JFXButton("Terminer!");
                            AlertMaker.showMaterialDialog(rootStackPane, (Node) rootAnchorPane, Arrays.asList(new JFXButton[]{button}), null, produit2 + " Bien Supprimée");
                        });
                        btnno.addEventHandler(MouseEvent.MOUSE_CLICKED, event1 -> {
                            JFXButton button = new JFXButton("OK");
                            AlertMaker.showMaterialDialog(rootStackPane, (Node) rootAnchorPane, Arrays.asList(new JFXButton[]{button}), null, "Suppression annulée");
                        });
                        AlertMaker.showMaterialDialog(rootStackPane, (Node) rootAnchorPane, Arrays.asList(new JFXButton[]{btnyes, btnno}), "Confirmation", "Voulez vous vraiment supprimer ce " + produit2 + " de façon permanente ?");
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
        public EditingCelInst(int ii, String from, String clm1, String clm2,TableView mytable) {
            this.i = ii;
            clm3 = clm1;
            clm4 = clm2;
            from2 = from;
            mytable2=mytable;
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
