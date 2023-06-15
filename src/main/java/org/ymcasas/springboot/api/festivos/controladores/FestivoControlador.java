package org.ymcasas.springboot.api.festivos.controladores;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ymcasas.springboot.api.festivos.servicios.FestivoServicio;

import java.time.LocalDate;

@RestController
@RequestMapping("/festivos")
public class FestivoControlador {

    private final FestivoServicio festivoServicio;

    public FestivoControlador(FestivoServicio festivoServicio) {
        this.festivoServicio = festivoServicio;
    }

    @GetMapping("/validar")
    public ResponseEntity<?> validarFestivo(@RequestParam("dia") int dia, @RequestParam("mes") int mes) {
        boolean esFestivo = festivoServicio.esFestivo(dia, mes);
        return ResponseEntity.ok(esFestivo);
    }

    @GetMapping("/calcular")
    public ResponseEntity<?> calcularFechaFestivo(@RequestParam("dia") int dia, @RequestParam("mes") int mes) {
        LocalDate fechaFestivo = festivoServicio.calcularFechaFestivo(dia, mes);
        if (fechaFestivo != null) {
            return ResponseEntity.ok(fechaFestivo);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fecha no válida");
        }
    }

}
