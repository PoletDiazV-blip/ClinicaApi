package com.data.repositories;

import com.data.persistence.DatabaseConfig;
import com.domain.entities.Paciente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteRepositoryImpl {

    /**
     * OBTENER ID_PACIENTE POR ID_USUARIO
     * Este es el que necesita el AuthController para el Login exitoso.
     */
    public int obtenerIdPacientePorUsuario(int id_usuario) {
        String sql = "SELECT id_paciente FROM pacientes WHERE id_usuario = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id_usuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_paciente");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al vincular Usuario con Paciente: " + e.getMessage());
        }
        return -1;
    }

    /**
     * OBTENER PACIENTE POR ID
     * Útil para ver el perfil del paciente logueado.
     */
    public Paciente obtenerPorId(int id_paciente) {
        String sql = "SELECT * FROM pacientes WHERE id_paciente = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id_paciente);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Paciente p = new Paciente();
                    p.setId_paciente(rs.getInt("id_paciente"));
                    p.setId_usuario(rs.getInt("id_usuario"));
                    p.setNombre(rs.getString("nombre"));
                    p.setTelefono(rs.getString("telefono"));
                    p.setTipo_sangre(rs.getString("tipo_sangre"));
                    p.setSexo(rs.getString("sexo"));
                    p.setEdad(rs.getString("edad"));
                    return p;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener paciente: " + e.getMessage());
        }
        return null;
    }

    /**
     * ACTUALIZAR DATOS DEL PACIENTE
     */
    public boolean actualizar(Paciente p) {
        String sql = "UPDATE pacientes SET nombre=?, telefono=?, tipo_sangre=?, sexo=?, edad=? WHERE id_paciente=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNombre());
            stmt.setString(2, p.getTelefono());
            stmt.setString(3, p.getTipo_sangre());
            stmt.setString(4, p.getSexo());
            stmt.setString(5, p.getEdad());
            stmt.setInt(6, p.getId_paciente());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar paciente: " + e.getMessage());
            return false;
        }
    }
}