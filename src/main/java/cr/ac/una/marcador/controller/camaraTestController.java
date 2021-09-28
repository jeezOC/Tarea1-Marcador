package cr.ac.una.marcador.controller;

import com.github.sarxos.webcam.Webcam;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class camaraTestController extends Controller implements Initializable {

    @FXML
    private HBox root;

    @FXML
    private AnchorPane paneCamera;
    @FXML
    private ImageView wcView;
    @FXML
    private JFXButton btnInitCamera;

    @FXML
    private JFXButton btnReintentar;

    @FXML
    private JFXButton btnConfirmar;

    boolean isOn = false;
    Webcam webcam;
//    WebcamPanel wcPanel = new WebcamPanel(webcam);
//    private boolean stopCamera = false;
//    private BufferedImage grabbedImage;
//    final private SimpleObjectProperty<Image> imageProp = new SimpleObjectProperty<Image>();
    @FXML
    void onAction_btnConfirmar(ActionEvent event) throws IOException {
        webcam.close();
//        FlowController.getInstance().hide();
    }
//    private WebcamPanel panel = null;
//    private Webcam webcam = null;
//    private ImageView imagev = null;
    @FXML
    void onAction_btnInitCamera(ActionEvent event) throws IOException {
        if(!isOn){

            webcam = Webcam.getWebcams().get(1);
            isOn = true;
            btnInitCamera.setText("DETENER");
            webcam.open();
        }else{
            isOn = false;
            btnInitCamera.setText("INICIAR");
            webcam = Webcam.getWebcams().get(1);
            webcam.close();

        }
//        wcPanel.setImageSizeDisplayed(true);
//
//        stopCamera  = false;
//        Task<Void> task = new Task<Void>() {
//            @Override
//            protected Void call() throws Exception {
//                while (!stopCamera) {
//                    try {
//                        if ((grabbedImage = webcam.getImage()) != null) {
//
//                            Platform.runLater(new Runnable() {
//                                @Override
//                                public void run() {
//                                    final Image image = convertToFxImage(grabbedImage);
//                                    imageProp.set(image);
//                                }
//                            });
//
//                            grabbedImage.flush();
//
//                        }
//                    } catch (Exception e) {
//                    } finally {
//
//                    }
//
//                }
//
//                return null;
//
//            }
//
//        };
//        Thread th = new Thread(task);
//        th.setDaemon(true);
//        th.start();
//        wcView.setFitHeight(200);
//        wcView.setFitWidth(200);
//        wcView.prefHeight(200);
//        wcView.prefWidth(200);
//        wcView.setPreserveRatio(true);
//        wcView.imageProperty().bind(imageProp);

//        Task<Void> webCamTask = new Task<Void>() {
//
//            @Override
//            protected Void call() throws Exception {
//
//                if (webCam != null) {
//                    disposeWebCamCamera();
//                }
//
//                webCam = Webcam.getWebcams().get(webCamIndex);
//                webCam.open();
//
//                startWebCamStream();
//
//                return null;
//            }
//        };
//
//        Thread webCamThread = new Thread(webCamTask);
//        webCamThread.setDaemon(true);
//        webCamThread.start();
//
//        bottomCameraControlPane.setDisable(false);
//        btnCamreaStart.setDisable(true);

    }

    @FXML
    void onAction_btnReintentar(ActionEvent event) throws IOException, InterruptedException {
        if(isOn) {

//            File outputFile = new File("src/main/resources/cr/ac/una/mantenimiento/icons/photo.png");;
            ImageIO.write(webcam.getImage(), "PNG", new File("src/main/resources/cr/ac/una/mantenimiento/icons/photo.png"));
            Thread.sleep(3000);
            Image newPhoto = new Image(getClass().getResource("/cr/ac/una/mantenimiento/icons/photo.png").toExternalForm());
            this.paneCamera.getChildren().removeAll();
            this.wcView = new ImageView();
            this.wcView.setFitHeight(200);
            this.wcView.setFitWidth(200);
            this.wcView.setX(50);
            this.wcView.setY(50);
            this.wcView.setId("wcView");

            this.wcView.setImage(newPhoto);
            this.paneCamera.getChildren().add(wcView);
            btnReintentar.setText("REINTENTAR");
        }else{
            //mostrar mensaje camara apagada
        }
    }
    @Override
    public void initialize() {


    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        isOn=false;

//        outputFile.getParentFile().mkdirs();
    }
//    private static javafx.scene.image.Image convertToFxImage(BufferedImage image) {
//        WritableImage wr = null;
//        if (image != null) {
//            wr = new WritableImage(image.getWidth(), image.getHeight());
//            PixelWriter pw = wr.getPixelWriter();
//            for (int x = 0; x < image.getWidth(); x++) {
//                for (int y = 0; y < image.getHeight(); y++) {
//                    pw.setArgb(x, y, image.getRGB(x, y));
//                }
//            }
//        }
//        return new ImageView(wr).getImage();
//    }
}
