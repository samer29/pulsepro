/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pulsepro;

import controllers.ServerController;
import java.util.List;
import javafx.application.Application;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import static tools.myFunctionsPP.loadWindow;
import static tools.myFunctionsPP.outpoutstring;

/**
 *
 * @author Samer
 */
public class PulsePro extends Application {
    StackPane rootStackPane;
    @Override
    public void start(Stage stage) {
        try {
            // Font.loadFont(getClass().getResource("C:\\Users\\Samer\\Documents\\NetBeansProjects\\myPharm\\src\\fonts\\Roboto.ttf").toExternalForm(), 12);
            loadWindow(getClass().getResource("FXMLDocument.fxml"), "my Pharm", null, "no");

            testdatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void testdatabase() {
        final List output = outpoutstring("test.dat");
        ServerController.testdatab = String.join(",", output);
        if (output.isEmpty()) {
            loadWindow(this.getClass().getResource("/views/viewServer.fxml"), "Server ", null, "no");
        }
    }

}
