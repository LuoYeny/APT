package com.allapt.threatAction.mapping;



import com.allapt.threatAction.modals.Bagofwords;
import com.allapt.threatAction.modals.Document_Terms;
import com.allapt.threatAction.stnfd.basic.RelationExtractor;
import com.allapt.threatAction.stnfd.basic.Sentence;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.allapt.threatAction.mapping.Utilities.scanning;


@Service
public class OntologyParser implements Comparator<Double>{
	private static final Logger LOGGER = Logging.LOGGER;

	private static boolean isStemming;
	private static ArrayList<Document_Terms> dt_obj_TA, dt_obj_G1, dt_obj_G2;
	private static double freq;
	private static ArrayList<Bagofwords> bagswords;
	static OntologyParser class_obj;
	static ArrayList<Document_Terms> Ontology_doc_terms = null;
	private static ArrayList<String> resultList;
	// directory for input files
	private static List<Map<String,String>> resultMap;
	private static List<List<Technique>> result;
	// no need to change if corpus is same
	private static HashMap ttpDrillConfigMap;

	private static OntologyParser ontologyParserInstance = null;

	/**
	 * Initialization block
	 */
	static {
		dt_obj_TA = new ArrayList<Document_Terms>();
		dt_obj_G1 = new ArrayList<Document_Terms>();
		dt_obj_G2 = new ArrayList<Document_Terms>();
		bagswords = new ArrayList<Bagofwords>();
		class_obj = new OntologyParser();
		// creating Ontology
		//isStemming = GUI.isStemming;
		//always true
		isStemming = true;
	}

	public static OntologyParser getInstance(List<SecurityThreatCategory> ontologyList, HashMap configMap) {
		if (ontologyParserInstance == null) {
			createInstance(ontologyList, configMap);
		}
		return ontologyParserInstance;
	}

	private static OntologyParser createInstance(List<SecurityThreatCategory> ontologyList, HashMap configMap) {
		if (ontologyParserInstance==null) {
			ontologyParserInstance = new OntologyParser();
			ttpDrillConfigMap = new HashMap();
			Ontology_doc_terms = new ArrayList<Document_Terms>();
			Ontology_doc_terms = createDocTerms(ontologyList);
			ttpDrillConfigMap = configMap;
		}
		return ontologyParserInstance;
	}

	public OntologyParser() {
	}

	public OntologyParser(List<SecurityThreatCategory> ontologyList, HashMap configMap) {
		getInstance(ontologyList, configMap);
	}


	/**
	 * driver method
	 * You only have to change input directory if you want to change the corpus directory
	 * @param
	 * //args
	 */





	public static void queryOntology(Sentence sent, String threat_action,  Document_Terms... document_Terms) {
//		System.out.println("Ghaith -- querying the ontology -- ontologyparser");
		TreeMap<String, Double> tmap = new TreeMap<>();
		TreeMap<String, HashMap<String,Integer>> tmapContent = new TreeMap<>();
		for (Document_Terms docTerm: document_Terms)
			for (Document_Terms dt: Ontology_doc_terms) {
//			System.out.println("Ghaith -- querying the ontology for this action -- ontologyparser" + docTerm.term_freq_hmap.toString());
				double score =TTPDrill_BM25.Calculate_BM25_Similarity_bagofwords_ankit(docTerm, dt, bagswords, true,
						totalDocs, avgDocLength);
				if (score>0.1) {
					tmap.put(dt.ID, score);
					tmapContent.put(dt.ID, dt.term_freq_hmap);
				}
			}printResults(sent, threat_action, tmap, tmapContent);
	}

