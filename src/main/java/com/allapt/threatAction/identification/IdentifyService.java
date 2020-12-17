package com.allapt.threatAction.identification;


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
}
