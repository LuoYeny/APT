package com.allapt.threatAction.identification;


import com.allapt.util.TextUtill;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Properties;

@Service
public class IdentifyService {
    private StanfordCoreNLP pipeline;
    private static NLPCoreExtractor nlpExtractor = null;

    public List<String> doIdentification(String text){
        NLPCoreExtractor coreExtractor=new NLPCoreExtractor();
        TextPreprocessor preprocessor = new TextPreprocessor();
        text =preprocessor.replacePathWithRegex(text);
        List<String> results=coreExtractor.extractActionTreeBasedApproach(text);
        return  results;

    }

    public static void main(String[] args) {
        String text= TextUtill.readFileContent("src/main/resources/text/1.txt");

        System.out.println(new IdentifyService().doIdentification(text));
    }
}
