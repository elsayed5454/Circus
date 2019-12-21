package eg.edu.alexu.csd.oop.game.model.Strategy;

public class MediumStrategy implements IStrategy {
    @Override
    public int speed() {
        return 8;
    }

    @Override
    public int controlSpeed() {
        return 10;
    }

    @Override
    public int differenceBetweenPlateAndStick() {
        return 30;
    }

    @Override
    public int differenceBetweenPlateAndPlate() {
        return 40;
    }
}
