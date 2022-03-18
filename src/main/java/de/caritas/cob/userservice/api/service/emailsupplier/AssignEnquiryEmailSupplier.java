package de.caritas.cob.userservice.api.service.emailsupplier;

import static de.caritas.cob.userservice.api.helper.EmailNotificationTemplates.TEMPLATE_ASSIGN_ENQUIRY_NOTIFICATION;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import de.caritas.cob.userservice.api.helper.UsernameTranscoder;
import de.caritas.cob.userservice.api.repository.consultant.Consultant;
import de.caritas.cob.userservice.api.service.ConsultantService;
import de.caritas.cob.userservice.api.service.LogService;
import de.caritas.cob.userservice.mailservice.generated.web.model.MailDTO;
import de.caritas.cob.userservice.mailservice.generated.web.model.TemplateDataDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Supplier to provide mails to be sent when an enquiry is assigned.
 */
@Service
@RequiredArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AssignEnquiryEmailSupplier implements EmailSupplier {

  @Setter
  private Consultant receiverConsultant;

  @Setter
  private String senderUserId;

  @Setter
  private String askerUserName;

  @Value("${app.base.url}")
  private String applicationBaseUrl;

  private final ConsultantService consultantService;

  @Value("${multitenancy.enabled}")
  private boolean multiTenancyEnabled;

  private final TenantTemplateSupplier tenantTemplateSupplier;


  /**
   * Generates the enquiry notification mail sent to regarding consultant.
   *
   * @return a list of the generated {@link MailDTO}
   */
  @Override
  @Transactional
  public List<MailDTO> generateEmails() {
    if (isReceiverConsultantValid()) {
      return buildAssignEnquiryMailWithValidReceiver();
    }
    LogService.logEmailNotificationFacadeError(String.format(
        "Error while sending assign message notification: Receiver consultant with id %s is null or doesn't have an email address.",
        nonNull(receiverConsultant) ? receiverConsultant.getId() : "unknown"));
    return emptyList();
  }

  private boolean isReceiverConsultantValid() {
    return nonNull(receiverConsultant) && isNotBlank(receiverConsultant.getEmail());
  }

  private List<MailDTO> buildAssignEnquiryMailWithValidReceiver() {

    Optional<Consultant> senderConsultant = consultantService.getConsultant(senderUserId);
    if (senderConsultant.isPresent()) {
      return singletonList(buildMailDtoForAssignEnquiryNotification(
          receiverConsultant.getEmail(),
          senderConsultant.get().getFullName(),
          receiverConsultant.getFullName(),
          new UsernameTranscoder().decodeUsername(askerUserName)));
    }
    LogService.logEmailNotificationFacadeError(String.format(
        "Error while sending assign message notification: Sender consultant with id %s could not be found in database.",
        senderUserId));

    return emptyList();
  }

  private MailDTO buildMailDtoForAssignEnquiryNotification(String email, String nameSender,
      String nameRecipient, String nameUser) {
    var templateAttributes = new ArrayList<TemplateDataDTO>();
    templateAttributes.add(new TemplateDataDTO().key("name_sender").value(nameSender));
    templateAttributes.add(new TemplateDataDTO().key("name_recipient").value(nameRecipient));
    templateAttributes.add(new TemplateDataDTO().key("name_user").value(nameUser));

    if (!multiTenancyEnabled) {
      templateAttributes.add(new TemplateDataDTO().key("url").value(applicationBaseUrl));
    } else {
      templateAttributes.addAll(tenantTemplateSupplier.getTemplateAttributes());
    }

    return new MailDTO()
        .template(TEMPLATE_ASSIGN_ENQUIRY_NOTIFICATION)
        .email(email)
        .templateData(templateAttributes);
  }

}
