package demo;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.CustomerProtos.Customer.EmailAddress;

@RestController
public class CustomerRestController {
	@Autowired
	private CustomerRepository customerRepository;

	@RequestMapping("/customers/{id}")
	CustomerProtos.Customer customer(@PathVariable Integer id) {
		return this.customerRepository.findById(id);
	}

	// public static void main(String[] args) {
	// SpringApplication.run(DemoApplication.class, args);
	// }

	@Bean
	ProtobufHttpMessageConverter protobufHttpMessageConverter() {
		return new ProtobufHttpMessageConverter();
	}

	private CustomerProtos.Customer customer(int id, String f, String l, Collection<String> emails) {
		// Collection<CustomerProtos.Customer.EmailAddress> emailAddresses =
		// emails.stream().map(e ->
		// CustomerProtos.Customer.EmailAddress.newBuilder()
		// .setType(CustomerProtos.Customer.EmailType.PROFESSIONAL)
		// .setEmail(e).build())
		// .collect(Collectors.toList());
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
//		Map<Integer, CustomerProtos.Customer> customers = new ConcurrentHashMap<>();
//		// populate with some dummy data
//		Arrays.asList(customer(1, "Chris", "Richardson", Arrays.asList("crichardson@email.com")),
//				customer(2, "Josh", "Long", Arrays.asList("jlong@email.com")),
//				customer(3, "Matt", "Stine", Arrays.asList("mstine@email.com")),
//				customer(4, "Russ", "Miles", Arrays.asList("rmiles@email.com")));
//				// .forEach(c -> customers.put(c.getId(), c));

		return new CustomerRepositoryImpl(); 
//		{
//			public CustomerProtos.Customer findById(int id) {
//				return customers.get(id);
//			}
//		};
	}
}