import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//Name: Muhammad Nuh Bin Norseni
//java version "1.8.0_212"

class RSA{
	public static Random rand = new Random();
	public static BigInteger base, mod, encrypt, decrypt;
	public static long N;
	
	public static boolean checkPrime(int num){
        boolean flag = false;
        for (int i = 2; i <= num / 2; ++i) {
            // condition for nonprime number
            if (num % i == 0) {
                flag = true;
                break;
            }
        }
        return !flag;
    }
	
	//getting e
	public static int generatePublicExponent(long n){
		boolean check = true;
		int gcd = 1;
		int r = 0;
		while(check){
			r = rand.nextInt(1000);
			if(r < n){
				for(int i = 1; i <= r && i <= n; i++){
					if(r%i==0 && n%i==0)
						gcd = i;
						
					if(gcd == 1){
						check = false;
					}
				}
			}
		}
		return r;
	}
	
	//getting d
	public static int generatePrivateExponent(int e, int p, int q){
		boolean check = true;
		int r = rand.nextInt(100);
		while(check){
			r += 1;
			int ch = ((e*r)-1)%((p-1)*(q-1));
			if(ch == 0){
				check = false;
			}
		}
		return r;
	}
	
	public static void KeyGen(){
        Scanner scan = new Scanner(System.in);

        System.out.printf("Enter p: ");
        int p = scan.nextInt();

		//check if p is prime or p = 1
		while(!checkPrime(p) || p==1){
			if(p==1)
				System.out.println("Inavlid input, p cannot be 1, please re-enter.");
			else
				System.out.println("Inavlid input, p has to be a prime, please re-enter.");
			System.out.printf("\nEnter p: ");
			p = scan.nextInt();
		}

        System.out.printf("Enter q: ");
        int q = scan.nextInt();

        //check if q = 1 or q = p or if q is prime
		while(q==1 || q==p || !checkPrime(q)){
			if(q==1)
				System.out.println("Inavlid input, q cannot be 1, please re-enter.");
			else if(q==p)
				System.out.println("Inavlid input, q cannot be same as p, please re-enter.");
			else
				System.out.println("Inavlid input, q has to be a prime, please re-enter.");
			System.out.printf("\nEnter q: ");
			q = scan.nextInt();
		}
		
        long N = p*q;
		int e = generatePublicExponent(N);
		int d = generatePrivateExponent(e,p,q);
		
		//Creating public key
        ArrayList<Long> pubKey = new ArrayList<>();
        pubKey.add(N);
        pubKey.add((long) e);
		
		//Creating private key
        ArrayList<Long> privKey = new ArrayList<>();
        privKey.add(N);
        privKey.add((long) p);
        privKey.add((long) q);
        privKey.add((long) d);
		
		System.out.println("Keys successfully created!");
		
		try {
            FileWriter Pub = new FileWriter("pk.txt");
            Pub.write(String.valueOf(pubKey));
            Pub.close();
            System.out.println("Public key successfully recorded in pk.txt.");
        } catch (IOException l) {
            System.out.println("An error occurred.");
            l.printStackTrace();
        }

        try {
            FileWriter Priv = new FileWriter("sk.txt");
            Priv.write(String.valueOf(privKey));
            Priv.close();
            System.out.println("Private key successfully recorded in sk.txt.");
        } catch (IOException l) {
            System.out.println("An error occurred.");
            l.printStackTrace();
        }
		
		//generate m where m<N
        int m = rand.nextInt((int)N);
		if(m == 0)
			m = m + 1;
        try {
            FileWriter msg = new FileWriter("mssg.txt");
            msg.write(String.valueOf(m));
            System.out.println("Message m successfully recorded in mssg.txt.");
            msg.close();
        } catch (IOException l) {
            System.out.println("An error occurred.");
            l.printStackTrace();
        }
    }
	
	public static void RSAEncrypt() {
        //get public key values
        String data="";
        try {
            File myObj = new File("pk.txt");
            Scanner read = new Scanner(myObj);
            while (read.hasNextLine()) {
                data = read.nextLine();
            }
            read.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred, pk.txt not found.");
            e.printStackTrace();
        }

        String dataStr = data.substring(1,data.length()-1);
        String[] values= dataStr.split(",");
        N = Integer.parseInt(values[0].trim());
        int e = Integer.parseInt(values[1].trim());

        String message = "";
        try {
            File myObj = new File("mssg.txt");
            Scanner read = new Scanner(myObj);
            while (read.hasNextLine()) {
                message = read.nextLine();
            }
            read.close();
        } catch (FileNotFoundException b) {
            System.out.println("An error occurred, mssg.txt not found.");
            b.printStackTrace();
        }

        int m = Integer.parseInt(message);
        base = new BigInteger(String.valueOf(m));
        mod = new BigInteger(String.valueOf(N));
		encrypt = (base.pow(e)).mod(mod);

        try {
            FileWriter cipher = new FileWriter("cipher.txt");
            cipher.write(String.valueOf(encrypt));
            System.out.println("Ciphertext successfully written to cipher.txt.");
            cipher.close();
        } catch (IOException l) {
            System.out.println("An error occurred.");
            l.printStackTrace();
        }
    }
	
	public static void RSADecrypt() {
        //get private key values
        String data="";
        try {
            File myObj = new File("sk.txt");
            Scanner read = new Scanner(myObj);
            while (read.hasNextLine()) {
                data = read.nextLine();
            }
            read.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred, sk.txt not found.");
            e.printStackTrace();
        }

        String dataStr = data.substring(1,data.length()-1);
        String[] values = dataStr.split(",");
        long N = Integer.valueOf(values[0].trim());
        int p = Integer.valueOf(values[1].trim());
        int q = Integer.valueOf(values[2].trim());
        int d = Integer.valueOf(values[3].trim());

        //get ciphertext
        String cipher="";
        try {
            File myObj = new File("cipher.txt");
            Scanner read = new Scanner(myObj);
            while (read.hasNextLine()) {
                cipher = read.nextLine();
            }
            read.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        int sData = Integer.valueOf(cipher);

        

        base = new BigInteger(String.valueOf(sData));
        mod = new BigInteger(String.valueOf(N));
		decrypt = (base.pow(d)).mod(mod);
        System.out.println("Decrypted message: "+ decrypt);

    }

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
		boolean exit = true;
		while(exit){
			System.out.println("\nRSA encryption/decryption program");
            System.out.println("-----------------------------------------------");
            System.out.println("1. RSA Key Generation");
            System.out.println("2. RSA Encryption");
            System.out.println("3. RSA Decryption");
            System.out.println("0. Exit");
			System.out.printf("Please key in your selection(1,2,3,0): ");
            String option = scan.nextLine();
			
			if(option.equals("1"))
				KeyGen();
			else if(option.equals("2"))
				RSAEncrypt();
			else if(option.equals("3"))
				RSADecrypt();
			else if(option.equals("0"))
				exit = false;
			else
				System.out.println("Invalid option, key again.");
		}
    }
}
