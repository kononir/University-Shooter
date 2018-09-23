package bsuir.vlad.universityshooter.application;

import javafx.scene.layout.Pane;

public class BulletsView implements Movable {
    private Pane bulletsPane;
    private double bulletsPaneAngle;

    public BulletsView(double bulletsPaneAngle){
        this.bulletsPaneAngle = bulletsPaneAngle;
    }

    public void updateBulletsView(){

    }

    @Override
    public void updateMovementAngle(double newMovementAngle) {

    }

    @Override
    public void moveLeft(int movementX) {

    }

    @Override
    public void moveRight(int movementX) {

    }

    @Override
    public void moveUp(int movementY) {

    }

    @Override
    public void moveDown(int movementY) {

    }
}
