The key generation function (KeyGen) takes the bit-length (up to 32) of p and q as input,
and output the public key (N, e) and the corresponding private key (N, p, q, d) into two separate files
pk.txt and sk.txt respectively, where p, q are distinct primer numbers, N = p * q (i.e., N is up to 64
bits), d * e = 1mod((p - 1) * (q - 1)).

The Encryption function (Encrypt) should take the public key from pk.txt and a message M
(a positive integer smaller than N) from a file mssg.txt as input, and output the corresponding
ciphertext c = M^d (mod N) into another file cipher.txt.

The Decryption function (Decrypt) should take the private key from sk.txt and the ciphertext
from cipher.txt and output the message M on the screen (terminal). When your program is executed,
a menu with these three functions should be displayed and a user can choose to invoke any of these
functions for multiple times.


Use Command Prompt:
Compile -
javac RSA.java

Run -
java RSA