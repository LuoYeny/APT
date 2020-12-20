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
        System.out.println(OntologyParser.getResult());
    }

    public static void main(String[] args) {
        String text="The Trojan Dimnie is used to steal information from the computer. When the Trojan is executed, it creates the following file: filename1.dll . the Trojan creates the following registry entries ..The Trojan may then perform the following actions: obtain system information, take screenshots, log keystrokes. The Trojan may send the stolen information to the following location:gmail.com/upload.php";
        String text2="APT28 used other victims as proxies to relay command traffic, for instance using a compromised Georgian military email server as a hop point to NATO victims. The group has also used a tool that acts as a proxy to allow C2 even if the victim is behind a router. APT28 has also used a machine to relay and obscure communications between CHOPSTICK and their server.";
        String text3="APT28 has deployed a bootkit along with Downdelph to ensure its persistence on the victim. The bootkit shares code with some variants of BlackEnergy.";
        String text4="APT28 has used a brute-force/password-spray tooling that operated in two modes: in password-spraying mode it conducted approximately four authentication attempts per hour per targeted account over the course of several days or weeks.";
        String text5="These attacks were carried out using spear-phishing attacks against the target organizations, using messages related to diplomatic discussions in the Asia-Pacific region.\n" +
                "\n" +
                "The spear-phishing email contains a malicious document as an attachment, which exploits CVE-2012-0158, a dated vulnerability in Windows common control. This vulnerability was also used in other targeted attacks, most recently the “Safe” campaign that compromised several government agencies, media outlets and other institutions.";
        List<String> strings = new IdentifyService().doIdentification(text5);
         for(String s:strings)
        new Mapping().doMapping(s);
    }
}

