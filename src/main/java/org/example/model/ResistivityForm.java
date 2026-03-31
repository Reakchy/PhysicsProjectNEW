package org.example.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}