package tools;

import java.awt.print.PrinterException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;




public class PDFPrintingExample {

    public static void main(String[] args) {
        printPDF("path/to/your/file.pdf");
    }
        public static void printPDF(String pdfFilePath) {
        try (PDDocument document = PDDocument.load(new File(pdfFilePath))) {
            // Create a PrinterJob
            PrinterJob job = PrinterJob.getPrinterJob();
            // Get the default print service
            PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
            // Set the print service to the default print service
            job.setPrintService(printService);
            // Set up the printable object with the document
            job.setPageable(new PDFPageable(document));
            // Print the document
            job.print();
        } catch (IOException | PrinterException e) {
            e.printStackTrace();
        }
    }

}
