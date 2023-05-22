import java.io.*;

/**
 * Main class for the LZWCompression adn decompression. It will function as compressor and decompressor
 *
 * @author Nick Ma, haoxuanm
 */
public class LZWCompression {

    private int bytesRead; // used to cound how many bytes were read from the input file
    private int bytesWrite; //count how many bytes were write to the output file
    private MyLinkedList[] hashMap; //the hashmap used to keep patterns
    DataInputStream in; //input stream
    DataOutputStream out; //output stream
    final int ARRAY_SIZE = 127; //the array size for the hashmap
    int size; //the total size of the pattern stored

    //constructor
    public LZWCompression(String in, String out) throws IOException {
        bytesRead = 0;
        bytesWrite = 0;
        bitBuffer = 0;
        bitCount = 0;
        this.in = new DataInputStream(new BufferedInputStream(new FileInputStream(in)));
        this.out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(out)));

    }

    /**
     * The compressor method, implemented based on the pseudocode.
     *
     * @throws IOException
     */
    public void LZW_Compress() throws IOException {
        //enter all symbols in the table;
        buildHashMap(ARRAY_SIZE);
        //read(first character from w into string s);
        //String s = String.valueOf((char) in.readUnsignedByte());
        String s = "";
        int input;
        String sc;
        char c;
        int code;

        //while(any input left){
        while (true) {
            //read(character c);
            try {
                //use readUnsignedByte because it is 0-255, no need to ensure its positivity in this case
                input = in.readUnsignedByte();
                bytesRead++;
            } catch (EOFException e) {
                in.close();
                break;
            }
            //handle for binary files
            c = (char) (input & 0xFF);
            sc = s + c;
            //incase overflow
            if (size == (int) Math.pow(2, 12)) {
                buildHashMap((int) Math.pow(2, 12));
            }
            //if(s + c is in the table)
            if (getValueFromKey(sc) != -1) {
                //s = s + c;
                s = sc;
            }
            //else {
            else {
                code = getValueFromKey(s);
                //output codeword(s);
                outputCode(code, false);
                // Enter s + c into the table
                addToMap(sc, size++);
                //s = c;
                s = String.valueOf(c);
                //} // end if/else
            }
            //} // end while
        }
        //output codeword(s);
        outputCode(getValueFromKey(s), true);
        out.flush(); // flush the output stream
        out.close(); // close the output stream
    }

    /**
     * Helper method for hashmap search, take key and get the value out
     *
     * @param sc the key
     * @return the value
     */
    public int getValueFromKey(String sc) {
        int index = (sc.hashCode() & 0x7FFFFFFF) % ARRAY_SIZE;

        MyLinkedList ll = hashMap[index];
        ListNode cur = ll.getHead();
        while (cur != null) {
            if (cur.getKey().equals(sc)) {
                return cur.getVal();
            }
            cur = cur.getNext();
        }
        return -1;
    }

    /**
     * Add a new node to the map
     *
     * @param sc    the key
     * @param value the value
     */
    public void addToMap(String sc, int value) {
        int index = (sc.hashCode() & 0x7FFFFFFF) % ARRAY_SIZE;
        hashMap[index].addLast(new ListNode(sc, value));
    }

    /**
     * Helper search method for hashmap, search the key by the value, since the val is the int that is
     * incremental, this can ensure no dup val for all items stored in the dataset
     *
     * @param value the value
     * @return the key
     */
    public String getKeyFromValue(int value) {
        for (int i = 0; i < ARRAY_SIZE; i++) {
            MyLinkedList ll = hashMap[i];
            ListNode cur = ll.getHead();
            while (cur != null) {
                if (cur.getVal() == value) {
                    return cur.getKey();
                }
                cur = cur.getNext();
            }
        }
        return null;
    }

    /**
     * Initialize the hashmap, also will be used to reset when overflow encounters
     *
     * @param size the size of the data, normally be 127
     */
    private void buildHashMap(int size) {
        this.size = 256;
        hashMap = new MyLinkedList[size];
        for (int i = 0; i < ARRAY_SIZE; i++) {
            hashMap[i] = new MyLinkedList();
        }
        //populate the first 256 item
        int ASCII = 256;
        for (char c = 0; c < ASCII; c++) {
            hashMap[(c & 0x7FFFFFFF) % ARRAY_SIZE].addLast(new ListNode(String.valueOf(c), (int) c));
        }
    }
    //helper to track the input and output method
    //buffer is used for saving actual bytes as int format
    int bitBuffer;
    //count is used to store remaining bits
    int bitCount;
    //for output and input code, bitwise operation were studied from https://www.geeksforgeeks.org/bitwise-operators-in-java/]

    /**
     * Bitwise operation that help the compressor to write data out to the file
     *
     * @param code  the current codeword
     * @param isEOF check if it reached to the end of line
     * @throws IOException
     */
    private void outputCode(int code, boolean isEOF) throws IOException {
        final int BITS = 12; //12 bits according to LZW
        //shift the code to the left by the number of current bitcount and add it to the buffer
        bitBuffer |= (code << bitCount);
        //now have 12bit in the buffer, so add bitcount
        bitCount += BITS;

        //keep looping if there are at least a byte in the buffer
        while (bitCount >= 8) {
            //write and adjust the bifbuffer to remove the written byte
            out.write(bitBuffer & 0xFF);
            bitBuffer >>= 8;
            bitCount -= 8;
            bytesWrite++;
        }
        //handle EOF cases
        if (isEOF) {
            //if buffer have leftover, but eof reached
            if (bitCount > 0) {
                //write the left over with padding for the non data
                out.writeByte(bitBuffer & 0xFF);
                bytesWrite++;
            }
            //reset those, in case compressor and decompressor called in the same instancee
            bitBuffer = 0;
            bitCount = 0;
        }
    }

    /**
     * The decompress method, implemented based on the pseudocode
     *
     * @param in  input file
     * @param out output file
     * @throws IOException
     */
    public void LZW_Decompress(String in, String out) throws IOException {
        //reset those again in case a sequential call after the compress
        this.in = new DataInputStream(new BufferedInputStream(new FileInputStream(in)));
        this.out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(out)));
        buildHashMap(ARRAY_SIZE);
        this.bitBuffer = 0;
        this.bitCount = 0;
        this.bytesWrite = 0;
        this.bytesRead = 0;
        //enter all symbols into the table;
        //read(priorcodeword) and output its corresponding character;
        int priorCode = inputCode();
        if (priorCode != -1) {
            String key = getKeyFromValue(priorCode);
            if (key != null) {
                this.out.writeBytes(key);
            }
        }
        int code = 0;
        //while(codewords are still left to be input){
        //read(codeword);
        while ((code = inputCode()) != -1) {

            //incase overflow
            if (size == (int) Math.pow(2, 12)) {
                buildHashMap((int) Math.pow(2, 12));
            }
            String newEntry;
            //if(codeword not in the table) {
            if (getKeyFromValue(code) == null) {
                String lastEntry = getKeyFromValue(priorCode);
                if (lastEntry != null) {
                    //enter string(priorcodeword) + firstChar(string(priorcodeword)) into the table
                    newEntry = lastEntry + lastEntry.charAt(0);
                    addToMap(newEntry, size++);
                    //output string(priorcodeword) + firstChar(string(priorcodeword));
                    this.out.writeBytes(newEntry);
                    bytesWrite += newEntry.length();
                }
                //else {
            } else {
                //enter string(priorcodeword) + firstChar(string(codeword)) into the table;
                newEntry = getKeyFromValue(code);
                if (newEntry != null) {
                    String priorKey = getKeyFromValue(priorCode);
                    if (priorKey != null) {
                        addToMap(priorKey + newEntry.charAt(0), size++);
                    }
                }
                //output string(codeword);
                if (newEntry != null) {
                    this.out.writeBytes(newEntry);
                    bytesWrite += newEntry.length();

                }
            }
            //priorcodeword = codeword;
            priorCode = code;
        }
        // Check if the last byte of the decompressed file is a null byte
        if (bytesWrite > 0 && getKeyFromValue(priorCode).charAt(0) == '\0') {
            // Reduce the number of bytes written by 1
            bytesWrite--;
        }
        this.out.flush();
        this.out.close();
        bytesWrite++;

    }

    /**
     * Bitwise operation helper to read input as 12bits from the compressed file for LZW_decompress
     * different from the example bad compressor logic, I found read in byte wise makes
     * it easier to handle corner cases than a 3 bytes batch
     * It reads a compressed code from the in and output it as 12bit int
     * Read byte until there are enough 12 bit in the buffer or EOF
     * in first case, it then handles for the binary files and output the int format code
     * and adjust the buffer and return
     * in the second case, it will make proper hanle for EOF cases
     *
     * @return the 12bit codeword representation in integer
     * @throws IOException
     */
    private int inputCode() throws IOException {
        final int BITS = 12;
        int code = 0;
        int reader;

        // Read byte until there are 12 bits
        while (bitCount < BITS) {
            reader = in.read();

            if (reader < 0) { // Handle EOF case
                if (bitCount == 0) {
                    return -1;
                }
                break;
            }

            bytesRead++;

            // Prevent sign extension
            reader &= 0xFF;

            // Add the read 8-bit to the bitBuffer
            bitBuffer |= (reader << bitCount);
            bitCount += 8;
        }

        // Get the 12 bits code
        code = bitBuffer & ((1 << BITS) - 1);

        // Update the bitBuffer and bitCount
        bitBuffer >>= BITS;
        bitCount -= BITS;

        // If there aren't enough bits for the next output, reset bitCount to 0
        if (bitCount < 0) {
            bitCount = 0;
        }

        return code;
    }

    /**
     * The main routine of this program. Take in a command line fashion to compile
     * The program works for both ASCII and binary files, although the performance for the binary file is bad, i will
     * specify in below.
     * Although no major difference working on those two types of files, the thing needs to
     * ensure to get binary file works is no automatic sign extension. In that way, binary files will work.
     * <p>
     * Result of the three file
     * <p>
     * words.html:
     * - Compression: bytes read = 2494562, bytes written = 1072025
     * - Decompression: bytes read = 1072025, bytes written = 2494562
     * - Degree of compression: 0.43
     * <p>
     * CrimeLatLonXY.csv:
     * - Compression: bytes read = 2608940, bytes written = 1283751
     * - Decompression: bytes read = 1283751, bytes written = 2608940
     * - Degree of compression: 0.49
     * <p>
     * 01_Overview.mp4
     * - Compression: bytes read = 25008838, bytes written = 33773775
     * - Decompression: bytes read = 33773775, bytes written = 25008838
     * - Degree of compression: 1.35 (really bad)
     * The LZW algorithm itself make binary file unsuitable to solve, the entropy is so big, so the compressed
     * file is larger than original.
     *
     * @param args command line args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        if (args.length < 4) {
            System.err.println("Usage: java LZWCompression (-c|-d) -v inputFile outputFile");
            System.exit(1);
        }

        String mode = args[0];
        String inputFile = args[2];
        String outputFile = args[3];

        if ("-c".equals(mode)) {
            LZWCompression lzwCompress = new LZWCompression(inputFile, outputFile);
            lzwCompress.LZW_Compress();
            System.out.printf("Compression: bytes read = %d, bytes written = %d%n", lzwCompress.bytesRead, lzwCompress.bytesWrite);
        } else if ("-d".equals(mode)) {
            LZWCompression lzwDecompress = new LZWCompression(inputFile, outputFile);
            lzwDecompress.LZW_Decompress(inputFile, outputFile);
            System.out.printf("Decompression: bytes read = %d, bytes written = %d%n", lzwDecompress.bytesRead, lzwDecompress.bytesWrite);
        } else {
            System.err.println("Invalid mode. Use -c for compression or -d for decompression.");
            System.exit(1);
        }
    }

}
