package Doug;

// Richard Riedel, Nick Redheffer, Zach Houston
// Group: Doug
// Spring 2017 CS 434
// Programming Assignment 1b | Part 4
// This is the command line option parser for DougTheRipper


import java.util.Base64;
import java.lang.Integer;
import org.apache.commons.cli.*;
//import org.apache.commons.cli.Option;
//import org.apache.commons.cli.DefaultParser;
//import org.apache.commons.cli.CommandLine;
//import org.apache.commons.cli.CommandLineParser;
//import org.apache.commons.cli.HelpFormatter;
//import org.apache.commons.cli.ParseException;

public class DougCmdOpts
{
	public DougCmdOpts(String[] args)
    {
        Option HELP = Option.builder("h")
                .argName("help")
                .longOpt("help")
                .desc("Help diaglog flag.")
                .build();
        Option Group = Option.builder("g")
                .argName("group")
                .longOpt("group")
                .hasArg()
                .desc("Group Name to use.")
                .type(String.class)
                .build();

        Option challenge = Option.builder("c")
                .argName("challenge")
                .longOpt("challenge")
                .hasArg()
                .desc("Challenge string to use.")
                .type(String.class)
                .build();

        Option hash = Option.builder("x")
                .argName("hash")
                .longOpt("hash")
                .hasArg()
                .desc("Hash to compare against.")
                .type(String.class)
                .build();


        Option base64 = Option.builder("b")
                .argName("base64")
                .longOpt("base")
                .hasArg()
                .desc("Base64 String to compare")
                .type(String.class)
                .build();

        Option log = Option.builder("l")
                .argName("Logging")
                .longOpt("log")
                .desc("Enables logging.")
                .build();

        Option verb = Option.builder("v")
                .argName("Verbose Output")
                .longOpt("verbose")
                .desc("Enables verbose messgages.")
                .build();

        Option dictionary = Option.builder("d")
                .argName("dictonary")
                .longOpt("dictonary")
                .hasArg()
                .desc("Dictonary to use in cracking.")
                .type(String.class)
                .build();

        Option threads = Option.builder("t")
                .argName("threads")
                .longOpt("threads")
				.hasArg()
                .desc("Number of threads to use for cracking.")
                .type(Integer.class)
                .build();

        Option multiProcess = Option.builder("m")
                .argName("Multi-proccess")
                .longOpt("multiproc")
                .desc("Muti processing option to further paralellize"
                        + "over multiple computers, [computer#] [totalNumComputers]")
                .numberOfArgs(2)
                .type(Integer.class)
                .build();

        Option maxWords = Option.builder("w")
                .argName("Maximum words")
                .longOpt("maxwords")
                .desc("Maximum number of words to smash together for the passphrase option")
                .hasArg()
                .type(Integer.class)
                .build();

        Option maxIter = Option.builder("i")
                .argName("Maximum iterations")
                .longOpt("maxiter")
                .desc("Maximum number of iterations for the entropy option")
                .hasArg()
                .type(Integer.class)
                .build();

        Option mode = Option.builder("e")
                .argName("Cracking Mode")
                .longOpt("entropy")
                .desc("Mode to use for password cracking.\n  Off - Word Smashhing\n  On  - Emtropy Mode")
                .build();



        Options opts = new Options();
		opts.addOption(HELP);
        opts.addOption(log);
        opts.addOption(Group);
        opts.addOption(challenge);
        opts.addOption(hash);
        opts.addOption(base64);
        opts.addOption(verb);
        opts.addOption(dictionary);
        opts.addOption(threads);
        opts.addOption(multiProcess);
        opts.addOption(maxWords);
        opts.addOption(maxIter);
        opts.addOption(mode);

        CommandLineParser parser = new DefaultParser();
        CommandLine line = null;

        try
        {
            line = parser.parse(opts, args);
        }
        catch(ParseException e)
        {
            System.err.println("Parsing failed. Reason: " + e.getMessage());
        }

        if(line.hasOption("h"))
        {
            HelpFormatter help = new HelpFormatter();
            help.printHelp("DougTheRipper", opts, true);
			System.exit(0);
        }

        GROUP_NAME = line.getOptionValue("g", groupName);
        CHALLENGE = line.getOptionValue("c", challenge2);
        BASE64 = line.getOptionValue("b", text2);
        HASH = line.getOptionValue("x", new String(Base64.getDecoder().decode(BASE64)));
        Logs = line.hasOption("l");
        Verbose = line.hasOption("v");
        DICTONARY = line.getOptionValue("d", dict);
        NUM_THREADS = Integer.parseInt(line.getOptionValue("t", threadNum));
        MAX_NUM_WORDS = Integer.parseInt(line.getOptionValue("w", wordNums));
        MAX_NUM_ITER = Integer.parseInt(line.getOptionValue("i", iterNum));
        MULTIPROCC = line.hasOption("m");

        String[] temp = line.getOptionValues("m");
		if(temp != null)
		{
			COMP_NUM = Integer.parseInt(temp[0]);
			NUM_COMPS = Integer.parseInt(temp[1]);
		}
		else
		{
			COMP_NUM = -1;
			NUM_COMPS = -1;
		}

        CRACKING_MODE = line.hasOption("e");
    }

