package com.best.electronics.report.sender;

public class SendReportDelegator {

    public ISendReport identifySender(String channel)
    {
        if (channel == null || channel.isEmpty())
            return null;
        switch (channel) {
            case "SMTP":
                return new SmtpEmailSendReport();
            default:
                throw new IllegalArgumentException("Unknown channel "+channel);
        }
    }
}
