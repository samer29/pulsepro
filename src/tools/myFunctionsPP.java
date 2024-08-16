package tools;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import static controllers.FirstWindowController.theme;
import controllers.SettingsController;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import static tools.AlertMaker.showMaterialDialogAvertissemnt;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import javafx.util.converter.IntegerStringConverter;
import static tools.myConnectionPP.SelectStringFromTableWhereClmIs;
import static tools.myConnectionPP.delete;
import static tools.myConnectionPP.fillComboDISTINCT;
import static tools.myConnectionPP.fillComboWithConditionDuplicatedDataAndSorted;
import static tools.myConnectionPP.fillComboWithConditionwithoutDuplicatedData;
import static tools.myConnectionPP.fillComboWithoutDuplicateData;
import static tools.myConnectionPP.inst;
import static tools.myConnectionPP.inst2;
import static tools.myConnectionPP.inst3;
import static tools.myConnectionPP.passe;
import static tools.myConnectionPP.fillCombo;
public class myFunctionsPP {

    static ObservableList<Object> data;
    public static final String ICON_IMAGE_LOC = "/icons/logo.png";
    public static String mytheme;
    static ObservableList opt;
    public static UnaryOperator<TextFormatter.Change> integerFilter;
    public static int sizeofoption = 0;
    public static Exception exept;
    public static Image image;
    public static File file;
    public static BufferedImage bufferedImage;
    public static String imagePath;

    public static void setStageIcon(Stage stage) {
        stage.getIcons().add(new javafx.scene.image.Image(ICON_IMAGE_LOC));
    }

