package net.thekttn.kttnmail.valueobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.thekttn.kttnmail.data.entities.MailServer;
import net.thekttn.kttnmail.types.ServerType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "server")
public class MailServerVO
{
  @JsonProperty("id")
  private long id;
  
  @JsonProperty("serverType")
  private ServerType serverType;

  @JsonProperty("serverAddress")
  private String serverAddress; 

  @JsonProperty("serverPort")
  private int serverPort;

  @JsonProperty("credentialId")
  private long credentialId;

  public MailServerVO(MailServer entity)
  {
    this.id = entity.getId();
    this.serverType = entity.getServerType();
    this.serverAddress = entity.getServerAddress();
    this.serverPort = entity.getServerPort();
    this.credentialId = entity.getCredentialId();
  }
}
