package com.allapt.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.allapt.dao.attack.CVERepository;
import com.allapt.dao.capec.CWERepository;
import com.allapt.dao.nvd.AssetRepository;
import com.allapt.dao.nvd.CVSSRepository;
import com.allapt.dao.nvd.ReferenceRepository;
import com.allapt.entity.CVE;
import com.allapt.entity.CWE;
import com.allapt.entity.nvd.Asset;
import com.allapt.entity.nvd.CVSS;
import com.allapt.entity.nvd.Reference;
import com.allapt.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 罗叶妮
 * @version 1.0
 * @date 2020/10/26 17:20
 */

@Service
public class NVDService {
    @Autowired
    private CVERepository cveRepository;
    @Autowired
    private CWERepository cweRepository;
    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private CVSSRepository cvssRepository;
    @Autowired
    private ReferenceRepository referenceRepository;

    public String doAll() throws IOException {
        File[] files =getFiles("src/main/resources/nvd");
        for(File file:files){
            doAttackJosn(file);
        }

        return "sucess";
    }



    public String doAttackJosn(File file ) throws IOException {
        System.out.println(file.getName());
        JSONObject nvdJson= JsonUtil.readJsonFromClassPath("nvd/"+file.getName(),JSONObject.class);
        JSONArray nvdArr=nvdJson.getJSONArray("CVE_Items");
        //遍历文档
        for (int i = 0; i <nvdArr.size() ; i++) {
            JSONObject nvd=nvdArr.getJSONObject(i);
            JSONObject cve =nvd.getJSONObject("cve");
            //time
            String publishedDate =nvd.getString("publishedDate");
            String lastModifiedDate =nvd.getString("lastModifiedDate");

            //cve
            JSONObject CVE_data_meta =cve.getJSONObject("CVE_data_meta");
            String cve_id=CVE_data_meta.getString("ID");
            CVE cveNode=cveRepository.findByCVE(cve_id);
            if(cveNode==null){
                cveNode=new CVE();
                cveNode.setCVE(cve_id);
                System.out.println(cve_id);
                //description
                JSONObject description =nvd.getJSONObject("description");
                if(description!=null) {
                    cveNode.setDescription(description.toString());
                    System.out.println(description.toString());
                }
                cveRepository.save(cveNode);

            }
            //time
            cveNode.setPublishedDate(publishedDate);
            cveNode.setLastModifiedDate(lastModifiedDate);
            
           // System.out.println(cveNode);
           // System.out.println(cve_id);

            //cwe
            JSONObject problemtype =cve.getJSONObject("problemtype");
           // System.out.println(problemtype);
            if(problemtype!=null){

                JSONArray problemtype_data =problemtype.getJSONArray("problemtype_data");

                JSONObject description = problemtype_data.getJSONObject(0);
                JSONArray description_v=description.getJSONArray("description");
                if(description_v!=null&&description_v.size()!=0){
                    JSONObject value=description_v.getJSONObject(0);
                    String cwe=value.getString("value");
                    if(cwe.contains("CWE")&&!cwe.contains("noinfo")&&!cwe.contains("Other")){
                        String cweNum=cwe.substring(4);
                       // System.out.println(cveNum);
                        CWE cweNode=cweRepository.findByCWE(cweNum);
                        cveNode.setCwe(cweNode);
                        cveRepository.save(cveNode);
                    }
                }

            }
            //references
            JSONObject references=cve.getJSONObject("references");
            if(references!=null&&references.size()!=0){
                JSONArray nodes =references.getJSONArray("reference_data");
                List<Reference> referencesList = new ArrayList<>();
                for (int j = 0; j <nodes.size() ; j++) {

                    JSONObject node=nodes.getJSONObject(j);
                    String url= node.getString("url");
                    Reference reference = referenceRepository.findReferenceByUrl(url);
                    if(reference==null){
                        reference=new Reference();
                        String name= node.getString("name");
                        String refsource= node.getString("refsource");
                        JSONArray tags= node.getJSONArray("tags");
                        List<String> tagList =new ArrayList<>();
                        for (int k = 0; k <tags.size() ; k++) {
                            tagList.add(tags.getString(k));
                        }
                        reference.setUrl(url);
                        reference.setName(name);
                        reference.setRefsource(refsource);
                        reference.setTagList(tagList);
                    }
                    referencesList.add(reference);
                }
                cveNode.setReferenceList(referencesList);
                cveRepository.save(cveNode);
            }
            //configurations asset
            JSONObject configurations=nvd.getJSONObject("configurations");
            if(configurations!=null&&configurations.size()!=0){
                JSONArray nodes =configurations.getJSONArray("nodes");
                List<Asset> assetList= new ArrayList<>();
                for (int j = 0; j <nodes.size() ; j++) {
                    JSONObject node=nodes.getJSONObject(j);
                    JSONArray cpe_match =node.getJSONArray("cpe_match");
                    if(cpe_match!=null){

                        for (int k = 0; k <cpe_match.size() ; k++) {
                            String cpe23Uri =cpe_match.getJSONObject(k).getString("cpe23Uri");
                            String[] cpeStrs=cpe23Uri.split(":");
                            //System.out.println(cpeStrs[2]+" "+cpeStrs[3]);
                            Asset asset =assetRepository.findAssetByProduct(cpeStrs[4]);
                            if(asset==null){
                                asset =new Asset();
                                asset.setPart(cpeStrs[2]);;
                                asset.setProduct(cpeStrs[4]);
                                asset.setVendor(cpeStrs[3]);
                                assetRepository.save(asset);
                            }
                            assetList.add(asset);

                        }

                    }

                }
                cveNode.setAssetList(assetList);
                cveRepository.save(cveNode);
            }


            //CVSS
            JSONObject impact =nvd.getJSONObject("impact");
            JSONObject baseMetricV3 =impact.getJSONObject("baseMetricV3");
            if(baseMetricV3!=null){
                JSONObject cvssV3 =baseMetricV3.getJSONObject("cvssV3");
                String name="cvssV3";
                String version=cvssV3.getString("version");
                String vectorString=cvssV3.getString("vectorString");
                String attackVector=cvssV3.getString("attackVector");
                String attackComplexity=cvssV3.getString("attackComplexity");
                String privilegesRequired=cvssV3.getString("privilegesRequired");
                String userInteraction=cvssV3.getString("userInteraction");
                String scope=cvssV3.getString("scope");
                String confidentialityImpact=cvssV3.getString("confidentialityImpact");
                String integrityImpact=cvssV3.getString("integrityImpact");
                String availabilityImpact=cvssV3.getString("availabilityImpact");
                String baseScore=cvssV3.getString("baseScore");
                String baseSeverity=cvssV3.getString("baseSeverity");
                String severity=baseMetricV3.getString("severity");
                String exploitabilityScore=baseMetricV3.getString("exploitabilityScore");
                String impactScore=baseMetricV3.getString("impactScore");
                String acInsufInfo=baseMetricV3.getString("acInsufInfo");
                String obtainAllPrivilege=baseMetricV3.getString("obtainAllPrivilege");
                String obtainUserPrivilege=baseMetricV3.getString("obtainUserPrivilege");
                String obtainOtherPrivilege=baseMetricV3.getString("obtainOtherPrivilege");
                String userInteractionRequired=baseMetricV3.getString("userInteractionRequired");

                CVSS cvss =new CVSS();
                cvss.setName(name);
                cvss.setVersion(version);
                cvss.setVectorString(vectorString);
                cvss.setAttackVector(attackVector);
                cvss.setAttackComplexity(attackComplexity);
                cvss.setPrivilegesRequired(privilegesRequired);
                cvss.setUserInteraction(userInteraction);
                cvss.setScope(scope);
                cvss.setConfidentialityImpact(confidentialityImpact);
                cvss.setIntegrityImpact(integrityImpact);
                cvss.setAvailabilityImpact(availabilityImpact);
                cvss.setBaseScore(baseScore);
                cvss.setBaseSeverity(baseSeverity);
                cvss.setSeverity(severity);
                cvss.setExploitabilityScore(exploitabilityScore);
                cvss.setImpactScore(impactScore);
                cvss.setAcInsufInfo(acInsufInfo);
                cvss.setObtainAllPrivilege(obtainAllPrivilege);
                cvss.setObtainUserPrivilege(obtainUserPrivilege);
                cvss.setObtainOtherPrivilege(obtainOtherPrivilege);
                cvss.setUserInteractionRequired(userInteractionRequired);
                cvssRepository.save(cvss);
                cveNode.setCvss3(cvss);
                cveRepository.save(cveNode);

            }
            JSONObject baseMetricV2 =impact.getJSONObject("baseMetricV2");
            if(baseMetricV2!=null){
                JSONObject cvssV2 =baseMetricV2.getJSONObject("cvssV2");
                String name="cvssV2";
                String version=cvssV2.getString("version");
                String vectorString=cvssV2.getString("vectorString");
                String attackVector=cvssV2.getString("attackVector");
                String attackComplexity=cvssV2.getString("attackComplexity");
                String privilegesRequired=cvssV2.getString("privilegesRequired");
                String userInteraction=cvssV2.getString("userInteraction");
                String scope=cvssV2.getString("scope");
                String confidentialityImpact=cvssV2.getString("confidentialityImpact");
                String integrityImpact=cvssV2.getString("integrityImpact");
                String availabilityImpact=cvssV2.getString("availabilityImpact");
                String baseScore=cvssV2.getString("baseScore");
                String baseSeverity=cvssV2.getString("baseSeverity");
                String severity=baseMetricV2.getString("severity");
                String exploitabilityScore=baseMetricV2.getString("exploitabilityScore");
                String impactScore=baseMetricV2.getString("impactScore");
                String acInsufInfo=baseMetricV2.getString("acInsufInfo");
                String obtainAllPrivilege=baseMetricV2.getString("obtainAllPrivilege");
                String obtainUserPrivilege=baseMetricV2.getString("obtainUserPrivilege");
                String obtainOtherPrivilege=baseMetricV2.getString("obtainOtherPrivilege");
                String userInteractionRequired=baseMetricV2.getString("userInteractionRequired");

                CVSS cvss =new CVSS();
                cvss.setName(name);
                cvss.setVersion(version);
                cvss.setVectorString(vectorString);
                cvss.setAttackVector(attackVector);
                cvss.setAttackComplexity(attackComplexity);
                cvss.setPrivilegesRequired(privilegesRequired);
                cvss.setUserInteraction(userInteraction);
                cvss.setScope(scope);
                cvss.setConfidentialityImpact(confidentialityImpact);
                cvss.setIntegrityImpact(integrityImpact);
                cvss.setAvailabilityImpact(availabilityImpact);
                cvss.setBaseScore(baseScore);
                cvss.setBaseSeverity(baseSeverity);
                cvss.setSeverity(severity);
                cvss.setExploitabilityScore(exploitabilityScore);
                cvss.setImpactScore(impactScore);
                cvss.setAcInsufInfo(acInsufInfo);
                cvss.setObtainAllPrivilege(obtainAllPrivilege);
                cvss.setObtainUserPrivilege(obtainUserPrivilege);
                cvss.setObtainOtherPrivilege(obtainOtherPrivilege);
                cvss.setUserInteractionRequired(userInteractionRequired);
                cvssRepository.save(cvss);
                cveNode.setCvss2(cvss);
                cveRepository.save(cveNode);

            }




        }

        return "sucess";
    }
    public File[] getFiles(String path){
        List<String> list = new ArrayList<>();
        File file = new File(path);
        File[] files = file.listFiles();


        return files;
    }
    public static void main(String[] args) throws IOException {
                NVDService nvdService =new NVDService();
            //    nvdService.doAttackJosn();
    }
}
