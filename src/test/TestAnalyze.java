package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import util.Analyze;
import data.Component;
import data.Defect;

public class TestAnalyze {

    private final String RAW_DATA_File_NAME = "testResource/raw_data.txt";
    private final String RECORDED_EXCEL_FILE = "testResource/CMVC_Trends.xlsx";

    @Test
    public void testGetNewDefect() {
        Analyze a = new Analyze();
        ArrayList<Defect> jitNewResult = a.getNewDefect(Component.JIT,
                RAW_DATA_File_NAME, RECORDED_EXCEL_FILE);
        ArrayList<Defect> jitClosedResult = a.getClosedDefect(Component.JIT,
                RAW_DATA_File_NAME, RECORDED_EXCEL_FILE);
        int jitNew = jitNewResult.size();
        int jitClosed = jitClosedResult.size();

        assertEquals(6, jitNew);
        assertEquals(1, jitClosed);

        Defect closed = jitClosedResult.get(0);
        assertEquals("197745", closed.getId());

        ArrayList<String> expectedNewDefectsIDs = new ArrayList<String>();
        expectedNewDefectsIDs.add("198179");
        expectedNewDefectsIDs.add("198349");
        expectedNewDefectsIDs.add("198616");
        expectedNewDefectsIDs.add("198661");
        expectedNewDefectsIDs.add("198716");
        expectedNewDefectsIDs.add("198795");
        // {"196650", "197757", "198179", "198349",
        // "198616","198661","198716","198795"};
        ArrayList<String> newDefectsIDs = new ArrayList<String>();
        for (Defect d : jitNewResult) {
            newDefectsIDs.add(d.getId());
        }

        for (String defectID : expectedNewDefectsIDs) {
            assertTrue(newDefectsIDs.contains(defectID));
        }

        for (String defectID : newDefectsIDs) {
            assertTrue(expectedNewDefectsIDs.contains(defectID));
        }
    }

}
