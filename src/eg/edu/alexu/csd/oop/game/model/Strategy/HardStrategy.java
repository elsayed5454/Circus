package eg.edu.alexu.csd.oop.game.model.Strategy;

public class HardStrategy implements IStrategy{
    @Override
    public int speed() {
        return 6;
    }

    @Override
    public int controlSpeed() {
        return 7;
    }

    @Override
    public int differenceBetweenPlateAndStick() {
        return 20;
    }

    @Override
    public int differenceBetweenPlateAndPlate() {
        return 25;
    }
}
