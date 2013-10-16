package utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import model.Isp;

public class LoadBalancer {
	
	
	public LoadBalancer(ArrayList<Isp> ispList)
	{
		Iterator<Isp> it = ispList.iterator();
		int count=0;
		while(it.hasNext())
		{
			Isp i=it.next();
			System.out.println(i);
			count++;
		}
		editRTTable(count);
		addRoute(ispList);
		addDefault(ispList);
		addRule(ispList);
		balance(ispList);
	}
	private void addRoute(ArrayList<Isp> ispList)
	{
		String command;
		Iterator<Isp> it = ispList.iterator();
		while(it.hasNext())
		{
			Isp isp = it.next();
			command="/usr/bin/sudo /sbin/ip route add "+isp.getNetwork()+"/"+isp.getMask()+" dev "+isp.getIface()+" src "+isp.getIp_address()+" table "+isp.getName();
			System.out.println(command);
			SystemCall.execute(command);
			command="/usr/bin/sudo /sbin/ip route add default via "+isp.getGateway()+" table "+isp.getName();
			System.out.println(command);
			SystemCall.execute(command);
			command="/usr/bin/sudo /sbin/ip route add "+isp.getNetwork()+"/"+isp.getMask()+" dev "+isp.getIface()+" src "+isp.getIp_address();
			System.out.println(command);
			SystemCall.execute(command);
		}
	}
	private void addRule(ArrayList<Isp> ispList)
	{
		String command;
		Iterator<Isp> it = ispList.iterator();
		while(it.hasNext())
		{
			Isp isp = it.next();
			command="/usr/bin/sudo /sbin/ip rule add from "+isp.getIp_address()+" table "+isp.getName();
			System.out.println(command);
			SystemCall.execute(command);
		}
	}
	
	private void addDefault(ArrayList<Isp> ispList)
	{
		String command;
		Iterator<Isp> it = ispList.iterator();
		if(it.hasNext())
		{
			Isp isp = it.next();
			command="/usr/bin/sudo /sbin/ip route add default via "+isp.getGateway();
			System.out.println(command);
			SystemCall.execute(command);
		}
	}
	
	private void balance(ArrayList<Isp> ispList)
	{
		String command;
		Iterator<Isp> it = ispList.iterator();
		command="/usr/bin/sudo /sbin/ip route chg default scope global";
		while(it.hasNext())
		{
			Isp isp = it.next();
			command+=" nexthop via "+isp.getGateway()+" dev "+isp.getIface()+" weight "+isp.getWeight();
		}
		System.out.println(command);
		SystemCall.execute(command);
	}
	
	public void balance(Isp isp)
	{
		String command;
		command="/usr/bin/sudo /sbin/ip route chg default scope global";
		command+=" nexthop via "+isp.getGateway()+" dev "+isp.getIface()+" weight "+isp.getWeight();
		System.out.println(command);
		SystemCall.execute(command);
	}
	
	private void editRTTable(int count)
	{
		try {
            String command="/usr/bin/sudo /bin/chmod -R 777 /etc/iproute2/rt_tables";
            SystemCall.execute(command);
             
            File file =new File("/etc/iproute2/rt_tables");

            if(!file.exists()){
            	file.createNewFile();
            }
            
            int tablecount=1;
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while((line=bufferedReader.readLine())!=null)
            {
            	if(line.matches(".*Table"+tablecount))
            	{
            		if(tablecount==count)
            		{
				tablecount++;
				break;
                        }
            		tablecount++;
            	}
            }
            bufferedReader.close();
            
            FileWriter fileWritter = new FileWriter(file,true);
        	BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            while(tablecount<=count)
            {
            	bufferWritter.write(tablecount+" Table"+tablecount+"\n");
            	tablecount++;
            }
            bufferWritter.close();
        
            command="/usr/bin/sudo /bin/chmod -R 644 /etc/iproute2/rt_tables";
            SystemCall.execute(command);

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}

