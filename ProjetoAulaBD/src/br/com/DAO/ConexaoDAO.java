package br.com.DAO;

import java.sql.*;
//import java.sql.Connection;
//import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 * @author Eder
 */
public class ConexaoDAO {

    public static Connection conector() {
        java.sql.Connection conexao = null;

        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/bdprojetologin";
        String user = "root";
        String password = "root";
        
        try {
            //prepara a conexão com o banco
            Class.forName(driver);
            conexao = DriverManager.getConnection(url,user, password);
            return conexao;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null," Classe de Conexão "+ e.getMessage());
            return null;
        }
        
    }

}
