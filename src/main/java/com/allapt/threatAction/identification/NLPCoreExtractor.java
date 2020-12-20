/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */        
package com.allapt.threatAction.identification;


import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.util.CoreMap;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author mahmed27
 */
public class NLPCoreExtractor {
    
    private final StanfordCoreNLP pipeline;
    private static NLPCoreExtractor nlpExtractor = null;

    
    public NLPCoreExtractor() {
        Properties property = new Properties();
        property.put("annotators", "tokenize, ssplit, pos, parse");//"tokenize, ssplit, pos, lemma, ner, parse, dcoref"
        pipeline = new StanfordCoreNLP(property);
        
    }
    
    public static NLPCoreExtractor getInstance(){
        if(nlpExtractor == null) {
            nlpExtractor = new NLPCoreExtractor();
        }
        return nlpExtractor;
    }   
    
    public List<String> extractActionTreeBasedApproach(String text) {
        Annotation document = new Annotation(text);
        pipeline.annotate(document);
        
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
        System.out.println("Size: " + sentences.size());
        
        List<String> fSentences = new ArrayList<>();
        
        for(CoreMap sentence: sentences) {
            SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
            Collection<IndexedWord> rootList = dependencies.getRoots();
            List<SemanticGraphEdge> allEdge = new ArrayList<>();
            for(IndexedWord root : rootList) {
                treeTraversalDFS(dependencies, root, allEdge);
                this.parseSVO(sentence, allEdge, fSentences);
            }
            
        }
        System.out.println("No of Sentence: " + fSentences.size());
        return fSentences;
    }

