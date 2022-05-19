package App.Entities;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "studenti")
public class Student implements Serializable {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "id_camin")
    private Integer idCamin;

    @Column(name = "id_camera")
    private Integer idCamera;

    @Column(name = "nr_matricol")
    private String nrMatricol;

    @Column(name = "nume")
    private String nume;

    @Column(name = "prenume")
    private String prenume;

    @Column(name = "gen")
    private String gen;

    @Column(name = "an")
    private String an;

    @Column(name = "grupa")
    private String grupa;

    @Column(name = "media")
    private Double media;

    @Column(name = "data_nastere")
    private Date dataNastere;

    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name="id",insertable=false, updatable=false)
    private Camera camera;

    @ManyToOne
    @JoinColumn(name="id",insertable=false, updatable=false)
    private Camin camin;

    public Student(){};

    public Student(Integer id, String nrMatricol, String nume, String prenume, String gen, String an, String grupa, Double media, Date dataNastere, String email) {
        this.id = id;
        this.nrMatricol = nrMatricol;
        this.nume = nume;
        this.prenume = prenume;
        this.gen = gen;
        this.an = an;
        this.grupa = grupa;
        this.media = media;
        this.dataNastere = dataNastere;
        this.email = email;
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

    public void setIdCamin(Integer idCamin) {
        this.idCamin = idCamin;
    }

    public Integer getIdCamera() {
        return idCamera;
    }

    public void setIdCamera(Integer idCamera) {
        this.idCamera = idCamera;
    }

    public String getNrMatricol() {
        return nrMatricol;
    }

    public void setNrMatricol(String nrMatricol) {
        this.nrMatricol = nrMatricol;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getGen() {
        return gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }

    public String getAn() {
        return an;
    }

    public void setAn(String an) {
        this.an = an;
    }

    public String getGrupa() {
        return grupa;
    }

    public void setGrupa(String grupa) {
        this.grupa = grupa;
    }

    public Double getMedia() {
        return media;
    }

    public void setMedia(Double media) {
        this.media = media;
    }

    public Date getDataNastere() {
        return dataNastere;
    }

    public void setDataNastere(Date dataNastere) {
        this.dataNastere = dataNastere;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Camera getCamera() {
        return camera;
    }

    public Camin getCamin() {
        return camin;
    }
}