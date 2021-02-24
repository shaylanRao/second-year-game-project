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

    /**
     * This function will position this Sprite at the chosen coordinates
     * @param x
     * @param y
     * */
    public void render(int x, int y) {
        // this method will place an item on the screen at an specified coordinate
        getImage().relocate(x, y);
    }

    /**
     * This function checks whether the coordinates of this Sprite intersects with the sprite passed as the parameter
     * @param entity
     * */
    public boolean collision(Sprite entity) {
        double widthCar = this.getImage().getBoundsInLocal().getWidth()/2;
        double heightCar = this.getImage().getBoundsInLocal().getHeight()/2;
        double widthPowerup = entity.getImage().getBoundsInLocal().getWidth()/2;
        double heightPowerup = entity.getImage().getBoundsInLocal().getHeight()/2;
        double cxCar = this.getImage().getLayoutX();
        double cyCar = this.getImage().getLayoutY();
        double cxPowerup = entity.getImage().getLayoutX();
        double cyPowerup = entity.getImage().getLayoutY();

        boolean hasCollided = Math.abs(cyCar - cyPowerup) <= ((heightCar + heightPowerup));
        if (cxCar > cxPowerup) {
            if (cxCar - cxPowerup + widthPowerup <= widthCar+widthPowerup) {
                return hasCollided;
            }
        } else {
            if (cxPowerup- cxCar - widthPowerup <= widthCar+widthPowerup) {
                return hasCollided;
            }
        }
        return false;

    }
}
