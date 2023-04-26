import java.io.*;
import java.net.*;
class TCPServer
{
	public static void main(String argv[]) throws Exception
	{
		String clientSentence;
		String capitalizedSentence;
		ServerSocket welcomeSocket = new ServerSocket(6789);
		int count;
		while(true)
		{
			Socket connectionSocket = welcomeSocket.accept();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			clientSentence = inFromClient.readLine();
			System.out.println("Connected");
			System.out.println("Received; " +clientSentence);
			count = countVowels(clientSentence);
			capitalizedSentence  = "no of vowels: " + count +'\n';
			outToClient.writeBytes(capitalizedSentence);
		}
	}
	
	public static int countVowels(String sentence) {
    int count = 0;
    String vowels = "aeiouAEIOU";
    for (int i = 0; i < sentence.length(); i++) {
      if (vowels.indexOf(sentence.charAt(i)) != -1) {
        count++;
      }
    }
    return count;
  }
}