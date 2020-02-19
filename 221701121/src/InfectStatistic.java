/**
 * InfectStatistic
 * TODO
 *
 * @author shenmw
 * @version 2.0
 * @since 2.18
 */

import java.util.List;
import java.io.*;
import java.util.HashMap;
import java.util.ArrayList;

class InfectStatistic 
{	
	public String logPath;//����·��
	
	public String outputPath;//output�ļ�����·��

	public String[] arg;//���������в���

	public boolean isRead;//��ȡ������־�ļ�

	public String date;//��־����
	
    public List<String> name;    //ʡ������    
    
    public String[] provinceName;//����ʡ������
    
    public Province country;    //ȫ������� 	

    public InfectStatistic(String[] args)
    {
        provinceName = new String[]{"����","����","����","����","����","�㶫",
            "����","����","����","�ӱ�","����","������","����","����",
            "����","����","����","����","���ɹ�","����","�ຣ","ɽ��","ɽ��","����","�Ϻ�",
            "�Ĵ�","���","����","�½�","����","�㽭"};
        arg = args;
        isRead = true;
        logPath = "G:\\example\\log\\";
        outputPath = "G:\\example\\result\\output3.txt";
        name = new ArrayList<>();       
        map = new HashMap<String,Province>();
        country = new Province("ȫ��");
        isOutput = true;
        isOutputAll = true;
        provinces = new ArrayList<>();
        output = new String[4];     
        isFinish = false;
        for (int i = 0; i < provinceName.length; i ++ )
        {
            name.add(provinceName[i]);
            map.put(name.get(i), null);
        }
        for (int i = 0; i < 4; i ++ )
        {
            output[i] = "";
        }
        this.init();
    }

	//������־�ļ�
    //��־�ļ�
    public void deal() throws IOException
    {               
        String logDate;     
        String[] sArray;
        File file = new File(logPath);
        File[] tempList = file.listFiles();
        
        if (!isRead)
        {
            logDate = new String(tempList[tempList.length-1].getName());                      
            sArray = logDate.split("\\.");                    
            logDate = new String(sArray[0]);
            if ((logDate.compareTo(date)) < 0)
            {
                isFinish = true;
                System.out.println("���ڳ�����Χ!");
                return;
            }
        }

        for (int i = 0; i < tempList.length; i ++ )                   
        {                
            logDate = new String(tempList[i].getName());                      
            sArray = logDate.split("\\.");                    
            logDate = new String(sArray[0]);
                                                        
            if (isRead || (logDate.compareTo(date)) <= 0)                      
            {                 
                BufferedReader br = null;               
                String line = null;         
                br = new BufferedReader(new InputStreamReader(new FileInputStream(tempList[i].toString()), "UTF-8"));  
                
                while ((line = br.readLine()) != null)
                {
                    String[] array = line.split(" ");
                    dealOneLine(array);
                }          
                br.close();
            }                               
        }
        allStatistic();
    }

	//����"-date"����
	public void init()
	{
		for(int i=0;i<arg.length;i++)
		{			
			switch(arg[i])
			{
			    case "-date":
				    date = new String(arg[i+1]);
				    isRead = false;	
				    break;
			    case "-log":
				    logPath = new String(arg[i+1]);
				    break;
			    case "-out":
				    outputPath = new String(arg[i+1]);
				    break;
			    default:					
			}			
		}
	}
    //-province����
    public void dealProvince(int index) 
    {
        while (index < arg.length)
        {
            switch (arg[index])
            {
                case "-date":
                    return;
                case "-log":
                    return;
                case "-out":
                    return;
                case "-type":
                    return;
                default:
                    provinces.add(arg[index]);
                    map.put(arg[index], null);
            }   
            index ++ ;
        }               
    }

    public static void main(String[] args) 
    {
    	if(args[0].equalsIgnoreCase("list"))
    	{
    	    InfectStatistic l = new InfectStatistic(args);
    	    l.deal();	
    	}
    	else
    	{
    	    System.out.print("δ֪�����" + args[0]);
    	}    		
    }
}
class Province
{ 
    private String name;    //ʡ������

    private int cure;    //��������

    private int dead;    //��������
    
    private int suspectedPatients;    //��������

    private int infectionPatients;    //��Ⱦ����  
    
    public Province(String n)
    {
        name = n;
    }
    //��Ⱦ��������
    public void addIp(String str)
    {
        str = str.substring(0, str.length()-1);
        infectionPatients += Integer.parseInt(str);
    }
    //��Ⱦ��������
    public void removeIp(String str)
    {
        str = str.substring(0, str.length()-1);
        infectionPatients -= Integer.parseInt(str);
    }
    //������������
    public void addSp(String str)
    {
        str = str.substring(0, str.length()-1);
        suspectedPatients += Integer.parseInt(str);
    }   
    //������������
    public void removeSp(String str)
    {
        str = str.substring(0, str.length()-1);
        suspectedPatients -= Integer.parseInt(str);
    }
    //������������
    public void cure(String str)
    {
        str = str.substring(0, str.length()-1);
        cure += Integer.parseInt(str);
        infectionPatients -= Integer.parseInt(str);
    }   
    //������������
    public void dead(String str)
    {
        str = str.substring(0, str.length()-1);
        dead += Integer.parseInt(str);
        infectionPatients -= Integer.parseInt(str);
    }
    //ʡ�����
    public void output(boolean isOutput, String[] output, BufferedWriter bw) throws IOException
    {
        if (isOutput)
        {
            bw.write(name + " ��Ⱦ���� " + infectionPatients + "�� "
                + "���ƻ��� " + suspectedPatients + "�� "
                + "���� " + cure + "�� "
                + "���� " + dead + "��");
            bw.newLine();
        }        
        else
        {
            bw.write(name);
            for (int i = 0; i < 4; i ++ )
            {
                switch (output[i])
                {
                    case "ip":                  
                        bw.write(" ��Ⱦ���� " + infectionPatients + "��");           
                        break;
                    case "sp":                  
                        bw.write(" ���ƻ��� " + suspectedPatients + "��");               
                        break;
                    case "cure":                    
                        bw.write(" ���� " + cure + "��");                  
                        break;
                    case "dead":                    
                        bw.write(" ���� " + dead + "��");          
                        break;
                    default:                
                        break;
                }
            }
            bw.newLine();
        }       
    }       
    //ȫ�������
    public void allAdd(Province p)
    {
        this.infectionPatients += p.infectionPatients;
        this.suspectedPatients += p.suspectedPatients;
        this.cure += p.cure;
        this.dead += p.dead;
    }
}

