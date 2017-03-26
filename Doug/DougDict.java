package Doug;

// Richard Riedel, Nick Redheffer, Zach Houston
// Group: Doug
// Spring 2017 CS 434
// Programming Assignment 1 | Part 4
// This program performs an offline dictonary attack using
// the cracklib-small dictionary.

import java.util.Iterator;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileNotFoundException;


public class DougDict implements Iterable<String>
{
	public DougDict(String filename)
	{
		this.OpenFile((filename == null) ? "cracklib-small" : filename);
		this.ReadContents();
	}
	
	// Function Name: OpenFile
	// Description: This function opens the file specified by filename, and prompts
	//				the user for a filename until a correct one is specified.
	// Inputs: String filename - filename string
	//		   System.in - User input for a filename.
	// Outputs: System.err - error message for FileNotFoundException.
	// Returns: None
    private void OpenFile(String filename)
    {
        for(;;)
        {
            try
            {
                dictionary = new BufferedReader(new FileReader(filename));
                break;
            }
            catch(FileNotFoundException e)
            {
                System.err.println(e);
                Scanner temp = new Scanner(System.in);
                filename = temp.next();
				temp.close();
            }
        }
    }

	// Function Name: ReadContents
	// Description: This function reads all the strings from the cracklib-small
	//				dictionary. All the strings are put into the array tests
	// Inputs: dictionary - Adds all the strings from the file to this array.
	// Outputs: System.err - failure on read from cracklib-small dictionary.
	// Returns: None
    private void ReadContents()
    {
        String temp;
        while(true)
        {
            try
            {
                temp = dictionary.readLine();
                if(temp == null)
                {
                    break;
                }
                temp = temp.trim();
                tests.add(temp);
            }
            catch(IOException e)
            {
                System.err.println(e);
            }
        }
    }

	public Iterator<String> iterator()
	{
		return tests.iterator();
	}

	String get(int i)
	{
		return tests.get(i);
	}

	public int size()
	{
		return tests.size();
	}

	public static void main(String[] args)
	{
		DougDict a = new DougDict(null);

		for(String i : a)
			System.out.println(i);
	}


    private BufferedReader dictionary; // Input reader for dictionary file
    private Vector<String> tests = new Vector<String>(); // Buffer to hold all the tests
}    
