package com.jpa.solicitud.solicitud.services;

import java.io.ByteArrayInputStream;
import java.util.Optional;

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

    public ByteArrayInputStream generatePdf(Long solicitudId) {

        Optional<Solicitud> solicitud = solicitudRepository.findById(solicitudId);

        if(solicitud.isPresent()){
            Solicitud solicitudData = solicitud.get();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("Solicitud ID: " + solicitudData.getId()));
            document.add(new Paragraph("Motivo: " + solicitudData.getMotivo()));
            document.add(new Paragraph("Fecha de inicio: " + solicitudData.getFechaInicio()));
            document.add(new Paragraph("Fecha de término: " + solicitudData.getFechaFin()));
            document.add(new Paragraph("Estado: " + solicitudData.getEstado().getNombre()));
            document.add(new Paragraph("Funcionario: " + solicitudData.getFuncionario().getNombre()));
            document.add(new Paragraph("Tipo de Solicitud: " + solicitudData.getTipoSolicitud().getNombre()));


            document.close();
            return new ByteArrayInputStream(out.toByteArray());
        } else {
            return null;
        }


    }
}
