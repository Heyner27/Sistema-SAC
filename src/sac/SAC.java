/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sac;

/**
 * @author Heyner Beltran
 */
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class SAC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
    
        String usuario = "Soporte";
        String password = "soportesoporte";
        String url = "jdbc:mysql://localhost:3306/mydb";
        Connection conexion;
        Statement statement;
        ResultSet rs;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SAC.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            conexion = DriverManager.getConnection(url,usuario, password);
            statement = conexion.createStatement();
            //statement.executeUpdate("INSERT INTO PERSONA (idpersona, nombresPersona, apellidosPersona) VALUES (7, 'ABC', 'DEF')");
            rs = statement.executeQuery("SELECT * from persona");
            rs.next();
            
            do{
                System.out.println(rs.getInt("idpersona")+ ", "+rs.getString("nombresPersona")+", "+rs.getString("apellidosPersona") );
            }while (rs.next());
            
            
        } catch (SQLException ex) {
            Logger.getLogger(SAC.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
