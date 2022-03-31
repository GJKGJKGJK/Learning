package com.gjk.demo_lean.iceblue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;

/**
 * spiredoc
 *
 * @author: gaojiankang
 * @date: 2021/07/28/15:25
 * @description:
 */
public class spiredoc {


    public static void main(String[] args) throws FileNotFoundException {
        File file;
        try (FileInputStream fileInputStream = new FileInputStream("E:\\DownLoad\\4d754971e182055779a829c92b25160e.docx")) {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Document document = new Document(fileInputStream);
            document.acceptChanges();
            document.saveToStream(outputStream, FileFormat.Docx);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            System.out.println("11");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
