package com.allapt.ner;

import com.allapt.util.TextUtill;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.util.CoreMap;

import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NerService {

    public static List<CoreMap> sentences;
    public static StanfordCoreNLP pipeline;


    public static void init() {
        // set up pipeline properties
        Properties props = new Properties();
        // set the list of annotators to run
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,depparse");
        // set a property for an annotator, in this case the coref annotator is being set to use the neural algorithm
        // props.setProperty("ner.model","src/main/resources/models/apt2-model.ser.gz");
        props.setProperty("coref.algorithm", "neural");
        // build pipeline
        props.setProperty("ner.useSUTime", "false");
       props.setProperty("ner.model","models/apt3-model.ser.gz");
         pipeline = new StanfordCoreNLP(props);
        // create a document object



    }

    public void regMatch(String text, Set<String> cveSet, Set<String> ipSet){

        String cveReg="CVE-\\d{4}-[0-9]{4,5}";
        String ipReg="((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))";
        Pattern cvePattern=Pattern.compile(cveReg);
        Pattern ipPattern=Pattern.compile(ipReg);
        Matcher cveMatcher=cvePattern.matcher(text);
        Matcher ipMatcher=ipPattern.matcher(text);
        while (cveMatcher.find()){
            cveSet.add(cveMatcher.group());
        }
        while (ipMatcher.find()){
            cveSet.add(cveMatcher.group());
        }




    }


    public void doNer(String s){
        System.out.println(s);
        CoreDocument document = new CoreDocument(s);
        pipeline.annotate(document);
       // System.out.println(document.sentences());
        NerResult nerResult=new NerResult();
        Set<String> groupSet =new HashSet<>();
        Set<String> industySet =new HashSet<>();
        Set<String> toolSet =new HashSet<>();
        Set<String> urlSet =new HashSet<>();
        Set<String> locationSet =new HashSet<>();
        Set<String> cveSet =new HashSet<>();
        Set<String> ipSet =new HashSet<>();
        regMatch(s,cveSet,ipSet);
        for(CoreSentence sentence:document.sentences()){
            List<CoreEntityMention> entityMentions = sentence.entityMentions();

            System.out.println("Example: entity mentions");


            for (CoreEntityMention entityMention:entityMentions){
               System.out.println(entityMention.entityType()+": "+entityMention);




                switch (entityMention.entityType()){
                    case "Industry":
                        industySet.add(entityMention.toString());break;
                    case "URL":
                        urlSet.add(entityMention.toString());break;
                    case "Tool":
                        toolSet.add(entityMention.toString());break;
                    case "Location":
                        locationSet.add(entityMention.toString());break;
                    case "Group":
                        groupSet.add(entityMention.toString());break;
                    default:break;

                }
            }

        }


        nerResult.setGroup(groupSet);
        nerResult.setIndusty(industySet);
        nerResult.setLocation(locationSet);
        nerResult.setTool(toolSet);
        nerResult.setUrl(urlSet);
        nerResult.setCve(cveSet);
        nerResult.setIp(ipSet);
        System.out.println(nerResult);
        System.out.println();

    }



    public static void main(String[] args) {
       String text= TextUtill.readFileContent("src/main/resources/text/7.txt");
        System.out.println(text);
        NerService.init();
       new NerService().doNer(text);
    }



}
