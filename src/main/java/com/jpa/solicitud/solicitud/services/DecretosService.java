package com.jpa.solicitud.solicitud.services;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jpa.solicitud.solicitud.models.dto.DecretosDto;
import com.jpa.solicitud.solicitud.models.entities.Aprobacion;
import com.jpa.solicitud.solicitud.models.entities.Decretos;
import com.jpa.solicitud.solicitud.repositories.IAprobacionRepository;
import com.jpa.solicitud.solicitud.repositories.IDecretosRepository;

import jakarta.transaction.Transactional;

@Service
public class DecretosService {

    private final IDecretosRepository decretoRepository;

    private final IAprobacionRepository aprobacionRepository;
    private final WordTemplateProcessor  wordTemplateProcessor;

    public DecretosService(IDecretosRepository decretosRepository, IAprobacionRepository aprobacionRepository, WordTemplateProcessor wordTemplateProcessor) {
        this.decretoRepository = decretosRepository;
        this.aprobacionRepository = aprobacionRepository;
        this.wordTemplateProcessor = wordTemplateProcessor;
    }

     @Transactional
    public Decretos crearDecreto(DecretosDto decretoDTO) {
        // 1. Crear un nuevo Decreto
        Decretos decreto = new Decretos();
        LocalDate fechaDecreto = LocalDate.now();
        decreto.setNroDecreto(decretoDTO.getNroDecreto());
        decreto.setFechaDecreto(fechaDecreto);

        // 2. Obtener las aprobaciones por sus IDs
        List<Aprobacion> aprobaciones = aprobacionRepository.findAllById(decretoDTO.getAprobacionesIds());

        // 3. Asociar las aprobaciones al decreto
        for (Aprobacion aprobacion : aprobaciones) {
            aprobacion.setDecreto(decreto); // Relacionar la aprobación con el decreto
        }

        // 4. Guardar el decreto (esto también guardará las aprobaciones si están configuradas con cascade)
        Decretos savedDecreto = decretoRepository.save(decreto);

        // 5. Guardar las aprobaciones actualizadas
        aprobacionRepository.saveAll(aprobaciones);

        // 6. Generar el documento Word después de guardar el decreto
        generarDocumentoWord(savedDecreto, aprobaciones);

        return savedDecreto; // Devolver el decreto guardado si es necesario
    }

    // Método para generar el documento Word
    private void generarDocumentoWord(Decretos decreto, List<Aprobacion> aprobaciones) {
        // Definir la ruta de la plantilla y la salida
        String inputFilePath = "C:\\Users\\mgutierrez\\Documents\\Development\\Spring\\solicitud\\solicitud\\src\\main\\java\\com\\jpa\\solicitud\\solicitud\\templates\\template.docx"; // Ruta de la plantilla
        String outputFilePath = "C:\\Users\\mgutierrez\\Documents\\Development\\Spring\\solicitud\\solicitud\\src\\main\\java\\com\\jpa\\solicitud\\solicitud\\templates\\decreto_" + decreto.getNroDecreto() + ".docx"; // Nombre del documento generado

        // Crear el mapa con los datos que queremos combinar en el documento
        Map<String, String> data = new HashMap<>();
        data.put("{{NRO_DECRETO}}", String.valueOf(decreto.getNroDecreto()));
        data.put("{{FECHA_DECRETO}}", decreto.getFechaDecreto().toString());

        // Puedes agregar más datos si es necesario, por ejemplo, información de aprobaciones
        // Aquí agregamos las aprobaciones como texto concatenado
        StringBuilder aprobacionesTexto = new StringBuilder();
        for (Aprobacion aprob : aprobaciones) {
            aprobacionesTexto.append("ID Aprobación: ").append(aprob.getId()).append(", Funcionario: ")
                    .append(aprob.getSolicitud().getFuncionario().getNombre()).append("\n");
        }
        data.put("{{APROBACIONES}}", aprobacionesTexto.toString());

        // Llamar al servicio para generar el documento
        wordTemplateProcessor.processTemplate(inputFilePath, outputFilePath, data);
    }

    public Decretos getDecretos(Long id){
        return decretoRepository.findDecretosWithAprobacion(id);
    }
}
