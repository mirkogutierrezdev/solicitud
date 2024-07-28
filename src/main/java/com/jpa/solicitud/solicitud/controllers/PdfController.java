package com.jpa.solicitud.solicitud.controllers;

import com.jpa.solicitud.solicitud.models.dto.PdfDto;
import com.jpa.solicitud.solicitud.services.JsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    @Autowired
    private JsonService jsonService;

    @PostMapping("/generate")
    public ResponseEntity<InputStreamResource> generatePdf(@RequestBody PdfDto pdfDto) throws Exception {
        byte[] pdfBytes = jsonService.generateReport(pdfDto);

        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(pdfBytes));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=generated.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}
