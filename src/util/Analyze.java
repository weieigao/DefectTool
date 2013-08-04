package util;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import data.Component;
import data.Defect;

public class Analyze {

    public ArrayList<Defect> getNewDefect(Component comp, String rawDataFileName, String recordedExcelFile) {
        try {
            if (comp.equals(Component.JCL)) {
                return getNewDefect(
                        Utils.jclDefectList(rawDataFileName),
                        Utils.loadRecordedJCLData(recordedExcelFile));
            } else if (comp.equals(Component.VM)) {
                return getNewDefect(
                        Utils.vmDefectList(rawDataFileName),
                        Utils.loadRecordedVMData(recordedExcelFile));
            } else {
                return getNewDefect(
                        Utils.jitDefectList(rawDataFileName),
                        Utils.loadRecordedJITData(recordedExcelFile));
            }
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public ArrayList<Defect> getClosedDefect(Component comp, String rawDataFileName, String recordedExcelFile) {
        try {
            if (comp.equals(Component.JCL)) {
                return getClosedDefect(
                        Utils.jclDefectList(rawDataFileName),
                        Utils.loadRecordedJCLData(recordedExcelFile));
            } else if (comp.equals(Component.VM)) {
                return getClosedDefect(
                        Utils.vmDefectList(rawDataFileName),
                        Utils.loadRecordedVMData(recordedExcelFile));
            } else {
                return getClosedDefect(
                        Utils.jitDefectList(rawDataFileName),
                        Utils.loadRecordedJITData(recordedExcelFile));
            }
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    private ArrayList<Defect> getClosedDefect(ArrayList<Defect> rawDataList,
            ArrayList<Defect> recordedList){
        ArrayList<Defect> result = new ArrayList<Defect>();
        for(Defect d : recordedList){
            if(!rawDataList.contains(d)){
                result.add(d);
            }
        }
        
//        System.out.println("==========Closed============");
        
//        for(Defect d : result){
//            System.out.println(d.getId());
//        }
        
        return result;
    }

    private ArrayList<Defect> getNewDefect(ArrayList<Defect> rawDataList,
            ArrayList<Defect> recordedList) {
        
//        System.out.println("old: " + recordedList.size());
//        System.out.println("new: " + rawDataList.size());
        ArrayList<Defect> result = new ArrayList<Defect>();
        for (Defect recordedDefect : recordedList) {
            if(recordedDefect.isNew()){
                result.add(recordedDefect);
            }
        }
        
//        System.out.println("=========Raw=============");
        
        for(Defect rawDefect : rawDataList){
//            System.out.println(rawDefect.getId());
            if(!recordedList.contains(rawDefect)){
                result.add(rawDefect);
            }
        }
        
//        System.out.println("==========New============");
        
//        for(Defect d : result){
//            System.out.println(d.getId());
//        }
        
        return result;
    }
}
