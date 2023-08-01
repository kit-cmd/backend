package com.cmd.shelter.Dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Shelterinfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sido_name;
    private String sigungu_name;
    private String remarks;
    private String shel_nm;
    private String address;
    private Double lon;
    private Double lat;
    private Double distance;
    private Long shel_av;
    private String shel_div_type;
    private Long height;

    protected Shelterinfo(){}
    public Shelterinfo(Long id, String sido_name, String sigungu_name, String remarks, String shel_nm, String address, Double lon, Double lat, Long shel_av, String shel_div_type, Long height) {
        this.id = id;
        this.sido_name = sido_name;
        this.sigungu_name = sigungu_name;
        this.remarks = remarks;
        this.shel_nm = shel_nm;
        this.address = address;
        this.lon = lon;
        this.lat = lat;
        this.shel_av = shel_av;
        this.shel_div_type = shel_div_type;
        this.height = height;
    }

    public Double getLon() {
        return lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
