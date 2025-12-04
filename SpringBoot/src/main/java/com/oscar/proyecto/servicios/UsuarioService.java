package com.oscar.proyecto.servicios;

import com.oscar.proyecto.dto.Usuario.UsuarioDTO;
import com.oscar.proyecto.exception.ElementoNoEncontradoException;
import com.oscar.proyecto.modelos.Usuario;
import com.oscar.proyecto.repositorios.UsuarioRepository;
import com.oscar.proyecto.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;
    private final InventarioService inventarioService;
    private final UsuarioMapper usuarioMapper;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Value("${usuario.uploads.path:uploads/}")
    private String CARPETA_UPLOADS;

    public List<Usuario> getTodos() {
        return usuarioRepo.findAll();
    }

    public Usuario crearUsuario(UsuarioDTO dto) {
        try {
            if (dto == null) throw new IllegalArgumentException("Los datos del usuario no pueden ser nulos");
            if (dto.getNombre() == null || dto.getNombre().isBlank()) throw new IllegalArgumentException("El nombre es obligatorio");
            if (dto.getCorreoElectronico() == null || dto.getCorreoElectronico().isBlank()) throw new IllegalArgumentException("El correo es obligatorio");
            if (dto.getContrasena() == null || dto.getContrasena().isBlank()) throw new IllegalArgumentException("La contraseña es obligatoria");
            if (!dto.getContrasena().equals(dto.getConfirmarContrasena())) throw new IllegalArgumentException("La contraseña y confirmación no coinciden");

            if (usuarioRepo.findByCorreoElectronico(dto.getCorreoElectronico()).isPresent()) {
                throw new IllegalArgumentException("Ya existe un usuario con este correo");
            }

            Usuario usuario = usuarioMapper.toEntity(dto);

            usuario.setContrasena(passwordEncoder.encode(dto.getContrasena()));

            Usuario nuevoUsuario = usuarioRepo.save(usuario);

            inventarioService.crearInventarioParaUsuario(nuevoUsuario);

            return nuevoUsuario;
        } catch (Exception e) {
            throw new ElementoNoEncontradoException("Error al crear el usuario " + e.getMessage() + e);
        }
    }

    public Usuario actualizarUsuario(Integer id, UsuarioDTO dto) {
        return usuarioRepo.findById(id)
                .map(u -> {
                    usuarioMapper.updateEntityFromDto(dto, u);

                    if (dto.getContrasena() != null && !dto.getContrasena().isBlank()) {
                        u.setContrasena(passwordEncoder.encode(dto.getContrasena()));
                    }
                    return usuarioRepo.save(u);
                })
                .orElse(null);
    }

    public Usuario actualizarFoto(Integer id, MultipartFile archivo) throws IOException {
        Usuario usuario = usuarioRepo.findById(id).orElse(null);
        if (usuario == null) return null;
        if (archivo == null || archivo.isEmpty()) return usuario;

        File carpetaUploads;
        if (new File(CARPETA_UPLOADS).isAbsolute()) {
            carpetaUploads = new File(CARPETA_UPLOADS);
        } else {
            carpetaUploads = new File(System.getProperty("user.dir"), CARPETA_UPLOADS);
        }

        if (!carpetaUploads.exists()) {
            boolean creado = carpetaUploads.mkdirs();
            if (!creado) {
                throw new IOException("No se pudo crear la carpeta de uploads: " + carpetaUploads.getAbsolutePath());
            }
        }

        String nombreArchivo = "usuario_" + id + "_" + System.currentTimeMillis() + "_" +
                archivo.getOriginalFilename().replaceAll("\\s+", "_");

        File destino = new File(carpetaUploads, nombreArchivo);
        archivo.transferTo(destino);

        usuario.setFotoUrl("/uploads/" + nombreArchivo);

        return usuarioRepo.save(usuario);
    }

    public byte[] obtenerFoto(Integer id) throws IOException {
        Usuario usuario = usuarioRepo.findById(id).orElse(null);
        if (usuario == null || usuario.getFotoUrl() == null) return null;

        String pathRelativo = usuario.getFotoUrl().replaceFirst("/uploads/", "");

        File carpetaUploads;
        if (new File(CARPETA_UPLOADS).isAbsolute()) {
            carpetaUploads = new File(CARPETA_UPLOADS);
        } else {
            carpetaUploads = new File(System.getProperty("user.dir"), CARPETA_UPLOADS);
        }

        File archivo = new File(carpetaUploads, pathRelativo);
        if (!archivo.exists()) return null;

        return Files.readAllBytes(archivo.toPath());
    }

    public String obtenerMimeType(Integer id) throws IOException {
        Usuario usuario = usuarioRepo.findById(id).orElse(null);
        if (usuario == null || usuario.getFotoUrl() == null) return null;

        String pathRelativo = usuario.getFotoUrl().replaceFirst("/uploads/", "");

        File carpetaUploads;
        if (new File(CARPETA_UPLOADS).isAbsolute()) {
            carpetaUploads = new File(CARPETA_UPLOADS);
        } else {
            carpetaUploads = new File(System.getProperty("user.dir"), CARPETA_UPLOADS);
        }

        File archivo = new File(carpetaUploads, pathRelativo);
        if (!archivo.exists()) return null;

        return Files.probeContentType(archivo.toPath());
    }

    public Optional<Usuario> obtenerPorCorreo(String correo) {
        try {
            String correoDecodificado = URLDecoder.decode(correo, StandardCharsets.UTF_8);
            return usuarioRepo.findByCorreoElectronico(correoDecodificado);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Usuario actualizarCorreo(Integer id, String nuevoCorreo) {
        Usuario usuario = usuarioRepo.findById(id).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        if (usuarioRepo.findByCorreoElectronico(nuevoCorreo).isPresent()) {
            throw new IllegalArgumentException("El correo electrónico ya está en uso");
        }

        usuario.setCorreoElectronico(nuevoCorreo);

        return usuarioRepo.save(usuario);
    }
}