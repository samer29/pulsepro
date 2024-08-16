/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import static controllers.FirstWindowController.DateConsultationDate;
import static controllers.FirstWindowController.HOVERED_BUTTON_STYLE;
import static controllers.FirstWindowController.IDLE_BUTTON_STYLE;
import static controllers.FirstWindowController.Nom;
import static controllers.FirstWindowController.Prenom;
import static controllers.FirstWindowController.Sexe;
import static controllers.FirstWindowController.age;
import static controllers.FirstWindowController.ordre;
import static controllers.FirstWindowController.theme;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import tools.AlertMaker;
import tools.LocalStorage;
import static tools.PDFPrintingExample.printPDF;
import tools.myConnectionPP;
import static tools.myConnectionPP.addToExamen;
import static tools.myConnectionPP.delete;
import static tools.myConnectionPP.editCell;
import static tools.myConnectionPP.ex2;
import static tools.myConnectionPP.inst3;
import static tools.myConnectionPP.myerrorMessage;
import static tools.myConnectionPP.passe;
import tools.myFunctionsPP.FooterImage;
import static tools.myFunctionsPP.fillComboData;
import static tools.myFunctionsPP.fillComboline;
import static tools.myFunctionsPP.fillTableWithConditionASCENDING;
import static tools.myFunctionsPP.fillcombox;
import static tools.myFunctionsPP.fillculms;

/**
 * FXML Controller class
 *
 * @author Samer
 */
public class OrdonExamController implements Initializable {

    @FXML
    private Label LabelOrdre;
    @FXML
    private Label NomPatient;
    @FXML
    private Label PrenomPatient;
    @FXML
    private Label AgePatient;
    @FXML
    private Label DateConsultation;
    @FXML
    private JFXComboBox comboMedicam;
    @FXML
    private TableView tableExamens;
    @FXML
    private JFXComboBox comboQuantiteMedic;
    @FXML
    private JFXTextField txtDetailMedic;
    @FXML
    private TableView tableOrdonance;
    @FXML
    private TableColumn clmID;
    @FXML
    private TableColumn clmMedicament;
    @FXML
    private TableColumn clmQuantite;
    @FXML
    private TableColumn clmDetailMedic;
    @FXML
    private TableColumn clmDeleteMedic;
    @FXML
    private StackPane rootStackPane;
    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    private JFXComboBox comboExamen;
    @FXML
    private TableColumn clmIDExamen;
    @FXML
    private TableColumn clmExamen;
    @FXML
    private TableColumn clmDeleteExamen;
    ObservableList<Object> opt;
    String filename;
    javafx.scene.image.Image imProfile = new javafx.scene.image.Image(getClass().getResourceAsStream("/icons/male.png"));
    javafx.scene.image.Image imProfileFe = new javafx.scene.image.Image(getClass().getResourceAsStream("/icons/female.png"));
    @FXML
    private ImageView genderImageSR;
    int testprinting = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        SetStart();
        fillDataTableMedic();
        fillDataExamens();
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

    public void SetStart() {
        LabelOrdre.setText(ordre + "");
        NomPatient.setText(Nom);
        PrenomPatient.setText(Prenom);
        AgePatient.setText(age + "");
        DateConsultation.setText(DateConsultationDate);
        if ("HOMME".equals(Sexe)) {
            genderImageSR.setImage(imProfile);
        }
        if ("FEMME".equals(Sexe)) {
            genderImageSR.setImage(imProfileFe);
        }
        fillcombox(Arrays.asList("1", "2", "3", "4", "5", "6", "7"), comboQuantiteMedic, "1");
        fillComboData(comboMedicam, "medicaments", "NomMed");

//                    fillComboline(comboMedicam, "medicaments", "NomMed", opt);
        fillComboline(comboExamen, "listexamens", "Examen", opt);

    }

