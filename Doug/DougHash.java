package Doug;

// Richard Riedel, Nick Redheffer, Zach Houston
// Group: Doug
// Spring 2017 CS 434
// Programming Assignment 1 | Part 4
// This class handles all hashing.

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DougHash
{
	public DougHash(String group, String challenge)
	{
		this.challenge = challenge;
		this.GroupName = group;
        
		try
        {
            Hasher = MessageDigest.getInstance("SHA-256");
        }
        catch(NoSuchAlgorithmException e)
        {
        }
	}

	public String digest(String password)
	{
		String temp = GroupName + ":" + challenge + ":" + password;
		return new String(Hasher.digest(temp.getBytes()));
	}

	private final String challenge;
	private final String GroupName;
	private MessageDigest Hasher;
}
