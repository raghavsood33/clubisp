package utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Isp;

public class ConfigGenerator {
	
	public ArrayList<Isp> arraylist = new ArrayList<Isp>();
	Isp isp = new Isp();
	
	public ArrayList<Isp> getArraylist() {
		return arraylist;
	}
	
	public void setArraylist(ArrayList<Isp> arraylist) {
		this.arraylist = arraylist;
	}
	
	public Isp getIsp() {
		return isp;
	}
	
	public void setIsp(Isp isp) {
		this.isp = isp;
	}
	
	public ArrayList<Isp> createConfigList()
	{
		arraylist = new ArrayList<Isp>();
		Iterator<String> it = null;
		try {
			it = getInterfaces().iterator();
		
			int count=1;
			while(it!=null && it.hasNext())
			{
				String iface = it.next(); 
				isp = new Isp();
				isp.setName("Table"+count);
				isp.setIface(iface);
				if(iface.matches("^ppp.*"))
				{
					String line = getPPPInformation(iface);
					String network = getPPPNetworkAddress(line);
					isp.setNetwork(network);
					isp.setGateway(network);
					isp.setIp_address(getPPPIPAddress(line));
					isp.setMask(32);
				}
				else
				{
					isp.setIp_address(getIPAddress(iface));
					isp.setGateway(getGatewayAddress(iface));
					isp.setNetwork(getNetworkAddress(iface));
					isp.setMask(getMask(iface));
				}
				isp.setOnline(true);
				isp.setEnabled(true);
				isp.setWeight(1);
				arraylist.add(isp);
				count++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return arraylist;
	}
	
	private String getIPAddress(String iface) throws IOException
	{
		String command = "/usr/bin/nmcli dev list iface "+iface+"";
		BufferedReader buf = SystemCall.execute(command);
		String line;
		while ((line = buf.readLine()) != null) 
        {
        	if(line.matches(".*ip_address.*"))
        	{
        		Pattern pattern = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}");
        		Matcher matcher = pattern.matcher(line);
        		if(matcher.find())
        			return line.substring(matcher.start(), matcher.end());
        	}
        }
		return null;
	}
	
	private String getNetworkAddress(String iface) throws IOException
	{
		String command = "/usr/bin/nmcli dev list iface "+iface+"";
		BufferedReader buf = SystemCall.execute(command);
		String line;
		while ((line = buf.readLine()) != null) 
        {
			if(line.matches(".*network_number.*"))
        	{
				Pattern pattern = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}");
				Matcher matcher = pattern.matcher(line);
				if(matcher.find())
					return line.substring(matcher.start(), matcher.end());
        	}
        }
		return null;
	}
	
	private String getGatewayAddress(String iface) throws IOException
	{
		String command = "/usr/bin/nmcli dev list iface "+iface+"";
		BufferedReader buf = SystemCall.execute(command);
		String line;
		while ((line = buf.readLine()) != null) 
        {
			if(line.matches(".*gw.*"))
        	{	
				Pattern pattern = Pattern.compile("gw\\s=\\s[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}");
				Matcher matcher = pattern.matcher(line);
				if(matcher.find())
				{
					String[] array=line.substring(matcher.start(), matcher.end()).split("\\s=\\s");
					return array[1];
				}
        	}
        }
		return null;
	}
	
	private Integer getMask(String iface) throws IOException
	{
		String command = "/usr/bin/nmcli dev list iface "+iface+"";
		BufferedReader buf = SystemCall.execute(command);
		String line;
		while ((line = buf.readLine()) != null) 
        {
			if(line.matches(".*gw.*"))
        	{	
				Pattern pattern = Pattern.compile("ip\\s=\\s[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}/[0-9]{1,3}");
				Matcher matcher = pattern.matcher(line);
				if(matcher.find())
				{
					String[] array=line.substring(matcher.start(), matcher.end()).split("/");
					return Integer.parseInt(array[1]);
				}
        	}
        }
		return null;
	}
	
	public static ArrayList<String> getInterfaces() throws IOException
	{
		ArrayList<String> array = new ArrayList<String>();
		String command = "/sbin/ip route";
		BufferedReader buf = SystemCall.execute(command);
		String line;
		while ((line = buf.readLine()) != null) 
        {
        	if(line.matches(".*proto.*kernel.*"))
        	{
        		Pattern pattern = Pattern.compile("dev.*proto");
        		Matcher matcher = pattern.matcher(line);
        		
        		if(matcher.find())
        		{
        			String[] interfaces=line.substring(matcher.start(), matcher.end()).split("\\s");
        			array.add(interfaces[1]);
        		}
        	}
        }
		return array;
	}
	
	private String getPPPNetworkAddress(String line)
    {
    	Pattern pattern = Pattern.compile("^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}");
        Matcher matcher = pattern.matcher(line);
        if(matcher.find())
            return line.substring(matcher.start(), matcher.end());
        
        return null;
    }
	
	private String getPPPIPAddress(String line)
    {
    	Pattern pattern = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\s$");
        Matcher matcher = pattern.matcher(line);
        if(matcher.find())
            return line.substring(matcher.start(), matcher.end());
        
        return null;
    }
	
	private String getPPPInformation(String iface) throws IOException
	{
		String command = "/sbin/ip route";
		BufferedReader buf = SystemCall.execute(command);
		String line;
		while ((line = buf.readLine()) != null) 
        {
			if(line.matches(".*dev\\sppp.*proto\\skernel.*"))
				return line;
        }
        return null;
	}
	
	public Isp getIspAtIndex(int index)
	{
		return arraylist.get(index);
	}
}

