package edu.ntnu.flightbookingbackend.cryptography;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.security.Key;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Converter for encrypting and decrypting data in the database. */
@Converter
public class CryptoConverter implements AttributeConverter<String, String> {

  // Secret Key for encryption
  // TODO: Move all values to ..env file
  private final String SECRET_KEY = "SecurityApp@2025";
  // Encryption algorithm
  private final String ENC = "AES";
  private final String ALGORITHM = "AES/ECB/PKCS5Padding";

  private final Logger logger = LoggerFactory.getLogger(CryptoConverter.class);

  /**
   * Encrypts the data before storing it in the database.
   *
   * @param attribute attribute to be encrypted
   * @return encrypted attribute
   */
  @Override
  public String convertToDatabaseColumn(String attribute) {
    // Encode data to store into database
    logger.info("Convert Application data to Database: {}", attribute);
    String value = null;
    try {
      Key key = new SecretKeySpec(SECRET_KEY.getBytes(), ENC);
      Cipher c = Cipher.getInstance(ALGORITHM);
      c.init(Cipher.ENCRYPT_MODE, key);
      value = Base64.getEncoder().encodeToString(c.doFinal(attribute.getBytes()));
    } catch (Exception e) {
      logger.info("Failed to encode: " + e.getMessage());
      e.printStackTrace();
    }
    return value;
  }

  /**
   * Decrypts the data after retrieving it from the database.
   *
   * @param dbData data to be decrypted
   * @return decrypted data
   */
  @Override
  public String convertToEntityAttribute(String dbData) {
    // Decode data to use in Application
    logger.info("Convert Database to Application data: {}", dbData);
    String value = null;
    try {
      Key key = new SecretKeySpec(SECRET_KEY.getBytes(), ENC);
      Cipher c = Cipher.getInstance(ALGORITHM);
      c.init(Cipher.DECRYPT_MODE, key);
      value = new String(c.doFinal(Base64.getDecoder().decode(dbData)));
    } catch (Exception e) {
      logger.info("Failed to decode: " + e.getMessage());
      e.printStackTrace();
    }
    return value;
  }
}
