package sample.models;

import javafx.scene.image.ImageView;

public abstract class Sprite {

    private ImageView image;

    public Sprite(ImageView image) {
        this.image = image;
    }

    public ImageView getImage() {
        return image;
    }

    public void render(int x, int y) {
        //TODO implement render function
        // this method will place an item on the screen at an specified coordinate
        getImage().relocate(x, y);
    }

    public boolean collision(Sprite entity) {
        double wcar = this.getImage().getBoundsInLocal().getWidth()/2;
        double hcar = this.getImage().getBoundsInLocal().getHeight()/2;
        double wpwr = entity.getImage().getBoundsInLocal().getWidth()/2;
        double hpwr = entity.getImage().getBoundsInLocal().getHeight()/2;
        double cxcar = this.getImage().getLayoutX();
        double cycar = this.getImage().getLayoutY();
        double cxpwr = entity.getImage().getLayoutX();
        double cypwr = entity.getImage().getLayoutY();

        if (cxcar > cxpwr) {
            if (cxcar - cxpwr + wpwr <= wcar+wpwr) {
                return Math.abs(cycar - cypwr) <= ((hcar + hpwr));
            }
        } else {
            if (cxpwr- cxcar - wpwr <= wcar+wpwr) {
                return Math.abs(cycar - cypwr) <= ((hcar + hpwr));
            }
        }
        return false;

    }
}
