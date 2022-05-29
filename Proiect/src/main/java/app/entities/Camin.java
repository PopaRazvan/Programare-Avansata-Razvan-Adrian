package app.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(value = {"studenti", "camere"})
@Entity
@Table(name = "camine")
public class Camin implements Serializable,Printable {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "nume")
    private String name;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "nr_camere")
    private Integer nrCamere;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "camin")
    private List<Camera> camere = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "camin")
    private List<Student> studenti = new ArrayList<>();



    public Camin() {
        this.id = null;
        this.name = null;
        this.nrCamere = null;
        this.rating=null;
    }

    public Camin(Integer id, String name, Integer nrCamere,Integer rating) {
        this.id = id;
        this.name = name;
        this.nrCamere = nrCamere;
        this.rating=rating;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNrCamere() {
        return nrCamere;
    }

    public void setNrCamere(Integer nrCamere) {
        this.nrCamere = nrCamere;
    }

    public List<Student> getStudenti() {
        return studenti;
    }

    public List<Camera> getCamere() {
        return camere;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Camin{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                ", nrCamere=" + nrCamere +
                ", studenti=" + studenti +
                ", camere=" + camere +
                '}';
    }
}
