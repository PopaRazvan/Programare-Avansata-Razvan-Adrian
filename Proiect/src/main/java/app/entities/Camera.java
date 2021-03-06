package app.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(value = {"studenti", "camin"})
@Entity
@Table(name = "camere")
public class Camera implements Serializable,Printable {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "id_camin")
    private Integer idCamin;

    @Column(name = "nr_locuri")
    private Integer nrLocuri;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "camera")
    private List<Student> studenti = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_camin", insertable = false, updatable = false)
    private Camin camin;

    public Camera() {
        this.id = null;
        this.nrLocuri = null;
    }

    public Camera(Camera camera) {
        this.id = camera.id;
        this.idCamin = camera.idCamin;
        this.nrLocuri = camera.nrLocuri;
    }

    public Camera(Integer id, Integer idCamin, Integer nrLocuri) {
        this.id = id;
        this.idCamin = idCamin;
        this.nrLocuri = nrLocuri;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCamin() {
        return idCamin;
    }

    public void setIdCamin(Integer id_camin) {
        this.idCamin = id_camin;
    }

    public Integer getNrLocuri() {
        return nrLocuri;
    }

    public void setNrLocuri(Integer nrLocuri) {
        this.nrLocuri = nrLocuri;
    }

    public List<Student> getStudenti() {
        return studenti;
    }

    public Camin getCamin() {
        return camin;
    }

    public void setStudenti(List<Student> studenti) {
        this.studenti = studenti;
    }

    @Override
    public String toString() {
        return "Camera{" +
                "id=" + id +
                ", idCamin=" + idCamin +
                ", nrLocuri=" + nrLocuri +
                ", studenti=" + studenti +

                '}';
    }
}
