package fr.aprevot.grandoralcrypto;

import com.github.miachm.sods.Sheet;
import com.github.miachm.sods.SpreadSheet;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main{

    public static void main(String[] args) {
        try {



            // Creating pdf report
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("gdoral-report-" + formatter.format(LocalDate.now())));


            SpreadSheet data = new SpreadSheet(new File("java_ods.ods"));

            DecimalFormat df = new DecimalFormat("###.#####");

            Sheet sheet = data.getSheet(0);

            double opening = 0;
            double closing = 0;
            int priceDown = 0;
            int priceDownTime = 0;

            double evolution = 0;

            boolean 

            for (int i = 1; i < sheet.getMaxRows(); i++) {
                opening = Double.parseDouble(sheet.getRange(i, 1).getValue().toString());
                closing = Double.parseDouble(sheet.getRange(i, 4).getValue().toString());

                evolution = (closing - opening) / opening * 100;

                System.out.printf("%-32s %s\n", "Date :", sheet.getRange(i, 0).getValue().toString());
                System.out.printf("%-32s %s %s\n", "Price evolution :" , evolution > 0 ? "+" + df.format(evolution) : df.format(evolution), "%");

                if(opening > closing) {
                    priceDown = priceDown + 1;
                    priceDownTime = priceDownTime + 1;
                    System.out.printf("%-32s %s\n", "Price went down :", priceDownTime + " consecutive times.");
                } else {
                    priceDownTime = 0;
                }

                System.out.println("\n");

            }

            System.out.println("\nPrice went down " + priceDown + " times.");



        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }

}
