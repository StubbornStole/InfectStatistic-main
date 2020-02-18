import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * InfectStatistic
 * TODO
 *
 * @author shenmw
 * @version 1.0
 * @since 2.18
 */
class InfectStatistic 
{	
	private String logPath;//����·��
	
	private String outputPath;//output�ļ�����·��

	private String[] arg;//���������в���

	private boolean isRead;//��ȡ������־�ļ�

	private String date;//��־����
	

	public InfectStatistic(String[] args)
	{
		isRead = true;
		arg = args;
		logPath = "G:/log/";
		this.init();
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

	//������־�ļ�
	public void deal()
	{	    

		//��ȡ��־�ļ�		
	    List<String> files = new ArrayList<String>();
	    File file = new File(logPath);
	    File[] tempList = file.listFiles();

	    for (int i = 0; i < tempList.length; i++) 	                
	    {     	

	        String logDate = new String(tempList[i].getName());	                  
	        String[] sArray = logDate.split("\\.");	                  
	        logDate = new String(sArray[0]);

	        if (isRead || (logDate.compareTo(date)) <= 0) 	                   
	        {	                 	
	        	//����������־	              	                        
	        	//files.add(tempList[i].toString());                                      
	        	System.out.println(logDate);	                    
	        }            	                
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


