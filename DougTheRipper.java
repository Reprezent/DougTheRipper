// Richard Riedel, Nick Redheffer, Zach Houston
// Group: Doug
// Spring 2017 CS 434
// Programming Assignment 1b | Part 4
// This is the driver program that emulates JohnTheRipper in
// Java. This program uses the special format requested by 
// Professor Max Schuchard, "[GroupName]:[ChallengeNonce]:[Password]".
// Where the quotes are not included.


import java.util.Base64;
import java.lang.Integer;
import java.lang.Thread;
import java.util.ArrayList;
import Doug.DougSmasher;
import Doug.DougLog;
import Doug.DougHash;
import Doug.DougDict;
import Doug.DougCmdOpts;

public class DougTheRipper
{
	public DougTheRipper(String[] args)
	{
		DougCmdOpts cmdOpts = new DougCmdOpts(args);
		groupName = cmdOpts.getGROUP_NAME();
		challenge = cmdOpts.getCHALLENGE();
		hashString = cmdOpts.getHASH();
        dict = new DougDict(cmdOpts.getDICTONARY());
        NUM_THREADS = cmdOpts.getNUM_THREADS();



		words = new DougSmasher(dict, cmdOpts.getMAX_NUM_WORDS());
		this.wordSmasher();
	}

//	public DougTheRipper(int MAX_NUM_WORDS, int numSplits, int splitNumber)
//	{
//		words = new DougSmasher(dict, MAX_NUM_WORDS);
//		(dict.size()/numSplits)*(splitNumber - 1);
//    }

	public void wordSmasher()
	{
		Thread thing = new Thread(words);

		ArrayList<Thread> threadArrayList = new ArrayList<Thread>();
		for(int i = 1; i < NUM_THREADS; i++)
        {
            threadArrayList.add(new Thread(new Runnable()
		    {
			    public void run()
			    {
				    hashing(hashString, challenge);
			    }
		    }));
        }

//		Thread logging = new Thread(logs);
//		System.err.println("Logging started.");
//		logging.start();
		System.err.println("String Creation started");
		thing.start();

		for(int i = 1; i < NUM_THREADS; i++) {
            System.err.println("Hasher " + Integer.toString(i) + " started");
            threadArrayList.get(i).start();
        }

        boolean allBlocked = true;
		boolean oneFound = false;
		while(true)
		{
		    allBlocked = true;
		    for(Thread i : threadArrayList)
            {
                if(i.getState() != Thread.State.BLOCKED)
                {
                    allBlocked = false;
                    break;
                }
            }

            oneFound = false;
            for(Thread i : threadArrayList)
            {
                if(i.getState() == Thread.State.TERMINATED)
                {
                    oneFound = true;
                    break;
                }
            }

            if(allBlocked
			&& thing.getState() == Thread.State.TERMINATED)
			{
				System.out.println("We found nothing...");
				System.exit(1);
			}
			else if(oneFound)
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
		new DougTheRipper(args);
	}
	

	private final int NUM_THREADS;
	private final DougDict dict;
	private final DougLog logs = new DougLog();
	private final DougSmasher words;
	private DougHash hash;

	//private final String text;
	private final String hashString;
	private final String challenge;
//    private final String text2 = "61KeWef3OaQFINrCHf8MUnU8VSvtdKgyMgO2yNNIr4w=";
//	private final String text4 = "j5zHYIjKwGl6XHAN9Gecm2IGARoIuSSC+b3hXUwL0Oo="; // Hash captured from Wireshark
//	private final String text5 = "EbP5fQE2l/k573CmC7C9lAJ+iNSGBvaeLtTVV4uZkbU=";
//
//	private final String nonBase64_2 = new String(Base64.getDecoder().decode(text2));
//	private final String nonBase64_4 = new String(Base64.getDecoder().decode(text4));
//	private final String nonBase64_5 = new String(Base64.getDecoder().decode(text5));
//
//    private final String challenge2 = "879880826"; // Challenge Nonce captured from wireshark
//	private final String challenge4 = "-1120810204"; // Challenge Nonce captured from wireshark
//	private final String challenge5 = "-1819859971";

	private final String groupName; // Group name created by us
}
