package es.ieslosmontecillos.appinformes;
/**
 * Clase para realizar conexiones con la base de datos.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conectar {

    /* Atributos de la clase */
    private String bd;
    private String usuario;
    private String pass;
    private String servidor;
    private Connection conexion;
    private String url;
    private String sgbd;

    /* Constructor */
    public Conectar(String bd, String usuario, String pass, String servidor, String sgbd) throws ClassNotFoundException{

        String c;

        this.sgbd=sgbd;
        this.bd = bd;
        this.usuario = usuario;
        this.pass = pass;
        this.servidor = servidor;

        try {

            if(sgbd.equals("mysql"))
            {
                this.url = "com."+ sgbd +".cj.jdbc.Driver";
                c = "jdbc:"+this.sgbd+"://"+ this.servidor + "/" + this.bd;
                Class.forName(url);
                this.conexion = DriverManager.getConnection(c, this.usuario, this.pass);
            }
            else if(sgbd.equals("hsqldb"))
            {
                this.url = "org."+sgbd +".jdbcDriver";
                c = "jdbc:"+this.sgbd+this.bd ;
                Class.forName(url);
                this.conexion = DriverManager.getConnection(c);
            }

            if(conexion != null){
                System.out.println("Conexion exitosa");
            }else{
                System.out.println("Conexion fallida");
            }

        }catch(Exception e){
            throw new ClassNotFoundException("Error en la conexion");
        }

    }

    /* Generamos el getter para el objeto Connection creado */
    public Connection getConexion() {
        return conexion;
    }

    public void cerrarConexion() throws SQLException {
        this.conexion.close();
    }
}

