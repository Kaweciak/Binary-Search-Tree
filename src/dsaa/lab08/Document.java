package dsaa.lab08;

import java.util.Scanner;

public class Document implements IWithName{
	public String name;
	public BST<Link> link;
	public Document(String name) {
		this.name=name.toLowerCase();
		link=new BST<Link>();
	}



	public Document(String name, Scanner scan) {
		this.name=name.toLowerCase();
		link=new BST<Link>();
		load(scan);
	}
	public void load(Scanner scan) {
		String line;
		String[] words;
		Link res;
		do
		{
			line = scan.nextLine();
			words = line.split(" ");
			for(String word : words)
			{
				if(word.length() >= 6 && word.substring(0, 5).equalsIgnoreCase("link="))
				{
					String link1 = word.substring(5);
					res = createLink(link1);
					if(res != null)
					{
						link.add(res);
					}
				}
			}
		}
		while(!line.equals("eod"));
	}

	public static boolean isCorrectId(String id) {
		char[] letters = id.toCharArray();
		if(!((letters[0] >= 'a' && letters[0] <= 'z') || (letters[0] >= 'A' && letters[0] <= 'Z'))) return false;
		for(char letter : letters)
		{
			if(!((letter >= 'a' && letter <= 'z') || (letter >= 'A' && letter <= 'Z'))) return false;
		}
		return true;
	}

	public static boolean corretChar(char a)
	{
		return (a >= 'a' && a <= 'z') || (a >= 'A' && a <= 'Z') || (a >= '0' && a <= '9') || a == '_';
	}

	// accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
	static Link createLink(String link) {
		char[] letters = link.toCharArray();
		int weight = 0, endIndex = 0;
		Link res;
		if(!((letters[0] >= 'a' && letters[0] <= 'z') || (letters[0] >= 'A' && letters[0] <= 'Z'))) return null;
		for(int i = 0; i < letters.length; i++)
		{
			if(letters[i] == '(' && letters[letters.length-1] == ')' && letters.length-i > 2)
			{
				for(int j = i+1; j < letters.length-1; j++)
				{
					if(letters[j] < '0' || letters[j] > '9') return null;
				}
				weight = Integer.parseInt(link.substring(i+1, letters.length-1));
				endIndex = i;
				break;
			}
			if(!corretChar(letters[i])) return null;
		}
		if(weight == 0) res = new Link(link);
		else res = new Link(link.substring(0, endIndex), weight);
		return res;
	}

	@Override
	public String toString() {
		String retStr="Document: "+name+"\n";
		retStr+=link.toStringInOrder();		
		return retStr;
	}

	public String toStringPreOrder() {
		String retStr="Document: "+name+"\n";
		retStr+=link.toStringPreOrder();
		return retStr;
	}

	public String toStringPostOrder() {
		String retStr="Document: "+name+"\n";
		retStr+=link.toStringPostOrder();
		return retStr;
	}

	short[] hashingNumbers = {7, 11, 13, 17, 19};
	private static final int MODVALUE = 100000000;
	@Override
	public int hashCode()
	{
		short i = 0;
		char[] charArray = getName().toCharArray();
		int res = charArray[0];
		for(int j = 1; j < charArray.length; j++)
		{
			res *= hashingNumbers[(i++)%5];
			res += charArray[j];
			res %= MODVALUE;
		}
		return res;
	}

	@Override
	public String getName() {
		return name;
	}
}