    @FXML
    private void AddNewExamen(MouseEvent event) {
        JFXButton btn = new JFXButton("OK");
        String examen;
        int IDPatient;
        IDPatient = Integer.parseInt(LabelOrdre.getText());
        examen = comboExamen.getSelectionModel().getSelectedItem().toString();
        passe = 0;
        addToExamen(IDPatient, examen);
        fillDataExamens();

        if (passe == 1) {
            myConnectionPP.checkfromDB("listexamens", "Examen", examen);

        } else {
            AlertMaker.showMaterialDialogError(rootStackPane, (Node) rootAnchorPane, Arrays.asList(new JFXButton[]{
                btn
            }), "ERREUR D'AJOUT", myerrorMessage + "");
            btn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseevent
                    -> {
            });
        }

    }

    @FXML
    private void addNewMedic(MouseEvent event) {
        JFXButton btn = new JFXButton("OK");
        String Article, detail;
        int IDPatient, Quantite;
        IDPatient = Integer.parseInt(LabelOrdre.getText());
        Article = comboMedicam.getSelectionModel().getSelectedItem().toString();
        Quantite = Integer.parseInt(comboQuantiteMedic.getSelectionModel().getSelectedItem().toString());
        detail = txtDetailMedic.getText();
        passe = 0;
        int IDOrdonance=myConnectionPP.createNewOrdonnance(IDPatient);
        myConnectionPP.addToOrdonnance(IDPatient, Article, Quantite, detail);
        fillDataTableMedic();

        if (passe == 1) {
            myConnectionPP.checkfromDB("medicaments", "NomMed", Article);

        } else {
            AlertMaker.showMaterialDialogError(rootStackPane, (Node) rootAnchorPane, Arrays.asList(new JFXButton[]{
                btn
            }), "ERREUR D'AJOUT", ex2 + "");
            btn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseevent
                    -> {
            });
        }

    }

    public void fillDataTableMedic() {

        String id = LabelOrdre.getText();
        tableOrdonance.setEditable(true);

        fillculms(clmID, 0);
        fillculms(clmMedicament, 2);
        fillculms(clmQuantite, 3);
        fillculms(clmDetailMedic, 4);

        fillTableWithConditionASCENDING("ordonnance", "IDPatient", id, tableOrdonance, 5, clmID);

        clmMedicament.setCellFactory(TextFieldTableCell.forTableColumn());
        clmMedicament.setOnEditCommit(event -> editCommit((TableColumn.CellEditEvent<ObservableList<String>, String>) event, "Article", "ordonnance", "ID"));

        clmQuantite.setCellFactory(TextFieldTableCell.forTableColumn());
        clmQuantite.setOnEditCommit(event -> editCommit((TableColumn.CellEditEvent<ObservableList<String>, String>) event, "Quantite", "ordonnance", "ID"));

        clmDetailMedic.setCellFactory(TextFieldTableCell.forTableColumn());
        clmDetailMedic.setOnEditCommit(event -> editCommit((TableColumn.CellEditEvent<ObservableList<String>, String>) event, "Detail", "ordonnance", "ID"));

        clmDeleteMedic.setCellFactory((Callback) new Callback<TableColumn<ObservableList, String>, TableCell<ObservableList, String>>() {
            public TableCell<ObservableList, String> call(TableColumn<ObservableList, String> param) {
                return new DeleteButtonCellStock();
            }
        });
    }

    public void fillDataExamens() {
        String id = LabelOrdre.getText();
        tableExamens.setEditable(true);
        fillculms(clmIDExamen, 0);
        fillculms(clmExamen, 2);

        fillTableWithConditionASCENDING("examenprescrit", "IDPatient", id, tableExamens, 3, clmIDExamen);

        clmExamen.setCellFactory(TextFieldTableCell.forTableColumn());
        clmExamen.setOnEditCommit(event -> editCommit((TableColumn.CellEditEvent<ObservableList<String>, String>) event, "Examen", "examenprescrit", "ID"));

        clmDeleteExamen.setCellFactory((Callback) new Callback<TableColumn<ObservableList, String>, TableCell<ObservableList, String>>() {
            public TableCell<ObservableList, String> call(TableColumn<ObservableList, String> param) {
                return new DeleteButtonExamen();
            }
        });

    }

    public void printOrdoFunction() {
        try {
            JFXButton btn = new JFXButton("OK");

            String id = LabelOrdre.getText();
            filename = "reports/ordonnance/ordonnance_N" + id + ".pdf";

            // Create a new PDF document
            Document mydoc = new Document(PageSize.A5, 30, 30, 30, 30);
            PdfWriter writer = PdfWriter.getInstance(mydoc, new FileOutputStream(filename));
            mydoc.open();

            // Add content to the PDF
            mydoc.addAuthor("Samer Elouissi");
            mydoc.addTitle("Ordonnance");

            // Calculate the desired width and height of the image
            float maxWidth = 400f; // Adjust as needed
            float maxHeight = 200f; // Adjust as needed

            // Load the image
            Image img = Image.getInstance("src/icons/PulseProHeader.jpg");

            // Get the original dimensions of the image
            float originalWidth = img.getWidth();
            float originalHeight = img.getHeight();

            // Calculate the aspect ratio of the image
            float aspectRatio = originalWidth / originalHeight;

            // Calculate the new dimensions while preserving the aspect ratio
            float newWidth = Math.min(originalWidth, maxWidth);
            float newHeight = newWidth / aspectRatio;

            // Ensure that the height does not exceed the maxHeight
            if (newHeight > maxHeight) {
                newHeight = maxHeight;
                newWidth = newHeight * aspectRatio;
            }

            // Scale the image to the new dimensions
            img.scaleAbsolute(newWidth, newHeight);
            img.setAlignment(Element.ALIGN_CENTER);
            img.setSpacingBefore(10f);

            // Add the image to the document
            mydoc.add(img);

            // Add patient information
            BaseFont baseFont = BaseFont.createFont("fonts/Roboto.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            BaseFont baseFont2 = BaseFont.createFont("fonts/azonix.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            Font customFont = new Font(baseFont, 10, Font.NORMAL);
            Font bold = new Font(baseFont, 10, Font.BOLD);
            Font headerFont = new Font(baseFont2, 16, Font.NORMAL);

            Chunk chunkNum = new Chunk("Le : ", customFont);
            Chunk chunkBLNumDetail = new Chunk(DateConsultationDate, bold);
            Chunk chunkBLDate = new Chunk("Sexe :", customFont);
            Chunk chunkBLDateDetail = new Chunk(Sexe, bold);

            Phrase PhraseBLDateService = new Phrase();
            PhraseBLDateService.add(chunkNum);
            PhraseBLDateService.add(chunkBLNumDetail);
            PhraseBLDateService.add(new Chunk("                                                              ")); // Adjust the number of spaces as needed
            PhraseBLDateService.add(chunkBLDate);
            PhraseBLDateService.add(chunkBLDateDetail);

            Paragraph ParaBlDateService = new Paragraph();
            ParaBlDateService.add(PhraseBLDateService);
            ParaBlDateService.setAlignment(Paragraph.ALIGN_LEFT);

            Chunk chunkNom = new Chunk("Nom & Prénom : ", customFont);
            Chunk chunkNomDetail = new Chunk(Nom + " " + Prenom, bold);
            Chunk chunkAge = new Chunk("Age :", customFont);
            Chunk chunkAgeDetail = new Chunk(age + "", bold);
            Chunk chunkAns = new Chunk(" ans ", customFont);

            Phrase PhraseNomPrenom = new Phrase();
            PhraseNomPrenom.add(chunkNom);
            PhraseNomPrenom.add(chunkNomDetail);
            PhraseNomPrenom.add(new Chunk("                              ")); // Adjust the number of spaces as needed
            PhraseNomPrenom.add(chunkAge);
            PhraseNomPrenom.add(chunkAgeDetail);
            PhraseNomPrenom.add(chunkAns);

            Paragraph ParaNomPrenomAge = new Paragraph();
            ParaNomPrenomAge.add(PhraseNomPrenom);
            ParaNomPrenomAge.setAlignment(Paragraph.ALIGN_LEFT);

           
            Chunk chunkNOrdonance = new Chunk("N° Ordonance :", customFont);
            Chunk chunkNOrdonanceDetail = new Chunk(id, bold);

            Phrase PhraseAdresse = new Phrase();
            PhraseAdresse.add(chunkNOrdonance);
            PhraseAdresse.add(chunkNOrdonanceDetail);

            Paragraph ParaAdresse = new Paragraph();
            ParaAdresse.add(PhraseAdresse);
            ParaAdresse.setAlignment(Paragraph.ALIGN_LEFT);

            LineSeparator ls = new LineSeparator();
            mydoc.add(new Chunk(ls));
            mydoc.add((Element) ParaBlDateService);
            mydoc.add((Element) ParaNomPrenomAge);
            mydoc.add((Element) ParaAdresse);

            mydoc.add(new Chunk(ls));
            Paragraph paragraphHeader = new Paragraph("ORDONNANCE", headerFont);
            paragraphHeader.setAlignment(Paragraph.ALIGN_CENTER);
            mydoc.add((Element) paragraphHeader);

            ResultSet rs = inst3("ordonnance", "IDPatient", id);

            PdfPTable table = new PdfPTable(3);
            table.setSpacingBefore(30f);
            table.setTotalWidth(new float[]{30f, 280F, 60f}); // Set column widths
            table.setLockedWidth(true); // Lock the width of the table

            int rowCount = 0;
            while (rs.next()) {
                rowCount++;
                PdfPCell cell;

                // Add ID cell
                cell = new PdfPCell(new Phrase(String.valueOf(rowCount), customFont));
                cell.setBorder(PdfPCell.NO_BORDER); // Remove border
                cell.setPaddingBottom(10f); // Add space after each row
                table.addCell(cell);

                // Add Article and Detail cell
                cell = new PdfPCell(new Phrase(rs.getString("article") + " - " + rs.getString("detail"), customFont));
                cell.setBorder(PdfPCell.NO_BORDER); // Remove border
                cell.setPaddingBottom(10f); // Add space after each row

                table.addCell(cell);

                // Add Quantite cell
                cell = new PdfPCell(new Phrase(rs.getString("Quantite"), customFont));
                cell.setBorder(PdfPCell.NO_BORDER); // Remove border
                cell.setPaddingBottom(10f); // Add space after each row

                table.addCell(cell);
            }
            mydoc.add(table);

            FooterImage event2 = new FooterImage("src/icons/footerPulsePro.jpg");
            writer.setPageEvent(event2);

            // Close the document
            mydoc.close();

            // Show success message
            AlertMaker.showMaterialDialog(this.rootStackPane, this.rootAnchorPane, Arrays.asList(new JFXButton[]{
                btn
            }), null, "PDF généré avec succès");
            btn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseevent
                    -> {
                // Open the PDF file
                File file = new File(this.filename);

                try {
                    if (testprinting == 1) {
                        printPDF(filename);
                    } else {
                        Desktop.getDesktop().open(file);
                    }

                } catch (IOException ex) {
                    Logger.getLogger(OrdonExamController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        } catch (DocumentException | IOException | SQLException ex) {
            Logger.getLogger(OrdonExamController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void PrintExamen(ActionEvent event) {
        testprinting = 1;
        PrintExamFunct();
    }

    @FXML
    private void printOrdonnance(ActionEvent event) {
        testprinting = 1;
        printOrdoFunction();
    }

    @FXML
    private void visualiserOrdonnance(ActionEvent event) {
        testprinting = 0;
        printOrdoFunction();
    }

    @FXML
    private void visualiserExamen(ActionEvent event) {
        testprinting = 0;
        PrintExamFunct();
    }

    public void PrintExamFunct() {
        try {
            JFXButton btn = new JFXButton("OK");

            String id = LabelOrdre.getText();
            filename = "reports/examens/Examen_N" + id + ".pdf";

            // Create a new PDF document
            Document mydoc = new Document(PageSize.A5, 30, 30, 30, 30);
            PdfWriter writer = PdfWriter.getInstance(mydoc, new FileOutputStream(filename));
            mydoc.open();

            // Add content to the PDF
            mydoc.addAuthor("Samer Elouissi");
            mydoc.addTitle("Ordonnance");

            // Calculate the desired width and height of the image
            float maxWidth = 400f; // Adjust as needed
            float maxHeight = 200f; // Adjust as needed

            // Load the image
            Image img = Image.getInstance("src/icons/PulseProHeader.jpg");

            // Get the original dimensions of the image
            float originalWidth = img.getWidth();
            float originalHeight = img.getHeight();

            // Calculate the aspect ratio of the image
            float aspectRatio = originalWidth / originalHeight;

            // Calculate the new dimensions while preserving the aspect ratio
            float newWidth = Math.min(originalWidth, maxWidth);
            float newHeight = newWidth / aspectRatio;

            // Ensure that the height does not exceed the maxHeight
            if (newHeight > maxHeight) {
                newHeight = maxHeight;
                newWidth = newHeight * aspectRatio;
            }

            // Scale the image to the new dimensions
            img.scaleAbsolute(newWidth, newHeight);
            img.setAlignment(Element.ALIGN_CENTER);
            img.setSpacingBefore(10f);

            // Add the image to the document
            mydoc.add(img);

            // Add patient information
            BaseFont baseFont = BaseFont.createFont("fonts/Roboto.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            BaseFont baseFont2 = BaseFont.createFont("fonts/azonix.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            Font customFont = new Font(baseFont, 10, Font.NORMAL);
            Font bold = new Font(baseFont, 10, Font.BOLD);
            Font headerFont = new Font(baseFont2, 16, Font.NORMAL);

            Chunk chunkNum = new Chunk("Le : ", customFont);
            Chunk chunkBLNumDetail = new Chunk(DateConsultationDate, bold);
            Chunk chunkBLDate = new Chunk("Sexe :", customFont);
            Chunk chunkBLDateDetail = new Chunk(Sexe, bold);

            Phrase PhraseBLDateService = new Phrase();
            PhraseBLDateService.add(chunkNum);
            PhraseBLDateService.add(chunkBLNumDetail);
            PhraseBLDateService.add(new Chunk("                                                              ")); // Adjust the number of spaces as needed
            PhraseBLDateService.add(chunkBLDate);
            PhraseBLDateService.add(chunkBLDateDetail);

            Paragraph ParaBlDateService = new Paragraph();
            ParaBlDateService.add(PhraseBLDateService);
            ParaBlDateService.setAlignment(Paragraph.ALIGN_LEFT);

            Chunk chunkNom = new Chunk("Nom & Prénom : ", customFont);
            Chunk chunkNomDetail = new Chunk(Nom + " " + Prenom, bold);
            Chunk chunkAge = new Chunk("Age :", customFont);
            Chunk chunkAgeDetail = new Chunk(age + "", bold);
            Chunk chunkAns = new Chunk(" ans", customFont);

            Phrase PhraseNomPrenom = new Phrase();
            PhraseNomPrenom.add(chunkNom);
            PhraseNomPrenom.add(chunkNomDetail);
            PhraseNomPrenom.add(new Chunk("                              ")); // Adjust the number of spaces as needed
            PhraseNomPrenom.add(chunkAge);
            PhraseNomPrenom.add(chunkAgeDetail);
            PhraseNomPrenom.add(chunkAns);

            Paragraph ParaNomPrenomAge = new Paragraph();
            ParaNomPrenomAge.add(PhraseNomPrenom);
            ParaNomPrenomAge.setAlignment(Paragraph.ALIGN_LEFT);

          
            Chunk chunkNOrdonance = new Chunk("N° Examen :", customFont);
            Chunk chunkNOrdonanceDetail = new Chunk(id, bold);

            Phrase PhraseAdresse = new Phrase();
            PhraseAdresse.add(chunkNOrdonance);
            PhraseAdresse.add(chunkNOrdonanceDetail);

            Paragraph ParaAdresse = new Paragraph();
            ParaAdresse.add(PhraseAdresse);
            ParaAdresse.setAlignment(Paragraph.ALIGN_LEFT);

            LineSeparator ls = new LineSeparator();
            mydoc.add(new Chunk(ls));
            mydoc.add((Element) ParaBlDateService);
            mydoc.add((Element) ParaNomPrenomAge);
            mydoc.add((Element) ParaAdresse);

            mydoc.add(new Chunk(ls));
            Paragraph paragraphHeader = new Paragraph("EXAMENS A FAIRE ", headerFont);
            paragraphHeader.setAlignment(Paragraph.ALIGN_CENTER);
            mydoc.add((Element) paragraphHeader);

            ResultSet rs = inst3("examenprescrit", "IDPatient", id);

            PdfPTable table = new PdfPTable(2);
            table.setSpacingBefore(30f);
            table.setTotalWidth(new float[]{30f, 280F}); // Set column widths
            table.setLockedWidth(true); // Lock the width of the table

            int rowCount = 0;
            int i = 0;
            while (rs.next()) {
                rowCount++;
                PdfPCell cell;

                // Add ID cell
                cell = new PdfPCell(new Phrase(String.valueOf(rowCount), customFont));
                cell.setBorder(PdfPCell.NO_BORDER); // Remove border
                cell.setPaddingBottom(10f); // Add space after each row
                table.addCell(cell);

                // Add Article and Detail cell
                cell = new PdfPCell(new Phrase(rs.getString("Examen"), customFont));
                cell.setBorder(PdfPCell.NO_BORDER); // Remove border
                cell.setPaddingBottom(10f); // Add space after each row

                table.addCell(cell);

            }
            mydoc.add(table);

            FooterImage event2 = new FooterImage("src/icons/footerPulsePro.jpg");
            writer.setPageEvent(event2);
            // Close the document
            mydoc.close();

            // Show success message
            AlertMaker.showMaterialDialog(this.rootStackPane, this.rootAnchorPane, Arrays.asList(new JFXButton[]{
                btn
            }), null, "PDF généré avec succès");
            btn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseevent
                    -> {
                // Open the PDF file
                File file = new File(this.filename);
                printPDF(filename);
                try {
                    if (testprinting == 1) {
                        printPDF(filename);
                    } else {
                        Desktop.getDesktop().open(file);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(OrdonExamController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        } catch (DocumentException | IOException | SQLException ex) {
            Logger.getLogger(OrdonExamController.class.getName()).log(Level.SEVERE, null, ex);
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
                        ObservableList rowList = (ObservableList) tableOrdonance.getItems().get(DeleteButtonCellStock.this.getIndex());
                        String ID = null;
                        ID = rowList.get(0).toString();
                        delete(ID, "ordonnance", "ID");
                        fillDataTableMedic();
                    }
                });
                setGraphic((Node) cellButton2);
            } else {
                setGraphic(null);
            }
        }
    }

    private class DeleteButtonExamen extends TableCell<ObservableList, String> {

        final HBox cellButton = new HBox();
        final Button cellButton2 = new Button("");

        DeleteButtonExamen() {
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
                        ObservableList rowList = (ObservableList) tableExamens.getItems().get(DeleteButtonExamen.this.getIndex());
                        String ID = null;
                        ID = rowList.get(0).toString();
                        delete(ID, "examenprescrit", "ID");

                        fillDataExamens();
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
        fillDataExamens();
        fillDataTableMedic();
    }

}
