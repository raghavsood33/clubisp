package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utility.SystemCall;

public class Test 
{
	public static void main(String args[]) throws IOException
	{
		String command = "/usr/bin/sudo /usr/sbin/nethogs wlan0";
		BufferedReader buf = SystemCall.execute(command);
		String line;
		while ((line = buf.readLine()) != null) 
        {
        	System.out.println(line);
        }
	}

}
