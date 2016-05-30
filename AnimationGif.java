package gif;

import java.awt.image.BufferedImage;


import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Just for Effect in order to appear a dancing bear when we collect all the items. Only for fun!!
 * @author Aris
 */

public class AnimationGif extends Application {
    /*
     * Custom class just for gif images
     */
    public class AnimatedGif extends CustomAnimation {

        public AnimatedGif( String filename, double durationMs) {

            GifDecoder d = new GifDecoder();
            d.read( filename);

            Image[] sequence = new Image[ d.getFrameCount()];
            for( int i=0; i < d.getFrameCount(); i++) {

                WritableImage wimg = null;
                BufferedImage bimg = d.getFrame(i);
                sequence[i] = SwingFXUtils.toFXImage( bimg, wimg);

            }

            super.init( sequence, durationMs);
        }

    }
    /*
     * Custom class just for gif images
     */
    public class CustomAnimation extends Transition {

        private ImageView imageView;
        private ImageView backup;
        private int count;

        private int lastIndex;
        private int arisCounter;
        private Image[] sequence;

        private CustomAnimation() {
        }

        public CustomAnimation( Image[] sequence, double durationMs) {
            init( sequence, durationMs);
        }

        private void init( Image[] sequence, double durationMs) {
            this.imageView = new ImageView(sequence[0]);
            //to place the bear above the map
            this.imageView.setY(169);
            //to decrease a bit the size of it
            this.imageView.setScaleX(0.7);
            this.imageView.setScaleY(0.7);
            this.sequence = sequence;
            this.count = sequence.length;
            setCycleCount(1);
            setCycleDuration(Duration.millis(durationMs));
            setInterpolator(Interpolator.LINEAR);
            this.arisCounter = 0;
        }
        /* to enable and disable the bear */
        public void disableBear(){
        	this.imageView.setDisable(true);
        	this.imageView.setVisible(false);
        }
        public void enableBear(){
        	this.imageView.setDisable(false);
        	this.imageView.setVisible(true);
        }
        protected void interpolate(double k) {

            final int index = Math.min((int) Math.floor(k * count), count - 1);
            if (index != lastIndex) {
                imageView.setImage(sequence[index]);
                lastIndex = index;
            }
            // My custom method in order to close our dancing after its done
            if (k==1.0)
            	this.arisCounter += 1;
            if (this.arisCounter == 5){
            	disableBear();
            	this.arisCounter = 0;
            }
        }

        public ImageView getView() {
            return imageView;
        }

    }
	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
