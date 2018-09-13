import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;

public class ImageViewMap extends HashMap<String, ImageView> {
    public void fillImageViewMap(){
        addNewImageView("student_idle_knife");
        addNewImageView("student_move_knife");
        addNewImageView("student_meleeattack_knife");
    }

    private void addNewImageView(String imageName){
        Image playersImage = new Image(getClass().getResourceAsStream("resourses/" + imageName + ".png"));
        ImageView playersImageView = new ImageView(playersImage);
        this.put(imageName, playersImageView);
    }
}
