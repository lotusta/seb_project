package lt.testpro.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Utility {

	static final int mod97 = 97;
	
	public boolean readFromFile(String path) {
		File file = new File(path); 
        File fileOut = new File(path.substring(0, path.indexOf("."))+".out");
        Utility utils = new Utility();
		
		BufferedReader br;
		String tIBAN;
		if (!file.exists())
			return false;
		try {
	        FileWriter fileWriter = new FileWriter(fileOut);
	        BufferedWriter writer = new BufferedWriter(fileWriter);
			br = new BufferedReader(new FileReader(file));
			while ((tIBAN = br.readLine()) != null) {
				tIBAN = tIBAN.replaceAll("\\s+","");
				writer.write(tIBAN + "; " + utils.isIBANCorrect(tIBAN));
				writer.newLine();
			}		    
			writer.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} 
		
	}
	
	public void writeToFile(String IBAN, boolean isCorrect) {
		
	}
	
	public boolean isIBANCorrect(String IBAN) {
		String countryCode = IBAN.substring(0,2);
		String IBANCode = IBAN.substring(4, IBAN.length()) + IBAN.substring(0,4);
		if (!checkLength(IBAN, countryCode))
			return false;	
		BigInteger bigInt = new BigInteger(convertIBAN(IBANCode));	
		return bigInt.mod(BigInteger.valueOf(mod97)).intValue() == 1;
	}
	
	public boolean checkLength(String IBAN, String countryCode) {
		int length = IBAN.length();
		int countryMaxLength = 0;
		Map<String, Integer> countryDefinition = new HashMap<>();
		countryDefinition = getLengthByCountry();
		try {
			countryMaxLength = countryDefinition.get(countryCode);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return length == countryMaxLength;
		
	}
	
	public String convertIBAN(String code) {
		int i = 0;
		String intCode = "";
		while(i < code.length()) {
			if(Character.isLetter(code.charAt(i))) {
				intCode = Integer.toString(Character.getNumericValue(code.charAt(i)));	
				code = code.replace(code.substring(i,i+1), intCode);
			}
			i++;
		}
		return code;
	}
	
	public Map<String, Integer>  getLengthByCountry() {
	    final String countryString = ""
	            + "AL28 AD24 AT20 AZ28 BE16 BH22 BA20 BR29 BG22 "
	            + "HR21 CY28 CZ24 DK18 DO28 EE20 FO18 FI18 FR27 GE22 DE22 GI23 "
	            + "GL18 GT28 HU28 IS26 IE22 IL23 IT27 KZ20 KW30 LV21 LB28 LI21 "
	            + "LT20 LU20 MK19 MT31 MR27 MU30 MC27 MD24 ME22 NL18 NO15 PK24 "
	            + "PS29 PL28 PT25 RO24 SM27 SA24 RS22 SK24 SI19 ES24 SE24 CH21 "
	            + "TN24 TR26 AE23 GB22 VG24 GR27 CR21";
	    final Map<String, Integer> countryDefinition = new HashMap<>(); 
        for (String definition : countryString.split(" ")) 
        	countryDefinition.put(definition.substring(0, 2), Integer.parseInt(definition.substring(2)));
		return countryDefinition;
	}
}
