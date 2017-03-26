package Doug;

// Richard Riedel, Nick Redheffer, Zach Houston
// Group: Doug
// Spring 2017 CS 434
// Programming Assignment 1 | Part 4
// This class handles all multi-word dictionary functions.

import Doug.DougDict;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.lang.InterruptedException;
import java.lang.NullPointerException;
import java.lang.Runnable;
import java.lang.Integer;
import java.lang.Long;

public final class DougSmasher implements Runnable
{
	public DougSmasher(DougDict dictionary, int MAX_NUM_WORDS)
	{
		this.MAX_NUM_WORDS = MAX_NUM_WORDS;
		if(dict == null)
		{
			dict = dictionary;
		}

		this.end = dict.size();
	}
	public DougSmasher(DougDict dictionary, int MAX_NUM_WORDS, int start, int end)
	{
		this.MAX_NUM_WORDS = MAX_NUM_WORDS;
		this.start = start;
		this.end = end;
		if(dict == null)
		{
			dict = dictionary;
		}
	}

	public void smashWords(int numWords)
	{
		int[] wordLocations = new int[numWords];
		Arrays.fill(wordLocations, 0);
		smashWordsRecursion(wordLocations, 0);
	}

	public void smashAllWords()
	{
		for(int wordSize = 1; wordSize <= MAX_NUM_WORDS; wordSize++)
		{
			long startTime = System.nanoTime();
			int[] wordLocations = new int[wordSize];
			Arrays.fill(wordLocations, 0);
			wordLocations[0] = start;
			smashWordsRecursion(wordLocations, 0);
			System.out.println("Finished words of size " + Integer.toString(wordSize));
			System.out.println(Long.toString((System.nanoTime()-startTime)/1000/1000));
		}
	}


	private void smashWordsRecursion(int[] location, int locationIndex)
	{
		if(locationIndex == location.length - 1)
		{
			StringBuilder smasher = new StringBuilder();
			final int RealEnd = (location.length == 1) ? end : dict.size();
			for(; location[locationIndex] < RealEnd; location[locationIndex] += 1)
			{
				smasher.setLength(0);
				for(int j : location)
				{
					smasher.append(dict.get(j));
				}
				
				try
				{
					smashedWords.put(smasher.toString());
				}
				catch(InterruptedException e)
				{
					System.err.println("Thread interrupted");
					return;
				}
				catch(NullPointerException e)
				{
					continue;
				}
			}
		}
		else if(locationIndex == 0)
		{

			for(; location[locationIndex] < end; location[locationIndex]++)
			{
				smashWordsRecursion(location, locationIndex+1);
				location[locationIndex + 1] = 0;
			}
		}
		else
		{
			for(; location[locationIndex] < dict.size(); location[locationIndex]++)
			{
				smashWordsRecursion(location, locationIndex+1);
				location[locationIndex + 1] = 0;
			}
		}
	}

	
	public void run()
	{
		smashAllWords();
	}

	public String get() throws InterruptedException
	{
		return smashedWords.take();
	}

	private int start = 0;
	private int end = 0;
	private static DougDict dict = null;
	private static LinkedBlockingQueue<String> smashedWords = new LinkedBlockingQueue<String>(1000000);
//	private ArrayList<Iterator<String>> tests;
	private final int MAX_NUM_WORDS;
}

