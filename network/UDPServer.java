import java.io.*;
import java.net.*;
import java.lang.Integer;
class UDPServer
{
	public static void main(String args[]) throws Exception
	{
		DatagramSocket serverSocket= new DatagramSocket(9876);
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		while(true)
{
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		serverSocket.receive(receivePacket);
		String sentence= new String(receivePacket.getData());
		System.out.println("RECEIVED: " +sentence);
		InetAddress IPAddress = receivePacket.getAddress();
		int port= receivePacket.getPort();
		String str= sentence.toLowerCase();
		int count = 0;
		for (int i = 0; i < str.length(); i++) 
		{
            // check if char[i] is vowel
            if (str.charAt(i) == 'a' || str.charAt(i) == 'e'
                || str.charAt(i) == 'i'
                || str.charAt(i) == 'o'
                || str.charAt(i) == 'u') 
				{
                count++;
				}
        }

		sendData = count.byteValue();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		serverSocket.send(sendPacket);
}
}
}