package org.example.model;

public class ResistivityForm {

    private String metal;

    private Double t1;
    private Double t2;
    private Double alpha;

    // удельное сопротивление при начальной температуре
    private Double rho1;

    // длина нити, м
    private Double length;

    // диаметр нити, мм
    private Double diameterMm;

    // сопротивление при начальной температуре
    private Double r1;

    // результаты
    private Double rho2;
    private Double deltaRho;
    private Double percent;
    private Double dT;
    private Double k;
    private Double areaMm2;
    private Double r2;
    private String error;

    public boolean isValidBase() {
        return t1 != null && t2 != null && alpha != null;
    }

    public String getMetal() {
        return metal;
    }

    public void setMetal(String metal) {
        this.metal = metal;
    }

    public Double getT1() {
        return t1;
    }

    public void setT1(Double t1) {
        this.t1 = t1;
    }

    public Double getT2() {
        return t2;
    }

    public void setT2(Double t2) {
        this.t2 = t2;
    }

    public Double getAlpha() {
        return alpha;
    }

    public void setAlpha(Double alpha) {
        this.alpha = alpha;
    }

    public Double getRho1() {
        return rho1;
    }

    public void setRho1(Double rho1) {
        this.rho1 = rho1;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getDiameterMm() {
        return diameterMm;
    }

    public void setDiameterMm(Double diameterMm) {
        this.diameterMm = diameterMm;
    }

    public Double getR1() {
        return r1;
    }

    public void setR1(Double r1) {
        this.r1 = r1;
    }

    public Double getRho2() {
        return rho2;
    }

    public void setRho2(Double rho2) {
        this.rho2 = rho2;
    }

    public Double getDeltaRho() {
        return deltaRho;
    }

    public void setDeltaRho(Double deltaRho) {
        this.deltaRho = deltaRho;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public Double getDT() {
        return dT;
    }

    public void setDT(Double dT) {
        this.dT = dT;
    }

    public Double getK() {
        return k;
    }

    public void setK(Double k) {
        this.k = k;
    }

    public Double getAreaMm2() {
        return areaMm2;
    }

    public void setAreaMm2(Double areaMm2) {
        this.areaMm2 = areaMm2;
    }

    public Double getR2() {
        return r2;
    }

    public void setR2(Double r2) {
        this.r2 = r2;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}