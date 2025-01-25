package com.example.demo.UserController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.SearchService;

@RestController
@RequestMapping("/api/search")
public class SearchController {
	
	@Autowired
	private SearchService searchService;
	
	@GetMapping
	public ResponseEntity<Map<String, Object>> search(@RequestParam("keyword") String keyword) {
		Map<String, Object> results = searchService.search(keyword);
		return ResponseEntity.ok(results);
	}

}
