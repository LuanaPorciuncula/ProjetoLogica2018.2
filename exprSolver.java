import java.io.*;
import java.util.*;

public class exprSolver {
	public static void main(String[] args) throws IOException {
		try {
			Scanner in = new Scanner(new FileReader("Entrada.in"));
			FileWriter out = new FileWriter("Saída.out");

			String line = in.nextLine();
			int num_problem = 1;

			int qtd_prob = Integer.parseInt(line);

			while (num_problem <= qtd_prob) {
				String expr, strValues;
				int indexEnd = 0;
				
				line = in.nextLine();
				out.write("Problema #"+num_problem+"\n");
				System.out.print("Problema #"+num_problem+"\n");

				for (; indexEnd < line.length() && line.charAt(indexEnd) != '0' && line.charAt(indexEnd) != '1'; indexEnd++) {}

				expr = line.substring(0, indexEnd-1);
				strValues = line.substring(indexEnd, line.length());

				if (line.charAt(0) == '{') {
					String[] clauses;
					boolean valid = true, sat = true;

					expr = expr.substring(1, expr.length()-1);
					clauses = expr.split(",");
					for(int i = 0; i < clauses.length && valid && sat; i++){
						if(isValid(clauses[i])){
							
							if (!valSat(clauses[i])){
								sat = false;
							}
						} else {
							valid = false;
						}
					}
					if (valid){
						if(sat){
							out.write("A valoracao-verdade satisfaz o conjunto.\n\n");
							System.out.print("A valoracao-verdade satisfaz o conjunto.\n\n");
						} else {
							out.write("A valoracao-verdade nao satisfaz o conjunto.\n\n");
							System.out.print("A valoracao-verdade nao satisfaz o conjunto.\n\n");
						}
					} else {
						out.write("Ha uma palavra nao legitima no conjunto.\n\n");
						System.out.print("Ha uma palavra nao legitima no conjunto.\n\n");
					}
				} else {
					if(isValid(expr)){
						if (valSat(expr)){
							out.write("A valoracao-verdade satisfaz a proposicao.\n\n");
							System.out.print("A valoracao-verdade satisfaz a proposicao.\n\n");
						} else {
							out.write("A valoracao-verdade nao satisfaz a proposicao.\n\n");
							System.out.print("A valoracao-verdade nao satisfaz a proposicao.\n\n");
						}
					} else {
						out.write("A palavra nao e legitima.​\n\n");
						System.out.print("A palavra nao e legitima.​\n\n");
						
					}
				}

				num_problem++;
			}

			out.close();
			in.close();
		} catch (IOException e) {}
	}

	//public static String

	public static boolean isValid (String word){
		int wordLength = word.length();
		//Se a palavra é atômica
		if (wordLength == 1 && word.charAt(0) >= 'A' &&  word.charAt(0) <= 'Z'){
			return true;
		}

		//Se a palavra é da forma (~w)
		if (wordLength >= 4 && word.charAt(0)== '(' && word.charAt(1)== '~' && word.charAt(wordLength-1) == ')'){
			return isValid(word.substring(2, wordLength-1));
		}

		int indexOp = lastOp(word);
		//Se a palavra é da forma (w1 □ w2), onde □ e {&, v, >}
		if (wordLength >= 7 && word.charAt(0)== '(' && word.charAt(wordLength-1) == ')' && indexOp != -1){
			return isValid(word.substring(1, indexOp - 1)) && isValid(word.substring(indexOp + 2, wordLength-1));
		}

		return false;
	}

	public static int lastOp (String word){
		int openParent = 0;
		for (int i = 0; i < word.length(); i++){
			if (word.charAt(i)== '('){
				openParent++;
			} else if (word.charAt(i)== ')'){
				openParent--;
			} else if (openParent == 1 && (word.charAt(i)== 'v' || word.charAt(i)== '&' || word.charAt(i)== '>')){
				return i;
			}
		}
		return -1;
	}


	public static boolean valSat(String expr){
		String[] values;
		//values = strValues.split(" ");
		return false;
	}


}
