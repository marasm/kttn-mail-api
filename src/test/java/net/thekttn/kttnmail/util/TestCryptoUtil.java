package net.thekttn.kttnmail.util;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class TestCryptoUtil
{
  private MimeMessage message;
  private CryptoUtil util;
  
  @BeforeEach
  public void setup() throws AddressException, MessagingException
  {
    message = new MimeMessage((jakarta.mail.Session)null);
    message.setFrom(new InternetAddress("test@example.com"));
    message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress("recipient@example.com"));
    message.setSubject("Test Subject");
    message.setText("This is the body of the email.");

    util = new CryptoUtil();
    ReflectionTestUtils.setField(util, "keySecret", "testSecret");
    ReflectionTestUtils.setField(util, "keySalt", "testSalt");
    ReflectionTestUtils.setField(util, "initVector", "r++nXVdcyxNlv6C1");
  }

  @AfterEach
  public void teardown() throws MessagingException
  {
    message = null;
    util = null;
  }


  @Test
  void testEncryptDecryptString_SUCCESS() throws Exception
  {
    String originalString = "This is a test string for encryption.";
    String encryptedString = util.encryptString(originalString);
    String decryptedString = util.decryptString(encryptedString);

    assertNotEquals(originalString, encryptedString);
    assertTrue(decryptedString.equals(originalString));
  }

  @Test
  void testHashMessage_SUCCESS() throws MessagingException, IOException
  {
    String hash = util.hashMessage(message);

    assertTrue(hash != null && !hash.isEmpty());
    assertTrue(hash.length() == 128); // SHA-512 produces a 128-character hex string
  }

  @Test
  void testHashMessage_HASHES_DIFFERENT() throws MessagingException, IOException
  {
    String hash1 = util.hashMessage(message);
    
    message.setText("This is a modified body of the email.");
    String hash2 = util.hashMessage(message);

    assertTrue(hash1 != null && !hash1.isEmpty());
    assertTrue(hash1.length() == 128); // SHA-512 produces a 128-character hex string
    assertTrue(hash2 != null && !hash2.isEmpty());
    assertTrue(hash2.length() == 128); // SHA-512 produces a 128-character hex string
    assertNotEquals(hash1, hash2);
  }

  @Test
  void testHashMessage_NULL_MESSAGE() throws MessagingException, IOException
  {
    String hash = util.hashMessage(null);
    assertTrue(hash != null && !hash.isEmpty());
    assertTrue(hash.length() == 128); // SHA-512 produces a 128-character hex string even for an empty string
  }
}