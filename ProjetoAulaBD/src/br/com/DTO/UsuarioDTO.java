

package br.com.DTO;

/**
 *
 * @author Eder
 */
public class UsuarioDTO {
    private int id_uauario;
    private String nome_usuario, login_usuario, senha_usuario, perfil_usuario;

    public String getPerfilUsuario() {
        return perfil_usuario;
    }

    public void setPerfilUsuario(String perfil_usuario) {
        this.perfil_usuario = perfil_usuario;
    }

    public int getIdUsuario() {
        return id_uauario;
    }

    public void setIdUsuario(int id_uauario) {
        this.id_uauario = id_uauario;
    }

    public String getNomeUsuario() {
        return nome_usuario;
    }

    public void setNomeUsuario(String nome_usuario) {
        this.nome_usuario = nome_usuario;
    }

    public String getLoginUsuario() {
        return login_usuario;
    }

    public void setLoginUsuario(String login_usuario) {
        this.login_usuario = login_usuario;
    }

    public String getSenhaUsuario() {
        return senha_usuario;
    }

    public void setSenhaUsuario(String senha_usuario) {
        this.senha_usuario = senha_usuario;
    }

    public void apagar(UsuarioDTO objUsuarioDTO) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}