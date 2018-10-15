import java.io.*;
import java.util.*;

public class exprSolver {
	public static void main(String[] args) throws IOException {
		try {
			Scanner in = new Scanner(new FileReader("Entrada.in"));
			FileWriter out = new FileWriter("Saída.out");

			String linha = in.nextLine();
			int num_problem = 1;

			int qtd_prob = Integer.parseInt(linha);

			while (num_problem <= 3) {
				String expr, strValues;
				String[] values;
				int indexEnd = 0;
				
				linha = in.nextLine();
				out.write("Problema #"+num_problem+"\n");
				System.out.print("Problema #"+num_problem+"\n");

				for (; indexEnd < linha.length() && linha.charAt(indexEnd) != '0' && linha.charAt(indexEnd) != '1'; indexEnd++) {}

				expr = linha.substring(0, indexEnd-1);
				strValues = linha.substring(indexEnd, linha.length());
				values = strValues.split(" ");
				//System.out.println(expr);

				if (linha.charAt(0) == '{') {
					String[] clauses;
					boolean valid = true, sat = true;

					expr = expr.substring(1, expr.length()-1);
					clauses = expr.split(",");
					for(int i = 0; i < clauses.length && valid && sat; i++){
						if(isValid()){
							if (!valSat()){
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
					if(isValid()){
						if (valSat()){
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

	public static boolean isValid (String expr){
		return false;
	}

	public static boolean valSat(String expr){
		return false;
	}


}
