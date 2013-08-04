package main;

import java.util.ArrayList;

import data.Component;
import data.Config;
import data.Defect;
import util.Analyze;

public class Main {
    public static void main(String[] args) {
        Analyze a = new Analyze();

        System.out.println("Start to analyze VM defects");
        ArrayList<Defect> vmNewResult = a.getNewDefect(Component.VM, Config.RAW_DATA_File_NAME, Config.RECORDED_EXCEL_FILE);
        ArrayList<Defect> vmClosedResult = a.getClosedDefect(Component.VM, Config.RAW_DATA_File_NAME, Config.RECORDED_EXCEL_FILE);
        int vmNew = vmNewResult.size();
        int vmClosed = vmClosedResult.size();
        System.out.println("For VM component, we got " + vmNew
                + " New defects and " + vmClosed + " closed");
        
        System.out.println("Start to analyze JCL defects");
        ArrayList<Defect> jclNewResult = a.getNewDefect(Component.JCL, Config.RAW_DATA_File_NAME, Config.RECORDED_EXCEL_FILE);
        ArrayList<Defect> jclClosedResult = a.getClosedDefect(Component.JCL, Config.RAW_DATA_File_NAME, Config.RECORDED_EXCEL_FILE);
        int jclNew = jclNewResult.size();
        int jclClosed = jclClosedResult.size();
        System.out.println("For JCL component, we got " + jclNew
                + " New defects and " + jclClosed + " closed");

        System.out.println("Start to analyze JIT defects");
        ArrayList<Defect> jitNewResult = a.getNewDefect(Component.JIT, Config.RAW_DATA_File_NAME, Config.RECORDED_EXCEL_FILE);
        ArrayList<Defect> jitClosedResult = a.getClosedDefect(Component.JIT, Config.RAW_DATA_File_NAME, Config.RECORDED_EXCEL_FILE);
        int jitNew = jitNewResult.size();
        int jitClosed = jitClosedResult.size();
        System.out.println("For JIT component, we got " + jitNew
                + " New defects and " + jitClosed + " closed");

        System.out.println("In Total we got: " + (jclNew + vmNew + jitNew)
                + " new defects and " + (jclClosed + vmClosed + jitClosed)
                + " defects Closed/Canceled");
    }
}
