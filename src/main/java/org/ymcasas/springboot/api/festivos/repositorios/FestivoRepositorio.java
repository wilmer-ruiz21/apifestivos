package org.ymcasas.springboot.api.festivos.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ymcasas.springboot.api.festivos.modelos.Festivo;

import java.util.Optional;

@Repository
public interface FestivoRepositorio extends JpaRepository<Festivo, Long> {

    boolean existsByDiaAndMes(int dia, int mes);
    Optional<Festivo> findByDiaAndMes(int dia, int mes);

}
