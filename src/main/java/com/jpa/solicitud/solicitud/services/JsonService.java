package com.jpa.solicitud.solicitud.services;

import com.aspose.words.Document;
import com.aspose.words.JsonDataSource;
import com.aspose.words.ReportingEngine;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpa.solicitud.solicitud.models.dto.PdfDto;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

@Service
public class JsonService {

    public byte[] generateReport(PdfDto jsonSol) throws Exception {

        String template = defineTemplate(jsonSol.getNombreSolicitud());

        Document doc = new Document(template);

        // Convertir el objeto a una cadena JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(jsonSol);

        // Crear un JsonDataSource a partir del string JSON
        ByteArrayInputStream jsonStream = new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8));
        JsonDataSource dataSource = new JsonDataSource(jsonStream);

        // Crear el objeto ReportingEngine
        ReportingEngine engine = new ReportingEngine();

        // Informe de compilación usando el JsonDataSource
        engine.buildReport(doc, dataSource, "managers");

        // Guardar como documento de PDF
        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        doc.save(pdfOutputStream, com.aspose.words.SaveFormat.PDF);

        return pdfOutputStream.toByteArray();
    }

    public String defineTemplate(String tipoSol){
        if(tipoSol.equalsIgnoreCase("ADMINISTRATIVO")){
            return "src/main/java/com/jpa/solicitud/solicitud/templates/templatePA.docx";
        }else{
            return "src/main/java/com/jpa/solicitud/solicitud/templates/templateFL.docx";
        }

    }
}
