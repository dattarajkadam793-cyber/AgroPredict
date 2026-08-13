package com.agroPredict.demo;


import jakarta.persistence.*;

@Entity
@Table(name ="soildata_table")
public class SoilTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String soilColor;
    private int nitrogen;
    private int phosphorus;
    private int potassium;
    private float ph;
    private int rainfall;
    private int temperature;



    public String getSoilColor(){
        return soilColor;
    }
    public void setSoilColor(String soilColor){
        this.soilColor = soilColor;
    }
    public void setNitrogen(int nitrogen){
        this.nitrogen = nitrogen;
    }
    public void setPhosphorus(int phosphorus){
        this.phosphorus = phosphorus;
    }

    public void setPotassium(int potassium) {
        this.potassium = potassium;
    }

    public void setPh(float ph) {
        this.ph = ph;
    }

    public void setRainfall(int rainfall) {
        this.rainfall = rainfall;
    }
    public void setTemperature(int temperature){
        this.temperature = temperature;
    }
    public Long getId(){
        return id;
    }
    public int getNitrogen(){
        return nitrogen;
    }

    public int getPhosphorus() {
        return phosphorus;
    }

    public int getPotassium() {
        return potassium;
    }

    public float getPh() {
        return ph;
    }

    public int getRainfall() {
        return rainfall;
    }

    public int getTemperature() {
        return temperature;
    }

}
