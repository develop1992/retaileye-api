package edu.ttu.retaileye.services;

import edu.ttu.retaileye.dtos.EmployeeShiftDto;
import edu.ttu.retaileye.dtos.RecordingDto;
import edu.ttu.retaileye.dtos.IncidentDto;
import edu.ttu.retaileye.exceptions.InternalException;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final EmployeeServiceImpl employeeService;

    public void sendEmail(List<IncidentDto> savedIncidents) {
        var employee = savedIncidents.stream()
                .findFirst()
                .map(IncidentDto::getRecordingDto)
                .map(RecordingDto::getEmployeeShiftDto)
                .map(EmployeeShiftDto::getEmployeeDto)
                .orElse(null);

        var bodyCamera = savedIncidents.stream()
                .findFirst()
                .map(IncidentDto::getRecordingDto)
                .map(RecordingDto::getBodyCameraDto)
                .orElse(null);

        var manager = employeeService.getByRole("manager")
                .orElseThrow(() -> new InternalException("Manager not found"));

        if(StringUtils.isBlank(manager.getEmail())) {
            throw new InternalException("Manager email is not set");
        }

        try {
            // Prepare email content
            String subject = "New Incidents Detected";
            StringBuilder body = new StringBuilder(String.format(
                    "Dear %s,\n\nThe following incidents have been detected from %s, %s:\n\n",
                    manager.getFullName(),
                    bodyCamera != null ? String.format("body camera %s", bodyCamera.getSerialNumber()) : "an uploaded video",
                    employee != null ? String.format("currently is in use by employee %s", employee.getFullName()) : "analyzed recently"));

            for (IncidentDto incident : savedIncidents) {
                body.append("Incident: ").append(incident.getDescription()).append("\n");
                body.append("Timestamp: ").append(incident.getOccurrenceTime()).append("\n\n");
            }

            body.append("Please review the incidents and take necessary actions.\n\nBest regards,\nRetailEye");

            // Create email message
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("afsharbahramp@yahoo.com");
            message.setTo(manager.getEmail());
            message.setSubject(subject);
            message.setText(body.toString());

            log.info("Sending email notification to manager: {} regarding incidents", manager.getFullName());

            // Send email
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new InternalException("Error sending email notification", e);
        }
    }

}
