package com.sebastiansokolowski.auctionhunter.model

import com.sebastiansokolowski.auctionhunter.config.AuctionHunterProp
import com.sebastiansokolowski.auctionhunter.entity.Offer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import javax.mail.internet.MimeMessage

class MailSenderModel {

    @Autowired
    private lateinit var javaMailSender: JavaMailSender

    @Autowired
    private lateinit var templateEngine: TemplateEngine

    @Autowired
    private lateinit var auctionHunterProp: AuctionHunterProp

    fun sendMail(offers: List<Offer>) {
        val context = Context()
        context.setVariable("offers", offers)
        context.setVariable("allegroBaseUrl", auctionHunterProp.allegroShowItemBaseUrl)

        val mail: MimeMessage = javaMailSender.createMimeMessage()
        val body = templateEngine.process("email/auctions", context)

        val helper = MimeMessageHelper(mail, true)
        helper.setTo(auctionHunterProp.email)
        helper.setSubject("Auction Hunter")
        helper.setText(body, true)

        javaMailSender.send(mail)
    }
}