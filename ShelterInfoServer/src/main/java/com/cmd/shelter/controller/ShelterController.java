package com.cmd.shelter.controller;

import com.cmd.shelter.Dto.Shelterinfo;
import com.cmd.shelter.service.Shelterinformationservice;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/api/shelter")
@Slf4j
public class ShelterController {
    @Autowired
    private final Shelterinformationservice shelterinformationservice;

    public ShelterController(Shelterinformationservice shelterinformationservice){
        this.shelterinformationservice = shelterinformationservice;
    }

    @GetMapping("/fetch")
    public String fetchData(){
        try{
            shelterinformationservice.fetch();
            return "Data fetch and saved successfully.";
        }catch (IOException e){
            return "Error occurred while fetching and saving data: " + e.getMessage();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getinfo")
    public List<Shelterinfo> getShelterInfoList() {
        return shelterinformationservice.getAllShelterInfo();
    }

    @GetMapping("/getinfo/length")
    public List<Shelterinfo> getNearestShelters(@RequestParam double latitude, @RequestParam double longitude) {
        List<Shelterinfo> nearestShelters = shelterinformationservice.findNearestShelters(latitude, longitude);
        return nearestShelters;
    }



}
