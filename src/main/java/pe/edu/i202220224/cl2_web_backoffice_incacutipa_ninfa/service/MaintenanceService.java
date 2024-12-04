package pe.edu.i202220224.cl2_web_backoffice_incacutipa_ninfa.service;

import pe.edu.i202220224.cl2_web_backoffice_incacutipa_ninfa.dto.FilmDetailDto;
import pe.edu.i202220224.cl2_web_backoffice_incacutipa_ninfa.dto.FilmDto;
import pe.edu.i202220224.cl2_web_backoffice_incacutipa_ninfa.dto.FilmInsertDto;
import pe.edu.i202220224.cl2_web_backoffice_incacutipa_ninfa.dto.LanguageDto;

import java.util.List;

public interface MaintenanceService {

    List<FilmDto> findAllFilms();
    FilmDetailDto findFilmById(int id); //buscar por el id

    Boolean updateFilm(FilmDetailDto filmDetailDto);

    // Nuevo método para obtener los lenguajes
    List<LanguageDto> findAllLanguages();

    //----------------------INSERCION DE IMPLEMENTACION PARA EL REGISTRO
    Boolean addFilm(FilmInsertDto filmInsertDto);

    Boolean deleteFilm(int id);

}
