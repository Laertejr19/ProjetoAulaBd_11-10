package br.com.DAO;

import br.com.DTO.UsuarioDTO;
import br.com.Views.TelaPrincipal;
import br.com.Views.TelaUsuarios;
import java.awt.Color;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Eder
 */
public class UsuarioDAO {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public void logar(UsuarioDTO objusuarioDTO) {
        String sql = "select * from tb_usuarios where login = ? and senha = ?";
        conexao = ConexaoDAO.conector();

        try {
            // preparar a consulta no banco, em função ao que foi inserido nas caixas de texto
            pst = conexao.prepareStatement(sql);
            pst.setString(1, objusuarioDTO.getLoginUsuario());
            pst.setString(2, objusuarioDTO.getSenhaUsuario());

//            executa a query
            rs = pst.executeQuery();
//            verifica se existe usuario
            if (rs.next()) {
                // obtem o conteúdo do atributo perfil
                String perfil = rs.getString(5);
//                System.out.println(perfil);

                //tratamento de perfil
                if (perfil.equals("admin")) {
                    TelaPrincipal principal = new TelaPrincipal();
                    principal.setVisible(true);
                    TelaPrincipal.MenuRel.setEnabled(true);
                    TelaPrincipal.subMenuUsuarios.setEnabled(true);
                    TelaPrincipal.Usuario.setText(rs.getString(2));
                    TelaPrincipal.Usuario.setForeground(Color.RED);
                    conexao.close();//Fechar a conexão                    
                } else {
                    TelaPrincipal principal = new TelaPrincipal();
                    principal.setVisible(true);
                    principal.Usuario.setText(rs.getString(2));
                    TelaPrincipal.Usuario.setForeground(Color.BLUE);
                    conexao.close();//Fechar a conexão   

                }

            } else {
                JOptionPane.showMessageDialog(null, "Usuário e/ou senha invalidos");

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "** tela Login ***" + e);
        }
    }

    public void inserirUsuario(UsuarioDTO objUsuarioDTO) {
        String sql = "insert into tb_usuarios (id_usuario, usuario, login, senha, perfil)"
                + " values (?, ?, ?, ?, ?)";
        conexao = ConexaoDAO.conector();

        try {
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, objUsuarioDTO.getIdUsuario());
            pst.setString(2, objUsuarioDTO.getNomeUsuario());
            pst.setString(3, objUsuarioDTO.getLoginUsuario());
            pst.setString(4, objUsuarioDTO.getSenhaUsuario());
            pst.setString(5, objUsuarioDTO.getPerfilUsuario());
            int add  = pst.executeUpdate();
            if (add > 0) {
                pesquisaAuto();
                pst.close();
                limparCampos();
                JOptionPane.showMessageDialog(null, "Usuário inserido com sucesso! ");
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, " Método Inserir " + e);
        }
    }

    public void pesquisar(UsuarioDTO objUsuarioDTO) {
        String sql = "select * from tb_usuarios where id_usuario = ?";
        conexao = ConexaoDAO.conector();

        try {
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, objUsuarioDTO.getIdUsuario());
            rs = pst.executeQuery();
            if (rs.next()) {
                TelaUsuarios.txtNomeUsu.setText(rs.getString(2));
                TelaUsuarios.txtLoginUsuario.setText(rs.getString(3));
                TelaUsuarios.txtSenhaUsuario.setText(rs.getString(4));
                TelaUsuarios.cboPerfilUsu.setSelectedItem(rs.getString(5));
                conexao.close();
            } else {
                JOptionPane.showMessageDialog(null, "Usuário não cadastrado!");
                limparCampos();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, " Método Pesquisar" + e);
        }
    }

    public void pesquisaAuto() {
        String sql = "select * from tb_usuarios";
        conexao = ConexaoDAO.conector();

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) TelaUsuarios.TbUsuarios.getModel();
            model.setNumRows(0);

            while (rs.next()) {
                int id = rs.getInt("id_usuario");
                String nome = rs.getString("usuario");
                String login = rs.getString("login");
                String senha = rs.getString("senha");
                String perfil = rs.getString("perfil");
                model.addRow(new Object[]{id, nome, login, senha, perfil});
            }
            conexao.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, " Método Pesquisar Automático " + e);
        }
    }

    //Método editar
    public void editar(UsuarioDTO objUsuarioDTO) {
        String sql = "update tb_usuarios set usuario = ?, login = ?, senha = ?, perfil = ? where id_usuario = ?";
        conexao = ConexaoDAO.conector();
        try {
            pst = conexao.prepareStatement(sql);
            pst.setInt(5, objUsuarioDTO.getIdUsuario());
            pst.setString(4, objUsuarioDTO.getPerfilUsuario());
            pst.setString(3, objUsuarioDTO.getSenhaUsuario());
            pst.setString(2, objUsuarioDTO.getLoginUsuario());
            pst.setString(1, objUsuarioDTO.getNomeUsuario());
            int add = pst.executeUpdate();
            if (add > 0) {
                JOptionPane.showMessageDialog(null, "Usuário editado com sucesso!");
                pesquisaAuto();
                conexao.close();
                limparCampos();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, " Método editar " + e);
        }
    }

    //Método deletar
    public void deletar(UsuarioDTO objUsuarioDTO) {
        String sql = "delete from tb_usuarios where id_usuario = ?";
        conexao = ConexaoDAO.conector();

        try {
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, objUsuarioDTO.getIdUsuario());
            int del = pst.executeUpdate();
            if (del > 0) {
                JOptionPane.showMessageDialog(null, " Usuário deletado com sucesso!");
                pesquisaAuto();
                conexao.close();
                limparCampos();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, " Método deletar " + e);
        }
    }

    public void limparCampos() {
        TelaUsuarios.txtIdUsuario.setText(null);
        TelaUsuarios.txtLoginUsuario.setText(null);
        TelaUsuarios.txtNomeUsu.setText(null);
        TelaUsuarios.txtSenhaUsuario.setText(null);
        TelaUsuarios.cboPerfilUsuario.setSelectedItem(1);
    }

}