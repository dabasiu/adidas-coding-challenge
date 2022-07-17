/**
 * 
 */
package com.adidas.subscription.dto;


import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.adidas.subscription.util.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Telmo
 *
 */
@Data
@Valid
@ApiModel(value = "Subscription", description = "subscription details")
public class SubscriptionApiDTO {

	@NotBlank
	@Pattern(regexp = "^(.+)@(.+)$")
	@ApiModelProperty(required = true, value = "email to receive the newsletter", example = "name@email.com")
	private String email;
	
	@ApiModelProperty(value = "your first name", example = "John Doe")
	private String firstName;

	@ApiModelProperty(value = "your gender", example = "MALE")
	private Gender gender;

	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@ApiModelProperty(required = true, value = "the date of birth", example = "1988-10-11")
	private Date birthDate;

	@NotNull
	@AssertTrue
	@ApiModelProperty(required = true, value = "flag for consent", example = "true")
	private boolean consent;

	@NotNull(message = "bla")
	@ApiModelProperty(required = true)
	private Long newsletterId;
	
}
