/**
 * 
 */
package com.adidas.subscription.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import com.adidas.subscription.util.Gender;

/**
 * @author Telmo
 *
 */
@Entity
@DynamicInsert
@Table(name = "subscription")
@SequenceGenerator(name="subs_seq", sequenceName="subs_seq", allocationSize=1, initialValue = 1)
public class Subscription extends BaseEntity<Long>{
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "subs_seq")
	@Column(unique=true, nullable=false)
	private Long id;

	private String email;

	@Column(name="first_name")
	private String firstName;

	private Gender gender;

	@Column(name="birth_date")
	private Date birthDate;

	private boolean consent;

	@Column(name="newsletter_id")
	private Long newsletterId;

	@Column(name="mail_sent")
	private boolean mailSent;

	@Column(name="mail_sent_when")
	private Date mailSentWhen;

	@Override
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public boolean isConsent() {
		return consent;
	}

	public void setConsent(boolean consent) {
		this.consent = consent;
	}

	public Long getNewsletterId() {
		return newsletterId;
	}

	public void setNewsletterId(Long newsletterId) {
		this.newsletterId = newsletterId;
	}

	public boolean isMailSent() {
		return mailSent;
	}

	public void setMailSent(boolean mailSent) {
		this.mailSent = mailSent;
	}

	public Date getMailSentWhen() {
		return mailSentWhen;
	}

	public void setMailSentWhen(Date mailSentWhen) {
		this.mailSentWhen = mailSentWhen;
	}

}
