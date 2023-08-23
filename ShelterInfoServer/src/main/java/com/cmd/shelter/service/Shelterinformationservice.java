package com.cmd.shelter.service;
import java.lang.Math;
import com.cmd.shelter.Dto.Shelterinfo;
import com.cmd.shelter.repository.ShelterInfoRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class Shelterinformationservice {
    private final static String API_KEY = "Ub%2FvAXeQN4Kqo5ceY%2F1PkDotgnhauEJFFM1u2%2BviMATO6WQCqk44aUaLMSOquEL5AZc7GgOWuVoEHrnU0A%2BJ8w%3D%3D";
    private final static String API_URL = "http://apis.data.go.kr/1741000/TsunamiShelter3/getTsunamiShelter1List";
    @Autowired
    private ShelterInfoRepository infoRepository;
    public void fetch() throws IOException, ParseException {


        String urlBuilder = API_URL + "?" + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + "=" + API_KEY +
                "&" + URLEncoder.encode("pageNo", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("1", StandardCharsets.UTF_8) +
                "&" + URLEncoder.encode("numOfRows", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("700", StandardCharsets.UTF_8) +
                "&" + URLEncoder.encode("type", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("json", StandardCharsets.UTF_8);

        URL url = new URL(urlBuilder);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        String result="";
        try{
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            result = rd.readLine();
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject=(JSONObject)jsonParser.parse(result);
            JSONArray TsunamiShelter=(JSONArray) jsonObject.get("TsunamiShelter");
//            System.out.println(TsunamiShelter);
            for(Object obj:TsunamiShelter){
                JSONObject jsonobject = (JSONObject) obj;
                if (jsonobject!=null){
                    JSONArray infoArr = (JSONArray) jsonobject.get("row");
                    if(infoArr!=null){
//                        System.out.println(infoArr);
                        for(int i=0;i<infoArr.size();i++){
                        JSONObject tmp = (JSONObject)infoArr.get(i);
                            System.out.println(tmp.get("height"));
                        if(tmp!=null){
                            Shelterinfo infoObj=new Shelterinfo(i+(long)1, (String)tmp.get("sido_name"),(String)tmp.get("sigungu_name"),
                                    (String)tmp.get("remarks"), (String)tmp.get("shel_nm"), (String)tmp.get("address"), (Double)tmp.get("lon"),
                                    (Double)tmp.get("lat"), (long)tmp.get("shel_av"),(String)tmp.get("shel_div_type"),
                                    (long) tmp.get("height") );
                            System.out.println("Hello");
                            infoRepository.save(infoObj);
                        }
                    }
                    }
                }

            }

        }catch (IOException e){
            e.printStackTrace();
        }
        conn.disconnect();
    }

    public List<Shelterinfo> getAllShelterInfo() {
        return infoRepository.findAll();
    }

    public List<Shelterinfo> getpagedShelterInfo(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Shelterinfo> shelterPage = infoRepository.findAll(pageable);
        return shelterPage.getContent();
    }

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // 지구의 반지름 (단위: km)

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    public void updateDistance(List<Shelterinfo> shelters, double userLat, double userLon) {
        for (Shelterinfo shelter : shelters) {
            double distance = calculateDistance(userLat, userLon, shelter.getLat(), shelter.getLon());
            shelter.setDistance(distance);
        }
    }

    public List<Shelterinfo> findNearestShelters(double latitude, double longitude) {
        List<Shelterinfo> allShelters = infoRepository.findAll();

        updateDistance(allShelters, latitude, longitude);

        allShelters.sort(Comparator.comparingDouble(Shelterinfo::getDistance));

        int maxResults = 200;
        return allShelters.subList(0, Math.min(maxResults, allShelters.size()));
    }
}
