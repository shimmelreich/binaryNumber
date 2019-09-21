// A program that performs simple binary number operations.
// format: little-endian

import java.util.Arrays;
import java.util.Scanner;

public class BinaryNumber {

	// DATA FIELDS
	private int data[];
	private boolean overflow;
	
	// METHODS
	// Main method
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String bin;
		String binAdd;
		int index;
		int shiftNum;
		boolean endProgram = false;

		// prompts user to enter a binary number
		System.out.print("Enter binary number (i.e. 1010): ");
		bin = scan.nextLine();   

		// checks to make sure entered number is binary, ends program if not
		for (int i = 0; i < bin.length() - 1; i++) {
			char c = bin.charAt(i);
			char c0 = (char)('0');
			char c1 = (char)('1');
		    if ((c != c0) && (c != c1)) {
		        System.out.print("Number not valid! Exiting program.");
		        endProgram = true;
		        break;
		    }
		}
		if (endProgram) {
		    System.exit(0);
		}

		BinaryNumber binNum = new BinaryNumber(bin);
		
		// uses getLength method
		System.out.println("Length of number: " + binNum.getLength());
		
		// prompts user to enter an index for getDigit
		System.out.print("Enter index: ");
		index = scan.nextInt();
		System.out.println("Get digit at index: " + binNum.getDigit(index));
		
		// converts previously entered binary to decimal notation
		System.out.println("Decimal notation: " + binNum.toDecimal());
		
		// prompts user to enter an integer to shift binary to the right
		System.out.print("Enter amount of spaces to shift right: ");
		shiftNum = scan.nextInt();
		System.out.println("Array shifted " + shiftNum + " space(s) right: ");
		binNum.shiftR(shiftNum);

		scan.nextLine(); // nextInt() doesn't consume \n so this is needed
		
		// prompts user to enter a binary number to add to previous one
		System.out.print("Enter binary number to add: ");
		binAdd = scan.nextLine();   
		
		// checks to make sure entered number is binary, ends program if not
		for (int i = 0; i < binAdd.length() - 1; i++) {
			char c = bin.charAt(i);
			char c0 = (char)('0');
			char c1 = (char)('1');
		    if ((c != c0) && (c != c1)) {
		        System.out.print("Number not valid! Exiting program.");
		        endProgram = true;
		        break;
		    }
		}
		if (endProgram) {
		    System.exit(0);
		}
		
		BinaryNumber addNum = new BinaryNumber(binAdd);
		
		// prints out sum of the two binaries, if applicable
		System.out.println(bin + " + " + binAdd + " is: ");
		binNum.add(addNum);
		
		scan.close();
	}

	// A constructor 'BinaryNumber(int length)' for creating a binary number of length
	// 'length' and consisting only of zeros.
	public BinaryNumber(int length) {
		data = new int[length];
	}

	// A constructor 'BinaryNumber(String str)' for creating a binary number given a string.
	public BinaryNumber(String str) {
		data = new int[str.length()];
		for (int i = 0; i < str.length(); i++) {
			char digit = str.charAt(i);
			int temp = Character.getNumericValue(digit);
			data[i] = temp;
		}
	}

	// An operation 'int getLength()' for determining the length of a binary number.
	public int getLength() {
		return data.length;
	}

	// An operation 'int getDigit(int index)' for obtaining a digit of a binary number 
	// given an index.
	public int getDigit(int index) {
		if (index >= 0 && index < data.length) {
			return data[index];
		} else {
			// If the index is out of bounds, then a message should be printed 
			// on the screen indicating this fact
			System.out.println("Index is out of bounds.");
			return -1; // returns bogus value -1 for out of bound indices
		}
	}

	// An operation 'void shiftR(int amount)' for shifting all digits 
	// in a binary number any number of places to the right
	public void shiftR(int amount) {
		if (amount < 0) {
			// method will not work with a negative input
			System.out.println("You can't shift a negative number of spaces!");
		} else {
			int newArray[] = Arrays.copyOf(data,  (data.length + amount));
			String arr = "";
			for (int i = 0; i < amount; i++) {
				newArray[i + amount] = data[i];
				newArray[i] = 0;
			}
			// translates array to string for printing
			for (int j = 0; j < newArray.length; j++) {
				char c = (char)('0' + newArray[j]);
				arr += c;
			}
			System.out.println(arr);
		}
	}

	// Adds two binary numbers
	public void add(BinaryNumber aBinaryNumber) {
		int carry = 0;
		int digit = 0;
		
        String sum = "";

		int len1 = this.getLength();
		int len2 = aBinaryNumber.getLength();
		
		if (len1 != len2) {
			// If the lengths of the binary numbers do not coincide, then
			// a message should be printed on the screen indicating this fact. 
			System.out.println("The lengths of the binary numbers do not coincide.");
		} else {
			for (int i = 0; i < len1; i++) {
                if (carry == 0) {
                    digit = this.data[i] + aBinaryNumber.data[i];
                    if (digit == 2) {
                    	digit = 0;
                    	carry = 1;
                    }
                } else {
                	digit = this.data[i] + aBinaryNumber.data[i] + carry;
                	if (digit == 2) {
                		digit = 0;
                		carry = 1;
                	} else if (digit == 3) {
                		digit = 1;
                		carry = 1;
                	} else {
                	    carry = 0;
                	}
                }
                char c = (char)('0' + digit);
				sum += c;
			}
            //for end:
			if (carry == 1) {
				// It is possible for the addition of two numbers to yield a result 
				// which has a larger length than the summands; flag with boolean overflow
				overflow = true;
				System.out.println("Overflow!");
			} else {
				overflow = false;
				System.out.println(sum);
			}	
			
		}
	}
	
	// An operation 'String toString()' for transforming a binary number to a String.  
	// If the number is the result of an overflow, the string “Overflow” should be returned.
	@Override
	public String toString() {
		if (!overflow) {
			return "Overflow";
		} else {
			return data.toString();
		}
	}
	
	// An operation 'int toDecimal()' for transforming a binary number to its 
	// decimal notation
	public int toDecimal() {
		int l = data.length;
		int decimalNum = 0;
		for (int i = 0; i < l; i++) {
			// left-most digit is the least significant!!
			decimalNum = (int) (decimalNum + data[i] * Math.pow(2,i));
		}
		return decimalNum;
	}
	
	// An operation 'clearOverflow()' that clears the overflow flag
	public void clearOverflow() {
		overflow = false;
	}
}
