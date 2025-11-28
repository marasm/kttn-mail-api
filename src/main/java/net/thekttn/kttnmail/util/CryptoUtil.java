package net.thekttn.kttnmail.util;

import jakarta.mail.Address;
import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;;

@Component
public class CryptoUtil
{
  private static final String ENCRYPTION_ALGORITHM = "AES/GCM/NoPadding";
  private static final String SECRET_KEY_ALGORITHM = "PBKDF2WithHmacSHA256";
  private static final String KEY_MATERIAL_ALGORITHM = "AES";
  private static final int ITERATION_COUNT = 65536;
  private static final int KEY_LENGTH = 256;
  private static final int INIT_VECTOR_LEN = 128;

  @Value("${crypto.key-secret}")
  private String keySecret;
  @Value("${crypto.key-salt}")
  private String keySalt;
  @Value("${crypto.init-vector}")
  private String initVector;


  private Cipher encryptor;
  private Cipher decryptor;

  public String encryptString(String inString) throws IllegalBlockSizeException, BadPaddingException, Exception
  {
    byte[] cipherText = getEncryptor().doFinal(inString.getBytes());
    return Base64.getEncoder().encodeToString(cipherText);
  }

  public String decryptString(String inString)  throws IllegalBlockSizeException, BadPaddingException, Exception
  {
    byte[] decodedBytes = Base64.getDecoder().decode(inString);
    byte[] plainText = getDecryptor().doFinal(decodedBytes);
    return new String(plainText);
  }

  public String hashMessage(Message inMessage) throws MessagingException, IOException
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


  private Cipher getEncryptor() throws Exception
  {
    if(encryptor == null)
    {
      SecretKey secretKey = getSecretKey();
      GCMParameterSpec ivSpec = getInitVector();

      encryptor = Cipher.getInstance(ENCRYPTION_ALGORITHM);
      encryptor.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
    }

    return encryptor;
  }
  
  private Cipher getDecryptor() throws Exception
  {
    if(decryptor == null)
    {
      SecretKey secretKey = getSecretKey();
      GCMParameterSpec ivSpec = getInitVector();

      decryptor = Cipher.getInstance(ENCRYPTION_ALGORITHM);
      decryptor.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
    }

    return decryptor;
  }


  private SecretKey getSecretKey() throws InvalidKeySpecException, NoSuchAlgorithmException
  {
    if(keySecret == null || keySalt == null)
    {
      throw new IllegalStateException("Key secret or salt not set");
    }

    SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_KEY_ALGORITHM);
    KeySpec spec = new PBEKeySpec(keySecret.toCharArray(), keySalt.getBytes(), ITERATION_COUNT, KEY_LENGTH);
    SecretKey secret = new SecretKeySpec(factory.generateSecret(spec)
        .getEncoded(), KEY_MATERIAL_ALGORITHM);
    return secret;
  }


  private GCMParameterSpec getInitVector()
  {
    if(initVector == null)
    {
      throw new IllegalStateException("Initialization vector not set");
    }

    byte[] ivBytes = Base64.getDecoder().decode(initVector.getBytes());

    return new GCMParameterSpec(INIT_VECTOR_LEN, ivBytes);
  }

}
