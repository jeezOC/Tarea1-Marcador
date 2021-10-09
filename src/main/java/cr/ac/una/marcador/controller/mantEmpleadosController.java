package cr.ac.una.marcador.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import cr.ac.una.marcador.model.EmpleadoDto;
import cr.ac.una.marcador.util.AppContext;
import cr.ac.una.marcador.util.CodigoRespuesta;
import cr.ac.una.marcador.util.FlowController;
import cr.ac.una.marcador.util.Formato;
import cr.ac.una.marcador.util.Mensaje;
import cr.ac.una.marcador.util.Respuesta;

import cr.ac.una.marcador.util.wsConsumer;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import javafx.fxml.Initializable;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Format;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class mantEmpleadosController extends Controller implements Initializable {

    boolean isNuevo;
    EmpleadoDto empleado;
    List<Node> requeridos = new ArrayList<>();
    @FXML
    private VBox root;

    @FXML
    private TextField txtFolio;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtCedula;

    @FXML
    private JFXToggleButton tggEsAdministrador;

    @FXML
    private JFXDatePicker dpFechaNacimiento;

    @FXML
    private Label lblFolio;

    @FXML
    private ImageView imgFotoEmpleado;

    @FXML
    private Label lblNombre;

    @FXML
    private JFXButton btnCambiarFoto;

    @FXML
    private JFXButton btnBorrar;

       

    @FXML
    private JFXButton btnNuevo;
    
    @FXML
    private TextField txtContra;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private JFXButton btnGuardar;
    File archivo;
    
    Image image = new Image("file:/cr/ac/una/mantenimiento/icons/USER.png");
    @FXML
    void onAction_btnBorrar(ActionEvent event) throws IOException {
        try {
            if (empleado.getFolio()== null) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar empleado", getStage(), "Debe cargar el empleado que desea eliminar.");
            } else {
                Respuesta respuesta = wsConsumer.getInstance().eliminarEmpleadoFolio(empleado.getFolio());
                if (!respuesta.getEstado()) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar empleado", getStage(), respuesta.getMensaje());
                } else {
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Eliminar empleado", getStage(), "Empleado eliminado correctamente.");
                    nuevoEmpleado();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(mantEmpleadosController.class.getName()).log(Level.SEVERE, "Error eliminando el empleado.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar empleado", getStage(), "Ocurrio un error eliminando el empleado.");
        }

    }

    @FXML
    void onAction_btnCambiarFoto(ActionEvent event) {
        
        JFileChooser selectorArchivos = new JFileChooser();
        selectorArchivos.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imagenes ", "jpg","png");
        selectorArchivos.setFileFilter(filtro);

        selectorArchivos.showOpenDialog(selectorArchivos);
        File file = selectorArchivos.getSelectedFile(); // obtiene el archivo seleccionado
        System.out.println("archivo"+file.toString());
        Image imagen = new Image(file.toURI().toString());
        
        
         try
            {
                BufferedImage bufferimage;
                bufferimage = ImageIO.read(file);
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                ImageIO.write(bufferimage , "jpg" , output);
                byte[] data = output.toByteArray();
                System.out.println("dasdas"+data);
                empleado.setFoto(data);
            }
            catch(IOException ex)
            {
                Logger.getLogger(mantEmpleadosController.class.getName()).log(Level.SEVERE , null , ex);
            }
        
        AppContext.getInstance().set("imagen", imagen);
        AppContext.getInstance().set("fileImage", file);
        AppContext.getInstance().actualizarViewImagen();
    }

     
    @FXML
    void onAction_btnNuevo(ActionEvent event) {
        nuevoEmpleado();
    }

    @Override
    public void initialize() {
        isNuevo = true;

        txtNombre.setTextFormatter(Formato.getInstance().letrasFormat(30));
        txtApellido.setTextFormatter(Formato.getInstance().letrasFormat(30));
        txtCedula.setTextFormatter(Formato.getInstance().cedulaFormat(40));
        txtFolio.setTextFormatter(Formato.getInstance().maxLengthFormat(10));
        txtContra.setTextFormatter(Formato.getInstance().maxLengthFormat(10));
        empleado = new EmpleadoDto();
            nuevoEmpleado();
        indicarRequeridos();

        txtFolio.setDisable(false);
        txtCedula.setDisable(false);
        txtNombre.setDisable(false);
        txtApellido.setDisable(false);
        txtContra.setDisable(true);
        dpFechaNacimiento.setDisable(false);
//        imgFotoEmpleado.setImage((Image)  AppContext.getInstance().get("imagen"));
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imgFotoEmpleado.setImage(null);
        AppContext.getInstance().set("ViewImagePrincipal",imgFotoEmpleado);
//        imgFotoEmpleado.setImage((Image)  AppContext.getInstance().get("imagen"));
        disableAll(true);
    }



    void disableAll(boolean disable){
        
   
//        txtFolio.setText("");
        txtFolio.setDisable(!disable);
        txtCedula.setDisable(disable);
        txtNombre.setDisable(disable);
        txtApellido.setDisable(disable);
        dpFechaNacimiento.setDisable(disable);
        txtContra.setDisable(disable);
    }
    void clearAll(){
        txtFolio.setText("");
        txtFolio.setText("");
        txtCedula.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        dpFechaNacimiento.setPromptText("");
        txtContra.setText("");
    }

    public void indicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtNombre, txtCedula, txtApellido,dpFechaNacimiento /*,foto*/));
    }

    private void nuevoEmpleado(){
        unbindEmpleado();
        empleado = new EmpleadoDto();
        bindEmpleado(true);
        validarAdministrador();
        txtFolio.clear();
        txtFolio.requestFocus();
    }
    

    private void cargarEmpleado(String folio) throws IOException, ClassNotFoundException {
        
            EmpleadoDto emp = (EmpleadoDto) wsConsumer.getInstance().buscarEmpleadoFolio(folio);
            Respuesta respuesta = new Respuesta(true,CodigoRespuesta.CORRECTO,"","","Empleado",emp);
        if (respuesta.getEstado()) {
            unbindEmpleado();
            empleado = (EmpleadoDto) respuesta.getResultado("Empleado");
            System.out.print("llego: " + Arrays.toString(empleado.getFoto()));
            bindEmpleado(false);
            validarAdministrador();
            validarRequeridos();
            
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar empleado", getStage(), respuesta.getMensaje());
        }
    }
    private byte[] convertToBytes(Object object) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(object);
            return bos.toByteArray();
        }
    }
    private void unbindEmpleado() {
           imgFotoEmpleado.setImage(null);
        txtFolio.textProperty().unbind();
        txtCedula.textProperty().unbindBidirectional(empleado.cedula);
        txtNombre.textProperty().unbindBidirectional(empleado.name);
        txtApellido.textProperty().unbindBidirectional(empleado.lastname);
        txtContra.textProperty().unbindBidirectional(empleado.psswr);        
        dpFechaNacimiento.valueProperty().unbindBidirectional(empleado.nacimiento);
        tggEsAdministrador.selectedProperty().unbindBidirectional(empleado.admin);
        if(empleado.getFoto()!=null){
            Image image = new Image(new ByteArrayInputStream(empleado.getFoto()));
            imgFotoEmpleado.setImage(null);
        }
//        imgFotoEmpleado
    }
    

    private void bindEmpleado(boolean nuevo)  {
        if (!nuevo) {
            txtFolio.textProperty().bind(empleado.folio);
        }
        
        txtCedula.textProperty().bindBidirectional(empleado.cedula);
        txtNombre.textProperty().bindBidirectional(empleado.name);
        txtApellido.textProperty().bindBidirectional(empleado.lastname);
        txtContra.textProperty().bindBidirectional(empleado.psswr);
        dpFechaNacimiento.valueProperty().bindBidirectional(empleado.nacimiento);
        tggEsAdministrador.selectedProperty().bindBidirectional(empleado.admin);
        
        if(empleado.getFoto()!=null){
            Image image = new Image(new ByteArrayInputStream(empleado.getFoto()));
            imgFotoEmpleado.setImage(image);
        }
        
       
    }
   public void convert( byte[] data) throws IOException { 
    File myFile =  new File("foto.png");
//            System.out.println("filename is " + file);
            OutputStream out = new FileOutputStream(myFile);
            try {
                out.write(data); // Just dump the database content to disk
                System.out.println(data);
            }
            finally {
                out.close();
            }
            System.out.println("Image file written successfully");
   }

    void validarAdministrador() {
        if (tggEsAdministrador.isSelected()) {
            requeridos.addAll(Arrays.asList(txtContra));
            txtContra.setDisable(false);
        } else {
           requeridos.removeAll(Arrays.asList(txtContra));
//           txtContra.validate();
           txtContra.clear();
           txtContra.setDisable(true);
        }
    }

    public String validarRequeridos() {
        Boolean validos = true;
        String invalidos = "";
        for (Node node : requeridos) {
            if (node instanceof JFXTextField && !((JFXTextField) node).validate()) {
                if (validos) {
                    invalidos += ((JFXTextField) node).getPromptText();
                } else {
                    invalidos += "," + ((JFXTextField) node).getPromptText();
                }
                validos = false;
            } else if (node instanceof JFXPasswordField && !((JFXPasswordField) node).validate()) {
                if (validos) {
                    invalidos += ((JFXPasswordField) node).getPromptText();
                } else {
                    invalidos += "," + ((JFXPasswordField) node).getPromptText();
                }
                validos = false;
            } else if (node instanceof JFXDatePicker && ((JFXDatePicker) node).getValue() == null) {
                if (validos) {
                    invalidos += ((JFXDatePicker) node).getAccessibleText();
                } else {
                    invalidos += "," + ((JFXDatePicker) node).getAccessibleText();
                }
                validos = false;
            } else if (node instanceof JFXComboBox && ((JFXComboBox) node).getSelectionModel().getSelectedIndex() < 0) {
                if (validos) {
                    invalidos += ((JFXComboBox) node).getPromptText();
                } else {
                    invalidos += "," + ((JFXComboBox) node).getPromptText();
                }
                validos = false;
            }
        }
        if (validos) {
            return "";
        } else {
            return "Campos requeridos o con problemas de formato [" + invalidos + "].";
        }
    }

    @FXML
    private void seleccionAdministrador(ActionEvent event) {
        validarAdministrador();
    }

    @FXML
    private void onAction_btnBuscar(ActionEvent event) {
        try {
            cargarEmpleado(txtFolio.getText());
//            disableAll(true);
//            btnNuevo.setText("EDITAR");
//            isNuevo = false;
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(mantEmpleadosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onAction_btnGuardar(ActionEvent event) {
         try {
            String invalidos = validarRequeridos();
           
            if (!invalidos.isEmpty()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar empleado", getStage(), invalidos);
            } else { 
                Image im = (Image)AppContext.getInstance().get("imagen");
                System.out.println(im.toString());
                
                Respuesta respuesta = wsConsumer.getInstance().guardarEmpleado(empleado);
                if (!respuesta.getEstado()) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar empleado", getStage(), respuesta.getMensaje());
                } else {
                    unbindEmpleado();
                    empleado = (EmpleadoDto) respuesta.getResultado("Emp");
                    bindEmpleado(false);
                    
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar empleado", getStage(), "Empleado actualizado correctamente.");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(mantEmpleadosController.class.getName()).log(Level.SEVERE, "Error guardando el empleado.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar empleado", getStage(), "Ocurrio un error guardando el empleado.");
        }
    }

    
    
    private void imageToByte() throws IOException{
           
       
    File file = (File) AppContext.getInstance().get("fileImage");
    foto = new byte[Files.readAllBytes(file.toPath()).length];
    foto = Files.readAllBytes(file.toPath());
//    foto = Base64.getEncoder().encodeToString(bytes);
    System.out.println("BUFFER: " + (Arrays.toString(foto)));   
    empleado.setFoto(foto);
    
//    Image img = (Image)AppContext.getInstance().get("imagen");
//    int w = (int)img.getWidth();
//    int h = (int)img.getHeight();
//
//// Create a new Byte Buffer, but we'll use BGRA (1 byte for each channel) //
//
//    byte[] buf = new byte[(w * h * 4)];
//
///* Since you can get the output in whatever format with a WritablePixelFormat,
//   we'll use an already created one for ease-of-use. */
//    System.out.println("BUFFER: " + buf.toString());   
//    img.getPixelReader().getPixels(0, 0, w, h, PixelFormat.getByteBgraInstance(), buf, 0, w * 4);
//    
//    empleado.setFoto(buf);
    
    
    
    }
    
    public static byte[] toByteArray(BufferedImage bi, String format)
        throws IOException {
//        BufferedImage bi = ImageIO.read(new File("c:\\test\\google.png"));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, format, baos);
        byte[] bytes = baos.toByteArray();
        return bytes;

    }
    
    private byte[] foto;
    
    
    
//    private void byteToFile() throws IOException{
////    ByteArrayInputStream bais = new ByteArrayInputStream(empleado.getFoto());
////    BufferedImage bi = ImageIO.read(bais);
////    File archivoGenerado = new File("tempImage.png");
////    System.out.println(empleado.getFoto());
////    ImageIO.write(bi, "jpg", archivoGenerado);
////    
////    
////    AppContext.getInstance().set("fileImage", archivoGenerado);
////    Image img = new Image(archivoGenerado.toURI().toString());
////    AppContext.getInstance().set("imagen", img);
////        AppContext.getInstance().actualizarViewImagen();
//
//
//        ByteArrayInputStream in = new ByteArrayInputStream(empleado.foto);
//
//
//
//
//
//
//        ImageIO.setUseCache(false);
//        BufferedImage image = ImageIO.read(in);
//        Image imagen = javafx.embed.swing.SwingFXUtils.toFXImage(image, null);
//        imgFotoEmpleado.setImage(imagen);
//        
////        InputStream in = new ByteArrayInputStream(empleado.getFoto());
////        ImageIO.setUseCache(false);
////        image = ImageIO.read(in);
////
////        File outputfile = new File("nuevoNombre.png");
////        ImageIO.setUseCache(false);
////        ImageIO.write(image, "png", outputfile);
//
//
//
//
////                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//                    //ImageIO.setUseCache(false);
////                    ImageIO.write(image.getSubimage(0, 0, 0, 0), "jpg", outputStream);
//
////                    byte[] imageBytes = ((DataBufferByte)image.getData().getDataBuffer()).getData();
//
//                    
//
//
////
////
////            BufferedImage img = createImageFromBytes(empleado.getFoto());
////            img.setData(Raster.createRaster(img.getSampleModel(), new DataBufferByte(img, img.length), new Point() ) );
//
//
//    }
//    private BufferedImage createImageFromBytes(byte[] imageData) {
//    ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
//    try {
//        return ImageIO.read(bais);
//    } catch (IOException e) {
//        throw new RuntimeException(e);
//    }
//}
    
}
