package com.example.StudentAPI.Entities;

import javax.persistence.*;

@Entity
@Table(name = "studenti")
public class Student {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "id_camin")
    private Integer idCamin;

    @Column(name = "id_camera")
    private Integer idCamera;

    @Column(name = "id_preferred_student")
    private Integer idPreferredStudent;

    @Column(name = "nr_matricol")
    private String nrMatricol;

    @Column(name = "nume")
    private String nume;

    @Column(name = "prenume")
    private String prenume;

    @Column(name = "gen")
    private String gen;

    @Column(name = "an")
    private Integer an;

    @Column(name = "grupa")
    private String grupa;

    @Column(name = "media")
    private Double media;

    @Column(name = "data_nastere")
    private String dataNastere;

    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "id_camera", insertable = false, updatable = false)
    private Camera camera;

    @ManyToOne
    @JoinColumn(name = "id_camin", insertable = false, updatable = false)
    private Camin camin;

    @OneToOne
    @JoinColumn(name = "id_preferred_student", insertable = false, updatable = false)
    private Student preferredStudent;
    public Student() {
    }

    public Student(Integer id, String nrMatricol, String nume, String prenume, String gen, Integer an, String grupa, Double media, String dataNastere, String email) {
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

    public Integer getAn() {
        return an;
    }

    public void setAn(Integer an) {
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

    public String getDataNastere() {
        return dataNastere;
    }

    public void setDataNastere(String dataNastere) {
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

    public Student getPreferredStudent() {
        return preferredStudent;
    }

    public void setPreferedStudent(Student preferredStudent) {
        this.preferredStudent = preferredStudent;
    }

}
