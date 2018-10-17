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

			while (num_problem <= qtd_prob && in.hasNextLine()) {
				out.write("Problema #" + num_problem + System.lineSeparator());

				String expr, strValues;
				String[] values;
				boolean[] alfValues;
				char[] varList;
				int indexEnd = 0;
				
				line = in.nextLine();
				//um for pra procurar onde acaba a palavra/conjunto e onde começa a valoração
				for (; indexEnd < line.length() && line.charAt(indexEnd) != '0' && line.charAt(indexEnd) != '1'; indexEnd++) {}

				expr = line.substring(0, indexEnd-1);
				strValues = line.substring(indexEnd, line.length());
				values = strValues.split(" ");

				if (line.charAt(0) == '{') {

					varList = listVariables(expr, values.length);
					alfValues = fixValues(varList, values);
					String validSat = setAnalysis(expr, alfValues);

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

					if(legitimacy(expr)){
						varList = listVariables(expr, values.length);
						alfValues = fixValues(varList, values);

						if (valuation(expr, alfValues)){
							out.write("A valoracao-verdade satisfaz a proposicao.");
						} else {
							out.write("A valoracao-verdade nao satisfaz a proposicao.");
						}
					} else {
						out.write("A palavra nao e legitima.");						
					}
				}

				if(num_problem!=qtd_prob){
					out.write(System.lineSeparator() + System.lineSeparator());
				} else {
					out.write(System.lineSeparator());
				}
				num_problem++;
			}

			out.close();
			in.close();
		} catch (IOException e) {}
	}
	//Função que chama a checagem de legitimidade e valoração de cada expressão do conjunto
	public static String setAnalysis(String set, boolean[] values){
		String[] clauses;
		boolean valid = true, sat = true;
		String validSat =  "11";
		//primeiro digito indica legitimidade e o segundo se a valoração satisfaz o conjunto
		set = set.substring(1, set.length()-1);
		clauses = set.split(", ");

		for(int i = 0; i < clauses.length && valid; i++){
			if(legitimacy(clauses[i])){
				if (sat && !valuation(clauses[i], values)){
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

	//Função que checa a legitimidade de uma expressão de forma recursiva
	public static boolean legitimacy (String word){
		int wordLength = word.length();

		//Se a palavra word é atômica
		if (wordLength == 1 && word.charAt(0) >= 'A' &&  word.charAt(0) <= 'Z'){
			return true;
		}

		if(wordLength >= 4 && word.charAt(0)== '(' && word.charAt(wordLength-1) == ')'){
			//Se a palavra word é da forma (~w)
			if (word.charAt(1)== '~'){
				return legitimacy(word.substring(2, wordLength-1));
			}

			int indexOp = lastOp(word);
			//Se a palavra word é da forma (w1 □ w2), onde □ e {&, v, >}
			if (wordLength >= 7 && indexOp != -1 
					&& word.charAt(indexOp - 1) == ' ' && word.charAt(indexOp + 1) == ' '){
				return legitimacy(word.substring(1, indexOp - 1)) 
					&& legitimacy(word.substring(indexOp + 2, wordLength-1));
			}
		}
		return false;
	}
	//Função que verifica se a valoração satisfaz a expressão
	public static boolean valuation(String word,  boolean[] values){
		int wordLength = word.length();
		
		//Se a palavra word é atômica
		if (wordLength == 1){
			return values[word.charAt(0) - 65];
		}
		
		//Se a palavra word é da forma (~w)
		if (word.charAt(1)== '~'){
			return !valuation(word.substring(2, wordLength-1), values);
		}
		int indexOp = lastOp(word);
		// Se a palavra word é da forma (w1 & w2)
		if (word.charAt(indexOp) == '&'){
			return valuation(word.substring(1, indexOp - 1), values) 
				&& valuation(word.substring(indexOp + 2, wordLength-1), values);
		} //Se a palavra word é da forma (w1 v w2)
		else if (word.charAt(indexOp) == 'v') {
			return valuation(word.substring(1, indexOp - 1), values) 
				|| valuation(word.substring(indexOp + 2, wordLength-1), values);
		} // Se a palavra word é da forma (w1 > w2)
		else {
			if (!valuation(word.substring(1, indexOp - 1), values)){
				return true;
			} else if (valuation(word.substring(indexOp + 2, wordLength-1), values)){
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

	/*Cria um array onde cada posição está associada a uma letra do alfabeto, 
		tornando direta a verificação do valor de uma variável*/
	public static boolean[] fixValues(char[] varList, String[] values){
		boolean[] newValues = new boolean [26];
		for (int i = 0; i < varList.length; i++){
			if(values[i].equals("1")){
				newValues[varList[i]-65] = true;
			}
		}
		return newValues;
	}
}
