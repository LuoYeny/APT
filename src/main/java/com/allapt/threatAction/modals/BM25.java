/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allapt.threatAction.modals;

/*Copyright 2011 Deminem Solutions

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under 
the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
either express or implied. See the License for the specific language governing permissions and limitations 
under the License.
*/

/**
 * Time to trigger magic ball 9 > BM25
 * 
 * 
 * 
 * R(d; q) = BM25(d; q) =  ∑ ( Wt * (k1 + 1) * tf(t,d) 	  (k3 + 1) * tf(t,q) ) + k2 * |q| *  avgdl - dl	
 * 							 	    ------------------- * ------------------				------------
 * 								   		K + tf(t,d)			 k3 + tf(t,d)					 avgdl + dl	
 * 
 * 
 * 						K = k1((1 - b) + (b * dl) /avgdl)
 * 
 * Wt = term weight based on relevance feedback (RSJ - w(1)) or
 * tf(t, d), tf(t, q) =  within term frequencies - document and query
 * k1, k2, k3, b =  tuning parameters 
 * dl; avgd = document length and average document length
 * 
 * k1 : governs the importance of within document frequency tf (t; q)
 * k2 : compensation factor for the high within document frequency values in large documents
 * k3 : governs the importance of within query frequency tf (t; q)
 * b : relative importance of within document frequency and document length
 * 
 */

public class BM25 {

    /** The constant k_1.*/
    private double k_1 = 1.2d;

    /** The constant k_3.*/
    private double k_3 = 8d;

    /** The parameter b.*/
    private double b;

    /** A default constructor.*/
    public BM25() {
            super();
            b = 0.75d;
    }
    
    /**
     * Returns the name of the model.
     * @return the name of the model
     */
    public final String getInfo() {
            return "BM25 >> b="+b +", k_1=" + k_1 +", k_3=" + k_3;
    }
    
    /**
     * Uses BM25 to compute a weight for a term in a document.
     * @param tf The term frequency in the document
     * @param numberOfDocuments number of documents
     * @param docLength the document's length
     * @param averageDocumentLength average document length
     * @return the score assigned to a document with the given
     *         tf and docLength, and other preset parameters
     */
    
    // this function is called for every term in the query and it returns the b25tf-idf score for the term, later an aggregation func is needed to sum these values
    public final double score(double tf, 
    		double numberOfDocuments, 
    		double docLength, 
    		double averageDocumentLength, 
    		double queryFrequency, 
    		double documentFrequency) {
    	
            double K = k_1 * ((1 - b) + ((b * docLength) / averageDocumentLength));
            double weight = ( ((k_1 + 1d) * tf) / (K + tf) );	//first part
            weight = weight * ( ((k_3 + 1) * queryFrequency) / (k_3 + queryFrequency) );	//second part

            // no IDF
            documentFrequency = 1;

            // multiply the weight with idf 
            double idf = weight * Math.log((numberOfDocuments - documentFrequency + 0.5d) / (documentFrequency + 0.5d));	
            return idf;
    }


    /**
     * Sets the b parameter to BM25 ranking formula
     * @param b the b parameter value to use.
     */
    public void setParameter(double b) {
        this.b = b;
    }


    /**
     * Returns the b parameter to the BM25 ranking formula as set by setParameter()
     */
    public double getParameter() {
        return this.b;
    }

}
