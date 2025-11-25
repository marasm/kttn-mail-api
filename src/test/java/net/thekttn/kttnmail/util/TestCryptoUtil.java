package net.thekttn.kttnmail.util;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class TestCryptoUtil
{
  private MimeMessage message;
  @BeforeEach
  public void setup() throws AddressException, MessagingException
  {
    message = new MimeMessage((jakarta.mail.Session)null);
    message.setFrom(new InternetAddress("test@example.com"));
    message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress("recipient@example.com"));
    message.setSubject("Test Subject");
    message.setText("This is the body of the email.");
  }

  @Test
  void testHashMessage_SUCCESS() throws MessagingException, IOException
  {
    String hash = CryptoUtil.hashMessage(message);

    assertTrue(hash != null && !hash.isEmpty());
    assertTrue(hash.length() == 128); // SHA-512 produces a 128-character hex string
  }

  @Test
  void testHashMessage_HASHES_DIFFERENT() throws MessagingException, IOException
  {
    String hash1 = CryptoUtil.hashMessage(message);
    
    message.setText("This is a modified body of the email.");
    String hash2 = CryptoUtil.hashMessage(message);

    assertTrue(hash1 != null && !hash1.isEmpty());
    assertTrue(hash1.length() == 128); // SHA-512 produces a 128-character hex string
    assertTrue(hash2 != null && !hash2.isEmpty());
    assertTrue(hash2.length() == 128); // SHA-512 produces a 128-character hex string
    assertNotEquals(hash1, hash2);
  }

  @Test
  void testHashMessage_NULL_MESSAGE() throws MessagingException, IOException
  {
    String hash = CryptoUtil.hashMessage(null);
    assertTrue(hash != null && !hash.isEmpty());
    assertTrue(hash.length() == 128); // SHA-512 produces a 128-character hex string even for an empty string
  }
}