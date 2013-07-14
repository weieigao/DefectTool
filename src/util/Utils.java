package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import data.Defect;

public class Utils {
    
    public ArrayList<Defect> loadRawData (String rawDataFileName) throws IOException{
        ArrayList<Defect> result = new ArrayList<Defect>();
        File f = new File(rawDataFileName);
        BufferedReader br = new BufferedReader(new FileReader(f));
        
        String line;
        while((line=br.readLine()) != null){
            String id  = extractID(line);
            String component = extractComponent(line);
            result.add(new Defect(id, component));
        } 
        br.close();
        return result;
    }

    private String extractComponent(String line) {
        final String FOUR_SPACES="    ";
        int fourSpacesIndex = line.indexOf(FOUR_SPACES);
        String lineRemovedID = line.substring(fourSpacesIndex+4);
        int firstSpaceIndex = lineRemovedID.indexOf(" ");
        String component = lineRemovedID.substring(0,firstSpaceIndex);
        return component;
    }

    private String extractID(String line) {
        return line.substring(0, 5);
    }
    
    
}
