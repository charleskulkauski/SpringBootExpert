package io.github.charleskulkauski.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    @NotEmpty(message = "{campo.login.obrigatorio}")
    private String login;
    private boolean admin;
}
