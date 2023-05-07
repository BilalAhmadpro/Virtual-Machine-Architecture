/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os_project;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;


/**
 *
 * @author USER
 */
public class OperatingSystem {
    private final short GPR[];
    private final short SPR[];
    private byte num;
    public  static byte memory[];
    private HashMap<Integer,Boolean> frames;
    private int memoryindex=0;
    Queue running=new Queue();
    Queue ready1=new Queue();
    LinkedList ready2=new LinkedList();
    
    public OperatingSystem() throws IOException
    {
         
         frames=new HashMap<>();
         GPR = new short[16]; //declaration of general purpose register array  (...........)
         SPR = new short[16]; //declaration of special purpose register array
         num = 0; //storage for immediate variable
         memory = new byte[64000];  //declaring memory of 64k bytes   
        executeInstructions();
    }
    private void executeInstructions() throws IOException
    {
        //adding process files to hashmap
        ArrayList<String> ar= new ArrayList<String>();
        ar.add("noop.txt");        
        ar.add("flags.txt");
        ar.add("large0.txt");
        ar.add("p5.txt");
        ar.add("power.txt");
        ar.add("sfull.txt"); 
        ar.add("p5 - Copy.txt");
       for(int i=0; i<ar.size(); i++)
       {
           System.out.println(ar.get(i));
           String output=FileToHex.convertFileToHex(Paths.get(ar.get(i)));//ASCII values convert to mapped hex values
       
        try (FileWriter myWriter = new FileWriter("process "+(i+1)+".txt")) 
        {
            myWriter.write(output); // hex writing to files
        }
        
       }
         for(int i=0; i<500; i++) // hashmap to reccord the free frames available in memory
         {
           frames.put(i, false);      
         }
         
        ArrayList<ProcessControlBlock> pcblist=new ArrayList<ProcessControlBlock>();//PCB's  array
        for(int i=0; i<ar.size(); i++)
        {
         try (FileReader reader = new FileReader("process "+(i+1)+".txt"))
         {
             
            Scanner in=new Scanner(reader);
            ProcessControlBlock pcb=null;
            if(i==0)
            {
                 pcb= makePCB_LoadProgram(in,"process "+(i+1)+".txt","NOOP");//making PCB from process txt file
            }
            else if(i==1)
            {
                 pcb= makePCB_LoadProgram(in,"process "+(i+1)+".txt","FLAG");
            }
            else if(i==2)
            {
                 pcb= makePCB_LoadProgram(in,"process "+(i+1)+".txt","LARGE");
            }
            else if(i==3)
            {
                 pcb= makePCB_LoadProgram(in,"process "+(i+1)+".txt","p5");
            }
            else if(i==4)
            {
                 pcb= makePCB_LoadProgram(in,"process "+(i+1)+".txt","POWER");
            }
            else if(i==5)
            {
                 pcb= makePCB_LoadProgram(in,"process "+(i+1)+".txt","full");
            }
            else if(i==6)
            {
                 pcb= makePCB_LoadProgram(in,"process "+(i+1)+".txt","p5 - Copy");
            }
           
          ready1.Enquue(pcb);
           
            if(  pcb != null && pcb.ProcessPriority>=0 && pcb.ProcessPriority<=15){
                ready1.Enquue(pcb); // priority wise adding pcbs to ready queue 1 and queue2
            }
            else if ( pcb!=null && pcb.ProcessPriority>=16 && pcb.ProcessPriority<=31){
                ready2.addNode(pcb);
            }
            
            else{
                System.out.println("Invalid Priority ERROR PROMPT");
            }
          
            pcblist.add(pcb);
           
            System.out.println("-----------------------------------------------");    
        }
         }
              pcblist.toString();
          System.out.println("priority of queue 1: "+ready1.toString());
          System.out.println("priority of queue 2: "+ready2.toString()); 
            for(int i=0;i<SPR.length;i++) // initializing the CPU's general adn special Purpose Registers
            {
                SPR[i]=0;
                GPR[i]=0;
            }
            
       this.ProcessScheduling(ready1, ready2, running);
    }
    
