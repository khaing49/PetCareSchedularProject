/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project1;

import java.time.LocalDate;

public class RecyclingEvent {
    private String materialType;
    private double weight;
    private LocalDate date;
    private double ecoPoints;
    
    public RecyclingEvent(String materialType,double weight){
        this.materialType=materialType;
        this.weight=weight;
        this.date=LocalDate.now();
        this.ecoPoints=weight*10;
    }
    
    public String getMaterialType(){
        return materialType;
    }
    
    public double getWeight(){
        return weight;
    }
    public LocalDate getDate(){
        return date;
    }
    public double getEcoPoints(){
        return ecoPoints;
    }
    public String toString(){
        return "Material type: "+this.materialType+"\nWeight: "+this.weight+
                "\nDate: "+this.date+"\nEcoPoints: "+this.ecoPoints;
    }
}
