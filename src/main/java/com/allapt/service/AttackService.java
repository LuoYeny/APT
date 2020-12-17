package com.allapt.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.allapt.dao.attack.*;
import com.allapt.dao.capec.CapecRepository;
import com.allapt.entity.CVE;
import com.allapt.entity.Group;
import com.allapt.entity.attack.*;
import com.allapt.entity.capec.Capec;
import com.allapt.entity.capec.Mitigations;
import com.allapt.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 罗叶妮
 * @version 1.0
 * @date 2020/10/15 14:33
 */
@Service
public class AttackService {
    @Autowired
    private AttackMitigationsRepository attackMitigationsRepository;
    @Autowired
    private AttackPatternRepository attackPatternRepository;
    @Autowired
    private DataSourcesRepository dataSourcesRepository;
    @Autowired
    private DetectionRepository detectionRepository;
    @Autowired
    private PlatformRepository platformRepository;
    @Autowired
    private SoftwareRepository softwareRepository;
    @Autowired
    private CVERepository cveRepository;
    @Autowired
    private CapecRepository capecRepository;
    @Autowired
    private GroupRepository groupRepository;




    public String doAll() throws IOException {
        File[] files =getFiles("src/main/resources/apt");
        for(File file:files){
            doAttackJosn(file);
        }

        return "sucess";
    }




    public String doAttackJosn(File file) throws IOException {
        System.out.println(file.getName());
        JSONArray attackArr= JsonUtil.readJsonFromClassPath("apt/"+file.getName(),JSONArray.class);
        JSONObject jsonObject= JSONObject.parseObject(attackArr.get(0).toString()) ;
        Group group = new Group();
        group.setName(jsonObject.get("apt_name").toString());
        group.setDescription(jsonObject.get("apt_description").toString());
        //software
        JSONArray softwareArr=jsonObject.getJSONArray("software_item");
        List<Software> softwareList ;
        if(softwareArr!=null){
            softwareList=getSoftwareList(softwareArr);
            group.setSoftwareList(softwareList);
        }

        //AttackPattern

        List<AttackPattern> attackPatternList=new ArrayList<>() ;
        for (int i = 1; i <attackArr.size() ; i++) {

            jsonObject= JSONObject.parseObject(attackArr.get(i).toString()) ;
            //attack
            AttackPattern attackPattern =new AttackPattern();
            attackPattern.setName(jsonObject.get("attack_pattern_name").toString());
            attackPattern.setDescription(jsonObject.get("attack_pattern_description").toString());


            //cve
            String descriptionStr=jsonObject.get("attack_pattern_description").toString();
            List<CVE> cveList =cveMatch(descriptionStr);
            attackPattern.setCveList(cveList);

            //dataSources
            if(jsonObject.get("attack_pattern_DataSources")!=null){
                String dataSourcesStr=jsonObject.get("attack_pattern_DataSources").toString();
                DataSources dataSources=new DataSources();
                dataSources.setName(attackPattern.getName()+"_dataSources");
                dataSources.setDescription(dataSourcesStr);
                dataSourcesRepository.save(dataSources);
                attackPattern.setDataSources(dataSources);
            }

            //capec
            if(jsonObject.get("attack_pattern_CAPEC_ID")!=null){
                String CAPECStr=jsonObject.get("attack_pattern_CAPEC_ID").toString();
                String capecId=CAPECStr.split("-")[1];

                Capec capec =capecRepository.findByCapecId(capecId);
            //    System.out.println(capecId+" "+capec);
                attackPattern.setCapec(capec);
            }



            //platform

            List<Platform> platformList;
            if(jsonObject.get("attack_pattern_platforms")!=null){
                String platformStr=jsonObject.get("attack_pattern_platforms").toString();
                platformList=new ArrayList<>();
                String[] platformArr=platformStr.split(",");
//                for (int j = 0; j <platformArr.length ; j++) {
//                    System.out.println(platformArr[j]+" ");
//                }

                for(String platform:platformArr){
                    Platform platformNode=platformRepository.findByPlatformName(platform);
                  //  if(platformNode!=null)
                 //  System.out.println(platformNode);
                    if(platformNode==null){
                        platformNode=new Platform();
                        platformNode.setPlatformName(platform);
                        platformRepository.save(platformNode);
                    }
                    platformList.add(platformNode);
                }
                attackPattern.setPlatformList(platformList);
            }

            //detection
            String detectionStr=jsonObject.get("attack_pattern_detection").toString();
            if(detectionStr!=null){
                Detection detection=new Detection();
                detection.setName(attackPattern.getName()+"_detection");
                detection.setDescription(detectionStr);
                detectionRepository.save(detection);
                attackPattern.setDetection(detection);
            }


            //mitigations
            JSONArray mitigationsArr=jsonObject.getJSONArray("attack_pattern_mitigations");
            if(mitigationsArr!=null){
                List<AttackMitigations> attackMitigationsList =getMitigationsList(mitigationsArr);
                attackPattern.setAttackMitigationsList(attackMitigationsList);
            }

                attackPatternList.add(attackPattern);
                attackPatternRepository.save(attackPattern);

        }
        group.setAttackPatternList(attackPatternList);
        groupRepository.save(group);


      //  System.out.println(group);
        return "sucess";
    }
    public List<AttackMitigations> getMitigationsList(JSONArray mitigationsArr){
        List<AttackMitigations> attackMitigationsArrayList =new ArrayList<>();
        for (int i = 0; i <mitigationsArr.size() ; i++) {
            JSONObject jsonObject= mitigationsArr.parseObject(mitigationsArr.get(i).toString()) ;

            AttackMitigations attackMitigations =new AttackMitigations();
            attackMitigations.setName(jsonObject.get("mitigations_name").toString());
            attackMitigations.setDescription(jsonObject.get("mitigations_description").toString());
            attackMitigationsRepository.save(attackMitigations);
            attackMitigationsArrayList.add(attackMitigations);
        }

        return attackMitigationsArrayList;
    }
    public List<Software> getSoftwareList(JSONArray softwareArr){
        List<Software> softwareList =new ArrayList<>();
        for (int i = 0; i <softwareArr.size() ; i++) {
           JSONObject jsonObject= softwareArr.parseObject(softwareArr.get(i).toString()) ;

            Software software =new Software();
            software.setName(jsonObject.get("software_name").toString());
            software.setDescription(jsonObject.get("software_description").toString());
            softwareRepository.save(software);
            softwareList.add(software);
        }

        return softwareList;
    }




    public List<CVE> cveMatch(String description){
        String reg = "CVE-\\d{4}-[0-9]{4,5}";//定义正则表达式

        Pattern patten = Pattern.compile(reg);//编译正则表达式
        Matcher matcher = patten.matcher(description);// 指定要匹配的字符串

        List<String> cveStrList = new ArrayList<>();

        while (matcher.find()) { //此处find（）每次被调用后，会偏移到下一个匹配
            cveStrList.add(matcher.group());//获取当前匹配的值
        }

       // System.out.println(cveStrList);

        List<CVE> cveList = new ArrayList<>();
        for(String str:cveStrList){
            CVE cve =cveRepository.findByCVE(str);
            cveList.add(cve);
        }
      //  System.out.println(cveList);
        return cveList;

    }





    public File[] getFiles(String path){
        List<String> list = new ArrayList<>();
        File file = new File(path);
        File[] files = file.listFiles();
        for (File file1:files)
            System.out.println(file1.getName());

        return files;
    }


    public static void main(String[] args) throws IOException {
        new AttackService().getFiles("src/main/resources/apt");
    }


}
