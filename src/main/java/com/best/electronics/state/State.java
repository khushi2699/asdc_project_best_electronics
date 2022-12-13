package com.best.electronics.state;

public abstract class State {

    protected String status;

    protected String nextPage;

    protected State(String type){
        setStatus();
        setNextPage(type);
    }

    public String getStatus() {
        return status;
    }

    public abstract void setStatus();


    public String getNextPage() {
        return nextPage;
    }

    public abstract void setNextPage(String type);
}
