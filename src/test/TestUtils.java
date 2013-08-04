package test;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

import util.Utils;
import data.Defect;


public class TestUtils {
    
    private final String RAW_DATA_File_NAME = "testResource/raw_data.txt";
    private final String RECORDED_EXCEL_FILE = "testResource/CMVC_Trends.xlsx";
    private final int vmDefectCount = 71;
    private final int jclDefectCount = 13;
    private final int jitDefectCount = 8;
    
    private final int vmRecordedDefectCount = 46;
    private final int jclRecordedDefectCount = 11;
    private final int jitRecordedDefectCount = 3;

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