    private ProcessControlBlock makePCB_LoadProgram(Scanner in, String filename,String processname)
    {
        
        int frame=giveUnoccupiedFrame();
        int bytecount=0;
        int baseadress=frame*128;
        int index=baseadress;
        int limitadress=baseadress+127;
        int process_priority=0;
        int data_size=-1;
        int process_id=0;
        int actualcount=0;
        boolean flag_first=false;
        HashMap<Integer ,String> frames_segment=new HashMap<>();
      
          while (in.hasNextLine())
            {
            
                  String str = in.nextLine();
                  String[] splited = str.split("\\s+");
                for (String splited1 : splited)
                {
                   
                    actualcount++; // counting total bytes of process
                    
                    if(actualcount>=9)//writing process data and code to physical memory
                    {
                     memory[index] = (byte) Integer.parseInt(splited1, 16); 
                     index++;
                     bytecount++;
                    }
                    
                    if(actualcount==5) // extracting priority,id and data size of process
                    {
                       process_priority=Integer.parseInt( splited[0],16);
                       process_id=  ((byte) Integer.parseInt(splited[1], 16)<<8|(byte) Integer.parseInt(splited[2], 16)&0xFF);
                       data_size=  ((byte) Integer.parseInt(splited[3], 16)<<8|(byte) Integer.parseInt(splited[4], 16)&0xFF);
                    }
                    if(data_size==0 && !flag_first) //spiliting process dump to code and data segments
                    {
                        System.out.println(frame+" "+"code");
                        frames_segment.put(frame,"code" );
                        frames.put(frame, true);
                        flag_first=true;
                    }
                    
                    
                    if(bytecount==128 || actualcount-7==data_size)
                    {
                       
                        if(actualcount-9>=data_size)
                        {
                          //making code segment frame
                            frames_segment.put(frame, "code");
                            frames.put(frame, true);
                            frame=giveUnoccupiedFrame();
                            frames_segment.put(frame, "code"); 
                            baseadress=frame*128;
                            index=baseadress;
                            limitadress=baseadress+127;
                            bytecount=0;
                           
                            
                        }
                        else
                        {
                          if(data_size!=0)
                           {
                               //making data segment frame
                            frames_segment.put(frame, "data");
                            frames.put(frame, true);
                            frame=giveUnoccupiedFrame();
                            baseadress=frame*128;
                            index=baseadress;
                            limitadress=baseadress+127;
                            bytecount=0; 
                           
                           }
                        }
                       
                    }
                 
                }
                
            }
          
            if(memory[baseadress]!=0 && frames.get(frame)==false )
           {
                 frames.put(frame, true);
                 
                 if(actualcount-9>=data_size)
                 {
                     frames_segment.put(frame,"code" );
                   //making code frame segment
                 }
                 else
                 {
                
                     frames_segment.put(frame, "data");
                     
                 }
                 
            }
             System.out.println("total data size is = "+data_size);
        
           
          int count_code=0;
          int count_data=0;
          for (Integer key: frames_segment.keySet())
          {
              System.out.println(key+" = "+frames_segment.get(key));
          }
          // counting the data and code frames in memory
           for (Integer key: frames_segment.keySet())
          {
              if( frames_segment.get(key)!=null && frames_segment.get(key).equalsIgnoreCase("data"))
              {
                  count_data++;
              }
              else
              {
                  count_code++;
              }
          }
           //making page tables of data and code segments
          PageTable[] data=new PageTable[count_data];
          PageTable[] code=new PageTable[count_code];
          int index_code=-1;
          int index_data=-1;
          int i=0;
            for (Integer key: frames_segment.keySet()){
           
              if(frames_segment.get(key)!=null && frames_segment.get(key).equalsIgnoreCase("data"))
              {
                  data[++index_data]=new PageTable(key,i);
                  
              }
              else
              {
                  code[++index_code]=new PageTable(key,i);
              }
              i++;
          }
            for(int j=0;j<data.length;j++)
            {
                System.out.println("page number data segment = "+data[j].pagenumber+" and frame number = "+data[j].framenumber);
            }
             for(int j=0;j<code.length;j++)
            {
                System.out.println("page number code segment = "+code[j].pagenumber+" and frame number = "+code[j].framenumber);
            }
           //segment table making with page tables
          SegmentTable[] segment=new SegmentTable[2];
          segment[0]=new SegmentTable(data.length*128,data);
          segment[1]=new SegmentTable(code.length*128,code);
          
          int size=segment[0].limit+segment[1].limit;
          ProcessControlBlock pcb=new ProcessControlBlock(process_id,process_priority,processname,size,segment, filename);
          pcb.SPR[0]=(short) (segment[1].pagetable[0].framenumber*128);//code base register
          pcb.SPR[1]=(short) (size-data_size);// code limit register 
          pcb.SPR[2]=(short)(size-data_size);// code counter register
          pcb.SPR[3]=(short)(segment[1].pagetable[0].framenumber*128); //data base register
          pcb.SPR[1]=(short)(data_size); // data limit register
          pcb.SPR[4]=(short)(data_size);  // data count register
          pcb.SPR[9]=(short)(segment[1].pagetable[0].framenumber*128); // Program Counter
          pcb.SPR[10]=(short)(memory[segment[1].pagetable[0].framenumber*128]); // Instruction Register
          
          return pcb;
    }
    private int giveUnoccupiedFrame()
    {
        //check the free frames available and give for the program loading
        for(int i=0;i<frames.size();i++)
        {
            if(frames.get(i)==false)
            {
                return i;
            }
        }
        return -1;
    }        
   
