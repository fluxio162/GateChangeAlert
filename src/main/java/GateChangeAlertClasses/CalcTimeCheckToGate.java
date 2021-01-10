package GateChangeAlertClasses;

public class CalcTimeCheckToGate {

    static public int calcTimeCheckToGate(int gate) {

        switch (gate){
            case 1:
                return 10;

            case 2:
                return 7;

            case 3:
                return 5;

            default:
                return 8;
        }
    }
}
