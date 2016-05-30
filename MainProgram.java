import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;
import world.ArisWorld;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import controller.Controller;
import controller.WorldController;
import gif.GifDecoder;
import inputs.SingletonProperties;
import gif.AnimationGif;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 * MainProgram that extends Application and has the ability to start our program and initialize our javaFX.
 * @author Aris
 *
 */
public class MainProgram extends Application {

	public void start(Stage stage) {

		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			String viewerFxml = "gui.fxml";
			AnchorPane page = (AnchorPane) fxmlLoader.load(this.getClass().getResource(viewerFxml).openStream());
			//lets add an image
			
//			URL resource = cldr.getResource("MyImage.png");
//			Image image = new Image(resource.toString());
//		    ImageView imageView = new ImageView();
//		    imageView.setFitWidth(200);
//		    imageView.setFitHeight(200);
//		    imageView.relocate(0, 0);
//		    imageView.setImage(image);
//		    page.getChildren().add(imageView);
			
			// Just for loading Gifs!!
			AnimationGif arisAnimation = new AnimationGif();
			String animPath = SingletonProperties.getInstance().getProperty("animation_path");
			AnimationGif.CustomAnimation anim =  arisAnimation.new AnimatedGif(getClass().getResource(animPath).toExternalForm(), 1000);
	        page.getChildren().addAll( anim.getView());
			
	        Scene scene = new Scene(page);
			stage.setScene(scene);
			
			Controller controller = (WorldController) fxmlLoader.getController();
			
			// for scalability we can not use casting and just change our controller from .fxml file! 
			//Controller controller = fxmlLoader.getController();

			//we parse not only our scene but also our bear anim in order to handle it from our controller
			controller.Initialise(scene,anim);
			
			stage.getIcons().add(new Image("/images/icon.png"));
			stage.setTitle("Aris Kots Virtual World");
			stage.setResizable(false);
			stage.sizeToScene();
			stage.show();
        
		} catch (IOException ex) {
		   Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		   System.exit(1);
		}
	}
	
    public static void main(String args[]) {
     	launch(args);
     	System.exit(0);
    }
}