    public void ProcessScheduling(Queue ready1, LinkedList ready2, Queue running) throws IOException{
      if(ready1!=null &&!ready1.IsEmpty())
      {
      //scheduling the ready queue 1 using First Come First Serve and priority queue
          running.Enquue(ready1.front.pb1);
          ready1.Dequue();
          while(!running.IsEmpty())
          {
             
             ProcessControlBlock pcb=running.Dequue();
              System.out.println(pcb);
              System.out.println("-------------------");
             for(int i=0;i<16;i++)
             {
                 SPR[i]=pcb.SPR[i];  // loading prcoess registers to CPU registers
                 GPR[i]=pcb.GPR[i];
             }
             
             SegmentTable[] st=pcb.table;
             SegmentTable code=st[1];
             SegmentTable data=st[0];
             PageTable[] codetable=code.pagetable;
             PageTable[] datatable=data.pagetable;
              System.out.println("length is"+datatable.length); 
             for(int i=0;i<codetable.length;i++)
             {
                
                SPR[9]=(short) (codetable[i].framenumber*128);// program counter register
             //adress translation of logicla to physical one
               for(int j=SPR[9];j<SPR[9]+127;j++)
              {
                //decoding the instruction hex value to respective decimal code
      int instruction=Integer.parseInt(Integer.toHexString(Byte.toUnsignedInt((byte) memory[SPR[9]])), 16);
                  System.out.println(instruction);
             
               executeInstruction(instruction);
                
              
              }
          }
             try (FileWriter myWriter = new FileWriter(pcb.ProcessName+"afterEcecution"+".txt"))
        {
            myWriter.write(pcb.toString()); // hex writing to files
        }
             if(!ready1.IsEmpty())
             {
              running.Enquue(ready1.front.pb1);
             ready1.Dequue();
             }
      }
          
      }
      // round-robin scheduling for ready queue number 2
      while(true)
      {
        
        ProcessControlBlock pcb;
        Node temp=ready2.head;
        while(temp!=null)
        {
            pcb=temp.pb1;
                    System.out.println("Process running in round_robin currently is :"+pcb.ProcessName);
            for(int i=0;i<16;i++)
             {
                 SPR[i]=pcb.SPR[i];
                 GPR[i]=pcb.GPR[i];
             }
             
             SegmentTable[] st=pcb.table;
             SegmentTable code=st[1];
             SegmentTable data=st[0];
             PageTable[] codetable=code.pagetable;
             PageTable[] datatable=data.pagetable;
             System.out.println("length is"+datatable.length); 
                   
                if(pcb.start==false)
                {    
                  SPR[9]=(short) (codetable[0].framenumber*128);//spr[9]=pc
                  pcb.start=true;
                }
              
         int response=-1;     
         // time quantum is 8 for each process and each instruction takes 2 quantum so 4 times each process gets it's turn
         for(int i=0;i<4;i++)
         {
            int instruction=Integer.parseInt(Integer.toHexString(Byte.toUnsignedInt((byte) memory[SPR[9]])), 16);
                        
           response= executeInstruction(instruction);
               
         }
         for(int i=0;i<16;i++)
         {
             //saving the state of process (Process-Switching)
              pcb.SPR[i]=SPR[i];
              pcb. GPR[i]=GPR[i];
         }
         //cheking for Program Termination condition
         if(response==0 || SPR[9]>=(codetable[codetable.length-1].framenumber*128+127))
         {
             
             ready2.deleteNode(pcb);
         }
         if(ready2.length==0)
         {
             return ;
         }
         if(temp.next==null)
         {
             temp=ready2.head;
         }
         
         else
         {
           temp=temp.next;
         }   
              }
          }
            
        }       
    public int executeInstruction(int code)
    {
         
          //243 is the encoded value of F3 (which is an end call)
            //Interval 1
            //int decimalvalue = Integer.parseInt(Integer.toHexString(Byte.toUnsignedInt(memory[pc])), 16);
            if (code >= 22 && code <= 28)
            { //1C is represented as 28 IN DECIMAL
                SPR[9]++; //mcounter moves to the next instruction
                
                int reg0=Integer.parseInt(Integer.toHexString(Byte.toUnsignedInt(memory[SPR[9]])), 16);
                SPR[9]++;
                int reg1=Integer.parseInt(Integer.toHexString(Byte.toUnsignedInt(memory[SPR[9]])), 16);
            if(reg1<16 && reg0<16)
            {
                System.out.println("value in register "+reg0+" =  "+  Integer.toHexString(Short.toUnsignedInt(GPR[reg0])).toUpperCase());
                RegisterToRegister(code, reg0, reg1);
            
                System.out.println("CALLING THE SWITCH CASE for REGISTER TO REGISTER FUNCTION ");
                SPR[9]++;
                System.out.println("1st Register is "+reg0);
                System.out.println("2nd register is "+reg1);
        
               
                System.out.println("value in register "+reg1+" =  "+  Integer.toHexString(Short.toUnsignedInt(GPR[reg1])).toUpperCase());
                System.out.println("After Execution of Instruction  the Register0 value is "+Integer.toHexString(Short.toUnsignedInt(GPR[reg0])).toUpperCase());
                System.out.println("------------------------------------------------"); 
            }
            else
            {
                System.out.println("Invalid-Register Code ERROR PROMPT");
                return -1;
            }
                return 2;
            }
                  
             //Interval 2
            else if (code >= 48 && code <= 54)
            { //36 is represented as 54 IN DECIMAL
                code = memory[SPR[9]];
                SPR[9]++; //mcounter moves to the next instruction
                int reg0=Integer.parseInt(Integer.toHexString(Byte.toUnsignedInt(memory[SPR[9]])), 16);
                SPR[9]++; 
                byte imm1 = memory[SPR[9]];
                SPR[9]++; 
                byte imm2 = memory[SPR[9]];
                SPR[9]++; 
               
                RegisterToImmediate(code, reg0, imm1,imm2);
                System.out.println("CALLING THE SWITCH CASE for REGISTER immediate FUNCTION ");
                System.out.println("Immediate value moved to Register is "+Integer.toHexString(GPR[reg0]).toUpperCase());
                System.out.println("-----------------------------------------------");
                return 3;
            }
            
            //Interval 3
            else if (code >= 55 && code <= 61) 
            { //hexa opcodes
                code = memory[SPR[9]];
                SPR[9]++;  //mcunter moves to the next instruction
                num = memory[SPR[9]];
                SPR[9]++; 
                ImmediateFunction(code, num);
                System.out.println("CALLING THE SWITCH CASE for immediate FUNCTION ");
                return 1;
            } 
            
            //interval 4
                      
            //Interval 5
            else if (code >= 113 && code <= 120)
            { //hexa opcodes
                code = memory[SPR[9]];
                 SPR[9]++;  //mcounter moves to the next instruction
                int reg0=Integer.parseInt(Integer.toHexString(Byte.toUnsignedInt(memory[SPR[9]])), 16);
                 SPR[9]++; 
                SingleOperand(code, reg0);
                System.out.println("CALLING THE SWITCH CASE for single operand FUNCTION ");
                return 1;
            } 
            //Interval 6
            else if (code >= 241 && code <= 242)
            {    //f1 to f2 rep in decimal
                NoOperand(code);
                System.out.println("calling switch case of no operand function ");
                return 0;
            } //end case
            else if (code == 243)
            {
                NoOperand(code);
                System.out.println("CALLING END FUNCTION OR NO OPERAND FUNCTION");
                return 0;
               
            }
            else
            {
                SPR[9]++;
            }        
          
        
        
      return 0;
    }
    
    

