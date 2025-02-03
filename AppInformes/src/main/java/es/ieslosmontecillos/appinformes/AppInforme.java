package es.ieslosmontecillos.appinformes;

/**
 * Desarrollo de interfaces
 * @author Shihan
 * AppInformes
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.DriverManager;

public class AppInforme extends Application {

    private AppInformeController appInformeController;
    @Override
    public void start(Stage stage) throws IOException {

        /* Carga la vista principal con el menú para los informes */
        FXMLLoader fxmlLoader = new FXMLLoader(AppInforme.class.getResource("app_informe.fxml"));

        Pane root = fxmlLoader.load();
        appInformeController = fxmlLoader.getController();
        conectarBD(appInformeController);

        Scene scene = new Scene(root, 420, 340);
        stage.setTitle("AppInformes");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() throws Exception {

        /* Cierra la conexión de la base de datos */
        try
        {
            DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/SampleDB;shutdown=true");
            System.out.println("Cierre base de datos.");
        }catch (Exception e)
        {
            System.out.println("Cierre fallida de la BD");
        }
    }

    /* Conexión a la base de datos */
    public void conectarBD(AppInformeController appInformeController)
    {
        String sgdb = "hsqldb";
        String usuario = "sa";
        String clave = "";
        String bd = ":hsql://localhost:9001/SampleDB";

        try {
            Conectar c = new Conectar(bd, usuario, clave, "", sgdb);
            appInformeController.setConexion(c.getConexion());

        }catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

}