	/**
	 * @throws Exception
	 *
	 */
	public static void use_nlp_to_load(String sentence){
		//load threat reported provided by GUI aka sentence
		double score = 0;
		int counter =0;

				TreeMap<String, Double> tmap = new TreeMap<>();
				TreeMap<String, HashMap<String,Integer>> tmapContent = new TreeMap<>();
				String text = sentence ;

				Sentence sent = new Sentence();
				sent.setOriginalSentence(text);
//			    System.out.println("use_nlp_to_load -- text before nlp" + text);
				text = Utilities.removeStopWords(text);

			//	TTPExtractor ex = new TTPExtractor(text);
				String [] strings =text.split(" ");

				Document_Terms TA = new Document_Terms();


//		    	System.out.println(++counter+","+ sent);
//		    	System.out.println("start="+start + " ,end="+end );
		for (int i = 0; i < strings.length; i++) {
			String word =strings[i];
			if (isStemming)
				word = Utilities.stem(word,false);

			if (!word.equalsIgnoreCase(Constants.blank))
				TA.Add_Term_(word);
		}

			      /// start looking in the ontology
		//	System.out.println("for score  ...");
				for (Document_Terms dt: Ontology_doc_terms) {
					// extract 3 files, goal1, goal2 and verb_action

					score = TTPDrill_BM25.Calculate_BM25_Similarity_bagofwords_ankit(TA, dt, bagswords, true,
					totalDocs, avgDocLength);
				//	System.out.println("score1  "+score);
					if (score>5) {
						tmap.put(dt.ID, score);

					//	System.out.println("dt.ID "+dt.ID);
						tmapContent.put(dt.ID, dt.term_freq_hmap);
						//System.out.println("TA "+TA+" dt.ID"+dt.ID+" score "+score);
					}
					}
				if(tmap.size()!=0){
					//System.out.println(strings);
					printResults( sent, text, tmap, tmapContent);
				}

		}






