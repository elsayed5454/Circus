package eg.edu.alexu.csd.oop.game.model.Strategy;

public interface IStrategy {

    //Plates falling frequency
    int speed ();

    //Clown movement frequency
    int controlSpeed ();

    //distance accepted between center of the plate and the center of the stick
    int differenceBetweenPlateAndStick ();

    //distance accepted between center of the plate and the center of plate below it
    int differenceBetweenPlateAndPlate ();

}
