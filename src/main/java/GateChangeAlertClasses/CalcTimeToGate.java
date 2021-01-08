package GateChangeAlertClasses;

public class CalcTimeToGate {

    static double calcTimeToGate(String passenger, int location, double delay, double timeToGate) {
        double doubleTimeToGate = 0;

        switch(location){
            case 0:
                return delay+timeToGate+15.0;
                //not at airport

            case 1:
                return delay+timeToGate+10.0;
                //at airport

            case 2:
                return timeToGate;
                //in security area
        }
        return doubleTimeToGate;
    }
}
