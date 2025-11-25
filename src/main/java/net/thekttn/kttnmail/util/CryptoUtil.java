package net.thekttn.kttnmail.util;

import jakarta.mail.Address;
import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;

import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;;

public class CryptoUtil
{

  public static String hashMessage(Message inMessage) throws MessagingException, IOException
  {
    
    StringBuilder sb = new StringBuilder();

    if(inMessage != null)
    {
      for(Address addr : inMessage.getFrom())
      {
        sb.append(addr.toString());
      }
      for(Address addr : inMessage.getAllRecipients())
      {
        sb.append(addr.toString());
      }
  
      sb.append(inMessage.getSubject());
      
      if(inMessage.getContent() instanceof Multipart)
      {
        Multipart parts = (Multipart)inMessage.getContent();
        for(int i = 0; i < parts.getCount(); i++)
        {
          BodyPart part = parts.getBodyPart(i);
          if (part.getContentType().toLowerCase().startsWith("text/")) 
          {
            sb.append((String)part.getContent());
          }
        }
      }
      else
      {
        sb.append((String)inMessage.getContent());
      }
    }


    return DigestUtils.sha512Hex(sb.toString());
  }

}
