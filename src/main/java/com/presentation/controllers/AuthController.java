package com.presentation.controllers;

import com.data.repositories.UsuarioRepositoryImpl;
import com.data.repositories.DoctorRepositoryImpl; // <--- Necesitas este
import com.data.repositories.PacienteRepositoryImpl; // <--- Y este
import com.domain.entities.Usuario;
import com.presentation.dtos.LoginRequest;
import io.javalin.http.Context;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private static final UsuarioRepositoryImpl usuarioRepo = new UsuarioRepositoryImpl();
    private static final DoctorRepositoryImpl doctorRepo = new DoctorRepositoryImpl();
    private static final PacienteRepositoryImpl pacienteRepo = new PacienteRepositoryImpl();

    public static void login(Context ctx) {
        try {
            LoginRequest loginData = ctx.bodyAsClass(LoginRequest.class);

            if (loginData.isInvalid()) {
                ctx.status(400).json(Map.of("error", "Correo y contrasena son obligatorios"));
                return;
            }

            Usuario usuario = usuarioRepo.buscarPorCorreo(loginData.correo);

            if (usuario != null && BCrypt.checkpw(loginData.password, usuario.getPassword())) {
                logger.info("Login exitoso: {}", usuario.getCorreo());

                // 1. Preparamos el mapa de respuesta dinámico
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("status", "exitoso");
                responseData.put("id_usuario", usuario.getId());
                responseData.put("rol", usuario.getRol());
                responseData.put("mensaje", "Acceso concedido");

                // 2. BUSQUEDA DE ID ESPECÍFICO SEGÚN ROL
                // Esto evita que el Front use el ID de usuario para agendar citas o publicar horarios
                if ("DOCTOR".equals(usuario.getRol())) {
                    // Este método debe devolver el id_doctor de la tabla 'doctores'
                    int idDoctor = doctorRepo.obtenerIdDoctorPorUsuario(usuario.getId());
                    responseData.put("id_doctor", idDoctor);
                    responseData.put("id_especifico", idDoctor);
                }
                else if ("PACIENTE".equals(usuario.getRol())) {
                    // Este método debe devolver el id_paciente de la tabla 'pacientes'
                    int idPaciente = pacienteRepo.obtenerIdPacientePorUsuario(usuario.getId());
                    responseData.put("id_paciente", idPaciente);
                    responseData.put("id_especifico", idPaciente);
                }

                ctx.status(200).json(responseData);
            } else {
                ctx.status(401).json(Map.of("error", "Credenciales invalidas"));
            }

        } catch (Exception e) {
            logger.error("Error en login: {}", e.getMessage());
            ctx.status(500).json(Map.of("error", "Error interno al procesar el login"));
        }
    }
}