import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Customer Name: ");
        String customerName = sc.nextLine();

        java.util.List<String> items = new ArrayList<>();
        java.util.List<Double> prices = new ArrayList<>();

        while (true) {
            System.out.print("Enter Item Name (or type 'done' to finish): ");
            String item = sc.nextLine();
            if (item.equalsIgnoreCase("done")) break;

            System.out.print("Enter Price for " + item + ": ");
            double price = sc.nextDouble();
            sc.nextLine(); // Consume newline

            items.add(item);
            prices.add(price);
        }

        int invoiceId = DatabaseConnector.saveInvoice(customerName, items, prices);
        generatePDF(customerName, items, prices, invoiceId);

        sc.close();
    }

    private static void generatePDF(String customerName, java.util.List<String> items, java.util.List<Double> prices, int invoiceId) {
        try {
            com.itextpdf.text.Document document = new com.itextpdf.text.Document();
            com.itextpdf.text.pdf.PdfWriter.getInstance(document, new java.io.FileOutputStream("Invoice_" + customerName + ".pdf"));
            document.open();

            document.add(new com.itextpdf.text.Paragraph("Invoice ID: " + invoiceId));
            document.add(new com.itextpdf.text.Paragraph("Customer Name: " + customerName));
            document.add(new com.itextpdf.text.Paragraph(" "));

            com.itextpdf.text.pdf.PdfPTable table = new com.itextpdf.text.pdf.PdfPTable(2);
            table.addCell("Item");
            table.addCell("Price");

            double total = 0;
            for (int i = 0; i < items.size(); i++) {
                table.addCell(items.get(i));
                table.addCell(String.valueOf(prices.get(i)));
                total += prices.get(i);
            }

            table.addCell("Total");
            table.addCell(String.valueOf(total));

            document.add(table);
            document.close();

            System.out.println("Invoice generated and saved as: Invoice_" + customerName + ".pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
