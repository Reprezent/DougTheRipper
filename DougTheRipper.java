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
		logs = (cmdOpts.getLog()) ? new DougLog() : null;

		int Num_comps = cmdOpts.getNUM_COMPS();
		int Comp_num = cmdOpts.getCOMP_NUM();
		int words_per_comp = dict.size()/Num_comps;
//		System.out.println("Comp Num: " + Integer.toString(Comp_num));
//		System.out.println("Num Comps: " + Integer.toString(Num_comps));
//		System.out.println("Dict size: " + Integer.toString(dict.size()));
//		System.out.println("Start: " + Integer.toString(words_per_comp * Comp_num));
//		System.out.println("End:   " + Integer.toString(words_per_comp * (Comp_num + 1)));
		if(Comp_num + 1 == Num_comps)
		{
			words = new DougSmasher(dict, cmdOpts.getMAX_NUM_WORDS(), words_per_comp * Comp_num, dict.size());
		}
		else
		{
			words = (cmdOpts.getMULTIPROCC()) ? new DougSmasher(dict, cmdOpts.getMAX_NUM_WORDS(), words_per_comp * Comp_num, (words_per_comp * (Comp_num + 1))) 
											  : new DougSmasher(dict, cmdOpts.getMAX_NUM_WORDS());
		}
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
		for(int i = 0; i < NUM_THREADS; i++)
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

		for(int i = 0; i < NUM_THREADS; i++) {
            System.err.println("Hasher " + Integer.toString(i + 1) + " started");
            threadArrayList.get(i).start();
        }

        boolean allBlocked = true;
		boolean oneFound = false;
		System.err.println("Starting Thread monitoring.");
		while(true)
		{
		    allBlocked = true;
		    for(Thread i : threadArrayList)
            {
                if(i.getState() != Thread.State.WAITING)
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
	private final DougLog logs;
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