	/**
	 * Parsing the files
	 */
	public static void parser(List<SecurityThreatCategory> securityThreatCategoryList) {
//	public void parser() {
		System.out.println("Step 1 ##### parsing ontology from "+ Constants.inputDir);

		try {
//			String inputDirFilePath = OntologyParser.class.getResource("/app/ttpdrill/" + Constants.inputDir).getPath();
//			File dir = new File(inputDirFilePath);
//			File[] directoryListing = dir.listFiles();
			if(securityThreatCategoryList != null && !securityThreatCategoryList.isEmpty()) { //if (directoryListing != null) {
				for(SecurityThreatCategory securityThreatCategory : securityThreatCategoryList) { //for (File child : directoryListing) {
//					if (child.isDirectory())
//						continue;
					String filename = securityThreatCategory.getCode(); //child.getName();
//					FileInputStream fis = new FileInputStream(child);
//					byte[] data = new byte[(int) child.length()];
//					fis.read(data);
//					fis.close();

					String str = securityThreatCategory.getName(); //new String(data, "UTF-8"); // str return ontology file content
					//System.out.println("str    " +str);
					if(str==null) continue;
					String[] words = str.split(Constants.space); // words split that with space and keep in words array
					Document_Terms dt = new Document_Terms(filename); // dt created with file name
					for (String word : words) {
						if (isStemming)
							word = Utilities.stem(word, false); // every word stemmed like added -> ad
						freq = dt.Get__Term_Frequency_inDoc(word);
						freq += 1;
						dt.Add_Term_Frequency(word, (int) freq);
					}
					if (filename.contains(Constants.TA_suffix)) {
						dt_obj_TA.add(dt);
					} else if (filename.contains(Constants.G1_suffix)) {
						dt_obj_G1.add(dt);
					} else if (filename.contains(Constants.G2_suffix)) {
						dt_obj_G2.add(dt);
					}
				//	System.out.println("Map1 size : " + dt_obj_TA.size());
				//	System.out.println("Map2 size : " + dt_obj_G1.size());
				//	System.out.println("Map3 size : " + dt_obj_G2.size());
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Create bag of Words
	 */
	public void createBagOfWords() {
		BufferedReader br = null;

		/////// ###########################/////////////////////
		/////////// parsing synonyms
		System.out.println("Step 2 ##### parsing synonym list");
		String line = null;
		try {
			String synonymsFilePath = getClass().getResource("/app/ttpdrill/synonyms-list.txt").getPath();
			br = new BufferedReader(new FileReader(synonymsFilePath)); //(".\\synonyms-list.txt"));
			int number_of_loaded_lines = 0;
			while ((line = br.readLine()) != null) {
				number_of_loaded_lines++;
				String[] field1 = line.split("\\t"); // getting rid of tab field
				String[] field = field1[0].split(";"); // separating a term from its synonyms (latter)
				String term = field[0];
				String[] synonyms = field[1].split(",");
				Bagofwords bow = new Bagofwords();
				if (synonyms.length >= 1) { // at least one synonym
					if(isStemming)
						term = Utilities.stem(term, false);
					bow.synonyms.add(term);
					for (String s : synonyms) {
						if(isStemming)
							s = Utilities.stem(s, false);
						bow.synonyms.add(s);
						LOGGER.finest("adding as term in a bag: " + s);
					}
					bagswords.add(bow); // adding an entire bag of words as a group
				} else {
					LOGGER.finest("This dictionary entry has no synonyms " + number_of_loaded_lines);
				}
			} // end of line while
			LOGGER.finest("number of loaded lines is :" + number_of_loaded_lines);

		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "sth is wrong", e);
			e.printStackTrace();
		}
	}

/**
 *
 * @param term
 * @param docalreadyincreased
 * @param bagswords
 */
	public static void Increase_Term_Popularity_BagofWords
    (String term, ArrayList<String> docalreadyincreased,
    ArrayList<Bagofwords> bagswords){
    //  the array contains the bags that the document already
    //	increased so we dont increase again we loop for a bag
    //	of words and increase its popularity
    //  if not found, we create new bag of words of this term

    if(bagswords.isEmpty())
    {
        Bagofwords temp = new Bagofwords();
        temp.synonyms.add(term);
        temp.popularity++;
        bagswords.add(temp);
    }
    else
    {
        for(Bagofwords bow: bagswords)
        {
            if(bow.Contains(term))
            {
                if(!bow.Contains(docalreadyincreased)) // to make sure we do not increase popularity for the same bag twice by the same doc
                {
                bow.popularity++;
                //System.out.println("bag of words found and pop increased -- increase popularity bag of words method");
                return;
                }
            }
        }

        // term was not found in any bag, create a new bag of words

          Bagofwords temp = new Bagofwords();
          temp.synonyms.add(term);
          temp.popularity++;
          bagswords.add(temp);
          return;
    	}
    }


    public static void increasePopularity() {
    	 // increase global popularity for this document
        ArrayList<String> emptyarray=new ArrayList<>();
        for(Document_Terms dt: dt_obj_TA){ // do we count per technique or per ontology entry (3 per technique)
            for(String t:dt.term_freq_hmap.keySet()){ // the terms from one ontology entry
                Increase_Term_Popularity_BagofWords(t, emptyarray,bagswords);
            }
        }
    }

    private static double totalTerms;
    private static double avgDocLength;
    private static int totalDocs;
    @SafeVarargs
	public static ArrayList<Document_Terms> computeStatistics(ArrayList<Document_Terms>... dt_arraylist) {

    	totalTerms = 0;
    	totalDocs = 0;
    	avgDocLength = 0;
    	ArrayList<Document_Terms> final_doc_terms = new ArrayList<Document_Terms>();
    	for(ArrayList<Document_Terms> dtArrList: dt_arraylist)
    	for(Document_Terms dt: dtArrList) {
    		totalTerms += dt.totalterms;
    		totalDocs +=1;
    		final_doc_terms.add(dt);
    	}
    	avgDocLength = totalTerms/totalDocs;
    	LOGGER.config(" Total terms count: "+totalTerms);
    	LOGGER.config(" Total doc count: "+totalDocs);
    	LOGGER.config(" Avg doc length: "+avgDocLength);
    	return final_doc_terms;
    }

	@Override
	public int compare(Double o1, Double o2) {
		// TODO Auto-generated method stub
		if (o1>o2)
			return 1;
		return 0;
	}


	private static SortedSet<Entry<String,Double>> entriesSortedByValues(Map<String,Double> map) {
	    SortedSet<Entry<String,Double>> sortedEntries = new TreeSet<Entry<String,Double>>(
	        new Comparator<Entry<String,Double>>() {
				@Override
				public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
					// TODO Auto-generated method stub
					int res = o2.getValue().compareTo(o1.getValue());
	                return res != 0 ? res : 1;
				}
	        }
	    );
	    sortedEntries.addAll(map.entrySet());
	    return sortedEntries;
	}

	/**
	 *
	 */
	private static void printResults(Sentence sentence, String threat_action, TreeMap<String, Double> tmap, TreeMap<String,HashMap<String,Integer>> tmapContent) {

		SortedSet<Entry<String,Double>> hulu = entriesSortedByValues(tmap);
		//System.out.println(hulu);
		int count = 0;
		// get data from configuration table

		List<Technique> list= new LinkedList<>();
		for(Entry<String,Double> entry: hulu) {


			// scanning for fixed regex


				int index = entry.getKey().toString().indexOf("_");
				String techNo = entry.getKey().toString().substring(0, index);
				String techName = Utilities.convertTechIDtoTechName(techNo);
				String techniqueData = "";
				try {
					techniqueData = techName.split(",")[0];
				} catch (Exception techniqueEx) {
					techniqueData = "";
				}

				String tacticData = "";
				try {
					tacticData = techName.split(",")[1];
				} catch (Exception tacticEx) {
					tacticData = "";
				}







				String id=entry.toString().split("_")[0];

//				if(!map.containsKey(id)){
//					Technique technique = new Technique();
//					technique.setAction(threat_action);
//					technique.setTactic(tacticData);
//					technique.setTechId(entry.toString().split("_")[0]);
//					technique.setTechnique(techniqueData);
//					 technique.setScore(entry.getValue().toString());
//
//					technique.setCount(1);
//					map.put(id,technique);
//					if(firstTech==null)
//						firstTech=technique;
//				} else {
//					Technique technique =map.get(id);
//					technique.setCount(technique.getCount()+1);
//				}
					Technique technique = new Technique();
					technique.setAction(threat_action);
					technique.setTactic(tacticData);
					technique.setTechId(entry.toString().split("_")[0]);
					technique.setTechnique(techniqueData);
					technique.setScore(entry.getValue().toString());
					list.add(technique);

			   count++;
			   if(count==5)break;

		}
		if(result==null)result=new LinkedList<>();
//		List<Technique> list= new LinkedList<>();
//		if(map.size()<5){
//			for(String s:map.keySet()){
//				Technique technique =map.get(s);
//				if(technique.getCount()>1)
//					list.add(technique);
//			}
//			if(list.size()==1)list.add(firstTech);
			result.add(list);
		//	System.out.println(result);
//		}



		//resultMap.addAll(countMap(midResultMap));

		if (count==0) { LOGGER.severe(Constants.noResult);
		LOGGER.severe(Constants.suggestion);
		System.out.println();}
	}


	/**
	 * createDocTerms
	 */
	public static ArrayList<Document_Terms> createDocTerms(List<SecurityThreatCategory> ontologyList){
		parser(ontologyList);
		// create bag of words
		class_obj.createBagOfWords();
		// increase popularity
		increasePopularity();
		// Computing statistics
		ArrayList<Document_Terms> final_doc_terms = computeStatistics(dt_obj_TA, dt_obj_G1, dt_obj_G2);
		return final_doc_terms;
	}



	public ArrayList<String> getResultList(){
		return resultList;
	}


	public List<Map<String,String>> getResultMap(){
		return resultMap;
	}

	public void setResultMap(List<Map<String,String>> resList){
	    this.resultMap = resList;
	}

	public static List<List<Technique>> getResult() {
		return result;
	}

	public static void setResult(List<List<Technique>> result) {
		OntologyParser.result = result;
	}
}
	
