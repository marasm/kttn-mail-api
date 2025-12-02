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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="CREDENTIALS", schema = "KTTN_MAIL_SCHEMA")
public class Credential
{
  @Id
  @GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "CREDENTIAL_ID_GEN"
	)
	@SequenceGenerator(
		name = "CREDENTIAL_ID_GEN",
		sequenceName = "CREDENTIAL_ID_SEQ",
    schema = "KTTN_MAIL_SCHEMA",
    allocationSize = 1
	)
  @Column(name="ID", nullable = false)
  private long id;

  @Column(name="MAILBOX_ID", nullable = false)
  private long mailboxId;
  
  @Column(name="USER_ID", nullable = false)
  private String userId;

  @Column(name="PASSWORD", nullable = false)
  private String password;
}
