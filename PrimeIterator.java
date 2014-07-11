import java.util.ArrayList;
import java.util.Iterator;

public class PrimeIterator implements Iterator<Integer>
{
	private ArrayList<Integer> potentialPrimes;
	
	public PrimeIterator(int max)
	{
		potentialPrimes = new ArrayList<Integer>();
		
		int currentNum = 2;
			
		while(currentNum <= max)
		{
			potentialPrimes.add(currentNum++);
		}
	}

	@Override
	public boolean hasNext()
	{
		return potentialPrimes.size() > 0;
	}

	@Override
	public Integer next()
	{
		Integer prime = null;
		
		if(!potentialPrimes.isEmpty())
		{
			prime = potentialPrimes.get(0);
			int currentPrimeMultiple = prime;
			
			while(!potentialPrimes.isEmpty() && currentPrimeMultiple <= potentialPrimes.get(potentialPrimes.size() - 1))
			{
				potentialPrimes.remove((Integer)currentPrimeMultiple);
				currentPrimeMultiple += prime;
			}
		}
		
		return prime;
	}

	@Override
	public void remove(){}
}
