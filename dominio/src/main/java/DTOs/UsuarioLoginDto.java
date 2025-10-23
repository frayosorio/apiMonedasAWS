package monedas.api.dominio.dtos;

import monedas.api.dominio.entidades.Usuario;

public class UsuarioLoginDto {
    private Usuario usuario;
    private String token;

    public UsuarioLoginDto(Usuario usuario) {
        this.usuario = usuario;
        this.token = "";
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
