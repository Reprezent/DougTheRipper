import Doug.DougDict;
import Doug.DougSmasher;
import java.util.concurrent.ThreadPoolExecutor;
import java.lang.InterruptedException;
import java.lang.Thread;
import java.lang.Integer;

public class DougSmashTest
{

	public DougSmashTest(int bleh)
	{
		DougDict a = new DougDict("test");
		DougSmasher b = new DougSmasher(a, bleh);
		//ThreadPoolExecutor c = new ThreadPoolExecutor();
		Thread thing = new Thread(new Runnable()
		{
			public void run()
			{
				String temp = new String();
				while(true)
				{
					try
					{
						temp = b.get();
					}
					catch(InterruptedException e)
					{
						System.exit(1);
					}
					System.out.println(temp);
				}
			}
		});

		System.err.println("Running Thread");
		thing.start();
		System.err.println("Making all the strings");
		b.smashAllWords();
	}



	public static void main(String[] args)
	{
		new DougSmashTest(Integer.parseInt(args[0]));
	}
}
