package net.thekttn.kttnmail.data.entities;

import java.time.OffsetDateTime;

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
import net.thekttn.kttnmail.types.MessageType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MESSAGES", schema = "KTTN_MAIL_SCHEMA")
public class EmailMessage
{
  @Id
  @GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "MSG_ID_GEN"
	)
	@SequenceGenerator(
		name = "MSG_ID_GEN",
		sequenceName = "MESSAGE_ID_SEQ",
    schema = "KTTN_MAIL_SCHEMA",
    allocationSize = 1
	)
  @Column(name = "ID", nullable = false)
  private long id;

  @Column(name = "MAILBOX_ID", nullable = false)
  private long mailboxId;

  @Column(name = "MESSAGE_TYPE", nullable = false)
  private MessageType messageType;

  @Column(name = "MESSAGE_HASH", nullable = false)
  private String messageHash;

  @Column(name = "LUPD", nullable = false)
  private OffsetDateTime lastUpdated;
}
