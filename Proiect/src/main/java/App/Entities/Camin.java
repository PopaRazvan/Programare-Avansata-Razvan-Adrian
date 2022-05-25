package App.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(value = {"studenti", "camere"})
@Entity
@Table(name = "camine")
public class Camin implements Printable {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "nume")
    private String name;

    @Column(name = "nr_camere")
    private Integer nrCamere;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "camin")
    private List<Student> studenti = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "camin")
    private List<Camera> camere = new ArrayList<>();

    public Camin() {
    }

    public Camin(Integer id, String name, Integer nrCamere) {
        this.id = id;
        this.name = name;
        this.nrCamere = nrCamere;
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
}
