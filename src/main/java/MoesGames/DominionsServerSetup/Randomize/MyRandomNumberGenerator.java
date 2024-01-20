package MoesGames.DominionsServerSetup.Randomize;
import java.util.ArrayList;
import java.util.Collections;

public class MyRandomNumberGenerator {
	
	
	public static ArrayList<Integer> getRandomNumbersWithOffset(int numberOfRandomNumbers,int setSize) {
		return getRandomNumbersWithOffset(numberOfRandomNumbers,setSize,0);
	}
	
	public static ArrayList<Integer> getRandomNumbersWithOffset(int numberOfRandomNumbers,int setSize,int offset) {
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		ArrayList<Integer> possibleNumbers = new ArrayList<Integer>();
		for (int i = 0; i < setSize; i++) {
			possibleNumbers.add(offset + i);
		}
		for (int i = 0; i < numberOfRandomNumbers; i++) {
			int randomIndex = (int) (possibleNumbers.size()*Math.random());
			numbers.add(possibleNumbers.remove(randomIndex));
		}
		Collections.sort(numbers);
		return numbers;
	}
	
	public static void main(String[] args) {
		ArrayList<Integer> numbers = getRandomNumbersWithOffset(10,35,43);
		for (int i = 0; i < args.length; i++) {
			System.out.println(numbers.get(i));
		}
	}
}
