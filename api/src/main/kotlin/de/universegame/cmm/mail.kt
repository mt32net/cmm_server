package de.universegame.cmm

import org.apache.commons.mail.DefaultAuthenticator
import org.apache.commons.mail.Email
import org.apache.commons.mail.SimpleEmail


fun sendEMail(from: String, to: String, subject: String, msg: String) {
    val email: Email = SimpleEmail()
    email.hostName = mailConfig.mailServerURL
    email.setSmtpPort(mailConfig.mailServerPort)
    email.setAuthenticator(DefaultAuthenticator(mailConfig.mailServerUsername, mailConfig.mailServerUserPwd))
    email.isSSLOnConnect = true
    email.setFrom(from)
    email.subject = subject
    email.setMsg(msg)
    email.addTo(to)
    email.send()
}