/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os_project;

/**
 *
 * @author Bilal Ahmad
 */
public class ProcessControlBlock {
    int ProcessID;
    int ProcessPriority;
    String ProcessName;
    int ProcessSize;
    SegmentTable[] table;
    short GPR[];
    short SPR[];
    String fname;
    int waitingtime;
    int runningtime;
    boolean start=false;
    ProcessControlBlock(int pid,int ppriority,String pname,int psize,SegmentTable[] st, String fname)
    {
        this.ProcessID=pid;
        this.ProcessName=pname;
        this.ProcessSize=psize;
        this.table=st;
        this.ProcessPriority=ppriority;
        this.fname=fname;
        this.waitingtime=0;
        this.runningtime=0;
        this.GPR=new short[16];
        this.SPR=new short[16];
        
    }
    public String toString()
    {
       String str="";
       str+="Process id: "+ProcessID+"\n"+"Process Priority: "+ProcessPriority+"\n"+
               "Process size: "+ProcessSize+" BYTES "+"\n"+"Process Name:"+ProcessName+"\n";
       for(int i=0;i<table.length;i++)
       {
           if(i==0)
           {
            str+="\n Data Segment dump of Process \n";
           }
           else
           {
               str+="\n CODE Segment dump of Process \n";
           }
           PageTable[] pt=table[i].pagetable;
           for(int j=0;j<pt.length;j++)
           {
               
               int frame=pt[j].framenumber;
               int b_add=frame*128;
               int l_add=b_add+127;
               int countofline=0;
              for(int k=b_add;k<l_add;k++)
              { 
                  countofline++;
              
               str +=  Integer.toHexString(Byte.toUnsignedInt(OperatingSystem.memory[k])).toUpperCase()+" ";
               if(countofline%15==0)
               {
                  str+="\n";
                  countofline=0;
               }
              }
           }
       }
        System.out.println(str);
       return str;
    }
}
