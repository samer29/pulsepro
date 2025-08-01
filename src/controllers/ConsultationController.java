/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import static controllers.FirstWindowPulseProController.ordre;
import static controllers.OrdonExamController.IDOrdonnace;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import tools.AlertMaker;
import tools.LocalStorage;
import tools.myConnectionPP;
import static tools.myConnectionPP.LastEnterySQL;
import static tools.myConnectionPP.ex2;
import static tools.myConnectionPP.passe;
import tools.myFunctionsPP;
import static tools.myFunctionsPP.OnlyIntegersForTextField;
import static tools.myFunctionsPP.loadWindow;

/**
 * FXML Controller class
 *
 * @author Samer
 */
public class ConsultationController implements Initializable {

    @FXML
    private StackPane rootStackPane;
    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    private Label LabelOrdre;
    @FXML
    private Label DateConsultation;
    @FXML
    private JFXTextField txtMotif;
    @FXML
    private JFXTextField txtPrix;
    Stage stage = new Stage();
    public static String DateConsultationDate;
    public static int IDConsultation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getdata();
        SetTheme();
        OnlyIntegersForTextField(txtPrix);
    }

    public void getdata() {
        LabelOrdre.setText(ordre + "");
        LocalDate today = LocalDate.now();
        if (DateConsultationDate != null) {
            DateConsultation.setText(DateConsultationDate);
        } else {
            DateConsultation.setText(today + "");
            DateConsultationDate = today.toString();
        }

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
    public void restartData() {
        IDConsultation = LastEnterySQL("ID", "consultation");
        myConnectionPP.addordonnance(IDConsultation);
        IDOrdonnace = IDConsultation;
        System.out.println("ID CONSULTATION " + IDConsultation + "ID Ordonannce " + IDOrdonnace);
        loadWindow(this.getClass().getResource("/views/viewOrdonExam.fxml"), "Crée une Ordonnance ", stage, "no");
        myFunctionsPP.closeStage(rootAnchorPane);

    }

    @FXML
    private void AddNewConsultation(ActionEvent event) {
        String Motif;
        double Prix;
        Motif = txtMotif.getText();
        Prix = Double.valueOf(txtPrix.getText());
        myConnectionPP.addConsultation(ordre, LocalDate.now(), Motif, Prix);
        if (passe == 1) {
            JFXButton btn = new JFXButton("OK");
            AlertMaker.showMaterialDialog(rootStackPane, (Node) rootAnchorPane, Arrays.asList(new JFXButton[]{
                btn
            }), null, "ENREGISTREMENT AVEC SUCCESS");
            btn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseevent -> {
                restartData();
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

}
