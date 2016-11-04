
package dao;

import factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Clientes;
import model.Pedido;
import model.Produto;

/**
 *
 * @author JayMixXx
 */
public class PedidoDAO {
    
     Connection con = null;
    PreparedStatement ptmt = null;
    ResultSet rs = null;
   

    public PedidoDAO() {

    }

    private Connection getConnection() throws SQLException {
        Connection conn;
        conn = ConnectionFactory.getInstance().getConnection();
        return conn;
    }
        
    public List<Pedido> select(Pedido pedido){
        List<Pedido> pedidos = new ArrayList<Pedido>();
        
        try {
            
        String querystring = " select p.cod_pedidos as codigo_pedido, p.data_hora as hora_pedido, c.cod_cliente as cliente,\n" +
                            " pr.nome as produto, pp.qnt as quantidade_produtos, pp.preco as preco_unitario, p.total  \n" +
                            " from pedidos as p join clientes as c on p.clientes_cod_cliente = c.cod_cliente\n" +
                            " join produtos_has_pedidos as pp on p.cod_pedidos = pp.pedidos_cod_pedidos\n" +
                            " join produtos as pr on pp.produtos_id = pr.id;";

            con = getConnection();
        
            ptmt = con.prepareStatement(querystring);
            
            //ptmt.setInt(1, pedido.getClientes().getCod_cliente());
            //ptmt.setString(2, "%"+ pedido.getCod_pedido()+"%");
            
            rs = ptmt.executeQuery();
            
                while (rs.next()) {    
                    
                   pedido = readFields();
                     
                    
                    pedidos.add(pedido);
                }
                
            } catch (SQLException e) {
            e.printStackTrace();
            } finally {
            
            fecharConexao();

        }
        
        return pedidos;
    }
    
    private Pedido readFields() throws SQLException {
        Pedido pedido;
        pedido = new Pedido();
        
        Clientes cliente = new Clientes();
        Produto produto = new Produto();
        
        pedido.setCod_pedido(rs.getInt("codigo_pedido"));
        pedido.setData_hora(rs.getDate("hora_pedido"));
        pedido.setTotal(rs.getDouble("total"));
        pedido.setCliente(rs.getString("cliente"));
        pedido.setProdutoNome(rs.getString("produto"));
       // cliente.setNome(rs.getString("cliente"));
       // pedido.getClientes().setNome(rs.getString("cliente"));  
        //produto.setNome(rs.getString("produto"));
        //pedido.getProduto().setNome(rs.getString("produto"));
        pedido.setQuantidade(rs.getInt("quantidade_produtos"));
        pedido.setPreco(rs.getDouble("preco_unitario"));
               
        return pedido;
    }
    
    public Clientes findByPrimaryKey(int id) {

        Clientes cliente = null;
        try {
            String querystring = "SELECT * FROM clientes WHERE cod_cliente =?";
            con = getConnection();
            ptmt = con.prepareStatement(querystring);
            ptmt.setInt(1, id);
            rs = ptmt.executeQuery();
            if (rs.next()) {
              //  cliente = readFields();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            
            fecharConexao();
        }
        return cliente;
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
