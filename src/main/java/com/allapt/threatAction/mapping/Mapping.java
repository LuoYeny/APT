package com.allapt.threatAction.mapping;


import com.allapt.threatAction.identification.IdentifyService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class Mapping {
    public void doMapping(String text){
        List<SecurityThreatCategory> ontologyList =new OntologyList().getOntologyList();
        System.out.println(ontologyList.size());
        HashMap configMap = new HashMap();
        configMap.put("1","1");
        OntologyParser op = OntologyParser.getInstance(ontologyList,configMap);
        OntologyParser.use_nlp_to_load(text);
    }

    public static void main(String[] args) {
        String text="The Trojan Dimnie is used to steal information from the computer. When the Trojan is executed, it creates the following file: filename1.dll . the Trojan creates the following registry entries ..The Trojan may then perform the following actions: obtain system information, take screenshots, log keystrokes. The Trojan may send the stolen information to the following location:gmail.com/upload.php";
        String text2="APT28 used other victims as proxies to relay command traffic, for instance using a compromised Georgian military email server as a hop point to NATO victims. The group has also used a tool that acts as a proxy to allow C2 even if the victim is behind a router. APT28 has also used a machine to relay and obscure communications between CHOPSTICK and their server.";
        String text3="APT28 has deployed a bootkit along with Downdelph to ensure its persistence on the victim. The bootkit shares code with some variants of BlackEnergy.";
         List<String> strings = new IdentifyService().doIdentification(text3);
         for(String s:strings)
        new Mapping().doMapping(s);
    }
}

