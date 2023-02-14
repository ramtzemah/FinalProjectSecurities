package com.example.finalprojectsecurities;

import java.util.HashMap;

public class ListOfPointsActivitry {
    private HashMap <Integer,Double> listOfPoints;

    public ListOfPointsActivitry(){
        listOfPoints = new HashMap<>();
        initMap(listOfPoints);
    }

    private void initMap(HashMap<Integer, Double> listOfPoints) {
        for (int i = 0; i < 1440; i++) {
            listOfPoints.put(i,0.0);
        }
    }

    public HashMap<Integer,Double> getListOfPoints() {
        return listOfPoints;
    }

    public void setListOfPoints(HashMap<Integer,Double> listOfPoints) {
        this.listOfPoints = listOfPoints;
    }
}
