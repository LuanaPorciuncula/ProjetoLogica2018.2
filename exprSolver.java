import java.io.*;
import java.util.*;

public class exprSolver {
	public static void main(String[] args) throws IOException {
		try {
			Scanner in = new Scanner(new FileReader("Entrada.in"));
			FileWriter out = new FileWriter("Saída.out");

			String linha = in.nextLine();

			int qtd_prob = Integer.parseInt(linha);

			while (qtd_prob > 0) {
				String expr;
				String strValues;
				String[] values;
				
				linha = in.nextLine();
				int i = 0;
				for (; linha.charAt(i) != '0' && linha.charAt(i) != '1' 
					&& i < linha.length(); i++) {
				}
				expr = linha.substring(0, i-1);
				strValues = linha.substring(i, linha.length());
				values = strValues.split(" ");
				
				if (linha.charAt(0) == '{') {
					
				} else {
				}

				qtd_prob--;
			}

			out.close();
			in.close();
		} catch (IOException e) {

		}
	}
}
