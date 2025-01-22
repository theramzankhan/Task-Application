package com.example.demo.UserController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import com.example.demo.service.TaskPdfReportService;
import com.itextpdf.text.DocumentException;

@RestController
@RequestMapping("/report")
public class TaskReportController {
	
	@Autowired
	private TaskPdfReportService taskPdfReportService;
	
	// Endpoint to download all tasks as a PDF
	@GetMapping("/tasks/pdf")
	public ResponseEntity<byte[]> getPdfReport() throws DocumentException, IOException {
		ByteArrayOutputStream outputStream = taskPdfReportService.getAllTasksPdfReport();
		byte[] pdfBytes = outputStream.toByteArray();
		
		// Set HTTP headers for PDF file download
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=tasks_report.pdf");
		
		// Return PDF as a byte array with headers to trigger download
		return new ResponseEntity<byte[]>(pdfBytes, headers, HttpStatus.OK);
	}

}
