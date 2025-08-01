/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import static controllers.ConsultationController.IDConsultation;
import static controllers.FirstWindowPulseProController.Nom;
import static controllers.FirstWindowPulseProController.Prenom;
import static controllers.FirstWindowPulseProController.age;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import tools.AlertMaker;
import tools.LocalStorage;
import static tools.myConnectionPP.editCell;
import static tools.myConnectionPP.ex2;
import static tools.myConnectionPP.passe;
import tools.myFunctionsPP;
import static tools.myFunctionsPP.closeStage;

/**
 * FXML Controller class
 *
 * @author samer
 */
public class NewDiagnosticController implements Initializable {

    @FXML
    private StackPane rootStackPane;
    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    private JFXTextField txtNom;
    @FXML
    private JFXTextField txtPrenom;
    @FXML
    private JFXTextField txtAge;
    @FXML
    private TextArea txtDiagnostic;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        SetTheme();
        setStart();
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

    public void setStart() {
        txtNom.setText(Nom);
        txtPrenom.setText(Prenom);
        txtAge.setText(age + "");
    }

    @FXML
    private void AddNewDiagnostics(ActionEvent event) {
        String observation=txtDiagnostic.getText();
        editCell("ID", "Observation", "consultation", observation, IDConsultation+"");
         if (passe == 1) {
            JFXButton btn = new JFXButton("OK");
            AlertMaker.showMaterialDialog(rootStackPane, (Node) rootAnchorPane, Arrays.asList(new JFXButton[]{
                btn
            }), null, "ENREGISTREMENT AVEC SUCCESS");
            btn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseevent -> {
                closeStage(rootAnchorPane);

            });
        } else {
            JFXButton btn = new JFXButton("OK");
            AlertMaker.showMaterialDialogError(rootStackPane, (Node) rootAnchorPane, Arrays.asList(new JFXButton[]{
                btn
            }), "ERREUR D'AJOUT", ex2 + "");
            btn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseevent -> {
            });
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        myFunctionsPP.closeStage(rootAnchorPane);
    }

}
