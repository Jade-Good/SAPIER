package com.esfp.sapaier.domain.request.service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.springframework.stereotype.Service;

import com.esfp.sapaier.domain.request.repository.Entity.Collection;
import com.esfp.sapaier.domain.request.repository.Entity.Request;
import com.esfp.sapaier.domain.request.repository.RequestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RequestService {
	private final RequestRepository requestRepository;

	public void createRequest() {
		Request request1 = new Request();
		request1.setRequestName("요청1");
		Request request2 = new Request();
		request2.setRequestName("요청2");
		Request request3 = new Request();
		request3.setRequestName("요청3");

		// List<Request> list = new ArrayList<>();
		// list.add(request1);
		// list.add(request2);
		// list.add(request3);

		TreeSet<Request> set = new TreeSet<>();
		set.add(request1);
		set.add(request2);
		set.add(request3);

		Collection collection = new Collection();
		collection.setApiList(set);

		requestRepository.save(collection);
	}
}
