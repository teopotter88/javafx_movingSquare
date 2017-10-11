package application;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

public class square extends Application{
	//An item in the scene graph is called a node
	private Node rect;
	private Image rectImg;
	private static final String HERO_IMAGE_LOC =
            "https://yt3.ggpht.com/-YOY03cZDNyQ/AAAAAAAAAAI/AAAAAAAAAAA/vftfg7SzqWw/s100-c-k-no-mo-rj-c0xffffff/photo.jpg";
	boolean running, goNorth, goSouth, goEast, goWest;
	private static final double W = 600, H = 400;
	public void start(Stage primaryStage) throws Exception{
		//define an image for the node
		rectImg = new Image(HERO_IMAGE_LOC);
		rect = new ImageView(rectImg);
		Group root = new Group(rect);
		moveHeroTo(W / 2, H / 2);
		Scene scene = new Scene(root,W,H);
		//when arrow keys are pressed, the position of the node changes
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:    goNorth = true; break;
                    case DOWN:  goSouth = true; break;
                    case LEFT:  goWest  = true; break;
                    case RIGHT: goEast  = true; break;
                    case SHIFT: running = true; break;
                }
           }
	});
		//when arrow keys are released, the noed stops moving
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:    goNorth = false; break;
                    case DOWN:  goSouth = false; break;
                    case LEFT:  goWest  = false; break;
                    case RIGHT: goEast  = false; break;
                    case SHIFT: running = false; break;
                }
            }
	});
		primaryStage.setScene(scene);
		primaryStage.show();
		//an animation is created for the node, the mathematical
		//changes in the position are transfered in real time
		AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                int dx = 0, dy = 0;
 
                if (goNorth) dy -= 1;
                if (goSouth) dy += 1;
                if (goEast)  dx += 1;
                if (goWest)  dx -= 1; 
                if (running) { dx *= 3; dy *= 3; }
                moveHeroBy(dx, dy);
            }
        };
        timer.start();
	}
	//changing the node's position
	private void moveHeroBy(int dx, int dy) {
        if (dx == 0 && dy == 0) return;
 
        final double cx = rect.getBoundsInLocal().getWidth()  / 2;
        final double cy = rect.getBoundsInLocal().getHeight() / 2;
 
        double x = cx + rect.getLayoutX() + dx;
        double y = cy + rect.getLayoutY() + dy;
 
        moveHeroTo(x, y);
    }
	//the maximum and minimum limits, which is the scene size
	private void moveHeroTo(double x, double y) {
        final double cx = rect.getBoundsInLocal().getWidth()  / 2;
        final double cy = rect.getBoundsInLocal().getHeight() / 2;
 
        if (x - cx >= 0 &&	x + cx <= W && y - cy >= 0 && y + cy <= H) {
            rect.relocate(x - cx, y - cy);
        }
    }
	//launch the app
	public static void main(String[] args) {
		launch(args);
	}
}
