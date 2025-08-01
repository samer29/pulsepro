/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import static controllers.ConsultationController.IDConsultation;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import tools.LocalStorage;
import static tools.myConnectionPP.loadFullConsultationByID;
import static tools.myFunctionsPP.closeStage;

/**
 * FXML Controller class
 *
 * @author samer
 */
public class DetailConsultationController implements Initializable {

    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    private JFXTextField txtNom;
    @FXML
    private JFXTextField txtPrenom;
    @FXML
    private JFXTextField txtAge;
    @FXML
    private JFXTextField txtDateConsultation;
    @FXML
    private JFXTextField txtMotif;
    @FXML
    private JFXTextArea txtDiagnostic;
    @FXML
    private JFXTextArea txtTraitement;
    @FXML
    private StackPane rootStackPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        try {
            ResultSet rs = loadFullConsultationByID(IDConsultation + "");
            StringBuilder traitementText = new StringBuilder();
            boolean firstRow = true;

            while (rs.next()) {
                if (firstRow) {
                    txtNom.setText(rs.getString("nom"));
                    txtPrenom.setText(rs.getString("prenom"));
                    txtAge.setText(rs.getString("age"));
                    txtDateConsultation.setText(rs.getString("DateConsultation"));
                    txtMotif.setText(rs.getString("Motif"));
                    txtDiagnostic.setText(rs.getString("Observation"));
                    firstRow = false;
                }

                String article = rs.getString("Article");
                String quantite = rs.getString("Quantite");

                if (article != null && quantite != null) {
                    traitementText.append("- ").append(article).append(" x ").append(quantite).append("\n");
                }
            }

            txtTraitement.setText(traitementText.toString());

        } catch (SQLException ex) {
            Logger.getLogger(DetailConsultationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generateConsultationReportA4() {
        try {
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            String outputFile = "reports/consultations/Consultation_" + IDConsultation + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(outputFile));
            document.open();

            // Fonts
            BaseFont baseFont = BaseFont.createFont("fonts/Roboto.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font titleFont = new Font(baseFont, 18, Font.BOLD);
            Font labelFont = new Font(baseFont, 12, Font.BOLD);
            Font valueFont = new Font(baseFont, 12, Font.NORMAL);

            // Title
            Paragraph title = new Paragraph("Rapport de Consultation Médicale", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            ResultSet rs = loadFullConsultationByID(IDConsultation + "");
            if (rs.next()) {
                PdfPTable table = new PdfPTable(2);
                table.setWidthPercentage(100);
                table.setSpacingAfter(15f);

                addRow(table, "Nom :", rs.getString("nom"), labelFont, valueFont);
                addRow(table, "Prénom :", rs.getString("prenom"), labelFont, valueFont);
                addRow(table, "Âge :", rs.getString("age") + " ans", labelFont, valueFont);
                addRow(table, "Date de consultation :", formatDate(rs.getString("DateConsultation")), labelFont, valueFont);
                addRow(table, "Motif :", rs.getString("Motif"), labelFont, valueFont);
                addRow(table, "Observation :", rs.getString("Observation"), labelFont, valueFont);

                document.add(table);

                // Ligne ordonnance table
                Paragraph ordTitle = new Paragraph("Traitement Prescrit", labelFont);
                ordTitle.setSpacingAfter(10);
                document.add(ordTitle);

                PdfPTable ordTable = new PdfPTable(2);
                ordTable.setWidthPercentage(100);
                ordTable.setWidths(new float[]{3, 1});

                PdfPCell header1 = new PdfPCell(new Phrase("Article", labelFont));
                PdfPCell header2 = new PdfPCell(new Phrase("Quantité", labelFont));
                ordTable.addCell(header1);
                ordTable.addCell(header2);

                // Add multiple rows (since ligneordonance has many lines per consultation)
                do {
                    String article = rs.getString("Article");
                    String quantite = rs.getString("Quantite");
                    if (article != null && quantite != null) {
                        ordTable.addCell(new Phrase(article, valueFont));
                        ordTable.addCell(new Phrase(quantite, valueFont));
                    }
                } while (rs.next());

                document.add(ordTable);
            }

            document.close();
            Desktop.getDesktop().open(new File(outputFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addRow(PdfPTable table, String label, String value, Font labelFont, Font valueFont) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        labelCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(labelCell);

        PdfPCell valueCell = new PdfPCell(new Phrase(value, valueFont));
        valueCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(valueCell);
    }

    private String formatDate(String inputDate) throws ParseException {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");
        return output.format(input.parse(inputDate));
    }

    @FXML
    private void showPDFData(ActionEvent event) {
        generateConsultationReportA4();
    }

    @FXML
    private void closewindow(ActionEvent event) {
        closeStage(rootAnchorPane);
    }

}
