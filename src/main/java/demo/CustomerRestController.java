package demo;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import demo.CustomerProtos.Customer.EmailAddress;

@RestController
@RequestMapping("/ws")
@EnableWebMvc
public class CustomerRestController {
	@Autowired
	private CustomerRepository customerRepository;
	//@RequestMapping(value="/customers/{id}", method = RequestMethod.GET, produces="application/x-protobuf")
    @RequestMapping(value="/customers/{id}", method = RequestMethod.GET)
	public CustomerProtos.Customer customer(@PathVariable Integer id) {
		return this.customerRepository.findById(id);
	}

	@Bean
	ProtobufHttpMessageConverter protobufHttpMessageConverter() {
		return new ProtobufHttpMessageConverter();
	}

	private CustomerProtos.Customer customer(int id, String f, String l, Collection<String> emails) {
		String emailAddressString = "defaultEmail@address.com";
		for (String email : emails) {
			emailAddressString = email;
			break;
		}

		EmailAddress emailAddress = CustomerProtos.Customer.EmailAddress.newBuilder()
				.setType(CustomerProtos.Customer.EmailType.PROFESSIONAL).setEmail(emailAddressString).build();

		return CustomerProtos.Customer.newBuilder().setFirstName(f).setLastName(l).setId(id).addEmail(emailAddress)
				// .addAllEmail(emailAddresses)
				.build();
	}

	@Bean
	CustomerRepository customerRepository() {
		return new CustomerRepositoryImpl(); 
	}
}