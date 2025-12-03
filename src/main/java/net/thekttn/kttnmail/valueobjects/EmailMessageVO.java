package net.thekttn.kttnmail.valueobjects;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.thekttn.kttnmail.data.entities.EmailMessage;
import net.thekttn.kttnmail.types.MessageType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "message")
public class EmailMessageVO
{
  @JsonProperty("id")
  private long id;

  @JsonProperty("mailboxId")
  private long mailboxId;

  @JsonProperty("messageType")
  private MessageType messageType;

  @JsonProperty("lupd")
  private OffsetDateTime lastUpdated;

  public EmailMessageVO(EmailMessage entity)
  {
    this.id = entity.getId();
    this.mailboxId = entity.getMailboxId();
    this.messageType = entity.getMessageType();
    this.lastUpdated = entity.getLastUpdated();
  }
}
