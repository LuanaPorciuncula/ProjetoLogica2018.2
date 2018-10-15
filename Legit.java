package projetoLogica;

import java.util.Scanner;

public class Legit {
	
	static boolean isLegit(String exp) {
		
		if(exp.length() == 1 && exp.charAt(0) >= 'A' && exp.charAt(0) <= 'Z')
			return true;
		
		int s = 0;
		int f = exp.length() - 1;
		
		if(exp.charAt(s) == '(' && exp.charAt(f) == ')' && exp.charAt(s + 1) == '~' && s + 2 < f)
			return isLegit(exp.substring(s + 2, f));
		
		else if(exp.charAt(s) == '(' && exp.charAt(f) == ')') {
			int operatorIndex = findOperator(exp);
			if(operatorIndex != -1)
				return isLegit(exp.substring(s + 1, operatorIndex - 1)) && 
						isLegit(exp.substring(operatorIndex + 2, f));
		}	
		
		return false;
		
	}
	
	static int findOperator(String exp) {
		
		int numParenthesis = 0;
		for(int i = 0; i < exp.length(); i++) {
			
			if(exp.charAt(i) == '(')
				numParenthesis++;
			else if(exp.charAt(i) == ')')
				numParenthesis--;
			
			if((exp.charAt(i) == '>' || exp.charAt(i) == '&' || exp.charAt(i) == 'v') &&
					numParenthesis == 1)
				return i;
		}
		
		return -1;		
	}

	public static void main(String[] args) {
		
		Scanner s = new Scanner(System.in);
		
		while(true) {
		String exp = s.nextLine();
		if(isLegit(exp))
			System.out.println("E legitima.");
		else
			System.out.println("Nao e legitima.");
		}
		//s.close();
		

	}

}
