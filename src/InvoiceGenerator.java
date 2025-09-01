import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;

public class InvoiceGenerator {
    public static void generate(String customerName, String item, double amount, String fileName) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            document.add(new Paragraph("INVOICE", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLUE)));
            document.add(new Paragraph("Customer: " + customerName));
            document.add(new Paragraph(" ")); // Spacer

            PdfPTable table = new PdfPTable(2);
            table.addCell("Item");
            table.addCell("Amount");

            table.addCell(item);
            table.addCell("₹" + String.format("%.2f", amount));

            document.add(table);
            document.add(new Paragraph("Total Amount: ₹" + String.format("%.2f", amount)));
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}