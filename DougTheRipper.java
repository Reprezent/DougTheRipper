// Richard Riedel, Nick Redheffer, Zach Houston
// Group: Doug
// Spring 2017 CS 434
// Programming Assignment 1b | Part 4
// This is the driver program that emulates JohnTheRipper in
// Java. This program uses the special format requested by 
// Professor Max Schuchard, "[GroupName]:[ChallengeNonce]:[Password]".
// Where the quotes are not included.


import java.util.Base64;
// import java.io.BufferedReader;
// import java.io.FileNotFoundException;
// import java.io.FileReader;
// import java.io.IOException;
// import java.security.MessageDigest;
// import java.security.NoSuchAlgorithmException;
// import java.util.Scanner;
// import java.util.Vector;
import java.lang.Thread;
import Doug.DougSmasher;
import Doug.DougHash;
import Doug.DougDict;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Option.Builder;

public class DougTheRipper
{
	public DougTheRipper()
	{
		words = new DougSmasher(dict, 5);
		this.wordSmasher();
	}

	public DougTheRipper(int MAX_NUM_WORDS, int numSplits, int splitNumber)
	{
		words = new DougSmasher(dict, MAX_NUM_WORDS);
//		(dict.size()/numSplits)*(splitNumber - 1);
	}

	public void wordSmasher()
	{
		Thread thing = new Thread(words);
		Thread thing2 = new Thread(new Runnable()
		{
			public void run()
			{
				hashing(nonBase64_5, challenge5);
			}
		});

		Thread thing3 = new Thread(new Runnable()
		{
			public void run()
			{
				hashing(nonBase64_5, challenge5);
			}
		});

		Thread thing4 = new Thread(new Runnable()
		{
			public void run()
			{
				hashing(nonBase64_5, challenge5);
			}
		});

		Thread thing5 = new Thread(new Runnable()
		{
			public void run()
			{
				hashing(nonBase64_5, challenge5);
			}
		});

		Thread thing6 = new Thread(new Runnable()
		{
			public void run()
			{
				hashing(nonBase64_5, challenge5);
			}
		});

//		Thread logging = new Thread(logs);
//		System.err.println("Logging started.");
//		logging.start();
		System.err.println("String Creation started");
		thing.start();
		System.err.println("Hasher1 started");
		thing2.start();
		System.err.println("Hasher2 started");
		thing3.start();
		System.err.println("Hasher3 started");
		thing4.start();
		System.err.println("Hasher4 started");
		thing5.start();
		System.err.println("Hasher5 started");
		thing6.start();

/*		try
		{
			thing.join();
		}
		catch(InterruptedException e)
		{
			System.err.println("String maker interrupted");
		}
*/	
		while(true)
		{
			if(thing2.getState() == Thread.State.BLOCKED 
			&& thing.getState() == Thread.State.TERMINATED
			&& thing3.getState() == Thread.State.BLOCKED
			&& thing4.getState() == Thread.State.BLOCKED
			&& thing5.getState() == Thread.State.BLOCKED
			&& thing6.getState() == Thread.State.BLOCKED)
			{
				System.out.println("We found nothing...");
				System.exit(1);
			}
			else if(thing2.getState() == Thread.State.TERMINATED
				 || thing3.getState() == Thread.State.TERMINATED
				 || thing4.getState() == Thread.State.TERMINATED
				 || thing5.getState() == Thread.State.TERMINATED
				 || thing6.getState() == Thread.State.TERMINATED)
			{
				System.exit(100);
			}
			else
			{
				try
				{
					Thread.sleep(1000);
				}
				catch(InterruptedException e)
				{
					return;
				}
			}
		}
	}

	public boolean hashing(String nonBase64, String chall)
	{
		DougHash hash = new DougHash(groupName, chall);
		String temp;
		while(true)
		{
			try
			{
				temp = words.get();
				
				//			logs.addTestCase(temp);
				if(nonBase64.compareTo(hash.digest(temp)) == 0)
				{
					System.out.println("Password Found: " + temp);
					break;
					//				logs.addFailureCase(temp, true);
				}
				else
				{
					//				logs.addFailureCase(temp, false);
				}
			}
			catch(InterruptedException e)
			{
				return false;
			}
		}
		return true;
	}



	public static void main(String[] args)
	{
		Option Group = Option.builder("g")
								.argName("group")
								.longOpt("group")
								.hasArg()
								.desc("Group Name to use.")
//								.type(String)
								.build();

		Option challenge = Option.builder("c")
									.argName("challenge")
									.longOpt("challenge")
									.hasArg()
									.desc("Challenge string to use.")
//									.type(String)
									.build();

		Option hash = Option.builder("h")
								.argName("hash")
								.longOpt("hash")
								.hasArg()
								.desc("Hash to compare against.")
//								.type(String)
								.build();

	//	Option hashFile = Option.Builder

		Option base64 = Option.builder("b")
								.argName("base64")
								.longOpt("base")
								.hasArg()
								.desc("Base64 String to compare")
//								.type(String)
								.build();

		Option log = Option.builder("l")
								.argName("Logging")
								.longOpt("log")
								.desc("Enables logging.")
								.build();

		Option logFull = Option.builder("LF")
									.argName("Full logging")
									.longOpt("logfull")
									.desc("Enables verbose logging.")
									.build();

		Option dictionary = Option.builder("d")
									.argName("dictonary")
									.longOpt("dictonary")
									.desc("Dictonary to use in cracking.")
									.build();




		Options opts = new Options();
		opts.addOption(log);
		opts.addOption(Group);
		opts.addOption(challenge);
		


//		opts.addOption("l", "log", "Enables Logging");
		//opts.addOption("d", "dictionary", true, "Dictionary File to be used instead of cracklib-small");
		//opts.addOption("h", "help", false, "Prints this help message");
		//opts.addOption("t", "threads", true, "Number of Threads to be used in total.");








//		new DougTheRipper();
	}
	

	private final DougDict dict = new DougDict(null);
	//	private final DougLog logs = new DougLog();
	private final DougSmasher words;
	private DougHash hash;

    private final String text2 = "61KeWef3OaQFINrCHf8MUnU8VSvtdKgyMgO2yNNIr4w=";
	private final String text4 = "j5zHYIjKwGl6XHAN9Gecm2IGARoIuSSC+b3hXUwL0Oo="; // Hash captured from Wireshark
	private final String text5 = "EbP5fQE2l/k573CmC7C9lAJ+iNSGBvaeLtTVV4uZkbU=";

	private final String nonBase64_2 = new String(Base64.getDecoder().decode(text2));
	private final String nonBase64_4 = new String(Base64.getDecoder().decode(text4));
	private final String nonBase64_5 = new String(Base64.getDecoder().decode(text5));

    private final String challenge2 = "879880826"; // Challenge Nonce captured from wireshark
	private final String challenge4 = "-1120810204"; // Challenge Nonce captured from wireshark
	private final String challenge5 = "-1819859971";

	private final String groupName = "Doug"; // Group name created by us
}
