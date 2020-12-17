package com.allapt.service;

import com.allapt.dao.capec.*;
import com.allapt.entity.CWE;
import com.allapt.entity.capec.*;
import com.allapt.util.XMLUtil;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author 罗叶妮
 * @version 1.0
 * @date 2020/10/14 16:11
 */

@Service
public class CapecService {
    @Autowired
    private CapecRepository capecRepository;
    @Autowired
    private CWERepository cweRepository;
    @Autowired
    private ConsequencesRepository consequencesRepository;
    @Autowired
    private IndicatorsRepository indicatorsRepository;
    @Autowired
    private InstancesRepository instancesRepository;
    @Autowired
    private MitigationsRepository mitigationsRepository;
    @Autowired
    private PrerequisitesRepository prerequisitesRepository;
    @Autowired
    private ResourcesRepository resourcesRepository;
    @Autowired
    private SkillsRequiredRepository skillsRequiredRepository;

@Transactional
   public String doCapecXml() throws IOException, DocumentException {
       Element root= XMLUtil.readJsonFromClassPath("capec/capec.xml");
       Element attackPatterns =root.element("Attack_Patterns");
       for (Iterator i = attackPatterns.elementIterator(); i.hasNext(); ) {
           Element attackPattern = (Element) i.next();
           Capec capec=new Capec();
          //id
           String capecId=  attackPattern.attribute("ID").getData().toString();
           capec.setCapecId(capecId);
           String name=attackPattern.attribute("Name").getData().toString();
           capec.setName(name);
           String description=attackPattern.element("Description").getData().toString();
           capec.setDescription(description);
           if(attackPattern.element("Likelihood_Of_Attack")!=null){
              capec.setLikelihoodOfAttack(attackPattern.element("Likelihood_Of_Attack").getData().toString());
              // System.out.println(attackPattern.element("Likelihood_Of_Attack").getData().toString());
           }
      //  capecRepository.save(capec);
           //prerequisites
           Element prerequisites= attackPattern.element("Prerequisites");
           List<Prerequisites> prerequisitesList;
           if(prerequisites!=null){
               prerequisitesList=new ArrayList<>();
               int pcount=0;
               for ( Iterator j = prerequisites.elementIterator(); j.hasNext(); ){
                   Element prerequisite = (Element) j.next();
                   Prerequisites element=new Prerequisites();
                   element.setDescription(prerequisite.getText());
                    pcount=pcount+1;
                   element.setName("capec-"+capecId+"_Prerequisite_" +pcount );
                   prerequisitesList.add(element);
                  prerequisitesRepository.save(element);
               }
               capec.setPrerequisitesList(prerequisitesList);
           }

           //Skills_Required
           Element skillsRequired= attackPattern.element("Skills_Required");
           List<SkillsRequired> skillsRequiredList;
           if(skillsRequired!=null){
               skillsRequiredList=new ArrayList<>();
               int scount=0;
               for ( Iterator j = skillsRequired.elementIterator(); j.hasNext(); ) {
                   Element skillsRequiredIt = (Element) j.next();
                   SkillsRequired skillsRequiredElem = new SkillsRequired();
                   String level = skillsRequiredIt.attribute("Level").getData().toString();
                   String skillDescription = skillsRequiredIt.getText();
                   skillsRequiredElem.setDescription(skillDescription);
                   skillsRequiredElem.setLevel(level);
                   skillsRequiredElem.setName("capec-"+capecId + "_SkillsRequired_"+(++scount));
                   skillsRequiredList.add(skillsRequiredElem);
                   skillsRequiredRepository.save(skillsRequiredElem);
               }
               capec.setSkillsRequiredList(skillsRequiredList);


           }
           //Indicators
           Element indicators= attackPattern.element("Indicators");
           List<Indicators> indicatorsList;
           if(indicators!=null){
               indicatorsList=new ArrayList<>();
               int icount=0;
               for ( Iterator j = indicators.elementIterator(); j.hasNext(); ){
                   Element indicatorsIt = (Element) j.next();
                   Indicators element=new Indicators();
                   element.setDescription(indicatorsIt.getText());
                   element.setName("capec-"+capecId+"_Indicators_" +(++icount) );
                   indicatorsList.add(element);
                  indicatorsRepository.save(element);
               }
               capec.setIndicatorsList(indicatorsList);

           }


           //Resources_Required
           Element resourcesRequired= attackPattern.element("Resources_Required");
           List<Resources> resourcesList;
           if(resourcesRequired!=null){
               resourcesList=new ArrayList<>();
               int rcount=0;
               for ( Iterator j = resourcesRequired.elementIterator(); j.hasNext(); ){
                   Element resourcesRequiredIt = (Element) j.next();
                   Resources element=new Resources();
                   element.setDescription(resourcesRequiredIt.getText());
                   element.setName("capec-"+capecId+"_Resources_" +(++rcount) );
                   resourcesList.add(element);
                   resourcesRepository.save(element);
               }
               capec.setResourcesList(resourcesList);

           }

           //Mitigations
           Element mitigations= attackPattern.element("Mitigations");
           List<Mitigations> mitigationsList;
           if(mitigations!=null){
               mitigationsList=new ArrayList<>();
               int mcount=0;
               for ( Iterator j = mitigations.elementIterator(); j.hasNext(); ){
                   Element mitigationsIt = (Element) j.next();
                   Mitigations element=new Mitigations();
                   element.setDescription(mitigationsIt.getText());
                   element.setName("capec-"+capecId+"_Mitigations_" +(++mcount) );
                   mitigationsList.add(element);
                   mitigationsRepository.save(element);
               }
               capec.setMitigationsList(mitigationsList);

           }

           //Example_Instances
           Element Instances= attackPattern.element("Example_Instances");
           List<Instances> instancesList;
           if(Instances!=null){
               instancesList=new ArrayList<>();
               int icount=0;
               for ( Iterator j = Instances.elementIterator(); j.hasNext(); ){
                   Element InstancesIt = (Element) j.next();
                   Instances element=new Instances();
                   element.setDescription(InstancesIt.getText());
                   element.setName("capec-"+capecId+"_Example_Instances_" +(++icount) );
                   instancesList.add(element);
                  instancesRepository.save(element);
               }
               capec.setInstancesList(instancesList);

           }
           //consequence
           Element consequences= attackPattern.element("Consequences");
           List<Consequences> consequencesList;
           if(consequences!=null){
              consequencesList=new ArrayList<>();
               int icount=0;
               for ( Iterator j = consequences.elementIterator(); j.hasNext(); ) {
                   Element consequencesIt = (Element) j.next();
                   String impact =consequencesIt.elementText("Impact");
                   if(impact.equals("Other"))
                       continue;
                   Consequences consequence = new Consequences();
                   consequence.setName("capec-"+capecId+"_Consequences_" +(++icount) );
                   consequence.setDescription(impact);
                   consequencesList.add(consequence);
                    consequencesRepository.save(consequence);
               }
               capec.setConsequencesList(consequencesList);

           }


           //cwe
           //Example_Instances
           Element relatedWeaknesses= attackPattern.element("Related_Weaknesses");
           List<CWE> cweList;
           if(relatedWeaknesses!=null){
               cweList=new ArrayList<>();
               int ccount=0;
               for ( Iterator j = relatedWeaknesses.elementIterator(); j.hasNext(); ){
                   Element relatedWeaknessesIt = (Element) j.next();
                   String cweStr=relatedWeaknessesIt.attribute("CWE_ID").getData().toString();

                   CWE element= cweRepository.findByCWE(cweStr);
                   if(element!=null)
                   cweList.add(element);


               }

               capec.setCweList(cweList);

           }


            capecRepository.save(capec);


       }




       return "secsess";
   }

    public static void main(String[] args) throws IOException, DocumentException {
        CapecService capecService=new CapecService();
        capecService.doCapecXml();
    }

}
