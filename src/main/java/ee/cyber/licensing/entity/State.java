package ee.cyber.licensing.entity;

import java.util.HashMap;
import java.util.Map;

public enum  State {
    REJECTED(1),
    NEGOTIATED(2),
    WAITING_FOR_SIGNATURE(3),
    ACTIVE(4),
    EXPIRATION_NEARING(5),
    TERMINATED(6);

    private static final Map<Integer, State> states = buildMap();
    private int number;

    State(int i) {
        this.number = i;
    }

    public int getStateNumber(){
        return number;
    }

    public static State getByStateNumber(int number){
        return states.get(number);
    }

    private static Map<Integer, State> buildMap() {
        Map<Integer, State> states = new HashMap<>();
        for (State state : State.values()) {
            states.put(state.getStateNumber(), state);
        }
        return states;
    }

}
