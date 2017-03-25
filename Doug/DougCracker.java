package Doug;

// Richard Riedel, Nick Redheffer, Zach Houston
// Group: Doug
// Spring 2017 CS 434
// Programming Assignment 1 | Part 4
// This class handles all logging related functions.


public class DougCrack
{
	public DougCrack(String start, int MAX_LEN)
	{
		initalString = start;
		this.MAX_LEN = MAX_LEN;
	}


	public static String upperCase(String password, int numUpper);
	public static String addNumsEnd(String password, int numNums);
	public static String addSpecialsCharsEnd(String password, int numSpecChars);
	public static String leetSpeak(String password, int numLeet);
	public static String addNumsStart(String password, int numNums);
	public static String addSpecialCharsStart(String password, int numSpecChars);

	private final String initalString;
	private int beginNums    = 0,
				endNums      = 0,
				beginSpecial = 0,
				endSpecial   = 0,
				leetChars    = 0,
				upperChars   = 0;
	private final int MAX_LEN;
}
