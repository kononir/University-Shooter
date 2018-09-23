package bsuir.vlad.universityshooter.application;

public interface Movable {
    void updateMovementAngle(double newMovementAngle);

    void moveLeft(int movementX);

    void moveRight(int movementX);

    void moveUp(int movementY);

    void moveDown(int movementY);
}
