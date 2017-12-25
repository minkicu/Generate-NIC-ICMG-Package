/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javacsv;

import static javacsv.util.padding.leftZeroPadding;
/**
 *
 * @author krissada.r
 */
public class configure {
    private static String clearingDate;
    private static String bankNo;
    private static String batchNo;
    private static int iBatchNo;
    private static int seqNo;
    
    public void setClearingData(String d1){
        clearingDate = d1;
    }
    
    public void setBankNo(String d1){
        bankNo = d1;
    }
    
    public void setBatchNo(int d1){
        batchNo = leftZeroPadding(d1,6);
        iBatchNo = d1;
    }
    
    public void setSeq(int d1){
        seqNo = d1;
    }
    
    public void IncreaseSeq(){
        seqNo += 1;
    }
    
    public String getClearingDate() {
        return clearingDate;
    } 
    
    public String getBankNo() {
        return bankNo;
    }
    
    public String getBatchNo() {
        return batchNo;
    }
    
    public Integer getiBatchNo() {
        return iBatchNo;
    }
    
    public Integer getSeqNo() {
        return seqNo;
    }
        
    
}
