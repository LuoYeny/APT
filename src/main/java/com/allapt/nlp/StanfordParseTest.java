package com.allapt.nlp;


import com.allapt.nlp.preProcess.NLPCoreExtractor;
import com.allapt.nlp.preProcess.TextPreprocessor;
import com.allapt.util.TextUtill;
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
        String text6="APT28 has used a brute-force/password-spray tooling that operated in two modes: in password-spraying mode it conducted approximately four authentication attempts per hour per targeted account over the course of several days or weeks.";
        String text7="APT28 added \"junk data\" to each encoded string, preventing trivial decoding without knowledge of the junk removal algorithm. Each implant was given a \"junk length\" value when created, tracked by the controller software to allow seamless communication but prevent analysis of the command protocol on the wire.";
        String text8="These attacks were carried out using spear-phishing attacks against the target organizations, using messages related to diplomatic discussions in the Asia-Pacific region.\n" +
                "\n" +
                "The spear-phishing email contains a malicious document as an attachment, which exploits CVE-2012-0158, a dated vulnerability in Windows common control. This vulnerability was also used in other targeted attacks, most recently the “Safe” campaign that compromised several government agencies, media outlets and other institutions.";
        String text9= TextUtill.readFileContent("src/main/resources/text/1.txt");

        System.out.println(new StanfordParseTest().doIdentification(text9));
    }
}
