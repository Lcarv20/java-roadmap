package com.example.demo;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication // same as @Configuration, @EnableAutoConfiguration and @ComponenScan
@RequestMapping("/api/v1/customers")
public class SbBasicsApplication {

  CustomerRepository customerRepository;
  

	public static void main(String[] args) {
		SpringApplication.run(SbBasicsApplication.class, args);
	}

  public SbBasicsApplication(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  // GET request
  @GetMapping
  public List<Customer> getCustomers() {
    return customerRepository.findAll();
  }

  // POST request
  record CustomerRequest(String name, String email, Integer age) {
  }

  @PostMapping
  public void addCustomer(@RequestBody CustomerRequest request) {
    Customer newCustmer = new Customer();
    newCustmer.setAge(request.age());
    newCustmer.setName(request.name());
    newCustmer.setEmail(request.email());

    customerRepository.save(newCustmer);
  }

  // DELETE request
  @DeleteMapping("{customerId}")
  public void deleteCustomer(@PathVariable("customerId") Integer id) {
    customerRepository.deleteById(id);
  }

  // PUT request
  @PutMapping("{customerId}")
  public void updateCustomer(
      @PathVariable("customerId") Integer id,
      @RequestBody Customer customerUpdate) {

    Customer customerRef = customerRepository.getReferenceById(id);

    if (customerUpdate.getName() != null)
      customerRef.setName(customerUpdate.getName());
    if (customerUpdate.getEmail() != null)
      customerRef.setEmail(customerUpdate.getEmail());
    if (customerUpdate.getAge() != null)
      customerRef.setAge(customerUpdate.getAge());

    customerRepository.save(customerRef);
  }

}
