import java.awt.Color;
import java.io.IOException;

import edu.neumont.ui.Picture;

public class Steganog
{
	/**
	 * Takes a clean image and changes the prime-indexed pixels to secretly
	 * carry the message
	 **/
	public Picture embedIntoImage(Picture cleanImage, String message)
			throws IOException
	{
		int numPixels = cleanImage.height() * cleanImage.width();
		
		PrimeIterator pI =  new PrimeIterator(numPixels);
		
		for(int i = 0; i < message.length() && pI.hasNext(); i++)
		{
			int secretChar = (int)message.charAt(i) - 32 ;
			
			int currentPixelIndex = pI.next();
			Color existingPixel = getPixel(currentPixelIndex, cleanImage);
			Color newPixel = new Color(
					(existingPixel.getRed() & 0B11111100) + (((secretChar & 0B00110000) >> 0B00000100)), 
					(existingPixel.getGreen() & 0B11111100) + (((secretChar & 0B00001100) >> 0B00000010)),
					(existingPixel.getBlue() & 0B11111100) + (secretChar & 0B00000011));
			cleanImage.set(getXLeftToRight(currentPixelIndex, cleanImage.width()), getYLeftToRight(currentPixelIndex, cleanImage.width()), newPixel);	
		}
		
		return cleanImage;	
	}

	/**
	 * Retrieves the embedded secret from the secret-carrying image
	 */
	public String retreiveFromImage(Picture imageWithSecretMessage)
			throws IOException
	{
		int numPixels = imageWithSecretMessage.height() * imageWithSecretMessage.width();
		
		PrimeIterator i = new PrimeIterator(numPixels);
		
		String message = "";
		
		while(i.hasNext())
		{
			int currentPixelIndex = i.next();
			Color currentPixel = getPixel(currentPixelIndex, imageWithSecretMessage);
			int x = currentPixel.getRed() & 0B00000011;
			x = x << 0b00000100;
			int y = currentPixel.getGreen() & 0B00000011;
			y = y << 0b00000010;
			int z = currentPixel.getBlue() & 0B00000011;
			int code = x + y + z + 32;
			message += (char)code;		
		}
		
		return message;
		
	}
	
	private Color getPixel(int index, Picture p)
	{
		return p.get(getXLeftToRight(index, p.width()), getYLeftToRight(index, p.width()));
	}
	
	public int getXLeftToRight(int index, int width)
	{
		return index % width;
	}
	
	public int getYLeftToRight(int index, int width)
	{
		return index / width;
	}
}
