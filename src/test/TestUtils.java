package test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

import util.Analyze;
import util.Utils;
import data.Component;
import data.Defect;


public class TestUtils extends TestCase{
    
    private final String RAW_DATA_File_NAME = "testResource/raw_data.txt";
    private final String RECORDED_EXCEL_FILE = "testResource/CMVC_Trends.xlsx";
    private final String COPY_OF_RECORDED_EXCEL_FILE = "testResource/Copy_CMVC_Trends.xlsx";
    
    private final int vmDefectCount = 71;
    private final int jclDefectCount = 13;
    private final int jitDefectCount = 8;
    
    private final int vmRecordedDefectCount = 46;
    private final int jclRecordedDefectCount = 11;
    private final int jitRecordedDefectCount = 3;
    
    @Override
    public void setUp() throws IOException{
        copyExcelFile();
    }
    
    @Override
    public void tearDown(){
        removeCopyExcelFile();
    }
    
    private void copyExcelFile(){
        File f = new File(COPY_OF_RECORDED_EXCEL_FILE);
        if(!f.exists()){
            try {
                Utils.copyFile(RECORDED_EXCEL_FILE, COPY_OF_RECORDED_EXCEL_FILE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void removeCopyExcelFile(){
        File f = new File(COPY_OF_RECORDED_EXCEL_FILE);
        if(f.exists()){
            f.delete();
        }
    }
    

    @Test
    public void testWriteVMNewDefectData() throws InvalidFormatException, IOException{
        removeCopyExcelFile();
        copyExcelFile();
        ArrayList<Defect> existList = Utils.loadRecordedVMData(COPY_OF_RECORDED_EXCEL_FILE);
        int numOfExistDefect = existList.size();
        
        Analyze a = new Analyze();
        ArrayList<Defect> newList = a.getNewDefect(Component.VM, RAW_DATA_File_NAME, COPY_OF_RECORDED_EXCEL_FILE);
        Utils.writeVMNewDefectData(COPY_OF_RECORDED_EXCEL_FILE, newList);
        
        ArrayList<Defect> addedList = Utils.loadRecordedVMData(COPY_OF_RECORDED_EXCEL_FILE);
        assertEquals(addedList.size(), numOfExistDefect + newList.size());
        
        removeCopyExcelFile();
    }

    @Test
    public void testVmDefectList() throws IOException{
        ArrayList<Defect> defectList = Utils.vmDefectList(RAW_DATA_File_NAME);
        assertEquals(vmDefectCount,defectList.size());
    }
    
    @Test
    public void testJCLDefectList() throws IOException {
        ArrayList<Defect> defectList = Utils.jclDefectList(RAW_DATA_File_NAME);
        assertEquals(jclDefectCount,defectList.size());
    }

    @Test
    public void testJITDefectList() throws IOException {
        ArrayList<Defect> defectList = Utils.jitDefectList(RAW_DATA_File_NAME);
        assertEquals(jitDefectCount,defectList.size());
    }
    
    @Test
    public void testLoadRecordedVMData() throws InvalidFormatException, IOException{
        ArrayList<Defect> defectList = Utils.loadRecordedVMData(RECORDED_EXCEL_FILE);
        assertEquals(vmRecordedDefectCount, defectList.size());
    }
    
    @Test
    public void testLoadRecordedJCLData() throws InvalidFormatException, IOException{
        ArrayList<Defect> defectList = Utils.loadRecordedJCLData(RECORDED_EXCEL_FILE);
        assertEquals(jclRecordedDefectCount, defectList.size());
    }
    
    @Test
    public void testLoadRecordedJITData() throws InvalidFormatException, IOException{
        ArrayList<Defect> defectList = Utils.loadRecordedJITData(RECORDED_EXCEL_FILE);
        assertEquals(jitRecordedDefectCount, defectList.size());
    }
}
