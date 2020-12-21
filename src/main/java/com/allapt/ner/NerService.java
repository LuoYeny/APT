package com.allapt.ner;

import com.allapt.util.TextUtill;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.util.CoreMap;

import java.util.List;
import java.util.Properties;

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


    public void doNer(String s){
        System.out.println(s);
        CoreDocument document = new CoreDocument(s);
        pipeline.annotate(document);
       // System.out.println(document.sentences());
        for(CoreSentence sentence:document.sentences()){
            List<CoreEntityMention> entityMentions = sentence.entityMentions();
            System.out.println("Example: entity mentions");
            for (CoreEntityMention entityMention:entityMentions){
                System.out.println(entityMention.entityType()+" : "+entityMention);
            }
        }



        System.out.println();

    }



    public static void main(String[] args) {
       String text= TextUtill.readFileContent("src/main/resources/text/43.txt");
        System.out.println(text);
        NerService.init();
       new NerService().doNer(text);
    }



}
