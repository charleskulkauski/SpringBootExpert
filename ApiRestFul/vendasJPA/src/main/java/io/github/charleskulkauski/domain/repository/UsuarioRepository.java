package io.github.charleskulkauski.domain.repository;

import io.github.charleskulkauski.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query
    Optional<Usuario> findByLogin(String login);
}
