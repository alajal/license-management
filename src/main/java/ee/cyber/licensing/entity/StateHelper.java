package ee.cyber.licensing.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown=true)
public class StateHelper {
  Boolean rejected;
  Boolean negotiated;
  Boolean waiting_for_signature;
  Boolean active;
  Boolean expiration_nearing;
  Boolean terminated;

  public StateHelper() {

  }

  public StateHelper(Boolean rejected, Boolean negotiated, Boolean waiting_for_signature, Boolean active, Boolean expiration_nearing, Boolean terminated) {
    this.rejected = rejected;
    this.negotiated = negotiated;
    this.waiting_for_signature = waiting_for_signature;
    this.active = active;
    this.expiration_nearing = expiration_nearing;
    this.terminated = terminated;
  }

  public Boolean getRejected() {
      return rejected;
  }

  public void setRejected(Boolean value) {
      this.rejected = value;
  }

  public Boolean getNegotiated() {
      return negotiated;
  }

  public void setNegotiated(Boolean value) {
      this.negotiated = value;
  }

  public Boolean getWaiting_for_signature() {
      return waiting_for_signature;
  }

  public void setWaiting_for_signature(Boolean value) {
      this.waiting_for_signature = value;
  }

  public Boolean getActive() {
      return active;
  }

  public void setActive(Boolean value) {
      this.active = value;
  }

  public Boolean getExpiration_nearing() {
      return expiration_nearing;
  }

  public void setExpiration_nearing(Boolean value) {
      this.expiration_nearing = value;
  }

  public Boolean getTerminated() {
      return terminated;
  }

  public void setTerminated(Boolean value) {
      this.terminated = value;
  }
}
