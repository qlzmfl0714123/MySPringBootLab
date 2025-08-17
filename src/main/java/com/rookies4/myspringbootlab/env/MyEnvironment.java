package com.rookies4.myspringbootlab.env;

public class MyEnvironment {
    private String mode;
    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }
    @Override public String toString() { return "MyEnvironment{mode='" + mode + "'}"; }
}