    private void parseSVO(CoreMap sentence, List<SemanticGraphEdge> allEdge
            , List<String> fSentences) {
        String nsubj = "", obj="", dobj = "", verb = "", det = "", aux = "", auxpass = ""
                , mark = "", advmod = "", case1 = "", amod = "", nmod = "", acl = ""
                , previous = "", aclDobj = "", compound = "";
        IndexedWord word;
        String fs = "";
        boolean bnsubj = false, bdobj = false, bnmod = false, bacl = false;
        System.out.println("befor loop");

        List<Action> list = new LinkedList<>();
        for(SemanticGraphEdge edge : allEdge){
            String relation=edge.getRelation().getShortName().trim();
            if(relation.contains("nsubj")){
                nsubj = edge.getTarget().word().trim();

                //
            }
            if(relation.contains("obj")){
                verb = edge.getSource().word().trim();
                obj=edge.getTarget().word().trim();
                Action action = new Action();
                action.setNsubj(nsubj);
                action.setObj(obj);
                action.setVerb(verb);
                list.add(action);

            }
            if(relation.contains("compound")){

                if(edge.getSource().word().trim().equals(nsubj)){
                    nsubj=edge.getTarget().word().trim()+" "+nsubj;

                }
                if(edge.getSource().word().trim().equals(obj)
                        &&edge.getSource().word().trim().
                        equals(list.get(list.size()-1).getObj())){
                    Action action =list.get(list.size()-1);
                    action.setCompound(edge.getTarget().word().trim());

                }


            }
            if(relation.contains("amod")){
                if(edge.getSource().word().trim().equals(obj)
                        &&edge.getSource().word().trim().
                        equals(list.get(list.size()-1).getObj())){
                    Action action =list.get(list.size()-1);
                    action.setAmod(edge.getTarget().word().trim());


                }

            }

        }
        System.out.println("list   "+list);
        for(Action action:list){
            if(!fSentences.contains(action.toString()))
            fSentences.add(action.toString());
        }

//        for(SemanticGraphEdge edge : allEdge) {
//            switch(edge.getRelation().getShortName().trim()) {
//                case "nsubj":
//                    if(!nsubj.isEmpty()) {
//                        fs = nsubj +  (nsubj.isEmpty()? "": " ") + verb
//                                +  (verb.isEmpty()? "": " ") + dobj + (dobj.isEmpty()? "": " ")
//                                + nmod + (nmod.isEmpty()? "": " ") + acl + (acl.isEmpty()? "": " ") + aclDobj;
//                        System.out.println("Sentence:" + fs);
//                        if(!fs.trim().isEmpty()) {
//                            fSentences.add(fs.trim());
//                        }
//                        mark = aux = auxpass = dobj = nsubj = nmod = acl = case1 = det = amod = advmod = compound = "";
//                    }
//                    nsubj = edge.getTarget().word().trim();
//                    verb = edge.getSource().word().trim();
//                    bnsubj = true;
//                    break;
//             /*   case "conj":
//                    if(bnsubj) {
//                        nsubj = case1 + " " + det + " " + amod + " " + advmod + " " + compound + " "   + nsubj;
//                        case1 = det = amod = advmod = compound = "";
//                        bnsubj = false;
//                    } else if(bdobj) {
//                        dobj = case1 + " " + det + " " + amod + " " + advmod + " " + compound + " "   + dobj;
//                        case1 = det = amod = advmod = compound = "";
//                        bdobj = false;
//                    } else if(bacl) {
//                        aclDobj = case1 + " " + det + " " + amod + " " + advmod + " " + compound + " "   + aclDobj;
//                        case1 = det = amod = advmod = compound = "";
//                        bacl = false;
//                    } else if(bnmod) {
//                        nmod = case1 + " " + det + " " + amod + " " + advmod + " " + nmod;
//                        case1 = det = amod = advmod = "";
//                        bacl = false;
//                    }
//                    if(getThePOS(sentence, edge.getSource().word().trim()).equalsIgnoreCase("VB")
//                                && getThePOS(sentence, edge.getTarget().word().trim()).equalsIgnoreCase("VB")) {
//
//                        fs = nsubj +  (nsubj.isEmpty()? "": " ") + verb +  (verb.isEmpty()? "": " ") + dobj
//                                + (dobj.isEmpty()? "": " ") + nmod + (nmod.isEmpty()? "": " ")
//                                + acl + (acl.isEmpty()? "": " ") + aclDobj;
//                        System.out.println("Sentence:" + fs);
//                        if(!fs.trim().isEmpty()) {
//                            fSentences.add(fs.trim());
//                        }
//                        verb = edge.getTarget().word().trim();
//                        mark = aux = auxpass = dobj = nsubj = nmod = acl = case1 = det = amod = advmod = compound = "";
//                    }
//                    break;*/
//
//                case "dobj":
//                    if(bacl) {
//                        aclDobj = edge.getTarget().word().trim();
//                    } else {
//                        dobj = edge.getTarget().word().trim();
//                        verb = getThePOS(sentence, edge.getSource().word().trim()).equalsIgnoreCase("VB")? edge.getSource().word().trim() : verb;
//                        if(bnsubj) {
//                            nsubj = case1 + " " + det + " " + amod + " " + advmod + " " + compound + " " + nsubj;
//                            case1 = det = amod = advmod = compound = "";
//                            bnsubj = false;
//                        } else if(bnmod) {
//                            nmod = case1 + " " + det + " " + amod + " " + advmod + " " + nmod;
//                            case1 = det = amod = advmod = "";
//                            bnmod = false;
//                        }
//                        bdobj = true;
//                        verb = edge.getSource().word().trim();
//                        System.out.println("Ghaith NLP" + verb +" "+dobj);
//                    }
//                    break;
//    /*            case "nsubjpass":
//                    nsubj = edge.getTarget().word().trim();
//                    verb = edge.getSource().word().trim();
//                    break;*/
//                case "compound":
//                    compound = edge.getTarget().word().trim();
//                    break;
//             /*   case "aux":
//                    aux = edge.getTarget().word().trim();
//                    break;
//                case "auxpass":
//                    auxpass = edge.getTarget().word().trim();
//                    break;*/
//           /*     case "case":
//                    case1 =  edge.getTarget().word().trim();
//                    break;
//                case "det":
////                    det =  edge.getTarget().word().trim();
//                    break;
//                case "advmod":
//                    advmod = (getThePOS(sentence, edge.getTarget().word().trim()).equalsIgnoreCase("WRB")
//                            || getThePOS(sentence, edge.getTarget().word().trim()).equalsIgnoreCase("RB"))? "" : edge.getTarget().word().trim();
//                    break;
//                case "amod":
//                    amod =  getThePOS(sentence, edge.getTarget().word().trim()).equalsIgnoreCase("VBG")? "" : edge.getTarget().word().trim();
//                    break;
//                case "nmod":
//                    nmod = edge.getTarget().word().trim();
//                    verb = getThePOS(sentence, edge.getSource().word().trim()).equalsIgnoreCase("VB")? edge.getSource().word().trim() : verb;
//                    if(bnsubj) {
//                        nsubj = case1 + " " + det + " " + amod + " " + advmod + " " + compound + " " + nsubj;
//                        case1 = det = amod = advmod = compound = "";
//                        bnsubj = false;
//                    } else if(bdobj) {
//                        dobj = case1 + " " + det + " " + amod + " " + advmod + " " + compound + " " + dobj;
//                        case1 = det = amod = advmod = compound = "";
//                        bdobj = false;
//                    } else if(bacl) {
//                        aclDobj =  case1 + " " + det + " " + amod + " " + advmod + " " + compound + " " + aclDobj;
//                        case1 = det = amod = advmod = compound = "";
//                        bacl = false;
//                    }
//                    bnmod = true;
//                    break;
//                case "acl":
//                    acl = edge.getTarget().word().trim();
//                    if(bnsubj) {
//                        nsubj = case1 + " " + det + " " + amod + " " + advmod + " " + compound + " " + nsubj;
//                        case1 = det = amod = advmod = compound = "";
//                        bnsubj = false;
//                    } else if(bdobj) {
//                        dobj = case1 + " " + det + " " + amod + " " + advmod + " " + compound + " " + dobj;
//                        case1 = det = amod = advmod = compound = "";
//                        bdobj = false;
//                    } else if(bnmod) {
//                        nmod = case1 + " " + det + " " + amod + " " + advmod + " " + nmod;
//                        case1 = det = amod = advmod = "";
//                        bnmod = false;
//                    }
//                    bacl = true;
//                    break;
//                case "mark":
//                    mark = edge.getTarget().word().trim();
//                    break;*/
//                case "default":
//                    break;
//            }
//        }
//
//        if(bnsubj) {
//            nsubj = case1 + " " + det + " " + amod + " " + advmod + " " + compound+  " " + nsubj;
//        } else if(bdobj) {
//            dobj = case1 + " " + det + " " + amod + " " + advmod + " " + compound + " " + dobj;
//        } else if(bacl) {
//            aclDobj = case1 + " " + det + " " + amod + " " + advmod + " " + compound + " " + aclDobj;
//        } else if(bnmod) {
//            nmod = case1 + " " + det + " " + amod + " " + advmod + " " + nmod;
//        }
//        fs = nsubj +  (nsubj.isEmpty()? "": " ") + verb +  (verb.isEmpty()? "": " ") + dobj + (dobj.isEmpty()? "": " ")
//                + nmod + (nmod.isEmpty()? "": " ") + acl + (acl.isEmpty()? "": " ") + aclDobj;
//        System.out.println("Sentence:" + fs);
//        if(!fs.trim().isEmpty()) {
//            fSentences.add(fs.trim());
//        }
    }
    
    private void treeTraversalDFS(SemanticGraph dependencies, IndexedWord word, List<SemanticGraphEdge> allEdgeList) {
        List<SemanticGraphEdge> outEdgeList = dependencies.getOutEdgesSorted(word);
        if(outEdgeList == null || outEdgeList.size() == 0 ) {
            return;
        }
        for(SemanticGraphEdge edge : outEdgeList) {
            allEdgeList.add(edge);
            System.out.println(edge.toString() + " Relation: " + edge.getRelation().getShortName());
            treeTraversalDFS(dependencies, edge.getTarget(), allEdgeList);
            
        }
    }
    
    public void writeSentenceToFile(String filePath, List<String> sentences) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        String strToFile = "" ;
        for(String sentence : sentences) {
            strToFile += sentence + "\n";
        }
        writer.write(strToFile);
        writer.close();

    }
    
    private String getThePOS(CoreMap sentence, String txt) {
        String pos = null;
        for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
            pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
            if(token.getString(TextAnnotation.class).trim().contentEquals(txt)) {
                break;
            }
        }
        return pos;
    }
}
