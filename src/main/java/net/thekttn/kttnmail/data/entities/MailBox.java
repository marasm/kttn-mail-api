package net.thekttn.kttnmail.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import net.thekttn.kttnmail.types.ProcessingType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MAILBOXES", schema = "KTTN_MAIL_SCHEMA")
public class MailBox
{
  @Id
  @GeneratedValue(
    strategy = GenerationType.SEQUENCE, 
    generator = "MBX_ID_GEN")
  @SequenceGenerator(
    name = "MBX_ID_GEN", 
    sequenceName = "MAILBOX_ID_SEQ", 
    schema = "KTTN_MAIL_SCHEMA", 
    allocationSize = 1)
  @Column(name = "ID", nullable = false)
  private long id;

  @Column(name = "NAME", nullable = false)
  private String name;

  @Column(name = "EMAIL_ADDRESS", nullable = false)
  private String emailAddress;

  @Column(name = "ENABLED", nullable = false)
  private boolean enabled;

  @Column(name = "CHECK_INTERVAL", nullable = false)
  private int checkIntervalMinutes;

  @Column(name = "DELETE_AFTER_PROCESSING", nullable = false)
  private boolean deleteAfterProcessing;

  @Column(name = "PROCESS_TYPE", nullable = false)
  private ProcessingType processingType;

  @Column(name = "FWD_DEST_EMAIL_ADDRESS", nullable = false)
  private String forwardDestinationEmailAddress;
}
