package com.yaozou.pattern.behavior;

/**
 * @author yaozou
 */
public class StateMethod {
    public static void main(String[] args) {
        Context context = new Context();

        StartState startState = new StartState();
        startState.doAction(context);

        System.out.println(context.getState().toString());

        StopState stopState = new StopState();
        stopState.doAction(context);

        System.out.println(context.getState().toString());
    }
}

class Context{
    private State state;
    public Context(){
        state = null;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }
}

interface State{
    void doAction(Context context);
}

class StartState implements State{
    @Override
    public void doAction(Context context) {
        System.out.println("Player is in start state");
        context.setState(this);
    }
    public String toString(){
        return "Start State";
    }
}
class StopState implements State{
    @Override
    public void doAction(Context context) {
        System.out.println("Player is in Stop state");
        context.setState(this);
    }
    public String toString(){
        return "Stop State";
    }
}