    public static void main(String[] args) throws IOException {
      OperatingSystem os=new OperatingSystem();
       

        
    }
    public  void  RegisterToRegister(int code, int R1, int R2){
          if(R1<0 && R1>15 && R2<0 && R2>15)
          {
              System.out.println("Invalid - Register Code ERROR PROMPT");
              return;
          }
     
        switch(code){
            case 22:
                GPR[R1] = GPR[R2];
                System.out.println("CALLING MOV FUNCTION");
                break;
            case 23:
                GPR[R1] = (short) (GPR[R1] + GPR[R2]);
                System.out.println("CALLING ADD FUNCTION");
                break;
            case 24:
                GPR[R1] = (short) (GPR[R1] - GPR[R2]);
                System.out.println("CALLING SUB FUNCTION");
                break;
            case 25:
                GPR[R1] = (short) (GPR[R1] * GPR[R2]);
                System.out.println("CALLING MUL FUNCTION");
                break;
            case 26:
                GPR[R1] = (short) (GPR[R1] / GPR[R2]);
                System.out.println("CALLING DIV FUNCTION");
                break;
            case 27:
                GPR[R1] = (short) (GPR[R1] & GPR[R2]);
                System.out.println("CALLING AND FUNCTION");
                break;
            case 28:
                GPR[R1] = (short) (GPR[R1] | GPR[R2]);
                System.out.println("CALLING OR FUNCTION");
                break;   
        }
        
    }
    
