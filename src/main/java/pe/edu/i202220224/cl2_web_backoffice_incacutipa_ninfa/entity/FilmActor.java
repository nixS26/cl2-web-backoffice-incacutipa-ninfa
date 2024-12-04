package pe.edu.i202220224.cl2_web_backoffice_incacutipa_ninfa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmActor {

    @EmbeddedId
    private FilmActorPk id; //clae embebida como clave primary

    @ManyToOne
    @MapsId("actorId") //VINCULAMOS EL CAMPO DE LA CLAVE EMBEBIDA
    @JoinColumn(name = "actor_id")
    private Actor actor;

    @ManyToOne
    @MapsId("filmId") //VINCULAMOS EL CAMPO DE LA CLAVE EMBEBIDA
    @JoinColumn(name = "film_id")
    private Film film;

    private Date lastUpdate;

}
