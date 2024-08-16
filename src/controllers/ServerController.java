/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXRadioButton;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import static tools.myFunctionsPP.closeStage;
import static tools.myFunctionsPP.writefile;


/**
 * FXML Controller class
 *
 * @author samer
 */
public class ServerController implements Initializable {

   public static String testdatab;
    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    private JFXRadioButton radioOffline;
    @FXML
    private JFXRadioButton radioOnline;
    @FXML
    private StackPane rootStackPane;

    public void initialize(URL url, ResourceBundle rb) {
        if (testdatab.equals("Offline")) {
            offlinemode();
        } else if (testdatab.equals("Online")) {
            onlinemode();
        }
    }

    @FXML
    private void validateServer(ActionEvent event) {
        if (radioOffline.isSelected()) {
            testdatab = "Offline";
        }
        if (radioOnline.isSelected()) {
            testdatab = "Online";
        }
        writefile("test.dat", testdatab,rootStackPane,rootAnchorPane);
        closeStage(rootAnchorPane);
    }

    @FXML
    private void onOfflineClick(ActionEvent event) {
        offlinemode();
    }

    @FXML
    private void onOnlineClick(ActionEvent event) {
        onlinemode();
    }

    public void offlinemode() {
        radioOnline.setSelected(false);
        radioOffline.setSelected(true);
    }

    public void onlinemode() {
        radioOffline.setSelected(false);
        radioOnline.setSelected(true);
    }
    
}
