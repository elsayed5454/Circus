package eg.edu.alexu.csd.oop.game.model.Strategy;

public class EasyStrategy implements IStrategy {
    @Override
    public int speed() {
        return 10;
    }

    @Override
    public int controlSpeed() {
        return 15;
    }

    @Override
    public int differenceBetweenPlateAndStick() {
        return 40;
    }

    @Override
    public int differenceBetweenPlateAndPlate() {
        return 60;
    }
}
