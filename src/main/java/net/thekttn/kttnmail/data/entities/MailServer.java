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
import net.thekttn.kttnmail.types.ServerType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="SERVERS", schema = "KTTN_MAIL_SCHEMA")
public class MailServer {

  @Id
  @GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "BOX_ID_GEN"
	)
	@SequenceGenerator(
		name = "BOX_ID_GEN",
		sequenceName = "MAILBOX_ID_SEQ",
    schema = "KTTN_MAIL_SCHEMA",
    allocationSize = 1
	)
  @Column(name="ID", nullable = false)
  private long id;

  @Column(name="MAILBOX_ID", nullable = false)
  private long mailboxId;

  @Column(name="SERVER_TYPE", nullable = false)
  private ServerType serverType;

  @Column(name="CREDENTIAL_ID", nullable = false)
  private long credentialId;

  @Column(name="SERVER_ADDRESS", nullable = false)
  private String serverAddress;

  @Column(name="SERVER_PORT", nullable = false)
  private int serverPort;
}
