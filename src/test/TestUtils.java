package test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import data.Component;
import data.Defect;
import util.Utils;


public class TestUtils {

    @Test
    public void testLoadRawData() throws IOException {
        Utils u = new Utils();
        ArrayList<Defect> defectList = u.loadRawData("testResource/raw_data.txt");
        assertEquals(defectList.size(), 92);
        
        int countJ9cloud = 0;
        int countClasses = 0;
        int countJIT = 0;
        
        for(Defect defect : defectList){
            if(defect.getComponent().equals(Component.VM)){
                countJ9cloud++;
            } else if(defect.getComponent().equals(Component.JCL)){
                countClasses++;
            } else if(defect.getComponent().equals(Component.JIT)){
                countJIT++;
            }
        }
        assertEquals(71,countJ9cloud);
        assertEquals(13,countClasses);
        assertEquals(8,countJIT);
    }

}
