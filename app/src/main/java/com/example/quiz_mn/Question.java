package com.example.quiz_mn;

public class Question {
    private int id;
    private boolean isTrue;

    public Question(int id, boolean isTrue) {
        this.id = id;
        this.isTrue = isTrue;
    }

    public boolean isTrue() {
        return isTrue;
    }
    public int getId(){
        return id;
    }
}