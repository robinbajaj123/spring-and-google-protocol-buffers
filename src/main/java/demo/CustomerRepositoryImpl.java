package demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import demo.CustomerProtos.Customer;

public class CustomerRepositoryImpl implements CustomerRepository {

	public Customer findById(int id) {

		Map<Integer, CustomerProtos.Customer> customers = new ConcurrentHashMap<Integer, Customer>();
		List<Customer> customerList = Arrays.asList(
				prepareCustomer(1, "Chris", "Richardson", Arrays.asList("crichardson@email.com")),
				prepareCustomer(2, "Josh", "Long", Arrays.asList("jlong@email.com")),
				prepareCustomer(3, "Matt", "Stine", Arrays.asList("mstine@email.com")),
				prepareCustomer(4, "Russ", "Miles", Arrays.asList("rmiles@email.com")));
		for (Customer customer : customerList) {
			customers.put(customer.getId(), customer);
		}
		// populate with some dummy data
		// Arrays.asList(
		// customer(1, "Chris", "Richardson",
		// Arrays.asList("crichardson@email.com")),
		// customer(2, "Josh", "Long", Arrays.asList("jlong@email.com")),
		// customer(3, "Matt", "Stine", Arrays.asList("mstine@email.com")),
		// customer(4, "Russ", "Miles", Arrays.asList("rmiles@email.com"))
		// ).forEach(c -> customers.put(c.getId(), c));
		// our lambda just gets forwarded to Map#get(Integer)
		//return customer.get;
		return customers.get(id);
	}
	
	public CustomerProtos.Customer prepareCustomer(int id, String f, String l, Collection<String> emails) {
		ArrayList<CustomerProtos.Customer.EmailAddress> emailAddresses = new ArrayList<CustomerProtos.Customer.EmailAddress>();
		//
		for (String email : emails) {
			emailAddresses.add(CustomerProtos.Customer.EmailAddress.newBuilder()
					.setType(CustomerProtos.Customer.EmailType.PROFESSIONAL).setEmail(email).build());
		}
		return CustomerProtos.Customer.newBuilder().setFirstName(f).setLastName(l).setId(id).addAllEmail(emailAddresses)
				.build();
	}

}
