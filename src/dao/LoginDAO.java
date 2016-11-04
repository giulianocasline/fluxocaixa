
package dao;

import model.Login;
import factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author JayMixXx
 */
public class LoginDAO {
    
    Connection con = null;
    PreparedStatement ptmt = null;
    ResultSet rs = null;
   

    public LoginDAO() {

    }

    private Connection getConnection() throws SQLException {
        Connection conn;
        conn = ConnectionFactory.getInstance().getConnection();
        return conn;
    }
    
    public Login selectSenha(Login login){
        
        try {
             
            String querystring = "SELECT * FROM login";
        
            con = getConnection();
        
            ptmt = con.prepareStatement(querystring);            
            rs = ptmt.executeQuery();
            
                if (rs.next()) {    
                                
                login.setSenha(rs.getString("senha"));
                login.setUsuario(rs.getString("usuario"));
                
                }
                
            } catch (SQLException e) {
            e.printStackTrace();
            } finally {
            
            fecharConexao();

        }
        return login;
        
    }
    
    public Login updateSenha(Login login){
    
                try {
            String querystring = "UPDATE login "
                    + "SET usuario =?, "
                    + "senha =? ";             

            con = getConnection();
            ptmt = con.prepareStatement(querystring);

            ptmt.setString(1, login.getUsuario());
            ptmt.setString(2, login.getSenha());                    
                
            ptmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            
            fecharConexao();

        }        
        return login;
    }
    
    public void fecharConexao(){
         try {
                if (rs != null) {
                    rs.close();
                }
                if (ptmt != null) {
                    ptmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
