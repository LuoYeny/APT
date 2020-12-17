package com.allapt.nlp.ontologyParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OntologyList {
    public List<SecurityThreatCategory> getOntologyList()   {
        List<SecurityThreatCategory> list = new ArrayList<>();
        File dir = new File("D:\\myJava\\allApt\\src\\main\\resources\\app\\ttpdrill\\ontology");
        BufferedReader br = null;
        System.out.println("dir  " + dir);
        File[] directoryListing = dir.listFiles();
        try {
            if (directoryListing != null) {
                for (File child : directoryListing) {
                    if (child.isDirectory())
                        continue;
                    // Do something with child
                    SecurityThreatCategory securityThreatCategory = new SecurityThreatCategory();
                    securityThreatCategory.setCode(child.getName());
                  //  System.out.println(child.getName());
                    br = new BufferedReader(new FileReader(child));
                    String st;
                    while ((st = br.readLine()) != null) {
                        if(securityThreatCategory.getName()==null)
                        securityThreatCategory.setName( st);
                        else  securityThreatCategory.setName(securityThreatCategory.getName() + st);
                    }
                 //   System.out.println(securityThreatCategory.getName());
                    list.add(securityThreatCategory);
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        new OntologyList().getOntologyList();
    }
}
