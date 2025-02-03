package es.ieslosmontecillos.appinformes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AppInformeController {

    @FXML
    private Label welcomeText;
    @FXML
    private MenuItem menuSubinformes;
    @FXML
    private MenuItem menuVentas;
    @FXML
    private MenuItem menuFactura;
    @FXML
    private MenuItem menuClientes;
    @FXML
    private ImageView imgInformes;

    @FXML
    public VBox contenedor;
    public Connection conexion;
    public String rutaInforme;
    public Map parametros = new HashMap();

    public static int id = 0;
    public static String rutaImg = AppInformeController.class.getResource("img/").toString();

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    @FXML
    public void onActionInformeFacturas(ActionEvent actionEvent) {

        reset("1. Informe de facturas");

        try {

            rutaInforme = "jasperReports/Facturas.jasper";
            parametros.clear();
            parametros.put("DirImagenes", rutaImg);
            generarInforme(rutaInforme, parametros);

        } catch (JRException e) {

            System.out.println("Error generando informe.");

        }catch (NullPointerException nullEx)
        {
            System.out.println("No se ha encontrado el informe jasper");
        }

        welcomeText.setText("1. Informe de facturas");
    }

    @FXML
    public void onActionInformeVentas(ActionEvent actionEvent)
    {
        reset("2. Informe de ventas totales");

        try {

            rutaInforme = "jasperReports/VentasTotales.jasper";
            parametros.clear();
            parametros.put("DirImagenes", rutaImg);
            generarInforme(rutaInforme, parametros);


        } catch (JRException e) {
            System.out.println("Error generando informe.");

        }catch (NullPointerException nullEx)
        {
            System.out.println("No se ha encontrado el informe jasper");
        }
    }

    @FXML
    public void onActionInformeCliente(ActionEvent actionEvent)
    {

        reset("3. Factura por cliente");

        Label lbCliente = new Label("ID del cliente: ");
        TextField tfCliente = new TextField();
        Button btnCliente = new Button("Generar informe");
        HBox rootParametro = new HBox(lbCliente, tfCliente);
        rootParametro.setAlignment(Pos.CENTER);
        rootParametro.setPadding(new Insets(10, 0, 10 , 0));

        contenedor.getChildren().addAll(rootParametro, btnCliente);


        btnCliente.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                id = Integer.parseInt(tfCliente.getText());

                try {

                    rutaInforme = "jasperReports/FacturaPorCliente.jasper";
                    parametros.clear();
                    parametros.put("Cliente", id);
                    parametros.put("DirImagenes", rutaImg);
                    generarInforme(rutaInforme, parametros);

                } catch (JRException e) {
                    System.out.println("Error generando informe.");

                }catch (NullPointerException nullEx)
                {
                    System.out.println("No se ha encontrado el informe jasper");
                }
            }
        });

    }

    @FXML
    public void onActionSubinforme(ActionEvent actionEvent)
    {
        reset("4.Facturas con subinformes");

        try {

            JasperReport jsr = (JasperReport) JRLoader.loadObject(Objects.requireNonNull(AppInforme.class.getResource("jasperReports/Fac_sub1.jasper")));
            rutaInforme = "jasperReports/Facturas_subinforme.jasper";
            parametros.clear();
            parametros.put("subReportParameter", jsr);
            parametros.put("DirImagenes", rutaImg);
            generarInforme(rutaInforme, parametros);

        } catch (JRException e) {
            System.out.println("Error generando informe.");

        }catch (NullPointerException nullEx)
        {
            System.out.println("No se ha encontrado el informe jasper");
        }

    }

    public void generarInforme(String ruta, Map parametros) throws JRException {

        JasperReport jr = (JasperReport) JRLoader.loadObject(Objects.requireNonNull(AppInforme.class.getResource(ruta)));
        JasperPrint jp = JasperFillManager.fillReport(jr, parametros, conexion);
        JasperViewer.viewReport(jp, false);
    }

    @FXML
    public void onActionInicio(ActionEvent event)
    {
        reset("AppInforme");
    }

    /* La interfaz del inicio */
    public void reset(String text)
    {
        contenedor.getChildren().clear();
        welcomeText.setText(text);
        contenedor.getChildren().add(welcomeText);
        contenedor.getChildren().add(imgInformes);
    }



}