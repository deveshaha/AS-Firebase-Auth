package com.dam.pruebafirebaseauth.model;

import java.io.Serializable;

public class Coche implements Serializable {
    private String matricula;
    private String marca;
    private String modelo;

    public Coche() {
    }

    public Coche(String matricula, String marca, String modelo) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    @Override
    public String toString() {
        return matricula + " " + marca + " " + modelo;
    }
}