    public String getGROUP_NAME()
    {
        return GROUP_NAME;
    }

    public String getHASH()
    {
        return HASH;
    }

    public String getBASE64()
    {
        return BASE64;
    }

    public String getCHALLENGE()
    {
        return CHALLENGE;
    }

    public boolean getLog()
    {
        return Logs;
    }

    public boolean getVerbose()
    {
        return Verbose;
    }

    public boolean isCRACKING_MODE()
    {
        return CRACKING_MODE;
    }

    public int getMAX_NUM_WORDS()
    {
        return MAX_NUM_WORDS;
    }

    public int getMAX_NUM_ITER()
    {
        return MAX_NUM_ITER;
    }

    public int getNUM_THREADS()
    {
        return NUM_THREADS;
    }

    public String getDICTONARY()
    {
        return DICTONARY;
    }

    public int getNUM_COMPS()
    {
        return NUM_COMPS;
    }

    public int getCOMP_NUM()
    {
        return COMP_NUM;
    }

    public boolean getMULTIPROCC()
    {
        return MULTIPROCC;
    }

    private final String iterNum = "5";
    private final String wordNums = "5";
    private final String threadNum = "5";
    private final String text2 = "61KeWef3OaQFINrCHf8MUnU8VSvtdKgyMgO2yNNIr4w=";
    private final String text4 = "j5zHYIjKwGl6XHAN9Gecm2IGARoIuSSC+b3hXUwL0Oo="; // Hash captured from Wireshark
    private final String text5 = "EbP5fQE2l/k573CmC7C9lAJ+iNSGBvaeLtTVV4uZkbU=";

    private final String nonBase64_2 = new String(Base64.getDecoder().decode(text2));
    private final String nonBase64_4 = new String(Base64.getDecoder().decode(text4));
    private final String nonBase64_5 = new String(Base64.getDecoder().decode(text5));

    private final String challenge2 = "879880826"; // Challenge Nonce captured from wireshark
    private final String challenge4 = "-1120810204"; // Challenge Nonce captured from wireshark
    private final String challenge5 = "-1819859971";

    private final String dict = "cracklib-small";
    private final String groupName = "Doug"; // Group name created by us



    private final int NUM_COMPS;
    private final int COMP_NUM;
    private final boolean MULTIPROCC;
    private final boolean CRACKING_MODE;
    private final int MAX_NUM_WORDS;
    private final int MAX_NUM_ITER;
    private final int NUM_THREADS;
    private final String DICTONARY;
    private final String GROUP_NAME;
	private final String HASH;
	private final String BASE64;
	private final String CHALLENGE;
	private final boolean Logs;
	private final boolean Verbose;

}
