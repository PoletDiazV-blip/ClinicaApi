package com.domain.entities;

/**
 * Entidad Paciente: Mapeo directo de la tabla 'pacientes' en la DB.
 * Usada para el perfil y para agendar citas.
 */
public class Paciente {
    private int id_paciente;
    private int id_usuario;
    private String nombre;
    private String telefono;
    private String tipo_sangre;
    private String enfermedades_importantes;
    private double peso;
    private double altura;
    private String edad; // En tu SQL es VARCHAR
    private String sexo;

    // 1. Constructor vacío (Para Jackson/Javalin)
    public Paciente() {
    }

    // 2. Getters y Setters (Los cables para que el Repo no marque error)
    public int getId_paciente() { return id_paciente; }
    public void setId_paciente(int id_paciente) { this.id_paciente = id_paciente; }

    public int getId_usuario() { return id_usuario; }
    public void setId_usuario(int id_usuario) { this.id_usuario = id_usuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getTipo_sangre() { return tipo_sangre; }
    public void setTipo_sangre(String tipo_sangre) { this.tipo_sangre = tipo_sangre; }

    public String getEnfermedades_importantes() { return enfermedades_importantes; }
    public void setEnfermedades_importantes(String enfermedades_importantes) { this.enfermedades_importantes = enfermedades_importantes; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }

    public double getAltura() { return altura; }
    public void setAltura(double altura) { this.altura = altura; }

    public String getEdad() { return edad; }
    public void setEdad(String edad) { this.edad = edad; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }
}