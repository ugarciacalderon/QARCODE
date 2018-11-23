package com.example.usuario.control_vehicular;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class TemplatePDF {
    private Context context;
    private File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;
    private Font fTitle = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
    private Font fSubtTitle = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private Font fText = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private Font fHighText = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD, BaseColor.DARK_GRAY);
    private Image image;
    File folder = new File(Environment.getExternalStorageDirectory().toString(), "PDF");


    public TemplatePDF(Context context) {

        this.context = context;
    }

    public void openDocument(String nombre) {
        createFile(nombre);
        try {
            document = new Document(PageSize.A4);
            pdfWriter = pdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();

        } catch (Exception e) {
            Log.e("openDocument", e.toString());
        }

    }

    private void createFile(String nombre) {


        if (!folder.exists())

        {
            folder.mkdir();

        }
        pdfFile = new File(folder, nombre.trim() + ".PDF");
    }

    public void closeDocument() {
        document.close();
    }

    public void addMetaData(String title, String subject, String author) {
        document.addTitle(title);
        document.addSubject(subject);
        document.addAuthor(author);


    }

    public void addTitles(String title, String subTitle, String date) {
        paragraph = new Paragraph();
        addChildP(new Paragraph(title, fTitle));
        addChildP(new Paragraph(subTitle, fSubtTitle));
        addChildP(new Paragraph("Generado: " + date, fHighText));
        paragraph.setSpacingAfter(30);
        try {
            document.add(paragraph);
        } catch (Exception e) {
            Log.e("addTitles", e.toString());
        }

    }

    private void addChildP(Paragraph childParagraph) {
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);
    }

    public void addParagraph(String text) {
        try {
            paragraph = new Paragraph(text, fText);
            paragraph.setSpacingAfter(5);
            paragraph.setSpacingBefore(5);
            document.add(paragraph);
        } catch (Exception e) {
            Log.e("addParagraph", e.toString());
        }
    }

    public void createTable(String[] header, ArrayList<String[]> clients) {
        try {
            paragraph = new Paragraph();
            paragraph.setFont(fText);
            PdfPTable pdfPTable = new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(100);
            PdfPCell pdfPCell;
            pdfPTable.setSpacingBefore(20);

            int indexC = 0;
            while (indexC < header.length) {

                pdfPCell = new PdfPCell(new Phrase(header[indexC++], fSubtTitle));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
              //  pdfPCell.setBackgroundColor(BaseColor.BLUE);

                pdfPTable.addCell(pdfPCell);
            }

            for (int indexR = 0; indexR < clients.size(); indexR++) {

                String[] row = clients.get(indexR);
                for (indexC = 0; indexC < header.length; indexC++) {

                    pdfPCell = new PdfPCell(new Phrase(row[indexC]));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfPCell.setFixedHeight(40);
                    pdfPTable.addCell(pdfPCell);

                }
            }
            paragraph.add(pdfPTable);
            document.add(paragraph);
        } catch (Exception e) {
            Log.e("createTable", e.toString());
        }
    }
/*
    public void viewPDF() {
        Intent intent = new Intent(context, ViewPDFAcitity.class);
        intent.putExtra("path", pdfFile.getAbsolutePath());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
*/
    public void appviewPDF(Fragment activity) {
        if (pdfFile.exists()) {
            Uri uri = Uri.fromFile(pdfFile);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "aplication/pdf");
            try {
                activity.startActivity(intent);
            } catch (Exception e) {
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.adobe.reader&hl=es_419")));
                Toast.makeText(activity.getActivity().getApplicationContext(), "No cuentas on una app", Toast.LENGTH_SHORT).show();

            }
        } else {

            Toast.makeText(activity.getActivity().getApplicationContext(), "Archivo no encontrado", Toast.LENGTH_SHORT).show();
        }
    }

        public void addImgName ( String imageName){
            //File folder2 = new File(Environment.getExternalStorageDirectory().toString(),"P");
            try{

                if (folder.exists())

                {

                    Image image = Image.getInstance(folder.getAbsolutePath() + File.separator + imageName);
                    image.setSpacingBefore(15);
                    image.setSpacingAfter(15);
                    image.scaleToFit(400,400);
                    image.setAlignment(Element.ALIGN_CENTER);
                    document.add(image);
                }

            }catch (Exception e){
                Log.e("addImgName ", e.toString());
            }


        }


    }