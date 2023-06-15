package org.ymcasas.springboot.api.festivos.servicios;

import org.springframework.stereotype.Service;
import org.ymcasas.springboot.api.festivos.modelos.Festivo;
import org.ymcasas.springboot.api.festivos.repositorios.FestivoRepositorio;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;

@Service
public class FestivoServicio {
    private final FestivoRepositorio festivoRepositorio;

    public FestivoServicio(FestivoRepositorio festivoRepositorio) {
        this.festivoRepositorio = festivoRepositorio;
    }

    public boolean esFestivo(int dia, int mes) {
        return festivoRepositorio.existsByDiaAndMes(dia, mes);
    }

    public LocalDate calcularFechaFestivo(int dia, int mes) {
        Optional<Festivo> optionalFestivo = festivoRepositorio.findByDiaAndMes(dia, mes);
        if (optionalFestivo.isPresent()) {
            Festivo festivo = optionalFestivo.get();
            if (festivo.getIdTipo().getId() == 1) {
                return LocalDate.of(LocalDate.now().getYear(), mes, dia);
            } else if (festivo.getIdTipo().getId() == 2) {
                return obtenerLunesSiguiente(dia, mes);
            } else if (festivo.getIdTipo().getId() == 3) {
                return calcularFechaBasadaEnPascua(dia, mes, festivo.getDiasPascua());
            } else if (festivo.getIdTipo().getId() == 4) {
                LocalDate fechaCalculada = calcularFechaBasadaEnPascua(dia, mes, festivo.getDiasPascua());
                return obtenerLunesSiguiente(fechaCalculada.getDayOfMonth(), fechaCalculada.getMonthValue());
            }
        }
        return null;
    }

    private LocalDate obtenerLunesSiguiente(int dia, int mes) {
        LocalDate fecha = LocalDate.of(LocalDate.now().getYear(), mes, dia);
        if (fecha.getDayOfWeek() != DayOfWeek.MONDAY) {
            fecha = fecha.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        }
        return fecha;
    }

    private LocalDate calcularFechaBasadaEnPascua(int dia, int mes, int diasAgregar) {
        int year = LocalDate.now().getYear();
        LocalDate fechaPascua = calcularFechaPascua(year);
        return fechaPascua.plusDays(diasAgregar);
    }

    private LocalDate calcularFechaPascua(int year) {
        int a = year % 19;
        int b = year % 4;
        int c = year % 7;
        int d = (19 * a + 24) % 30;
        int dias = d + (2 * b + 4 * c + 6 * d + 5) % 7;
        LocalDate fechaMarzo = LocalDate.of(year, Month.MARCH, 15);
        return fechaMarzo.plusDays(dias);
    }

}
