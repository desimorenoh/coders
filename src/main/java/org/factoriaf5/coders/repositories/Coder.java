package org.factoriaf5.coders.repositories;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "coders")
public class Coder implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellidos;
    private int edad;
    private String paisDeOrigen;
    private String nivelDeEstudios;
    private String direccion;
    private String promocion;
    private String photoImage;

    public Coder() {

    }

    public Coder(String nombre, String apellidos, int edad, String paisDeOrigen, String nivelDeEstudios, String direccion, String promocion, String photoImage) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.paisDeOrigen = paisDeOrigen;
        this.nivelDeEstudios = nivelDeEstudios;
        this.direccion = direccion;
        this.promocion = promocion;
        this.photoImage = photoImage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getPaisDeOrigen() {
        return paisDeOrigen;
    }

    public void setPaisDeOrigen(String paisDeOrigen) {
        this.paisDeOrigen = paisDeOrigen;
    }

    public String getNivelDeEstudios() {
        return nivelDeEstudios;
    }

    public void setNivelDeEstudios(String nivelDeEstudios) {
        this.nivelDeEstudios = nivelDeEstudios;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPromocion() {
        return promocion;
    }

    public void setPromocion(String promocion) {
        this.promocion = promocion;
    }

    public String getPhotoImage() {
        return photoImage;
    }

    public void setPhotoImage(String photoImage) {
        this.photoImage = photoImage;
    }


    @Override
    public String toString() {
        return "Coder{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", edad='" + edad + '\'' +
                ", paisDeOrigen='" + paisDeOrigen + '\'' +
                ", nivelDeEstudios='" + nivelDeEstudios + '\'' +
                ", direccion='" + direccion + '\'' +
                ", promocion='" + promocion + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coder coder = (Coder) o;
        return Objects.equals(id, coder.id) && Objects.equals(nombre, coder.nombre) && Objects.equals(apellidos, coder.apellidos) && Objects.equals(edad, coder.edad) && Objects.equals(paisDeOrigen, coder.paisDeOrigen) && Objects.equals(nivelDeEstudios, coder.nivelDeEstudios) && Objects.equals(direccion, coder.direccion) && Objects.equals(promocion, coder.promocion) && Objects.equals(photoImage, coder.photoImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, apellidos, edad, paisDeOrigen, nivelDeEstudios, direccion, promocion, photoImage);
    }
}