        public void RegisterToImmediate(int code, int R1, byte num,byte num2)
        {
              if(R1<0 && R1>15 )
          {
              System.out.println("Invalid - Register Code ERROR PROMPT");
              return;
          }
            short rst = (short) (num<<8 | num2 & 0xFF);
        switch(code){
            case 48:
                GPR[R1] = rst;
                System.out.println("CALLING MOVI FUNCTION");
                break;
            case 49:
                GPR[R1] = (short) (GPR[R1] + rst);
                System.out.println("CALLING ADDI FUNCTION");
                break;
            case 50:
                GPR[R1] = (short) (GPR[R1] - rst);
                System.out.println("CALLING SUBI FUNCTION");
                break;
            case 51:
                GPR[R1] = (short) (GPR[R1] * rst);
                System.out.println("CALLING MULI FUNCTION");
                break;
             case 52:
                
                 if(rst==0)
                 {
                     System.out.println("Divide by ZERO ERROR PROMPT");
                     return;
                 }
                  GPR[R1] = (short) (GPR[R1] / rst);
                System.out.println("CALLING DIVI FUNCTION");
                break;
                
            case 53:
                GPR[R1] = (short) (rst & GPR[R1]);
                System.out.println("CALLING ANDI FUNCTION");
                break;
            case 54:
                GPR[R1] = (short) (GPR[R1] | rst);
                System.out.println("CALLING ORI FUNCTION");
                break;      
        }
        
    }
        public static void ImmediateFunction(int code, byte imm){
            
          
            switch(code){
            case 55:
                System.out.println("CALLING BZ FUNCTION");
                break;
            case 56:
                System.out.println("CALLING BNZ FUNCTION");
                break;
            case 57:
                System.out.println("CALLING BC FUNCTION");
                break;
            case 58:
                System.out.println("CALLING BS FUNCTION");
                break;
             case 59:
                System.out.println("CALLING JMP FUNCTION");
                break;
            case 60:
                System.out.println("CALLING PROCEDURE CALL FUNCTION");
                break;
            case 61:
                System.out.println("CALLING ACT FUNCTION");
                break;
        }
            
        }
       
        public void SingleOperand(int code, int R1)
        {
            if(R1<0 && R1>15 )
          {
              System.out.println("Invalid - Register Code ERROR PROMPT");
              return;
          }
        
        switch(code){
            case 71:
                GPR[R1] = (short) (GPR[R1] << 1);
                System.out.println("CALLING SHL FUNCTION");
                break;
            case 72:
                GPR[R1] = (short) (GPR[R1] >> 1);
                System.out.println("CALLING SHR FUNCTION");
                break;
            case 73:
                System.out.println("CALLING RTL FUNCTION");
                break;
            case 74:
                System.out.println("CALLING RTR FUNCTION");
                break;
            case 75:
                GPR[R1] = (short) (GPR[R1] + 1);
                System.out.println("CALLING INC FUNCTION");
                break;
            case 76:
                GPR[R1] = (short) (GPR[R1] - 1);
                System.out.println("CALLING DEC FUNCTION");
                break;
            case 77:
                System.out.println("CALLING PUSH FUNCTION");
                break;
            case 78:
                System.out.println("CALLING POP FUNCTION");
                break; 
        }
     
    }
        
        public static void NoOperand(int code){
              //................
        
        switch(code){
            case 241:
                System.out.println("CALLING RETURN FUNCTION");
                break;
            case 242:
                System.out.println("CALLING NOOP FUNCTION");
                break;
                }
            
        }
   
}