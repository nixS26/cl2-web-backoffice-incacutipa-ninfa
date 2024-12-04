package pe.edu.i202220224.cl2_web_backoffice_incacutipa_ninfa.service.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.i202220224.cl2_web_backoffice_incacutipa_ninfa.dto.FilmDetailDto;
import pe.edu.i202220224.cl2_web_backoffice_incacutipa_ninfa.dto.FilmDto;
import pe.edu.i202220224.cl2_web_backoffice_incacutipa_ninfa.dto.FilmInsertDto;
import pe.edu.i202220224.cl2_web_backoffice_incacutipa_ninfa.dto.LanguageDto;
import pe.edu.i202220224.cl2_web_backoffice_incacutipa_ninfa.entity.Film;
import pe.edu.i202220224.cl2_web_backoffice_incacutipa_ninfa.entity.Inventory;
import pe.edu.i202220224.cl2_web_backoffice_incacutipa_ninfa.entity.Language;
import pe.edu.i202220224.cl2_web_backoffice_incacutipa_ninfa.repository.FilmRepository;
import pe.edu.i202220224.cl2_web_backoffice_incacutipa_ninfa.repository.InventoryRepository;
import pe.edu.i202220224.cl2_web_backoffice_incacutipa_ninfa.repository.LanguageRepository;
import pe.edu.i202220224.cl2_web_backoffice_incacutipa_ninfa.repository.RentalRepository;
import pe.edu.i202220224.cl2_web_backoffice_incacutipa_ninfa.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    FilmRepository filmRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private RentalRepository rentalRepository;

    //-------------------IMPLEMENTACION PARA TRAER LA LISTA DE REGISTROS------------------------------------
    @Cacheable(value = "films", unless = "#result == null")
    @Override
    public List<FilmDto> findAllFilms() {

        List<FilmDto> films = new ArrayList<FilmDto>();
        //recuperar los elemtneos de una lista d efilms
        Iterable<Film> iterable = filmRepository.findAll();
        iterable.forEach(film -> {
            FilmDto filmDto = new FilmDto(film.getFilmId(),
                    film.getTitle(),
                    film.getLanguage().getName(),
                    film.getRentalDuration(),
                    film.getRentalRate());
            films.add(filmDto);
        });


        return films ;
    }

    //----------------------IMPLEMENTACION PARA TRAER EL DETALLE------------------------------------------
    @Override
    @Cacheable(value = "films", key = "#id", unless = "#result == null")
    public FilmDetailDto findFilmById(int id) {

        Optional<Film> optional = filmRepository.findById(id);
        return optional.map(
                film -> new FilmDetailDto(film.getFilmId(),
                        film.getTitle(),
                        film.getDescription(),
                        film.getReleaseYear(),
                        film.getLanguage().getLanguageId(),
                        film.getLanguage().getName(),
                        film.getRentalDuration(),
                        film.getRentalRate(),
                        film.getLength(),
                        film.getReplacementCost(),
                        film.getRating(),
                        film.getSpecialFeatures(),
                        film.getLastUpdate())
        ).orElse(null);

    }
    //----------------------IMPLEMENTACION PARA ACTUALIZAR REGISTROS-------------------------------------
    @CacheEvict(value = "films", allEntries = true)
    @Override
    public Boolean updateFilm(FilmDetailDto filmDetailDto) {
        Optional<Film> optional = filmRepository.findById(filmDetailDto.filmId());
        return optional.map(
                film -> {
                    film.setTitle(filmDetailDto.title());
                    film.setDescription(filmDetailDto.description());
                    film.setReleaseYear(filmDetailDto.releaseYear());
                    film.setRentalDuration(filmDetailDto.rentalDuration());
                    film.setRentalRate(filmDetailDto.rentalRate());
                    film.setLength(filmDetailDto.length());
                    film.setReplacementCost(filmDetailDto.replacementCost());
                    film.setRating(filmDetailDto.rating());
                    film.setSpecialFeatures(filmDetailDto.specialFeatures());
                    film.setLastUpdate(new Date());
                    filmRepository.save(film);
                    return true;
                }
        ).orElse(false);
    }


    //---------------------- IMPLEMENTACION PARA TRAER LA LISTA DE LENGUAJES------------------------------
    @Override
    public List<LanguageDto> findAllLanguages() {
        List<LanguageDto> languageDtos = new ArrayList<>();
        Iterable<Language> languages = languageRepository.findAll();
        languages.forEach(language -> {
            LanguageDto languageDto = new LanguageDto(language.getLanguageId(), language.getName());
            languageDtos.add(languageDto);
        });
        return languageDtos;
    }

    //----------------------- IMPLEMENTACION PARA EL REGISTRO---------------------------------------------
    @CacheEvict(value = "films", allEntries = true)
    @Override
    public Boolean addFilm(FilmInsertDto filmInsertDto) {
        // Validar el DTO
        validateFilmInsertDto(filmInsertDto);

        Language language = languageRepository.findById(filmInsertDto.languageId())
                .orElseThrow(() -> new RuntimeException("Idioma no encontrado"));

        Film film = new Film();
        film.setTitle(filmInsertDto.title());
        film.setDescription(filmInsertDto.description());
        film.setReleaseYear(filmInsertDto.releaseYear());
        film.setLanguage(language);
        film.setRentalDuration(filmInsertDto.rentalDuration());
        film.setRentalRate(filmInsertDto.rentalRate());
        film.setLength(filmInsertDto.length());
        film.setReplacementCost(filmInsertDto.replacementCost());
        film.setRating(filmInsertDto.rating());
        film.setSpecialFeatures(filmInsertDto.specialFeatures());
        film.setLastUpdate(new java.util.Date());

        filmRepository.save(film);

        return true;
    }

    //-----------------------INSERCION DE IMPLEMENTACION PARA ELIMINAR-------------------------------------
    @CacheEvict(value = "films", allEntries = true)
    @Override
    @Transactional
    public Boolean deleteFilm(int id) {
        // Buscar el Film
        Optional<Film> optional = filmRepository.findById(id);
        if (optional.isPresent()) {
            Film film = optional.get();

            List<Inventory> inventories = inventoryRepository.findAllByFilm_FilmId(film.getFilmId());
            inventories.forEach(inventory -> {
                rentalRepository.deleteAllByInventory(inventory);
            });
            inventoryRepository.deleteAll(inventories);
            filmRepository.delete(film);

            return true;
        } else {
            return false;
        }
    }

    //------------------------------VALIDACIONES---------------------------
    public void validateFilmInsertDto(FilmInsertDto filmInsertDto) {

        Optional.ofNullable(filmInsertDto.title())
                .filter(title -> !title.isEmpty() && title.length() <= 128)
                .orElseThrow(() -> new IllegalArgumentException("El título es obligatorio y no puede exceder los 128 caracteres."));

        Optional.of(filmInsertDto.releaseYear())
                .filter(year -> year >= 1900 && year <= 2024)
                .orElseThrow(() -> new IllegalArgumentException("El año de lanzamiento debe estar entre 1900 y 2024."));

        Optional.of(filmInsertDto.rentalDuration())
                .filter(duration -> duration > 0)
                .orElseThrow(() -> new IllegalArgumentException("La duración del alquiler debe ser mayor a 0."));

        Optional.of(filmInsertDto.rentalRate())
                .filter(rate -> rate > 0)
                .orElseThrow(() -> new IllegalArgumentException("La tarifa de alquiler debe ser mayor a 0."));

        Optional.ofNullable(filmInsertDto.specialFeatures())
                .ifPresent(features -> {
                    Set<String> validFeatures = Set.of("Trailers", "Commentaries", "Deleted Scenes", "Behind the Scenes");
                    Set<String> providedFeatures = Set.of(features.split(","));
                    if (!validFeatures.containsAll(providedFeatures)) {
                        throw new IllegalArgumentException("Las características especiales no son válidas.");
                    }
                });
    }







}
