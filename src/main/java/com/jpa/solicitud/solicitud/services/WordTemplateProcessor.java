package com.jpa.solicitud.solicitud.services;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class WordTemplateProcessor {

    // Método principal para combinar la plantilla con los datos
    public void processTemplate(String inputFilePath, String outputFilePath, Map<String, String> data) {
        try {
            // Cargar la plantilla
            FileInputStream fis = new FileInputStream(inputFilePath);
            XWPFDocument document = new XWPFDocument(fis);

            // Reemplazar los placeholders con los datos
            replacePlaceholders(document, data);

            // Guardar el documento final
            FileOutputStream fos = new FileOutputStream(outputFilePath);
            document.write(fos);

            fos.close();
            fis.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para reemplazar los placeholders en el documento
    private void replacePlaceholders(XWPFDocument document, Map<String, String> data) {
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            List<XWPFRun> runs = paragraph.getRuns();
            if (runs != null) {
                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                    if (text != null) {
                        for (Map.Entry<String, String> entry : data.entrySet()) {
                            // Reemplazar el placeholder con el valor correspondiente
                            text = text.replace(entry.getKey(), entry.getValue());
                        }
                        run.setText(text, 0); // Actualizar el texto en el run
                    }
                }
            }
        }
    }
}
