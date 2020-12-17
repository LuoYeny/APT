package com.allapt.nlp;


import com.allapt.nlp.preProcess.NLPCoreExtractor;
import com.allapt.nlp.preProcess.TextPreprocessor;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.util.CoreMap;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class StanfordParseTest {
    private StanfordCoreNLP pipeline;
    private static com.allapt.threatAction.identification.NLPCoreExtractor nlpExtractor = null;

    public List<String> doIdentification(String text){
      NLPCoreExtractor coreExtractor=new NLPCoreExtractor();
      TextPreprocessor preprocessor = new TextPreprocessor();
        text =preprocessor.replacePathWithRegex(text);
        List<String> results=coreExtractor.extractActionTreeBasedApproach(text);
        return  results;

    }

    public static void main(String[] args) {
        String text ="The Trojan Dimnie is used to steal information from the computer. ";
        String text1="the Trojan creates the following registry entries ..The Trojan may then perform the following actions: obtain system information, take screenshots, log keystrokes. ";
        String text2="APT28 has deployed a bootkit along with Downdelph to ensure its persistence on the victim. The bootkit shares code with some variants of BlackEnergy.";
        String text3="it takes me some time";
        String text4="APT28 uses a tool to infect connected USB devices and transmit itself to air-gapped computers when the infected USB device is inserted.";
        String text5="The Trojan may send the stolen information to the following location:gmail.com/upload.php";
        System.out.println(new StanfordParseTest().doIdentification(text1));
    }
}
