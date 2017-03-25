package Doug;

// Richard Riedel, Nick Redheffer, Zach Houston
// Group: Doug
// Spring 2017 CS 434
// Programming Assignment 1 | Part 4
// This class handles all logging related functions.

import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.lang.Integer;
import java.lang.NullPointerException;
import java.lang.Runnable;

public final class DougLog implements Runnable
{
	private static final String Logging = "DougTheRipper-Log";
	private boolean kill;// = false;
	// Initalization Block
	{
		kill = false;
	}

	public DougLog()
	{
		this.openFile();
		outputQueue = new LinkedBlockingQueue<String>();
		ProccQueue = new LinkedBlockingQueue<String>();
	}

	public void addTestCase(String testCase)
	{
		try
		{
			outputQueue.put("[TESTING] " + testCase);
		}
		catch(NullPointerException e)
		{
			System.err.println(e);
		}
		catch(InterruptedException e)
		{
			return;
		}
	}
		
	public void addFailureCase(String testCase, boolean passFail)
	{
		try
		{
			outputQueue.put( (passFail) ? "[PASSED] " : "[FAILED] " + testCase);
		}
		catch(NullPointerException e)
		{
			System.err.println(e);
		}
		catch(InterruptedException e)
		{
			return;
		}
	}

	public void addProcessingLog(String proc)
	{
		try
		{
			ProccQueue.put(proc);
		}
		catch(NullPointerException e)
		{
			System.err.println(e);
		}
		catch(InterruptedException e)
		{
			return;
		}
	}

	public void kill() { kill = true; }
	
	private void openFile()
	{	
		File tempProcc   = null,
			 tempTests   = null,
			 tempAllLogs = null;
		try
		{
			tempProcc = new File(Logging + "_Processing_1");
			tempTests = new File(Logging + "_Tests_1");
			tempAllLogs = new File(Logging + "_1");
			for(int i = 2; tempProcc.exists() || tempTests.exists() || tempAllLogs.exists(); i++)
			{
				tempProcc = new File(Logging + "_Processing_" + Integer.toString(i));
				tempTests = new File(Logging + "_Tests_" + Integer.toString(i));
				tempAllLogs = new File(Logging + "_" + Integer.toString(i));
			}

			tempProcc.createNewFile();
			tempTests.createNewFile();
			tempAllLogs.createNewFile();
		}
		catch(NullPointerException e)
		{
			System.err.println(e);
			System.err.println("IO failed, logging stopped");
		}
		catch(IOException e)
		{
			System.err.println(e);
			System.err.println("IO failed, logging stopped");
		}

		try
		{
			if(tempProcc == null || tempTests == null || tempAllLogs == null)
			{
				throw new NullPointerException();
			}
			Procc = new PrintWriter(new BufferedWriter(new FileWriter(tempProcc)));
			Tests = new PrintWriter(new BufferedWriter(new FileWriter(tempTests)));
			AllLogs = new PrintWriter(new BufferedWriter(new FileWriter(tempAllLogs)));
		}
		catch(SecurityException e)
		{
			System.err.println(e);
			System.err.println("Welp, you can't log anything with without permissions can you?");
			System.err.println("IO failed, logging stopped");
		}
		catch(NullPointerException e)
		{
			System.err.println(e);
			System.err.println("IO failed, logging stopped");
		}
		catch(IOException e)
		{
			System.err.println(e);
			System.err.println("IO failed, logging stopped");
		}
	}

	private void WriteLogs()
	{
		String temp;
		while(true)
		{
			try
			{
				temp = outputQueue.take();
			}
			catch(InterruptedException e)
			{
				//	Thread.currentThread().interrupt();
				System.err.println("Finished up logging.");
				flushBuffers();
				return;
			}

			if(temp.startsWith("[TESTING] "))
			{
				Tests.println(temp);
			}

			AllLogs.println(temp);
		}
	}

	private void WriteProcLogs()
	{
		String temp;
		try
		{
			temp = ProccQueue.take();
		}
		catch(InterruptedException e)
		{
			return;
		}

		Procc.println(temp);
	}	

	private void flushBuffers()
	{
		Procc.flush();
		Tests.flush();
		AllLogs.flush();
	}

	public void run()
	{
		WriteLogs();
	}


	private static LinkedBlockingQueue<String> outputQueue;
	private static LinkedBlockingQueue<String> ProccQueue;
	private PrintWriter Procc, Tests, AllLogs;
}
