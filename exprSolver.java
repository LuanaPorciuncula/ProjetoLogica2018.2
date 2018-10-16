import java.io.*;
import java.util.*;

public class exprSolver {
	public static void main(String[] args) throws IOException {
		try {
			Scanner in = new Scanner(new FileReader("Entrada.in"));
			FileWriter out = new FileWriter("Saída.out");

			String line = in.nextLine();
			int num_problem, qtd_prob;

			num_problem = 1;
			qtd_prob = Integer.parseInt(line);

			while (num_problem <= qtd_prob) {
				out.write("Problema #" + num_problem + "\r");

				String expr, strValues;
				String[] values;
				char[] varList;
				int indexEnd = 0;
				
				line = in.nextLine();
				//for pra procurar onde acaba a palavra/conjunto e onde começa a valoração
				for (; indexEnd < line.length() && line.charAt(indexEnd) != '0' && line.charAt(indexEnd) != '1'; indexEnd++) {}

				expr = line.substring(0, indexEnd-1);
				strValues = line.substring(indexEnd, line.length());
				values = strValues.split(" ");
				/*CRIAR UMA NOVA FUNÇÃO PRA ORGANIZAR OS VALORES NUM ARRAY DE SIZE 26
					ONDE CADA POSIÇÃO CORRESPONDE A VARIÁVEL*/

				if (line.charAt(0) == '{') {

					varList = listVariables(expr, values.length);
					String validSat = setAnalysis(expr, varList, values);

					if (validSat.charAt(0) == '1'){
						if(validSat.charAt(1) == '1'){
							out.write("A valoracao-verdade satisfaz o conjunto.");
						} else {
							out.write("A valoracao-verdade nao satisfaz o conjunto.");
						}
					} else {
						out.write("Ha uma palavra nao legitima no conjunto.");
					}

				} else {

					if(isValid(expr)){
						varList = listVariables(expr, values.length);

						if (valSat(expr, varList, values)){
							out.write("A valoracao-verdade satisfaz a proposicao.");
						} else {
							out.write("A valoracao-verdade nao satisfaz a proposicao.");
						}
					} else {
						out.write("A palavra nao e legitima.​");						
					}
				}

				if(num_problem!=qtd_prob){
					out.write("\r\r");
				} else {
					out.write("\r");
				}
				num_problem++;
			}

			out.close();
			in.close();
		} catch (IOException e) {}
	}

	public static String setAnalysis(String expr, char[] varList, String[] values){
		String[] clauses;
		boolean valid = true, sat = true;
		String validSat =  "11";
		expr = expr.substring(1, expr.length()-1);
		clauses = expr.split(", ");

		for(int i = 0; i < clauses.length && valid; i++){
			if(isValid(clauses[i])){
				if (sat && !valSat(clauses[i], varList, values)){
					sat = false;
					validSat = "10";
				}
			} else {
				valid = false;
				validSat = "00";
			}
		}

		return validSat;
	}

	public static boolean isValid (String word){
		int wordLength = word.length();

		//Se a palavra word é atômica
		if (wordLength == 1 && word.charAt(0) >= 'A' &&  word.charAt(0) <= 'Z'){
			return true;
		}

		if(word.charAt(0)== '(' && word.charAt(wordLength-1) == ')'){
			//Se a palavra word é da forma (~w)
			if (wordLength >= 4 && word.charAt(1)== '~'){
				return isValid(word.substring(2, wordLength-1));
			}

			int indexOp = lastOp(word);
			//Se a palavra word é da forma (w1 □ w2), onde □ e {&, v, >}
			if (wordLength >= 7 && indexOp != -1){
				return isValid(word.substring(1, indexOp - 1)) 
					&& isValid(word.substring(indexOp + 2, wordLength-1));
			}
		}
		return false;
	}

	public static boolean valSat(String word, char[] varList, String[] values){
		int wordLength = word.length();
		
		//Se a palavra word é atômica
		if (wordLength == 1){
			for (int i = 0; i < varList.length; i++){
				if (varList[i] == word.charAt(0)){
					if (values[i].equals("1")){
						return true;
					} else {
						return false;
					}
				}
			}
		}
		
		//Se a palavra word é da forma (~w)
		if (word.charAt(1)== '~'){
			return !valSat(word.substring(2, wordLength-1), varList, values);
		}
		int indexOp = lastOp(word);
		// Se a palavra word é da forma (w1 & w2)
		if (word.charAt(indexOp) == '&'){
			return valSat(word.substring(1, indexOp - 1), varList, values) 
				&& valSat(word.substring(indexOp + 2, wordLength-1), varList, values);
		} //Se a palavra word é da forma (w1 v w2)
		else if (word.charAt(indexOp) == 'v') {
			return valSat(word.substring(1, indexOp - 1), varList, values) 
				|| valSat(word.substring(indexOp + 2, wordLength-1), varList, values);
		} // Se a palavra word é da forma (w1 > w2)
		else {
			if (!valSat(word.substring(1, indexOp - 1), varList, values)){
				return true;
			} else if (valSat(word.substring(indexOp + 2, wordLength-1), varList, values)){
				return true;
			} else {
				return false;
			}
		}
	
	}

	//Procura pelo index do ultimo operador aplicado
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

	/*Cria uma lista de variaveis, cada variavel vai estar associada ao valor
		na mesma posição do array values*/
	public static char[] listVariables(String expr, int arrSize){
		char[] varList = new char [arrSize];
		for (int i = 0; i < expr.length(); i++){
			if (expr.charAt(i) >= 'A' &&  expr.charAt(i) <= 'Z'){
				for (int j = 0; j < arrSize; j++){
					if (varList[j] == expr.charAt(i) || varList[j] == 0){
						varList[j] = expr.charAt(i);
						break;
					}
				}
			}
		}
		return varList;
	}
}
