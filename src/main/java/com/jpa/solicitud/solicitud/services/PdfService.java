package com.jpa.solicitud.solicitud.services;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.jpa.solicitud.solicitud.models.entities.Solicitud;
import com.jpa.solicitud.solicitud.repositories.ISolicitudRespository;

@Service
public class PdfService {

    @Autowired
    private ISolicitudRespository solicitudRepository;

    public ByteArrayInputStream generatePdf() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // Obtener las solicitudes de la base de datos
        List<Solicitud> solicitudes = solicitudRepository.findAll();

        // Añadir contenido al documento
        for (Solicitud solicitud : solicitudes) {
            document.add(new Paragraph("Solicitud ID: " + solicitud.getId()));
            document.add(new Paragraph("Nombre: " + solicitud.getMotivo()));
        
            document.add(new Paragraph("\n"));
        }

        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    }
}
