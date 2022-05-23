package App.Entities;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "camere")
public class Camera {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "idCamin")
    private Integer idCamin;

    @Column(name = "nr_locuri")
    private Integer nrLocuri;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "camera")
    private List<Student> studenti = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="id",insertable=false, updatable=false)
    private Camin camin;

    public Camera(){}

    public Camera(Integer id, Integer id_camin, Integer nrLocuri) {
        this.id = id;
        this.idCamin = id_camin;
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
}
