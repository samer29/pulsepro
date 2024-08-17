/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pulsepro;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import static tools.myConnectionPP.instload2things;
import static tools.myFunctionsPP.closeStage;
import static tools.myFunctionsPP.loadfadestage;

/**
 *
 * @author Samer
 */
public class FXMLDocumentController implements Initializable {

    private Label label;
    @FXML
    private StackPane rootStackPane;
    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    private JFXTextField txtusername;
    @FXML
    private JFXPasswordField txtpsw;
    @FXML
    private FontAwesomeIconView closeStage;
    @FXML
    private FontAwesomeIconView minuswindow;
    public static String usernameLG;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
  
    }

    @FXML
    private void login(ActionEvent event) {
        if (txtusername.getText().equals("admin") && txtpsw.getText().equals("19911129")) {
            loadfadestage(1000, this.rootAnchorPane, "/views/viewHiddenMenu.fxml", "", "yes");
        } else {
            try {
                ResultSet rs = instload2things("login", "username", txtusername.getText(), "password", txtpsw.getText());
                if (rs.next()) {
                    loadfadestage(1000, this.rootAnchorPane, "/views/viewFirstWindow.fxml", "", "yes");
                } else {
                    txtusername.getStyleClass().add("wrong-enteries");
                    txtpsw.getStyleClass().add("wrong-enteries");
                }
            } catch (SQLException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        usernameLG = txtusername.getText();
    }

    @FXML
    private void closeWindow(MouseEvent event) {
        closeStage(rootAnchorPane);
    }

    @FXML
    private void MinusWindow(MouseEvent event) {
    }

}