    public static void loadWebpage(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e1) {
            e1.printStackTrace();
            handleWebpageLoadException(url);
        }
    }

    public static String readmyfile(String filename) {
        String data = null;
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return data;
    }

    public static void copyFileUsingStream(File source, File dest, StackPane rootS, AnchorPane rootAn) {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch (FileNotFoundException ex) {
            JFXButton btn = new JFXButton("OK");
            AlertMaker.showMaterialDialogError(rootS, (Node) rootAn, Arrays.asList(new JFXButton[]{btn}), "Erreur", ex + "");
            btn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseevent -> {
            });

            Logger.getLogger(myFunctionsPP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(myFunctionsPP.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(myFunctionsPP.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                os.close();
            } catch (IOException ex) {
                Logger.getLogger(myFunctionsPP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void SetTheme(StackPane rootStack) {
        if (theme.equals("Dark")) {
            rootStack.getStylesheets().remove("/style/pulseProtheme.css");
            rootStack.getStylesheets().add("/style/pulseProthemeDARK.css");
        } else {
            rootStack.getStylesheets().remove("/style/pulseProtheme.css");
            rootStack.getStylesheets().add("/style/pulseProthemeDARK.css");
        }
    }

    public static void cellwithoutborder(PdfPTable table, String phrase2, int taille) {
        Font bold = FontFactory.getFont(FontFactory.TIMES_BOLD, taille, Font.BOLD);
        Chunk chunkAddress = new Chunk(phrase2, bold);
        Phrase phrase = new Phrase();
        phrase.add(chunkAddress);
        PdfPCell cell = new PdfPCell(new Phrase(phrase));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
    }

    public static boolean intervallContains(int low, int high, int n) {
        return n >= low && n <= high;
    }

    public static LocalDate convertToLocalDateViaInstant(Object objectToConvert) {
        if (objectToConvert instanceof Date) {
            Date dateToConvert = (Date) objectToConvert;
            return dateToConvert.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        } else {
            throw new IllegalArgumentException("The object provided is not an instance of java.sql.Date");
        }
    }

    public static class FooterImage extends PdfPageEventHelper {

        String imagePath;

        public FooterImage(String imagePath) {
            this.imagePath = imagePath;
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            try {
                PdfContentByte canvas = writer.getDirectContentUnder();

                // Load the image
                Image image = Image.getInstance(imagePath);

                // Set the position and scale of the image
                image.scaleToFit(PageSize.A5.getWidth(), PageSize.A5.getHeight()); // Scale the image to fit the page size
                image.setAbsolutePosition(0, 0); // Position the image at the bottom-left corner of the page

                // Add the image to the canvas
                canvas.addImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String converLocalDateToString(LocalDate Date) {
        String converted = Date.toString();
        return converted;
    }

    public static void populatetablepdf(ResultSet rs, int i, PdfPCell tableCell, PdfPTable mytable, int taille) {
        String sn;
        try {
            Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, taille, Font.NORMAL);

            sn = rs.getString(i);
            if (sn.equals("null")) {
                sn = "-";
            }

            Chunk chunkAddress = new Chunk(sn, font);
            Phrase phrase = new Phrase();
            phrase.add(chunkAddress);
            tableCell = new PdfPCell(phrase);
            tableCell.setBorder(Rectangle.NO_BORDER);
            tableCell.setLeading(3, 1);

            //tableCell.setFixedHeight(17);
            mytable.addCell(tableCell);
        } catch (SQLException ex) {
            Logger.getLogger(myFunctionsPP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void populatetablepdfWithoutRS(String sn, int i, PdfPCell tableCell, PdfPTable mytable, int taille) {

        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, taille, Font.NORMAL);

        Chunk chunkAddress = new Chunk(sn, font);
        Phrase phrase = new Phrase();
        phrase.add(chunkAddress);
        tableCell = new PdfPCell(phrase);
        tableCell.setBorder(Rectangle.NO_BORDER);
        tableCell.setLeading(3, 1);
        mytable.addCell(tableCell);
    }

    public static void populatetablepdfSpecial(ResultSet rs, int i, PdfPCell tableCell, PdfPTable mytable) {
        String sn;
        float min, max;
        String valeur;
        String unite;
        try {
            Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL);
            Font fontbold = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);

            sn = rs.getString(i);
            min = rs.getFloat("min");
            max = rs.getFloat("max");
            unite = rs.getString("unite");
            Chunk chunkAddress;
            if (sn.equals("null")) {
                sn = "-";
            }
            valeur = rs.getString("Valeur");
            if ("Negative".equals(valeur) || "Positive".equals(valeur)) {
                chunkAddress = new Chunk(valeur, font);
            } else {
                float valeur2 = Float.valueOf(valeur);
                if (valeur2 >= min && valeur2 <= max) {
                    chunkAddress = new Chunk(sn + "  " + unite, font);
                } else {
                    chunkAddress = new Chunk(sn + "  " + unite, fontbold);
                }
            }

            Phrase phrase = new Phrase();
            phrase.add(chunkAddress);
            tableCell = new PdfPCell(phrase);
            tableCell.setBorder(Rectangle.NO_BORDER);
            tableCell.setLeading(3, 1);

            mytable.addCell(tableCell);
        } catch (SQLException ex) {
            Logger.getLogger(myFunctionsPP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void populatetablepdfVarchar(ResultSet rs, int i, PdfPCell tableCell, PdfPTable mytable) {
        String sn;
        String valeur;
        try {
            Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL);
            Font fontbold = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);

            sn = rs.getString(i);
            if (sn.equals("null")) {
                sn = "-";
            }
            valeur = rs.getString("Valeur");

            Phrase phrase = new Phrase();
            Chunk chunkValeur = new Chunk(valeur, font);
            phrase.add(chunkValeur);
            tableCell = new PdfPCell(phrase);
            tableCell.setBorder(Rectangle.NO_BORDER);
            tableCell.setLeading(3, 1);

            mytable.addCell(tableCell);
        } catch (SQLException ex) {
            Logger.getLogger(myFunctionsPP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // Function to get the footer text based on page numbers

    public static String getFooterText(PdfWriter writer) {
        int pageNumber = writer.getPageNumber();
        int totalPages = writer.getCurrentPageNumber();
        if (totalPages == 1) {
            return ""; // No footer for single page document
        } else {
            return String.format("%d/%d", pageNumber, totalPages - 1);
        }
    }

    public static void populatetablepdfSpecialFORCRP(ResultSet rs, int i, PdfPCell tableCell, PdfPTable mytable) {
        String sn;
        String valeur;
        try {
            Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL);
            Font fontbold = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);

            sn = rs.getString(i);
            if (sn.equals("null")) {
                sn = "-";
            }
            valeur = rs.getString("Valeur");
            //min = rs.getFloat("min");
            //max = rs.getFloat("max");
            Chunk chunkAddress;
            if ("Negative".equals(valeur)) {
                chunkAddress = new Chunk(sn, font);
            } else {
                chunkAddress = new Chunk(sn, fontbold);
            }

            Phrase phrase = new Phrase();
            phrase.add(chunkAddress);
            tableCell = new PdfPCell(phrase);
            tableCell.setBorder(Rectangle.NO_BORDER);
            tableCell.setLeading(3, 1);

            mytable.addCell(tableCell);
        } catch (SQLException ex) {
            Logger.getLogger(myFunctionsPP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void minimizeScene(AnchorPane root) {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setIconified(true);
    }

    public static void calculateAgeFunction(JFXDatePicker birthDatePicker, JFXTextField mytext) {
        // Get the selected birthdate from the DatePicker
        LocalDate birthdate = birthDatePicker.getValue();

        if (birthdate != null) {
            // Get the current date
            LocalDate currentDate = LocalDate.now();
            // Calculate the period between the birthdate and the current date
            Period period = Period.between(birthdate, currentDate);
            // Display the calculated age
            int years = period.getYears();
            int months = period.getMonths();
            int days = period.getDays();

            System.out.println("You are " + years + " years, " + months + " months, and " + days + " days old.");
            mytext.setText(years + "");
        } else {
            System.out.println("Please select your birthdate.");
        }
    }

    public static LocalDate calculateDateOfBirth(int age, JFXDatePicker dateOfBirthPicker) {
        LocalDate currentDate = LocalDate.now();
        LocalDate calculatedDateOfBirth = currentDate.minusYears(age);
        return calculatedDateOfBirth;
    }

    public static void DateOfBirth(int age, JFXDatePicker DatePicker, JFXTextField myText) {
        myText.textProperty().isEmpty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> observable,
                    final Boolean oldValue, final Boolean newValue) {
                if (newValue) {
                    DatePicker.setValue(null);
                }
            }
        });

        Date date = null;
        int da = 01;
        int mo = 01;
        int ye = age;

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -ye);
        cal.add(Calendar.MONTH, -mo);
        cal.add(Calendar.DAY_OF_YEAR, -da);

        System.out.println(cal.getTime());
        date = cal.getTime();
        LocalDate date2 = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        DatePicker.setValue(date2);

    }

    private static void handleWebpageLoadException(String url) {
        WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();
        webEngine.load(url);
        Stage stage = new Stage();
        Scene scene = new Scene((Parent) new StackPane(new Node[]{browser}));
        stage.setScene(scene);
        stage.setTitle("Samer Elouissi");
        stage.show();
        myFunctionsPP.setStageIcon(stage);
    }

    public static Object loadWindow(URL loc, String title, Stage parentStage, String test) {
        Object controller = null;
        try {
            FXMLLoader loader = new FXMLLoader(loc);
            Parent parent = (Parent) loader.load();
            controller = loader.getController();
            Stage stage = null;
            stage = parentStage != null ? parentStage : new Stage(StageStyle.UNDECORATED);
            stage.setTitle(title);
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
            myFunctionsPP.setStageIcon(stage);
            if ("yes".equals(test)) {
                stage.setMaximized(true);
            } else {
                stage.setMaximized(false);
            }
        } catch (IOException ex) {
            Logger.getLogger(myFunctionsPP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return controller;
    }

    public static void loadfadestage(int time, final AnchorPane ap, final String URI2, final String title, final String test) {
        FadeTransition ft = new FadeTransition();
        ft.setDuration(Duration.millis((double) time));
        ft.setNode((Node) ap);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setOnFinished((EventHandler) new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                myFunctionsPP.loadWindow(this.getClass().getResource(URI2), title, null, test);
                myFunctionsPP.closeStage(ap);
            }
        });
        ft.play();
    }

    @FXML
    public static void closeStage(AnchorPane btn) {
        ((Stage) btn.getScene().getWindow()).close();
    }

    public static void numericTextfield(TextField tField) {
        tField.setOnKeyReleased(event -> {
            if (!tField.getText().matches("[0-9.]*")) {
                tField.deletePreviousChar();
            }
        });
        tField.setOnKeyPressed(event -> {
            if (!tField.getText().matches("[0-9.]*")) {
                tField.deletePreviousChar();
            }
        });
        tField.setOnKeyTyped(event -> {
            if (!tField.getText().matches("[0-9.]*")) {
                tField.deletePreviousChar();
            }
        });
    }

    public static void exit() {
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("taskkill /F /IM wampmanager.exe");
            Process process = rt.exec("taskkill /F /IM xampp-control.exe");
        } catch (Exception exception) {
            // empty catch block
        }
        System.exit(0);
    }

    public static void fillcombox(List<String> items, JFXComboBox mycombo, String valueS) {
        mycombo.getItems().clear();
        mycombo.getItems().addAll(items);
        mycombo.setValue((Object) valueS);
        AutoCompleteComboBoxListener<Object> autoCompleteComboBoxListener = new AutoCompleteComboBoxListener<>(mycombo);

    }

    public static long generateRandom(int max) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append(random.nextInt(9) + 1);
        for (int i = 0; i < max; ++i) {
            sb.append(random.nextInt(10));
        }
        return Long.valueOf(sb.toString());
    }

    public static void SearchInTABLE(JFXTextField source, TableView tv, String table, String clm, int size) {
        data = FXCollections.observableArrayList();
        try {
            ResultSet rs = inst2(source.getText(), table, clm);
            while (rs.next()) {
                ObservableList row = FXCollections.observableArrayList();
                for (int i = 1; i <= size; ++i) {
                    row.add((Object) rs.getString(i));
                }
                data.add((Object) row);
            }
            tv.setItems(data);
        } catch (SQLException ex) {
            Logger.getLogger(myFunctionsPP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void fillTable(String table, TableView tv, int size) {
        try {
            data = FXCollections.observableArrayList();
            int r = -1;
            ResultSet rst = inst(table);
            while (rst.next()) {
                ObservableList row = FXCollections.observableArrayList();
                for (int i = 1; i <= size; ++i) {
                    row.add((Object) ("" + rst.getString(i)));
                }
                data.add((Object) row);
            }
            tv.setItems(data);
        } catch (SQLException ex) {
            Logger.getLogger(myFunctionsPP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void fillTableWithConditionDESCENDING(String table, String clm, String id, TableView tv, int size, TableColumn myclm) {
        try {
            data = FXCollections.observableArrayList();
            ResultSet rst = inst3(table, clm, id);
            while (rst.next()) {
                ObservableList row = FXCollections.observableArrayList();
                for (int i = 1; i <= size; ++i) {
                    row.add((Object) ("" + rst.getString(i)));
                }
                data.add((Object) row);
            }
            tv.setItems(data);
            myclm.setSortType(TableColumn.SortType.DESCENDING);
            tv.getSortOrder().add(myclm);
            tv.sort();
        } catch (SQLException ex) {
            Logger.getLogger(myFunctionsPP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void fillTableWithConditionASCENDING(String table, String clm, String id, TableView tv, int size, TableColumn myclm) {
        try {
            data = FXCollections.observableArrayList();
            ResultSet rst = inst3(table, clm, id);
            while (rst.next()) {
                ObservableList row = FXCollections.observableArrayList();
                for (int i = 1; i <= size; ++i) {
                    row.add((Object) ("" + rst.getString(i)));
                }
                data.add((Object) row);
            }
            tv.setItems(data);
            myclm.setSortType(TableColumn.SortType.ASCENDING);
            tv.getSortOrder().add(myclm);
            tv.sort();
        } catch (SQLException ex) {
            Logger.getLogger(myFunctionsPP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //FilltablewithoutSorting
    public static void fillTableWithCondition(String table, String clm, String id, TableView tv, int size) {
        try {
            data = FXCollections.observableArrayList();
            ResultSet rst = inst3(table, clm, id);
            while (rst.next()) {
                ObservableList row = FXCollections.observableArrayList();
                for (int i = 1; i <= size; ++i) {
                    row.add((Object) ("" + rst.getString(i)));
                }
                data.add((Object) row);
            }
            tv.setItems(data);
        } catch (SQLException ex) {
            Logger.getLogger(myFunctionsPP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public static void deletefromtable(String id, String table, String where, StackPane rootS, AnchorPane rootAn, String item) {
        passe = 0;
        JFXButton btnyes = new JFXButton("Oui");
        JFXButton btnno = new JFXButton("Non");
        btnyes.addEventHandler(MouseEvent.MOUSE_CLICKED, event1 -> {
            delete(id, table, where);
            if (passe == 1) {
                JFXButton button = new JFXButton("Terminer!");
                AlertMaker.showMaterialDialog(rootS, (Node) rootAn, Arrays.asList(new JFXButton[]{button}), null, item + " Bien Supprim\u00e9");
            } else {
                JFXButton btn = new JFXButton("OK");
                AlertMaker.showMaterialDialogError(rootS, (Node) rootAn, Arrays.asList(new JFXButton[]{btn}), null, "ERREUR DE SUPPRESSION");
                btn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseevent -> {
                });
            }
        });
        btnno.addEventHandler(MouseEvent.MOUSE_CLICKED, event1 -> {
            JFXButton button = new JFXButton("OK");
            AlertMaker.showMaterialDialog(rootS, (Node) rootAn, Arrays.asList(new JFXButton[]{button}), null, "Suppression annul\u00e9e");
        });
        AlertMaker.showMaterialDialog(rootS, (Node) rootAn, Arrays.asList(new JFXButton[]{btnyes, btnno}), "Confirmation", "Voulez vous vraiment supprimer cet " + item + "  de fa\u00e7on permanente ?");
    }

    public static void sizeClm(TableColumn myClm, int i) {
        myClm.setMaxWidth((double) (2.14748365E9f * (float) i));
    }

    public static void tablePolicy(TableView myTable) {
        myTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public static Object SelectedItemTableViewSecondMethod(TableView myTable, int index) {
        ObservableList selectedCells = myTable.getSelectionModel().getSelectedCells();
        TablePosition tablePosition = (TablePosition) selectedCells.get(index);
        Object val = tablePosition.getTableColumn().getCellData(tablePosition.getRow());
        return val;
    }

    public static void emptyTextfield(JFXTextField mytext) {
        mytext.setText(null);
    }

    public static void fillComboline(JFXComboBox mycombo, String fromTable, String clm, ObservableList opt) {
        if (opt == null) {
            opt = FXCollections.observableArrayList();
        }

        try {
            ResultSet rst = fillCombo(fromTable);
            while (rst.next()) {
                String newItem = rst.getString(clm);
                if (!opt.contains(newItem)) {
                    opt.add(newItem);
                }
            }

            Set<Object> hashSet = new LinkedHashSet(opt);
            List<String> list = new ArrayList(hashSet);
            opt = FXCollections.observableList(list);
            mycombo.setItems(opt);
            AutoCompleteComboBoxListener<Object> autoCompleteComboBoxListener = new AutoCompleteComboBoxListener<>(mycombo);

        } catch (SQLException ex) {
            Logger.getLogger(myFunctionsPP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void fillCombolineAddString(JFXComboBox mycombo, String fromTable, String clm, ObservableList opt) {
        opt = FXCollections.observableArrayList();
        mycombo.getItems().clear();
        opt.add("TOUS");
        try {
            ResultSet rst = fillCombo(fromTable);
            while (rst.next()) {
                opt.add((Object) rst.getString(clm));
                //mycombo.setItems(opt);
            }

            Set<Object> hashSet = new LinkedHashSet(opt);

            List<String> list = new ArrayList(hashSet);
            opt = FXCollections.observableList(list);
            AutoCompleteComboBoxListener<Object> autoCompleteComboBoxListener = new AutoCompleteComboBoxListener<>(mycombo);

            mycombo.setItems(opt);
            //System.out.println(opt);

        } catch (SQLException ex) {
            Logger.getLogger(myFunctionsPP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void fillcombo(List<String> items, JFXComboBox mycombo, String valueS) {
        mycombo.getItems().clear();
        mycombo.getItems().addAll(items);
        mycombo.setValue((Object) valueS);
    }

    public static void fillComboMonth(JFXComboBox<String> comboBox, String selectedMonth) {
        ObservableList<String> monthList = FXCollections.observableArrayList();

        for (int i = 1; i <= 12; i++) {
            monthList.add(String.valueOf(i));
        }

        comboBox.setItems(monthList);
        comboBox.setValue(selectedMonth);
    }

    public static void fillComboYear(int startYear, int endYear, JFXComboBox comboBox) {
        int currentYear = java.time.Year.now().getValue();

        if (startYear <= currentYear && endYear >= currentYear) {
            ObservableList<String> yearList = FXCollections.observableArrayList();
            for (int i = startYear; i <= endYear; i++) {
                yearList.add(String.valueOf(i));
            }

            comboBox.setItems(yearList);

            // Set the default value to the current year
            String selectedYear = String.valueOf(currentYear);
            if (yearList.contains(selectedYear)) {
                comboBox.setValue(selectedYear);
            } else {
                System.out.println("Current year not in the range, setting to the first year.");
                comboBox.setValue(yearList.get(0));
            }

            // Attach AutoCompleteComboBoxListener
            // AutoCompleteComboBoxListener<Object> autoCompleteComboBoxListener = new AutoCompleteComboBoxListener<>(comboBox);
        } else {
            System.out.println("Invalid year range!");
        }
    }

    public static Stage getStage(AnchorPane an) {
        return (Stage) an.getScene().getWindow();
    }

    public static String convertnumtocharmonths(int m) {
        String charname = null;
        if (m == 1) {
            charname = "Janvier";
        }
        if (m == 2) {
            charname = "Février";
        }
        if (m == 3) {
            charname = "Mars";
        }
        if (m == 4) {
            charname = "Avril";
        }
        if (m == 5) {
            charname = "Mai";
        }
        if (m == 6) {
            charname = "Juin";
        }
        if (m == 7) {
            charname = "Juillet";
        }
        if (m == 8) {
            charname = "Aout";
        }
        if (m == 9) {
            charname = "Septembre";
        }
        if (m == 10) {
            charname = "Octobre";
        }
        if (m == 11) {
            charname = "Novembre";
        }
        if (m == 12) {
            charname = "Decembre";
        }
        return charname;
    }

    public static int convertCaractointMonths(String m) {
        int charname = 0;
        if ("JANUARY".equals(m)) {
            charname = 1;
        }
        if ("FEBRUARY".equals(m)) {
            charname = 2;
        }
        if ("MARS".equals(m)) {
            charname = 3;
        }
        if ("APRIL".equals(m)) {
            charname = 4;
        }
        if ("MAY".equals(m)) {
            charname = 5;
        }
        if ("JUNE".equals(m)) {
            charname = 6;
        }
        if ("JULY".equals(m)) {
            charname = 7;
        }
        if ("AUGUST".equals(m)) {
            charname = 8;
        }
        if ("SEPTEMBER".equals(m)) {
            charname = 9;
        }
        if ("OCTOBER".equals(m)) {
            charname = 10;
        }
        if ("NOVEMBER".equals(m)) {
            charname = 11;
        }
        if ("DECEMBER".equals(m)) {
            charname = 12;
        }

        return charname;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static List outpoutstring(String filename) {
        List<String> list = new ArrayList<>();
        File file = new File(filename);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;
            while ((text = reader.readLine()) != null) {
                list.add(text);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException iOException) {
            }
        } catch (IOException iOException) {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException iOException1) {
            }
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException iOException) {
            }
        }
        System.out.println(list);
        return list;
    }


    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void writefile(String filename, String content, StackPane rootStack, AnchorPane rootAnchor) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(filename, "UTF-8");
            writer.print(content);
            writer.close();
        } catch (FileNotFoundException | java.io.UnsupportedEncodingException ex) {
            Logger.getLogger(myFunctionsPP.class.getName()).log(Level.SEVERE, (String) null, ex);
            JFXButton btn = new JFXButton("OK");
            AlertMaker.showMaterialDialogError(rootStack, (Node) rootAnchor, Arrays.asList(new JFXButton[]{btn}), "ERREUR D'AJOUT", ex + "");
            btn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseevent -> {
            });
        } finally {
            writer.close();
        }
    }

    public static void searchForByCombo(JFXTextField txtSearch, JFXComboBox combosear, String table, int k, int j, TableView mytable, TableColumn myclm) {
        Object selectedItem = combosear.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            String where = selectedItem.toString();
            ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

            try {
                ResultSet rs = inst2(txtSearch.getText(), table, where);
                while (rs.next()) {
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for (int i = k; i <= j; ++i) {
                        row.add(rs.getString(i));
                    }
                    data.add(row);
                }

                mytable.setItems(data);
                myclm.setSortType(TableColumn.SortType.DESCENDING);
                mytable.getSortOrder().add(myclm);
                mytable.sort();
            } catch (SQLException ex) {
                Logger.getLogger(myFunctionsPP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void searchforbycomboTWOConditions(JFXTextField txtSearch, JFXComboBox combosear, String table, int j, TableView mytable, TableColumn myclm, String clm2, String myclm2) {
        String where = null;
        where = combosear.getSelectionModel().getSelectedItem().toString();
        data = FXCollections.observableArrayList();
        try {
            // ResultSet rs = inst2(txtSearch.getText(), table, where);
            ResultSet rs = myConnectionPP.SearchTwoConditions(txtSearch.getText(), table, "Ordre", "Valider", "oui");
            while (rs.next()) {
                ObservableList row = FXCollections.observableArrayList();
                for (int i = 1; i <= j; ++i) {
                    row.add((Object) rs.getString(i));
                }
                data.add((Object) row);
            }
            mytable.setItems(data);
            mytable.setItems(data);
            myclm.setSortType(TableColumn.SortType.DESCENDING);
            mytable.getSortOrder().add(myclm);
            mytable.sort();
        } catch (SQLException ex) {
            Logger.getLogger(myFunctionsPP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void OnlyIntegersForTextField(JFXTextField integerTextField) {
        TextFormatter<Integer> integerTextFormatter = new TextFormatter<>(new IntegerStringConverter(), null, change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null; // Reject the change if it's not a digit
        });

        // Apply the TextFormatter to the JFXTexfield
        integerTextField.setTextFormatter(integerTextFormatter);
    }

    public static void fillTable2(ObservableList<Object> data, TableView<Object> table, String from, int initial, int finale) {
        try {
            data = FXCollections.observableArrayList();
            int r = -1;
            ResultSet rst = inst(from);
            while (rst.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = initial; i <= finale; i++) {
                    row.add("" + rst.getString(i));
                }
                data.add(row);
            }
            table.setItems(data);
            // table.getSortOrder().add(e)
        } catch (SQLException ex) {
            Logger.getLogger(myFunctionsPP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void fillTableSortedByIDASCENDING(ObservableList<Object> data, TableView<Object> table, String from, int initial, int finale, TableColumn myclm) {
        try {
            data = FXCollections.observableArrayList();
            int r = -1;
            ResultSet rst = inst(from);
            while (rst.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = initial; i <= finale; i++) {
                    row.add("" + rst.getString(i));
                }
                data.add(row);
            }
            table.setItems(data);
            myclm.setSortType(TableColumn.SortType.ASCENDING);
            table.getSortOrder().add(myclm);
            table.sort();
            // table.getSortOrder(myclm.setSortType(TableColumn.SortType.ASCENDING));
        } catch (SQLException ex) {
            Logger.getLogger(myFunctionsPP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void fillTableSortedByIDDESCENDING(ObservableList<Object> data, TableView<Object> table, String from, int initial, int finale, TableColumn myclm) {
        try {
            data = FXCollections.observableArrayList();
            int r = -1;
            ResultSet rst = inst(from);
            while (rst.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = initial; i <= finale; i++) {
                    row.add("" + rst.getString(i));
                }
                data.add(row);
            }
            table.setItems(data);
            myclm.setSortType(TableColumn.SortType.DESCENDING);
            table.getSortOrder().add(myclm);
            table.sort();
            // table.getSortOrder(myclm.setSortType(TableColumn.SortType.ASCENDING));
        } catch (SQLException ex) {
            Logger.getLogger(myFunctionsPP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static TableColumn SortTypeClm(TableColumn myclm) {

        return myclm;
    }

    public static void fillculms(TableColumn myclm, final int i) {
        myclm.setCellValueFactory((Callback) new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {

            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                return new SimpleStringProperty(((ObservableList) param.getValue()).get(i).toString());
            }
        });
    }

    public static void setClm(TableColumn myClm, final int i, final String plus) {
        myClm.setCellValueFactory((Callback) new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {

            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                return new SimpleStringProperty(((ObservableList) param.getValue()).get(i).toString() + plus);
            }
        });
    }

    public static void fillComboEqui(JFXComboBox mycombo, String select, String from, String where, JFXComboBox myclm, ObservableList opt, String dateperemp) {
        opt = FXCollections.observableArrayList();
        mycombo.getItems().clear();
        String myClm = getStringfromCombo(myclm);
        try {
            //ResultSet rst = fillCombo2(fromtable, where, myClm);
            ResultSet rst = fillComboWithConditionwithoutDuplicatedData(select, from, where, myClm, dateperemp);
            while (rst.next()) {
                opt.add((Object) rst.getString(select));
                mycombo.setItems(opt);

                AutoCompleteComboBoxListener<Object> autoCompleteComboBoxListener = new AutoCompleteComboBoxListener<>(mycombo);
                // System.out.println("The Option containt " + opt);
            }
            sizeofoption = opt.size();
            // System.out.println("The size of the Option is "+sizeofoption);
        } catch (SQLException ex) {
            Logger.getLogger(myFunctionsPP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void fillComboWithDuplicatedData(JFXComboBox mycombo, String select, String from, String where, JFXComboBox myclm, ObservableList opt, String dateperemp) {
        opt = FXCollections.observableArrayList();
        mycombo.getItems().clear();
        String myClm = getStringfromCombo(myclm);
        try {
            //ResultSet rst = fillCombo2(fromtable, where, myClm);
            ResultSet rst = fillComboWithConditionDuplicatedDataAndSorted(select, from, where, myClm, dateperemp);
            while (rst.next()) {
                opt.add((Object) rst.getString(select));
                mycombo.setItems(opt);

                // System.out.println("The Option containt " + opt);
            }
            // Move AutoCompleteComboBoxListener outside the loop
            AutoCompleteComboBoxListener<Object> autoCompleteComboBoxListener = new AutoCompleteComboBoxListener<>(mycombo);
            sizeofoption = opt.size();
            // System.out.println("The size of the Option is "+sizeofoption);
        } catch (SQLException ex) {
            Logger.getLogger(myFunctionsPP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void ajfillCombo(String ss, ObservableList opt) {
        opt.add((Object) ss);
    }

    public static void opacitynullfortext(List<JFXTextField> controls) {
        if (controls.isEmpty()) {
            controls.add(new JFXTextField("Okay"));
        }
        controls.forEach(controlText -> controlText.setOpacity(0.0));
    }

    public static void opacitynullfortables(List<TableView> controls) {
        if (controls.isEmpty()) {
            controls.add(new TableView());
        }
        controls.forEach(controlText -> controlText.setOpacity(0.0));
    }

    public static String getStringfromCombo(ComboBox mycombo) {
        if (mycombo.getSelectionModel().isEmpty()) {
            return null; // or any other suitable action or value for an empty ComboBox
        } else {
            String result = mycombo.getSelectionModel().getSelectedItem().toString();
            return result;
        }
    }

    public static void opacitynullforbuttons(List<JFXButton> controls) {
        if (controls.isEmpty()) {
            controls.add(new JFXButton("Okay"));
        }
        controls.forEach(controlText -> controlText.setOpacity(0.0));
    }

    public static void testQt(StackPane rootStackPane, AnchorPane rootAnchorPane, JFXButton btn, int oldqt, int myqt, int newqt) {
        newqt = oldqt - myqt;
        if (newqt == 0) {
            AlertMaker.showMaterialDialogAvertissemnt(rootStackPane, (Node) rootAnchorPane, Arrays.asList(new JFXButton[]{btn}), "Avertissement", "Attention Rupture de Stock");
        }
    }

    static public void fillComboDes(JFXComboBox mycombo, String element, String fromTable, ObservableList option3) {
        option3 = FXCollections.observableArrayList();
        mycombo.getItems().clear();
        try {
            ResultSet rst = fillCombo(element, fromTable);
            while (rst.next()) {
                option3.add(rst.getString(element));
                mycombo.setItems(option3);
                AutoCompleteComboBoxListener<Object> autoCompleteComboBoxListener = new AutoCompleteComboBoxListener<>(mycombo);

            }
        } catch (SQLException ex) {
            Logger.getLogger(myFunctionsPP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void fillComboData(JFXComboBox mycombo, String fromTable, String clm) {
        opt = null;
        opt = FXCollections.observableArrayList();
        mycombo.getItems().clear();

        try {
            ResultSet rst = fillComboDISTINCT(fromTable, clm);

            while (rst.next()) {
                opt.add((Object) rst.getString(clm));
            }

            mycombo.setItems(opt);
            AutoCompleteComboBoxListener<Object> autoCompleteComboBoxListener = new AutoCompleteComboBoxListener<>(mycombo);

        } catch (SQLException ex) {
            Logger.getLogger(myFunctionsPP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void fillComboDataWithStringAdded(JFXComboBox mycombo, String fromTable, String clm) {
        opt = null;
        opt = FXCollections.observableArrayList();
        mycombo.getItems().clear();
        try {
            ResultSet rst = fillCombo(fromTable);
            opt.add("TOUS");
            while (rst.next()) {
                opt.add((Object) rst.getString(clm));
                mycombo.setItems(opt);
                AutoCompleteComboBoxListener<Object> autoCompleteComboBoxListener = new AutoCompleteComboBoxListener<>(mycombo);

            }
        } catch (SQLException ex) {
            Logger.getLogger(myFunctionsPP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void fillComboDataWithoutDuplicateData(JFXComboBox mycombo, String fromTable, String clm) {
        opt = null;
        opt = FXCollections.observableArrayList();
        mycombo.getItems().clear();
        try {
            ResultSet rst = fillComboWithoutDuplicateData(fromTable, clm);
            while (rst.next()) {
                opt.add((Object) rst.getString(clm));
                mycombo.setItems(opt);
                AutoCompleteComboBoxListener<Object> autoCompleteComboBoxListener = new AutoCompleteComboBoxListener<>(mycombo);

            }
        } catch (SQLException ex) {
            Logger.getLogger(myFunctionsPP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void fillComboDataWithSetedValue(JFXComboBox mycombo, String fromTable, String clm, int k) {
        opt = null;
        opt = FXCollections.observableArrayList();
        mycombo.getItems().clear();
        try {
            ResultSet rst = fillCombo(fromTable);
            while (rst.next()) {
                opt.add((Object) rst.getString(clm));
                mycombo.setItems(opt);
                AutoCompleteComboBoxListener<Object> autoCompleteComboBoxListener = new AutoCompleteComboBoxListener<>(mycombo);

            }
            mycombo.setValue(opt.get(k));

        } catch (SQLException ex) {
            Logger.getLogger(myFunctionsPP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //This method is to show a new buton for the DATE DE PEROMP De chaque LOT just try :) 
    public static void showmethedateOfPeremp(JFXComboBox comboProduit, JFXComboBox comboNLOT, StackPane rootStack, AnchorPane rootAnchor) {
        String produit = getStringfromCombo(comboProduit);
        String nlot = getStringfromCombo(comboNLOT);
        String datedeper = SelectStringFromTableWhereClmIs("stock", "DCI", produit, "DatePerom", "NLOT", nlot);
        System.out.println("la date de peromp est " + datedeper);
        JFXButton btn = new JFXButton("Merci ! ");
        showMaterialDialogAvertissemnt(rootStack, (Node) rootAnchor, Arrays.asList(new JFXButton[]{btn}), "DATE PEREPEMPTION ", "La Date Peremption du " + produit + " N° LOT " + nlot + " est: " + datedeper);
    }

    public static boolean isStringNull(String str) {

        if (str == null) {
            return true;
        } else {
            return false;
        }
    }
}
