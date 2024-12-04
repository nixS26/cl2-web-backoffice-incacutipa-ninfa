package pe.edu.i202220224.cl2_web_backoffice_incacutipa_ninfa.dto;

public record FilmInsertDto(String title,
                            String description,
                            Integer releaseYear,
                            Integer languageId,
                            Integer rentalDuration,
                            Double rentalRate,
                            Integer length,
                            Double replacementCost,
                            String rating,
                            String specialFeatures) {
}
